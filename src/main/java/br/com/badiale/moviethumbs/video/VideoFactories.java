package br.com.badiale.moviethumbs.video;

import br.com.badiale.moviethumbs.video.jcodec.JCodecVideoFactory;
import br.com.badiale.moviethumbs.video.vlcj.VlcjVideoFactory;

public enum VideoFactories {
    jcodec(new JCodecVideoFactory()),
    vlcj(new VlcjVideoFactory());


    private VideoFactory videoFactory;

    VideoFactories(VideoFactory videoFactory) {
        this.videoFactory = videoFactory;
    }

    public VideoFactory getFactory() {
        return videoFactory;
    }
}
