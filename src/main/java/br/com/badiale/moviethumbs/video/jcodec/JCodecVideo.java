package br.com.badiale.moviethumbs.video.jcodec;

import br.com.badiale.moviethumbs.video.Frame;
import br.com.badiale.moviethumbs.video.Video;
import org.jcodec.api.FrameGrab;
import org.jcodec.api.PictureWithMetadata;
import org.jcodec.common.DemuxerTrackMeta;
import org.jcodec.common.io.NIOUtils;
import org.jcodec.common.model.Size;
import org.jcodec.scale.AWTUtil;
import org.joda.time.Duration;

import java.awt.*;
import java.nio.file.Path;

class JCodecVideo implements Video {

    private final FrameGrab frameGrab;
    private final DemuxerTrackMeta metadata;

    JCodecVideo(Path path) {
        try {
            frameGrab = FrameGrab.createFrameGrab(NIOUtils.readableChannel(path.toFile()));
            metadata = frameGrab.getVideoTrack().getMeta();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Dimension getDimension() {
        Size size = metadata.getVideoCodecMeta().getSize();
        return new Dimension(size.getWidth(), size.getHeight());
    }

    public long getDuration() {
        return metadata.getTotalFrames();
    }

    public Frame getFrameAtPosition(long frame) {
        try {
            frameGrab.seekToFrameSloppy((int) frame);
            PictureWithMetadata frameWithMetadata = frameGrab.getNativeFrameWithMetadata();
            return new Frame(
                    Duration.millis((long) (frameWithMetadata.getTimestamp() * 1000)),
                    AWTUtil.toBufferedImage(frameWithMetadata.getPicture()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
    }
}
