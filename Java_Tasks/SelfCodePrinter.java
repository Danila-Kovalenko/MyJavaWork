// SelfCodePrinter.java

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

public class SelfCodePrinter {
    public static void main(String[] args) {
        try {
            // 1. Determine the current working directory
            Path currentDir = Paths.get("").toAbsolutePath();
            System.out.println("Current directory: " + currentDir);

            // 2. Get the program's filename
            String className = SelfCodePrinter.class.getSimpleName();
            String fileName = className + ".java";
            System.out.println("Program filename: " + fileName);

            // 3. Navigate to the src directory if it exists
            Path srcDir = currentDir.resolve("src");
            if (Files.exists(srcDir) && Files.isDirectory(srcDir)) {
                currentDir = srcDir;
                System.out.println("Navigated to src directory: " + currentDir);
            } else {
                System.out.println("src directory not found. Staying in the current directory.");
            }

            // 4. Build the path to the .java source file
            Path javaFilePath = currentDir.resolve(fileName);
            System.out.println("Path to .java file: " + javaFilePath);

            // Check if the file exists
            if (!Files.exists(javaFilePath)) {
                System.out.println("File " + fileName + " not found.");
                return;
            }

            // 5. Read and print the file content
            // Using Files.readAllBytes
            byte[] fileBytes = Files.readAllBytes(javaFilePath);
            String content = new String(fileBytes, StandardCharsets.UTF_8);
            System.out.println("\n--- Content of " + fileName + " ---\n");
            System.out.println(content);
            System.out.println("\n--- End of content ---");

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
