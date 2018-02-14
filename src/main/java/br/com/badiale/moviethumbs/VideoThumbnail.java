package br.com.badiale.moviethumbs;

import br.com.badiale.moviethumbs.video.Frame;
import br.com.badiale.moviethumbs.video.Video;
import br.com.badiale.moviethumbs.video.VideoFactory;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.nio.file.Path;

public class VideoThumbnail {
    private static final Font FONT = new Font("sans serif", Font.PLAIN, 20);

    private final Path path;
    private final VideoFactory videoFactory;
    private final int columns;
    private final int lines;
    private final int thumbsWidth;
    private final int screenshots;

    VideoThumbnail(Path path, VideoFactory videoFactory, int columns, int lines, int thumbsWidth) {
        this.path = path;
        this.videoFactory = videoFactory;
        this.columns = columns;
        this.lines = lines;
        this.thumbsWidth = thumbsWidth;
        this.screenshots = columns * lines;
    }

    public Path getPath() {
        return path;
    }

    public RenderedImage toImage() {
        try (Video video = videoFactory.create(path)) {
            int height = getHeightKeepingAspectRatio(video.getDimension());
            long totalFrames = video.getDuration();
            long step = totalFrames / (screenshots + 1);
            BufferedImage result = createImage(height);
            Graphics2D graphics = result.createGraphics();
            for (int line = 0; line < lines; line++) {
                for (int column = 0; column < columns; column++) {
                    long frameNumber = (line * columns + column + 1) * step;
                    Frame frame = video.getFrameAtPosition(frameNumber);
                    FrameThumbnail frameThumbnail = new FrameThumbnail(frame, new Dimension(thumbsWidth, height));
                    graphics.drawImage(frameThumbnail.toImage(), column * thumbsWidth, line * height, null);
                }
            }
            drawFileName(graphics, path);
            return result;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private int getHeightKeepingAspectRatio(Dimension size) {
        return (int) size.getHeight() * thumbsWidth / (int) size.getWidth();
    }

    private BufferedImage createImage(int height) {
        return new BufferedImage(columns * thumbsWidth, lines * height, BufferedImage.TYPE_INT_RGB);
    }

    private void drawFileName(Graphics2D graphics, Path path) {
        graphics.setFont(FONT);
        graphics.drawString(path.getFileName().toString(), 10, FONT.getSize() + 10);
    }
}
