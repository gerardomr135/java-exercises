package com.epam.training.thumbnailgenerator.image;

import com.epam.training.thumbnailgenerator.config.ImageDirectoryProperties;
import com.epam.training.thumbnailgenerator.message.ThumbnailCreationRequest;

import com.epam.training.thumbnailgenerator.sending.MessageSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.OffsetDateTime;
import java.util.stream.Stream;

@Component
public class ImageFileScanner implements CommandLineRunner {

    private static final Logger LOG = LoggerFactory.getLogger(ImageFileScanner.class);

    private final Path imageBaseDir;
    private final Path thumbnailBaseDir;
    private final MessageSender messageSender;

    public ImageFileScanner(ImageDirectoryProperties imageDirectoryProperties, MessageSender messageSender) {
        imageBaseDir = Paths.get(imageDirectoryProperties.getImageBaseDir());
        thumbnailBaseDir = Paths.get(imageDirectoryProperties.getThumbnailBaseDir());
        this.messageSender = messageSender;
    }

    public static void main(String[] args) {
        SpringApplication.run(ImageFileScanner.class, args);
    }

    @Override
    public void run(String... args) {
        try (Stream<Path> pathStream = Files.walk(imageBaseDir)) {
            pathStream
                    .filter(Files::isRegularFile)
                    .forEach(this::processImageFile);
        } catch (IOException e) {
            throw new RuntimeException("IO error happened when accessing basedir: " + imageBaseDir, e);
        }
    }

    private void processImageFile(Path imageFilePath) {
        Path thumbnailFilePath = getThumbnalFilePath(imageFilePath);
        LOG.debug("Image file: {}, thumbnail file: {}", imageFilePath, thumbnailFilePath);

        ThumbnailCreationRequest thumbnailCreationRequest = new ThumbnailCreationRequest(
                imageFilePath.toString(),
                thumbnailFilePath.toString(),
                OffsetDateTime.now());

        messageSender.sendMessage(thumbnailCreationRequest);
    }

    private Path getThumbnalFilePath(Path imageFile) {
        Path relative = imageBaseDir.relativize(imageFile);
        return thumbnailBaseDir.resolve(relative);
    }
}
