package br.com.badiale.moviethumbs;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class ThumbnailGenerator {
    private ThumbnailConfiguration configuration;

    public ThumbnailGenerator(ThumbnailConfiguration configuration) {
        this.configuration = configuration;
    }

    public void generate() {
        try {
            createThumbs(configuration.getPath());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createThumbs(Path path) throws Exception {
        configuration.getVideoFactory().start();
        try (Stream<Path> list = Files.walk(path)) {
            list
                    .filter(this::isVideoFile)
                    .peek(System.out::println)
                    .map(this::createThumbsForFile)
                    .forEach(this::saveThumbnail);
        }
        configuration.getVideoFactory().clear();
    }

    private void saveThumbnail(VideoThumbnail thumbnail) {
        try {
            ImageIO.write(thumbnail.toImage(), configuration.getFormat(), new File(thumbnail.getPath() + configuration.getFullSuffix()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isVideoFile(Path path) {
        String lowerCasePath = path.toString().toLowerCase();
        return Files.isRegularFile(path)
                && (lowerCasePath.endsWith(".mp4")
                || lowerCasePath.endsWith(".mkv"));
    }

    private VideoThumbnail createThumbsForFile(Path path) {
        return new VideoThumbnail(
                path,
                configuration.getVideoFactory(),
                configuration.getColumns(),
                configuration.getLines(),
                configuration.getThumbsWidth());
    }
}
