package br.com.badiale.moviethumbs;

import br.com.badiale.moviethumbs.video.VideoFactory;

import java.nio.file.Path;

public class ThumbnailConfiguration {
    private final Path path;
    private final String format;
    private final String suffix;
    private final VideoFactory videoFactory;
    private final int columns;
    private final int lines;
    private final int thumbsWidth;

    ThumbnailConfiguration(Path path, String format, String suffix, VideoFactory videoFactory, int columns, int lines, int thumbsWidth) {
        this.path = path;
        this.format = format;
        this.suffix = suffix;
        this.videoFactory = videoFactory;
        this.columns = columns;
        this.lines = lines;
        this.thumbsWidth = thumbsWidth;
    }

    public Path getPath() {
        return path;
    }

    public String getFormat() {
        return format;
    }

    public String getSuffix() {
        return suffix;
    }

    public VideoFactory getVideoFactory() {
        return videoFactory;
    }

    public int getColumns() {
        return columns;
    }

    public int getLines() {
        return lines;
    }

    public int getThumbsWidth() {
        return thumbsWidth;
    }

    public String getFullSuffix() {
        return getSuffix() + "." + getFormat();
    }
}
