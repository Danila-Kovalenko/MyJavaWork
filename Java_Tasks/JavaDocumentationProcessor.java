// JavaDocumentationProcessor.java

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaDocumentationProcessor {
    // Filenames
    private static final String SOURCE_FILE = "source.txt"; // Source file containing Java documentation
    private static final String TARGET_FILE = "documentation.txt"; // Target file

    public static void main(String[] args) {
        try {
            // 1. Copy content from source.txt to documentation.txt using NIO2
            copyFileContent(SOURCE_FILE, TARGET_FILE);
            System.out.println("Content of " + SOURCE_FILE + " successfully copied to " + TARGET_FILE);

            // 2. Read the file up to the third occurrence of the word "java"
            String contentUpToThirdJava = readUntilThirdJava(TARGET_FILE);
            System.out.println("\n--- Content up to the third occurrence of 'java' ---\n");
            System.out.println(contentUpToThirdJava);
            System.out.println("\n--- End of content ---");

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Copies the content from one file to another using NIO2.
     * @param sourceFileName the source filename.
     * @param targetFileName the target filename.
     * @throws IOException if an I/O error occurs.
     */
    private static void copyFileContent(String sourceFileName, String targetFileName) throws IOException {
        Path sourcePath = Paths.get(sourceFileName);
        Path targetPath = Paths.get(targetFileName);

        System.out.println("Attempting to copy file: " + sourcePath);
        System.out.println("To file: " + targetPath);

        if (!Files.exists(sourcePath)) {
            throw new FileNotFoundException("Source file " + sourceFileName + " not found.");
        }

        // Copy content with replacement if the target file exists
        Files.copy(sourcePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println("Copy completed. Target file size: " + Files.size(targetPath) + " bytes.");
    }

    /**
     * Reads the file up to the third occurrence of the word "java" (case-insensitive).
     * @param fileName the filename.
     * @return the content up to the third occurrence.
     * @throws IOException if an I/O error occurs.
     */
    private static String readUntilThirdJava(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new FileNotFoundException("File " + fileName + " not found.");
        }

        StringBuilder result = new StringBuilder();
        int javaCount = 0;
        // Use BufferedReader for reading the character stream
        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            // Regular expression to find the word "java" (case-insensitive)
            Pattern pattern = Pattern.compile("\\bjava\\b", Pattern.CASE_INSENSITIVE);

            while ((line = reader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                while (matcher.find()) {
                    javaCount++;
                    if (javaCount == 3) {
                        // Append the part of the line up to the third occurrence
                        int end = matcher.end();
                        result.append(line, 0, end);
                        return result.toString();
                    }
                }
                result.append(line).append(System.lineSeparator());
            }
        }

        // If the word "java" occurred less than three times
        System.out.println("The word 'java' occurred less than three times in the file.");
        return result.toString();
    }
}
