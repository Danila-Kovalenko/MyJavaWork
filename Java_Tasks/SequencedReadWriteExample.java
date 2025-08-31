import java.util.Vector;

// Вспомогательный класс для синхронизации
class SyncObject {
    private boolean writeTurn = true;

    public synchronized void write(int position, double value, Vector<Double> vector) {
        while (!writeTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        vector.set(position, value);
        System.out.println("Write: " + value + " to position " + position);
        writeTurn = false;
        notifyAll();
    }

    public synchronized void read(int position, Vector<Double> vector) {
        while (writeTurn) {
            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        double value = vector.get(position);
        System.out.println("Read: " + value + " from position " + position);
        writeTurn = true;
        notifyAll();
    }
}

// Поток записи
class SequencedWriter implements Runnable {
    private Vector<Double> vector;
    private SyncObject sync;
    private int size;

    public SequencedWriter(Vector<Double> vector, SyncObject sync) {
        this.vector = vector;
        this.sync = sync;
        this.size = vector.size();
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {
            double value = Math.random() * 100 + 1; // Случайное значение > 0
            sync.write(i, value, vector);
            try {
                Thread.sleep(200); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Поток чтения
class SequencedReader implements Runnable {
    private Vector<Double> vector;
    private SyncObject sync;
    private int size;

    public SequencedReader(Vector<Double> vector, SyncObject sync) {
        this.vector = vector;
        this.sync = sync;
        this.size = vector.size();
    }

    @Override
    public void run() {
        for (int i = 0; i < size; i++) {
            sync.read(i, vector);
            try {
                Thread.sleep(200); // Имитация работы
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}

// Главный класс
public class SequencedReadWriteExample {
    public static void main(String[] args) {
        Vector<Double> sharedVector = new Vector<>(5);
        for (int i = 0; i < 5; i++) {
            sharedVector.add(0.0);
        }

        SyncObject sync = new SyncObject();

        Thread writerThread = new Thread(new SequencedWriter(sharedVector, sync), "Writer");
        Thread readerThread = new Thread(new SequencedReader(sharedVector, sync), "Reader");

        // Установка приоритетов (можно варьировать)
        writerThread.setPriority(Thread.NORM_PRIORITY + 1);
        readerThread.setPriority(Thread.NORM_PRIORITY - 1);

        writerThread.start();
        readerThread.start();
    }
}
