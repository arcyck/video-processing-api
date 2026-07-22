package org.videoprocessing.VideoContents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VideoContentsRepository extends JpaRepository<VideoContents, UUID> {
}
