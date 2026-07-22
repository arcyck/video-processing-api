package org.videoprocessing.VideoContents;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public interface VideoContentsService {
    VideoContents uploadFile(MultipartFile file) throws IOException;
    String getVideoThumbnail(UUID id);
    List<VideoContents> getAllVideos();
    VideoContents getVideoContentById(UUID id);
    String getVideoUrlById(UUID id);
    String changeVideoResolution(UUID id, int height, int width);

}
