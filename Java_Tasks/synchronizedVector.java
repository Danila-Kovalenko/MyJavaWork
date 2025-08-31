import java.util.Vector;

// Интерфейс, аналогичный Vector (для примера)
interface IVector {
    void addElement(Double value);
    Double get(int index);
    void set(int index, Double value);
    int size();
    // Другие необходимые методы
}

// Синхронизированная обертка над Vector
class SynchronizedVector implements IVector {
    private Vector<Double> vector;

    public SynchronizedVector(Vector<Double> vector) {
        this.vector = vector;
    }

    @Override
    public synchronized void addElement(Double value) {
        vector.add(value);
    }

    @Override
    public synchronized Double get(int index) {
        return vector.get(index);
    }

    @Override
    public synchronized void set(int index, Double value) {
        vector.set(index, value);
    }

    @Override
    public synchronized int size() {
        return vector.size();
    }

    // Реализация других методов с синхронизацией
}

// Класс с методами обработки векторов
class VectorUtils {
    // Метод, возвращающий синхронизированный вектор
    public static IVector synchronizedVector(Vector<Double> vector) {
        return new SynchronizedVector(vector);
    }

    // Пример использования
    public static void main(String[] args) {
        Vector<Double> originalVector = new Vector<>();
        IVector syncVector = synchronizedVector(originalVector);

        // Использование синхронизированного вектора
        syncVector.addElement(10.5);
        syncVector.addElement(20.75);
        System.out.println("Size: " + syncVector.size());
        System.out.println("Element at 0: " + syncVector.get(0));
        syncVector.set(1, 30.25);
        System.out.println("Element at 1: " + syncVector.get(1));
    }
}
