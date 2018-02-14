package br.com.badiale.moviethumbs;

import br.com.badiale.moviethumbs.video.VideoFactories;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;

public class Main {

    private static class Arguments {
        @Argument(required = true, usage = "The path to be scanned")
        private File path;
        @Option(name = "-f", aliases = "--format", usage = "Defines which format the thumbnails will be")
        private String format;
        @Option(name = "-s", aliases = "--suffix", usage = "Defines a suffix to be used in the thumbnail name")
        private String suffix;
        @Option(name = "-vf", aliases = "--video-factory", usage = "Defines which library to use when decoding videos. Available options: jcodec, vlcj")
        private VideoFactories videoFactory;
        @Option(name = "-c", aliases = "--columns", usage = "How many columns will be on the resulting thumbnails")
        private Integer columns;
        @Option(name = "-l", aliases = "--lines", usage = "How many lines will be on the resulting thumbnails")
        private Integer lines;
        @Option(name = "-w", aliases = "--width", usage = "Width of the thumbnails")
        private Integer thumbsWidth;
    }

    public static void main(String[] args) {
        Arguments arguments = new Arguments();
        CmdLineParser parser = new CmdLineParser(arguments);
        try {
            parser.parseArgument(args);
            run(arguments);
        } catch (CmdLineException e) {
            // handling of wrong arguments
            System.err.println(e.getMessage());
            parser.printUsage(System.err);
        }
    }

    private static void run(Arguments arguments) {
        ThumbnailConfiguration configuration = new ThumbnailConfigurationBuilder(arguments.path.toPath())
                .setFormat(arguments.format)
                .setSuffix(arguments.suffix)
                .setVideoFactory(arguments.videoFactory)
                .setColumns(arguments.columns)
                .setLines(arguments.lines)
                .setThumbsWidth(arguments.thumbsWidth)
                .build();
        ThumbnailGenerator generator = new ThumbnailGenerator(configuration);
        generator.generate();
        System.out.println("Success");
    }
}