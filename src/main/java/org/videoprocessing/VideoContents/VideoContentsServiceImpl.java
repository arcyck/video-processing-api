package org.videoprocessing.VideoContents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.videoprocessing.utils.FfmpegUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VideoContentsServiceImpl implements VideoContentsService {
    private final VideoContentsRepository videoContentsRepository;

    @Value("${spring.servlet.multipart.location}")
    private String uploadDirectory;

    public VideoContentsServiceImpl(VideoContentsRepository videoContentsRepository) {
        this.videoContentsRepository = videoContentsRepository;
    }

    @Override
    public VideoContents uploadFile(MultipartFile file) throws IOException {
        File file1 = new File(uploadDirectory + file.getOriginalFilename());
        file.transferTo(file1);
        Path targetPath = Paths.get(file1.getPath());

        String videoDuration = FfmpegUtils.getVideoDuration(targetPath);
        String pathUrl = fileToUrl(file1.toPath());
        VideoContents videoContents = new VideoContents(
                file1.getName(),
                LocalDateTime.now(),
                pathUrl,
                videoDuration
        );
        return videoContentsRepository.save(videoContents);
    }

    @Override
    public String getVideoThumbnail(UUID id) {
        String videoUrl = getVideoUrlById(id);
        Path thumbnailPath = FfmpegUtils.generateThumbnail(videoUrl);
        return fileToUrl(thumbnailPath);
    }

    @Override
    public List<VideoContents> getAllVideos() {
        return videoContentsRepository.findAll();
    }

    @Override
    public VideoContents getVideoContentById(UUID id) {
        return videoContentsRepository.findById(id).orElseThrow();
    }

    @Override
    public String getVideoUrlById(UUID id) {
        return getVideoContentById(id).getFileUrl();
    }

    @Override
    public String changeVideoResolution(UUID id, int height, int width) {
        String videoUrl = getVideoUrlById(id);
        Path videoPath = FfmpegUtils.adjustVideoResolution(videoUrl, height, width);
        return fileToUrl(videoPath);
    }

    private String fileToUrl(Path filePath) {
        String uriPath = null;
        try{
            uriPath = filePath.toUri()
                    .toURL()
                    .toString();
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL");
        }
        assert uriPath != null;
        return uriPath.replaceFirst("file:" + uploadDirectory,"http://localhost:8080/");
    }

}
