package br.com.badiale.moviethumbs.video;

import java.awt.*;
import java.io.Closeable;

public interface Video extends Closeable {
    Dimension getDimension();

    long getDuration();

    Frame getFrameAtPosition(long position);
}
