package br.com.badiale.moviethumbs.video;

import org.joda.time.Duration;

import java.awt.*;

public class Frame {
    private final Duration timestamp;
    private final Image image;

    public Frame(Duration timestamp, Image image) {
        this.timestamp = timestamp;
        this.image = image;
    }

    public Duration getTimestamp() {
        return timestamp;
    }

    public Image getImage() {
        return image;
    }
}
