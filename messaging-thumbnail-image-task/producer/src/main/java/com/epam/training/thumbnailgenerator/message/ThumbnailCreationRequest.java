package com.epam.training.thumbnailgenerator.message;

import lombok.Getter;

import java.time.OffsetDateTime;

@Getter
public class ThumbnailCreationRequest {
    private String imageFilePath;
    private String thumbnailFilePath;
    private OffsetDateTime createdAt;

    public ThumbnailCreationRequest(String imageFilePath, String thumbnailFilePath, OffsetDateTime createdAt) {
        this.imageFilePath = imageFilePath;
        this.thumbnailFilePath = thumbnailFilePath;
        this.createdAt = createdAt;
    }

    public void setImageFilePath(String imageFilePath) {
        this.imageFilePath = imageFilePath;
    }

    public void setThumbnailFilePath(String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
