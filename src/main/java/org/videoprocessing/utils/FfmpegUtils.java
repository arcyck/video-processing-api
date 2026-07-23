package org.videoprocessing.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public final class FfmpegUtils {

    private FfmpegUtils() {}

    public static Path generateThumbnail(String fileUrl) {
        Process p = null;
        Path filePath = null;
        try {
            filePath = Files.createTempFile("output_", ".png");
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        try{
            assert filePath != null;
            p = new ProcessBuilder("ffmpeg", "-y","-i", fileUrl, "-ss", "00:00:01",
                        "-update","1", "-frames:v","1", filePath.toAbsolutePath().toString())
                    .start();
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        return filePath;
    }

    public static Path adjustVideoResolution(String fileUrl, int height, int width) {
        Process p = null;
        Path filePath = null;
        try {
            filePath = Files.createTempFile("output_", ".mp4");
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        String scale = String.format("scale=%d:%d", height, width);
        try{
            assert filePath != null;
            p = new ProcessBuilder("ffmpeg", "-y", "-i",
                    fileUrl, "-vf", scale, filePath.toAbsolutePath().toString())
            .start();
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        return filePath;
    }

    public static String getVideoDuration(Path fileUrl) {
        Process p = null;
        try{
            p = new ProcessBuilder("ffprobe","-i", fileUrl.toAbsolutePath().toString(),
                    "-show_entries", "format=duration","-sexagesimal",
                    "-v", "quiet",
                    "-of","csv=p=0")
                    .start();
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        String result = "";
        try(BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()))) {
            result = br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            System.err.println("Caught IOException");
        }

        return result;
    }

}
