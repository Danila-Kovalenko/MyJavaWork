// VectorsTask3.java

import java.io.*;
import java.util.LinkedList;

/**
 * The Vector interface defines the basic operations for vectors.
 */
interface Vector extends Serializable {
    /**
     * Returns the dimension of the vector.
     * @return the dimension.
     */
    int getDimension();

    /**
     * Returns the coordinate of the vector at the specified index.
     * @param index the coordinate index.
     * @return the value of the coordinate.
     */
    double getCoordinate(int index);

    /**
     * Sets the value of the vector's coordinate at the specified index.
     * @param index the coordinate index.
     * @param value the new value for the coordinate.
     */
    void setCoordinate(int index, double value);
}

/**
 * Array-based implementation of the Vector interface.
 */
class ArrayVector implements Vector {
    private static final long serialVersionUID = 1L;
    private double[] coordinates;

    /**
     * Constructor to create a vector with the specified dimension.
     * @param dimension the dimension of the vector.
     */
    public ArrayVector(int dimension) {
        coordinates = new double[dimension];
    }

    /**
     * Constructor to create a vector with the given coordinates.
     * @param coordinates the array of coordinates.
     */
    public ArrayVector(double[] coordinates) {
        this.coordinates = coordinates.clone();
    }

    @Override
    public int getDimension() {
        return coordinates.length;
    }

    @Override
    public double getCoordinate(int index) {
        return coordinates[index];
    }

    @Override
    public void setCoordinate(int index, double value) {
        coordinates[index] = value;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ArrayVector(");
        for(int i = 0; i < coordinates.length; i++) {
            sb.append(coordinates[i]);
            if(i < coordinates.length -1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}

/**
 * LinkedList-based implementation of the Vector interface.
 */
class LinkedListVector implements Vector {
    private static final long serialVersionUID = 1L;
    private LinkedList<Double> coordinates;

    /**
     * Constructor to create a vector with the specified dimension.
     * @param dimension the dimension of the vector.
     */
    public LinkedListVector(int dimension) {
        coordinates = new LinkedList<>();
        for(int i=0; i<dimension; i++) {
            coordinates.add(0.0);
        }
    }

    /**
     * Constructor to create a vector with the given coordinates.
     * @param coordinates the LinkedList of coordinates.
     */
    public LinkedListVector(LinkedList<Double> coordinates) {
        this.coordinates = new LinkedList<>(coordinates);
    }

    @Override
    public int getDimension() {
        return coordinates.size();
    }

    @Override
    public double getCoordinate(int index) {
        return coordinates.get(index);
    }

    @Override
    public void setCoordinate(int index, double value) {
        coordinates.set(index, value);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("LinkedListVector(");
        for(int i = 0; i < coordinates.size(); i++) {
            sb.append(coordinates.get(i));
            if(i < coordinates.size() -1) sb.append(", ");
        }
        sb.append(")");
        return sb.toString();
    }
}

/**
 * The VectorsTask3 class demonstrates serialization and deserialization of ArrayVector and LinkedListVector.
 */
public class Vectors {

    /**
     * Multiplies a vector by a scalar.
     * @param v the vector.
     * @param scalar the scalar.
     * @return a new vector resulting from the multiplication.
     */
    public static Vector multiplyByScalar(Vector v, double scalar) {
        Vector result;
        if(v instanceof ArrayVector) {
            double[] newCoordinates = new double[v.getDimension()];
            for(int i=0; i < v.getDimension(); i++) {
                newCoordinates[i] = v.getCoordinate(i) * scalar;
            }
            result = new ArrayVector(newCoordinates);
        } else if(v instanceof LinkedListVector) {
            LinkedList<Double> newCoordinates = new LinkedList<>();
            for(int i=0; i < v.getDimension(); i++) {
                newCoordinates.add(v.getCoordinate(i) * scalar);
            }
            result = new LinkedListVector(newCoordinates);
        } else {
            throw new IllegalArgumentException("Unsupported Vector type");
        }
        return result;
    }

    /**
     * Adds two vectors.
     * @param v1 the first vector.
     * @param v2 the second vector.
     * @return a new vector which is the sum of v1 and v2.
     */
    public static Vector add(Vector v1, Vector v2) {
        if(v1.getDimension() != v2.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        Vector result;
        if(v1 instanceof ArrayVector && v2 instanceof ArrayVector) {
            double[] newCoordinates = new double[v1.getDimension()];
            for(int i=0; i < v1.getDimension(); i++) {
                newCoordinates[i] = v1.getCoordinate(i) + v2.getCoordinate(i);
            }
            result = new ArrayVector(newCoordinates);
        } else if(v1 instanceof LinkedListVector && v2 instanceof LinkedListVector) {
            LinkedList<Double> newCoordinates = new LinkedList<>();
            for(int i=0; i < v1.getDimension(); i++) {
                newCoordinates.add(v1.getCoordinate(i) + v2.getCoordinate(i));
            }
            result = new LinkedListVector(newCoordinates);
        } else {
            throw new IllegalArgumentException("Unsupported Vector types or mismatched types");
        }
        return result;
    }

    /**
     * Calculates the dot product of two vectors.
     * @param v1 the first vector.
     * @param v2 the second vector.
     * @return the dot product.
     */
    public static double dotProduct(Vector v1, Vector v2) {
        if(v1.getDimension() != v2.getDimension()) {
            throw new IllegalArgumentException("Vectors must have the same dimension");
        }
        double result = 0.0;
        for(int i=0; i < v1.getDimension(); i++) {
            result += v1.getCoordinate(i) * v2.getCoordinate(i);
        }
        return result;
    }

    /**
     * Writes a vector to a byte stream.
     * Format: first the dimension (int), then the coordinates (double).
     * @param v the vector to write.
     * @param out the output stream.
     * @throws IOException if an I/O error occurs.
     */
    public static void outputVector(Vector v, OutputStream out) throws IOException {
        DataOutputStream dataOut = new DataOutputStream(out);
        dataOut.writeInt(v.getDimension());
        for(int i=0; i < v.getDimension(); i++) {
            dataOut.writeDouble(v.getCoordinate(i));
        }
        dataOut.flush();
    }

    /**
     * Reads a vector from a byte stream.
     * Expected format: dimension (int), then coordinates (double).
     * @param in the input stream.
     * @return the read vector.
     * @throws IOException if an I/O error occurs.
     */
    public static Vector inputVector(InputStream in) throws IOException {
        DataInputStream dataIn = new DataInputStream(in);
        int dimension;
        try {
            dimension = dataIn.readInt();
        } catch (EOFException e) {
            return null; // End of file
        }
        double[] coordinates = new double[dimension];
        for(int i=0; i < dimension; i++) {
            coordinates[i] = dataIn.readDouble();
        }
        return new ArrayVector(coordinates); // Choose implementation type
    }

    /**
     * Writes a vector to a character stream.
     * Format: dimension and coordinates separated by spaces in one line.
     * @param v the vector to write.
     * @param out the output writer.
     * @throws IOException if an I/O error occurs.
     */
    public static void writeVector(Vector v, Writer out) throws IOException {
        BufferedWriter writer = new BufferedWriter(out);
        writer.write(String.valueOf(v.getDimension()));
        for(int i=0; i < v.getDimension(); i++) {
            writer.write(" " + v.getCoordinate(i));
        }
        writer.newLine();
        writer.flush();
    }

    /**
     * Reads a vector from a character stream.
     * Expected format: dimension and coordinates separated by spaces in one line.
     * @param in the input reader.
     * @return the read vector.
     * @throws IOException if an I/O error occurs.
     */
    public static Vector readVector(Reader in) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(in);
        tokenizer.resetSyntax();
        tokenizer.wordChars('0', '9');
        tokenizer.wordChars('.', '.');
        tokenizer.whitespaceChars(' ', ' ');
        tokenizer.whitespaceChars('\n', '\n');
        tokenizer.whitespaceChars('\r', '\r');
        tokenizer.parseNumbers();

        // Read dimension
        if(tokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
            throw new IOException("Failed to read vector dimension");
        }
        int dimension = (int) tokenizer.nval;

        double[] coordinates = new double[dimension];
        for(int i=0; i < dimension; i++) {
            if(tokenizer.nextToken() != StreamTokenizer.TT_NUMBER) {
                throw new IOException("Failed to read vector coordinates");
            }
            coordinates[i] = tokenizer.nval;
        }
        return new ArrayVector(coordinates); // Choose implementation type
    }

    /**
     * Writes vectors to binary and text files and demonstrates reading them back.
     * Also allows user input for vector creation.
     */
    public static void main(String[] args) {
        try {
            // Create two vectors
            Vector v1 = new ArrayVector(new double[]{1.0, 2.0, 3.0});
            Vector v2 = new ArrayVector(new double[]{4.0, 5.0, 6.0});

            // Multiply vector by scalar
            Vector v3 = multiplyByScalar(v1, 2.0);
            System.out.println("v1 * 2 = " + v3);

            // Add two vectors
            Vector v4 = add(v1, v2);
            System.out.println("v1 + v2 = " + v4);

            // Dot product of two vectors
            double dp = dotProduct(v1, v2);
            System.out.println("v1 • v2 = " + dp);

            // Write vectors to binary file
            FileOutputStream fos = new FileOutputStream("vectors.bin");
            outputVector(v1, fos);
            outputVector(v2, fos);
            fos.close();
            System.out.println("Vectors written to vectors.bin");

            // Read vectors from binary file
            FileInputStream fis = new FileInputStream("vectors.bin");
            Vector v1_read = inputVector(fis);
            Vector v2_read = inputVector(fis);
            fis.close();
            System.out.println("Read v1 from vectors.bin: " + v1_read);
            System.out.println("Read v2 from vectors.bin: " + v2_read);

            // Write vectors to text file
            FileWriter fw = new FileWriter("vectors.txt");
            writeVector(v1, fw);
            writeVector(v2, fw);
            fw.close();
            System.out.println("Vectors written to vectors.txt");

            // Read vectors from text file
            FileReader fr = new FileReader("vectors.txt");
            Vector v1_read_text = readVector(fr);
            Vector v2_read_text = readVector(fr);
            fr.close();
            System.out.println("Read v1 from vectors.txt: " + v1_read_text);
            System.out.println("Read v2 from vectors.txt: " + v2_read_text);

            // Additionally: Read vector from System.in
            System.out.println("Enter a vector in the format: dimension coordinate1 coordinate2 ...");
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            Vector userVector = readVector(br);
            System.out.println("Read vector from input: " + userVector);

            // Write vector to System.out
            System.out.print("Vector written to System.out: ");
            writeVector(userVector, new OutputStreamWriter(System.out));
            System.out.println();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }
}
