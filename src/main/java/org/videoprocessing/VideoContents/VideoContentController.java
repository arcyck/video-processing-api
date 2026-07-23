package org.videoprocessing.VideoContents;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/video")
public class VideoContentController {

    private final VideoContentsService videoContentsService;

    public VideoContentController(VideoContentsService videoContentsService) {
        this.videoContentsService = videoContentsService;
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Tag(name = "Upload", description = "Uploads a file")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            VideoContents videoContents = videoContentsService.uploadFile(file);
            return ResponseEntity.ok(videoContents);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/thumbnail")
    @Tag(name = "Thumbnail", description = "Gets the thumbnail of the video. Hardcoded at 1 second of the video.")
    public ResponseEntity<?> getVideoThumbnail(@PathVariable String id) {
        String imageUrl = videoContentsService.getVideoThumbnail(UUID.fromString(id));
        Map<String, String> map = new HashMap<>();
        map.put("thumbnail_url", imageUrl);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{id}/change-scale")
    @Tag(name = "Video scale", description = "Changes the video scale by Id")
    public ResponseEntity<?> adjustVideoScale(@PathVariable String id, @Valid @RequestBody VideoScaleDTO videoScaleDTO) {
        String videoUrl = videoContentsService.changeVideoResolution(UUID.fromString(id),
                videoScaleDTO.height(), videoScaleDTO.width());
        Map<String, String> map = new HashMap<>();
        map.put("video_url", videoUrl);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/all")
    @Tag(name = "All", description = "Gets all of the uploaded videos")
    public ResponseEntity <List<VideoContents>> getAllVideos() {
        return ResponseEntity.ok(videoContentsService.getAllVideos());
    }
}
