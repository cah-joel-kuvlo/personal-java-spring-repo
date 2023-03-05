package org.example;

import java.io.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for writing chunks of a large file to disk.
 */
public class FileChunkWriter {
    private static final Logger logger = LogManager.getLogger(FileChunkWriter.class);
    private static final Executor executor = Executors.newFixedThreadPool(FileDivider.NUM_THREADS);
    private static final int BUFFER_SIZE = FileDivider.BUFFER_SIZE;

    /**
     * This method takes in the path to the file, the starting and ending positions of the chunk,
     * and the directory where the chunk will be saved.
     *
     * @param filePath  The path to the file that needs to be divided
     * @param startPos  The starting position of the chunk in the file
     * @param endPos    The ending position of the chunk in the file
     * @param chunkName The name of the chunk file
     * @param directory The directory where the chunk will be saved
     */
    public static void writeChunk(String filePath, long startPos, long endPos, String chunkName, File directory) {
        executor.execute(() -> {
            try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
                byte[] buffer = new byte[BUFFER_SIZE];
                raf.seek(startPos); // move the file pointer to the starting position of the chunk
                long bytesToRead = endPos - startPos;
                File chunkFile = new File(directory, chunkName);
                try (OutputStream os = new FileOutputStream(chunkFile)) {
                    while (bytesToRead > 0) {
                        int bytesRead = raf.read(buffer);
                        if (bytesRead == -1) {
                            break;
                        }
                        os.write(buffer, 0, bytesRead);
                        bytesToRead -= bytesRead;
                    }
                } catch (IOException e) {
                    logger.warn("Failed to write chunk " + chunkName, e);
                }
            } catch (IOException e) {
                logger.warn("Failed to read file " + filePath, e);
            }
        });
    }
}
