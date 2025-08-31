// IntegerFileProcessor.java

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

public class IntegerFileProcessor {
    // Filename for writing and reading
    private static final String FILE_NAME = "integers.bin";

    public static void main(String[] args) {
        // Example list of integers to write
        List<Integer> integersToWrite = List.of(10, 20, 30, 40, 50, 60, 70, 80, 90, 100);

        try {
            // 1. Write integers to file
            writeIntegersToFile(integersToWrite, FILE_NAME);
            System.out.println("Integers successfully written to " + FILE_NAME);

            // 2. Read as bytes
            List<Byte> byteList = readBytesFromFile(FILE_NAME);
            double averageBytes = calculateAverageBytes(byteList);
            System.out.println("Average of bytes: " + averageBytes);

            // 3. Read as int
            List<Integer> intList = readIntsFromFile(FILE_NAME);
            double averageInts = calculateAverage(intList);
            System.out.println("Average of ints: " + averageInts);
            System.out.println("Number of int elements: " + intList.size());

            // 4. Read as float
            List<Float> floatList = readFloatsFromFile(FILE_NAME);
            double averageFloats = calculateAverage(floatList);
            System.out.println("Average of floats: " + averageFloats);

            // 5. Calculate averages for specific subsets
            calculateSubAverages(intList);

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Writes a list of integers to a binary file.
     * @param integers the list of integers to write.
     * @param fileName the filename.
     * @throws IOException if an I/O error occurs.
     */
    private static void writeIntegersToFile(List<Integer> integers, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (DataOutputStream dos = new DataOutputStream(Files.newOutputStream(path, StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {
            for (Integer num : integers) {
                dos.writeInt(num);
            }
        }
    }

    /**
     * Reads all bytes from the binary file.
     * @param fileName the filename.
     * @return a list of bytes.
     * @throws IOException if an I/O error occurs.
     */
    private static List<Byte> readBytesFromFile(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        byte[] allBytes = Files.readAllBytes(path);
        List<Byte> byteList = new ArrayList<>();
        for (byte b : allBytes) {
            byteList.add(b);
        }
        return byteList;
    }

    /**
     * Calculates the average of a list of bytes, treating them as unsigned.
     * @param bytes the list of bytes.
     * @return the average value.
     */
    private static double calculateAverageBytes(List<Byte> bytes) {
        if (bytes.isEmpty()) return 0.0;
        long sum = 0;
        for (Byte b : bytes) {
            sum += (b & 0xFF); // Convert byte to unsigned
        }
        return (double) sum / bytes.size();
    }

    /**
     * Reads all integers from the binary file.
     * @param fileName the filename.
     * @return a list of integers.
     * @throws IOException if an I/O error occurs.
     */
    private static List<Integer> readIntsFromFile(String fileName) throws IOException {
        List<Integer> intList = new ArrayList<>();
        Path path = Paths.get(fileName);
        try (DataInputStream dis = new DataInputStream(Files.newInputStream(path))) {
            while (dis.available() >= 4) { // int is 4 bytes
                intList.add(dis.readInt());
            }
        }
        return intList;
    }

    /**
     * Reads all floats from the binary file.
     * @param fileName the filename.
     * @return a list of floats.
     * @throws IOException if an I/O error occurs.
     */
    private static List<Float> readFloatsFromFile(String fileName) throws IOException {
        List<Float> floatList = new ArrayList<>();
        Path path = Paths.get(fileName);
        try (DataInputStream dis = new DataInputStream(Files.newInputStream(path))) {
            while (dis.available() >= 4) { // float is 4 bytes
                floatList.add(dis.readFloat());
            }
        }
        return floatList;
    }

    /**
     * Calculates the average of a list of numbers.
     * @param numbers the list of numbers.
     * @param <T> the type of number.
     * @return the average value.
     */
    private static <T extends Number> double calculateAverage(List<T> numbers) {
        if (numbers.isEmpty()) return 0.0;
        double sum = 0.0;
        for (T num : numbers) {
            sum += num.doubleValue();
        }
        return sum / numbers.size();
    }

    /**
     * Calculates and prints the average of the second half and third quarter of the list.
     * @param intList the list of integers.
     */
    private static void calculateSubAverages(List<Integer> intList) {
        int size = intList.size();
        if (size == 0) {
            System.out.println("The integer list is empty.");
            return;
        }

        // Second half
        int half = size / 2;
        List<Integer> secondHalf = intList.subList(half, size);
        double averageSecondHalf = calculateAverage(secondHalf);
        System.out.println("Average of the second half: " + averageSecondHalf);

        // Third quarter
        int quarter = size / 4;
        List<Integer> thirdQuarter = intList.subList(quarter * 2, quarter * 3);
        double averageThirdQuarter = calculateAverage(thirdQuarter);
        System.out.println("Average of the third quarter: " + averageThirdQuarter);
    }
}
