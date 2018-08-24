package Util;

import java.io.File;
import java.io.IOException;

public class FileTempHelper {

    /**
     * Create temporary directory (<= JDK 1.6) (from JDK 1.7, Files.createTempDirectory available)
     *
     */
    public static File createTmpDir() throws IOException {
        File tmpFile = File.createTempFile("temp",
            Long.toString(System.nanoTime()));
        String fullPath = tmpFile.getAbsolutePath();
        tmpFile.delete();
        File tmpDir = new File(fullPath);
        if (!tmpDir.mkdir()) {
            throw new IOException("Failed to create tmpDir: " + fullPath);
        }
        return tmpDir;
    }
}

