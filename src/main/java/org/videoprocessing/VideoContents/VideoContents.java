package org.videoprocessing.VideoContents;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

import static jakarta.persistence.FetchType.LAZY;

@Entity
public class VideoContents {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="file_name")
    private String fileName;

    @Column(name="time_uploaded")
    @CreationTimestamp
    private LocalDateTime timeUploaded;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name="duration")
    private String duration;

    public VideoContents() {}

    public VideoContents(String fileName,
                         LocalDateTime timeUploaded, String fileUrl, String duration) {
        this.fileName = fileName;
        this.timeUploaded = timeUploaded;
        this.fileUrl = fileUrl;
        this.duration = duration;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public LocalDateTime getTimeUploaded() {
        return timeUploaded;
    }

    public void setTimeUploaded(LocalDateTime timeUploaded) {
        this.timeUploaded = timeUploaded;
    }

    public String getFileUrl() { return fileUrl; }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

}
