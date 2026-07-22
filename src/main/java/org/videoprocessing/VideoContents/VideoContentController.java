package org.videoprocessing.VideoContents;

import jakarta.validation.Valid;
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

    @PostMapping("/upload")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            VideoContents videoContents = videoContentsService.uploadFile(file);
            return ResponseEntity.ok(videoContents);

        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}/thumbnail")
    public ResponseEntity<?> getVideoThumbnail(@PathVariable String id) {
        String imageUrl = videoContentsService.getVideoThumbnail(UUID.fromString(id));
        Map<String, String> map = new HashMap<>();
        map.put("thumbnail_url", imageUrl);
        return ResponseEntity.ok(map);
    }

    @PostMapping("/{id}/change-scale")
    public ResponseEntity<?> adjustVideoScale(@PathVariable String id, @Valid @RequestBody VideoScaleDTO videoScaleDTO) {
        String videoUrl = videoContentsService.changeVideoResolution(UUID.fromString(id),
                videoScaleDTO.height(), videoScaleDTO.width());
        Map<String, String> map = new HashMap<>();
        map.put("video_url", videoUrl);
        return ResponseEntity.ok(map);
    }

    @GetMapping("/all")
    public ResponseEntity <List<VideoContents>> getAllVideos() {
        return ResponseEntity.ok(videoContentsService.getAllVideos());
    }
}
