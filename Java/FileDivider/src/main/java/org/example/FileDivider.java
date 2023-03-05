package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class is responsible for dividing a large file into smaller chunks.
 * Each chunk is saved as a separate file in a directory specified by the user.
 */
public class FileDivider {
    private static final Logger logger = LogManager.getLogger(FileDivider.class);
     static final int NUM_THREADS = 4; // Number of threads for the Executor framework
     static final int BUFFER_SIZE = 8192; // Buffer size for streaming large files
    private static final String CHUNKS_DIRECTORY = "chunks";

    /**
     * This method takes in the path to the file to be divided, the size of each chunk,
     * and the directory where the chunks will be saved.
     *
     * @param filePath        The path to the file that needs to be divided
     * @param chunkSize       The size of each chunk
     * @param outputDirectory The directory where the chunks will be saved
     * @return boolean indicating if the operation was successful
     */
    public static boolean divideFile(String filePath, long chunkSize, String outputDirectory) {
        // Validate inputs
        if (!validateInputs(filePath, chunkSize)) {
            return false;
        }

        // Create chunks directory
        File chunksDirectory = createChunksDirectory(outputDirectory);
        if (chunksDirectory == null) {
            return false;
        }

        // Check if the file is corrupt
        if (!isFileValid(filePath)) {
            return false;
        }

        // Divide file into chunks
        if (!divideIntoChunks(filePath, chunkSize, chunksDirectory)) {
            return false;
        }

        // Wait for all tasks to complete
        return waitForTasksToComplete();
    }

    private static boolean validateInputs(String filePath, long chunkSize) {
        if (chunkSize <= 0) {
            logger.warn("Chunk size should be greater than zero.");
            return false;
        }

        File inputFile = new File(filePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            logger.warn("Invalid file path: " + filePath);
            return false;
        }
        long inputFileSize = inputFile.length();
        if (inputFileSize <= 0) {
            logger.warn("The file is empty, unable to proceed.");
            return false;
        }
        if (chunkSize >= inputFileSize) {
            logger.warn("The chunk size should be less than the file size.");
            return false;
        }
        return true;
    }

    private static File createChunksDirectory(String outputDirectory) {
        File chunksDirectory = new File(outputDirectory + File.separator + CHUNKS_DIRECTORY);
        try {
            if (!chunksDirectory.exists()) {
                if (!chunksDirectory.mkdirs()) {
                    logger.warn("Failed to create chunks directory");
                    return null;
                }
            } else {
                if (chunksDirectory.list() != null && chunksDirectory.list().length > 0) {
                    logger.warn("Chunks directory is not empty, do you want to proceed and overwrite existing chunks? (y/n)");
                    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
                    String input = br.readLine();
                    if (!input.equalsIgnoreCase("y")) {
                        return null;
                    }
                }
            }
        } catch (IOException e) {
            logger.warn("Failed to create chunks directory", e);
            return null;
        }
        return chunksDirectory;
    }

    private static boolean isFileValid(String filePath) {
        Path path = Paths.get(filePath);
        boolean isValid = Files.isReadable(path) && Files.isRegularFile(path);
        if (!isValid) {
            logger.warn("The file is corrupt, unable to proceed.");
            return false;
        }
        return true;
    }

    private static boolean divideIntoChunks(String filePath, long chunkSize, File chunksDirectory) {
        try (RandomAccessFile raf = new RandomAccessFile(filePath, "r")) {
            String fileName = new File(filePath).getName();
            String fileExt = fileName.substring(fileName.lastIndexOf("."));
            String baseName = fileName.substring(0, fileName.lastIndexOf("."));
            long fileSize = raf.length();
            long startPos = 0;
            long endPos = chunkSize - 1;
            int index = 0;
            while (startPos < fileSize) {
                if (endPos > fileSize) {
                    endPos = fileSize - 1;
                }
                String chunkName = String.format("%s_%02d%s", baseName, index++, fileExt);
                FileChunkWriter.writeChunk(filePath, startPos, endPos, chunkName, chunksDirectory);
                startPos = endPos + 1;
                endPos += chunkSize;
            }
        } catch (IOException e) {
            logger.warn("Failed to divide file into chunks", e);
            return false;
        }
        return true;
    }


    private static boolean waitForTasksToComplete() {
        try {
            ExecutorService executor = (ExecutorService) Executors.newFixedThreadPool(NUM_THREADS);
            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            logger.warn("Failed to complete all tasks", e);
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        String filePath = "path/to/file.txt";
        long chunkSize = 0;
        String outputDirectory = ".";

        // Get user input for chunk size
        chunkSize = getChunkSizeFromUser();
        if (chunkSize <= 0) {
            return;
        }

        // Divide file into chunks
        boolean success = divideFile(filePath, chunkSize, outputDirectory);
        if (success) {
            logger.info("File divided successfully");
        }
    }

    private static long getChunkSizeFromUser() {
        Scanner sc = new Scanner(System.in);
        long chunkSize = 0;
        try {
            System.out.println("Enter the chunk size (in bytes): ");
            chunkSize = sc.nextLong();
        } catch (InputMismatchException e) {
            logger.warn("Invalid input: " + e.getMessage(), e);
        } finally {
            sc.close();
        }
        return chunkSize;
    }
}

