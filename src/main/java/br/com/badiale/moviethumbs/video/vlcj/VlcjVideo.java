package br.com.badiale.moviethumbs.video.vlcj;

import br.com.badiale.moviethumbs.video.Frame;
import br.com.badiale.moviethumbs.video.Video;
import io.reactivex.Observable;
import org.joda.time.Duration;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.media.callback.seekable.RandomAccessFileMedia;

import java.awt.*;
import java.nio.file.Path;

class VlcjVideo implements Video {
    private final MediaPlayer mediaPlayer;

    VlcjVideo(Path path, MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        Observable.create(emitter -> {
            mediaPlayer.addMediaPlayerEventListener(new MediaPlayerEventAdapter() {
                @Override
                public void videoOutput(MediaPlayer mediaPlayer, int newCount) {
                    mediaPlayer.pause();
                    emitter.onComplete();
                }
            });
            mediaPlayer.startMedia(new RandomAccessFileMedia(path.toFile()));
        }).blockingSubscribe();
    }

    @Override
    public Dimension getDimension() {
        return mediaPlayer.getVideoDimension();
    }

    @Override
    public long getDuration() {
        return mediaPlayer.getLength();
    }

    @Override
    public Frame getFrameAtPosition(long position) {
        mediaPlayer.setTime(position);
        return new Frame(new Duration(position), mediaPlayer.getSnapshot());
    }

    @Override
    public void close() {
    }
}
