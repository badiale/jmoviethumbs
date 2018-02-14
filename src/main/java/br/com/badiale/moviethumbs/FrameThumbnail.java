package br.com.badiale.moviethumbs;

import br.com.badiale.moviethumbs.video.Frame;
import org.joda.time.Duration;
import org.joda.time.format.PeriodFormatterBuilder;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FrameThumbnail {
    private static final Font FONT = new Font("sans serif", Font.PLAIN, 20);

    private Frame frame;
    private Dimension dimension;

    public FrameThumbnail(Frame frame, Dimension dimension) {
        this.frame = frame;
        this.dimension = dimension;
    }

    public BufferedImage toImage() {
        BufferedImage result = new BufferedImage((int) dimension.getWidth(), (int) dimension.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = result.createGraphics();
        Image frameImage = frame.getImage().getScaledInstance((int) dimension.getWidth(), (int) dimension.getHeight(), Image.SCALE_SMOOTH);
        graphics.drawImage(frameImage, 0, 0, null);
        drawFrameTimestamp(graphics, frame.getTimestamp(), (int) dimension.getHeight());
        return result;
    }

    private static void drawFrameTimestamp(Graphics2D graphics, Duration second, int height) {
        graphics.setFont(FONT);
        graphics.drawString(
                new PeriodFormatterBuilder()
                        .appendMinutes()
                        .appendSuffix("m", "m")
                        .appendSeparator(" ")
                        .appendSeconds()
                        .appendSuffix("s", "s")
                        .appendSeparator(" ")
                        .appendMillis3Digit()
                        .appendSuffix("ms", "ms")
                        .toFormatter()
                        .print(second.toPeriod()),
                10,
                height - FONT.getSize() - 10);
    }
}
