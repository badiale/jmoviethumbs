package br.com.badiale.moviethumbs.video;

import java.nio.file.Path;

public interface VideoFactory {
    void start();

    Video create(Path path);

    void clear();
}
