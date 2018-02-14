package br.com.badiale.moviethumbs.video.jcodec;

import br.com.badiale.moviethumbs.video.Video;
import br.com.badiale.moviethumbs.video.VideoFactory;

import java.nio.file.Path;

public class JCodecVideoFactory implements VideoFactory {

    @Override
    public void start() {
    }

    @Override
    public Video create(Path path) {
        return new JCodecVideo(path);
    }

    @Override
    public void clear() {
    }
}
