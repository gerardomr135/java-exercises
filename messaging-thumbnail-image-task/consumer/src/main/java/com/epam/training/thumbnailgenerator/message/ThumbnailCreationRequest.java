package com.epam.training.thumbnailgenerator.message;

import java.time.OffsetDateTime;

public class ThumbnailCreationRequest {
    private String imageFilePath;
    private String thumbnailFilePath;
    private OffsetDateTime createdAt;

    public ThumbnailCreationRequest() {
    }

    public ThumbnailCreationRequest(String imageFilePath, String thumbnailFilePath, OffsetDateTime createdAt) {
        this.imageFilePath = imageFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
        this.createdAt = createdAt;
    }

    public String getImageFilePath() {
        return imageFilePath;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
