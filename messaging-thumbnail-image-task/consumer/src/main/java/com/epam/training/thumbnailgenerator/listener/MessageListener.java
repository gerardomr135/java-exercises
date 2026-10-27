package com.epam.training.thumbnailgenerator.listener;

import java.io.File;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.epam.training.thumbnailgenerator.config.ThumbnailProperties;
import com.epam.training.thumbnailgenerator.message.ThumbnailCreationRequest;

import net.coobird.thumbnailator.Thumbnails;

@Component
public class MessageListener {
    private final ThumbnailProperties thumbnailProperties;

    public MessageListener(ThumbnailProperties thumbnailProperties) {
        this.thumbnailProperties = thumbnailProperties;
    }

    @RabbitListener(queues = "${messaging-config.queueName}")
    public void processImage(ThumbnailCreationRequest thumbnailCreationRequest) {
        File inputFile = new File(thumbnailCreationRequest.getImageFilePath());

        File outputFile = new File(thumbnailCreationRequest.getThumbnailFilePath());

        outputFile.getParentFile().mkdirs();
        try {
            Thumbnails.of(inputFile)
                    .size(thumbnailProperties.getWidth(), thumbnailProperties.getHeight())
                    .keepAspectRatio(true)
                    .toFile(outputFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
