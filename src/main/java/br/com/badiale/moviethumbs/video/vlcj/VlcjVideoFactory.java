package br.com.badiale.moviethumbs.video.vlcj;

import br.com.badiale.moviethumbs.video.Video;
import br.com.badiale.moviethumbs.video.VideoFactory;
import com.google.common.base.Strings;
import com.sun.jna.NativeLibrary;
import io.reactivex.Observable;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.media.callback.seekable.RandomAccessFileMedia;
import uk.co.caprica.vlcj.runtime.RuntimeUtil;

import java.nio.file.Path;

public class VlcjVideoFactory implements VideoFactory {
    private MediaPlayer mediaPlayer;

    @Override
    public void start() {
        if (Strings.isNullOrEmpty(System.getenv("VLC_PLUGIN_PATH"))) {
            throw new RuntimeException("You must define the VLC_PLUGIN_PATH env variable");
        }
        NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "C:\\Program Files\\VideoLAN\\VLC");
        mediaPlayer = new MediaPlayerFactory().newHeadlessMediaPlayer();
    }

    @Override
    public Video create(Path path) {
        return new VlcjVideo(path, mediaPlayer);
    }

    @Override
    public void clear() {
        mediaPlayer.release();
    }
}
