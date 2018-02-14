package br.com.badiale.moviethumbs;

import br.com.badiale.moviethumbs.video.VideoFactories;
import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.nio.file.Path;

public class ThumbnailConfigurationBuilder {
    private Path path;
    private String format;
    private String suffix;
    private VideoFactories videoFactory;
    private Integer columns;
    private Integer lines;
    private Integer thumbsWidth;

    public ThumbnailConfigurationBuilder(Path path) {
        this.path = Preconditions.checkNotNull(path);
    }

    public ThumbnailConfigurationBuilder setVideoFactory(VideoFactories videoFactory) {
        this.videoFactory = videoFactory;
        return this;
    }

    public ThumbnailConfigurationBuilder setFormat(String format) {
        this.format = format;
        return this;
    }

    public ThumbnailConfigurationBuilder setSuffix(String suffix) {
        this.suffix = suffix;
        return this;
    }

    public ThumbnailConfigurationBuilder setColumns(Integer columns) {
        this.columns = columns;
        return this;
    }

    public ThumbnailConfigurationBuilder setLines(Integer lines) {
        this.lines = lines;
        return this;
    }

    public ThumbnailConfigurationBuilder setThumbsWidth(Integer thumbsWidth) {
        this.thumbsWidth = thumbsWidth;
        return this;
    }

    public ThumbnailConfiguration build() {
        Integer columns = MoreObjects.firstNonNull(this.columns, 3);
        return new ThumbnailConfiguration(
                path,
                MoreObjects.firstNonNull(format, "png"),
                MoreObjects.firstNonNull(suffix, "_preview"),
                MoreObjects.firstNonNull(videoFactory, VideoFactories.jcodec).getFactory(),
                columns,
                MoreObjects.firstNonNull(this.lines, 3),
                MoreObjects.firstNonNull(thumbsWidth, 1500 / columns));
    }
}