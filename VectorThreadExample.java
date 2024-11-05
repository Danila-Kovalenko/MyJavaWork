import java.util.Vector;
import java.util.Random;

// Поток для записи вектора
class WriterThread extends Thread {
    private Vector<Double> vector;
    private Random random = new Random();

    public WriterThread(Vector<Double> vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.size(); i++) {
            double value;
            do {
                value = random.nextDouble() * 100;
            } while (value == 0.0);
            synchronized (vector) {
                vector.set(i, value);
                System.out.println("Write: " + value + " to position " + i);
                vector.notify(); // Уведомление читающего потока
            }
            try {
                Thread.sleep(300); // Имитация задержки
            } catch (InterruptedException e) {
                System.out.println("WriterThread прерван.");
            }
        }
    }
}

// Поток для чтения вектора
class ReaderThread extends Thread {
    private Vector<Double> vector;

    public ReaderThread(Vector<Double> vector) {
        this.vector = vector;
    }

    @Override
    public void run() {
        for (int i = 0; i < vector.size(); i++) {
            synchronized (vector) {
                try {
                    // Ожидание, пока данные не будут записаны
                    vector.wait();
                } catch (InterruptedException e) {
                    System.out.println("ReaderThread прерван.");
                }
                double value = vector.get(i);
                System.out.println("Read: " + value + " from position " + i);
            }
            try {
                Thread.sleep(300); // Имитация задержки
            } catch (InterruptedException e) {
                System.out.println("ReaderThread прерван.");
            }
        }
    }
}

// Главный класс
public class VectorThreadExample {
    public static void main(String[] args) {
        Vector<Double> sharedVector = new Vector<>(5);
        // Инициализация вектора нулями
        for (int i = 0; i < 5; i++) {
            sharedVector.add(0.0);
        }

        WriterThread writer = new WriterThread(sharedVector);
        ReaderThread reader = new ReaderThread(sharedVector);

        // Установка приоритетов (опционально)
        writer.setPriority(Thread.NORM_PRIORITY + 1);
        reader.setPriority(Thread.NORM_PRIORITY - 1);

        writer.start();
        reader.start();
    }
}
