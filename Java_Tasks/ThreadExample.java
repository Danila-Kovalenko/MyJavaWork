// Класс, расширяющий Thread
class MyThread extends Thread {
    private String threadName;

    public MyThread(String name) {
        this.threadName = name;
    }

    @Override
    public void run() {
        System.out.println("Поток " + threadName + " запущен.");
        // Здесь можно разместить код, выполняемый в потоке
        for (int i = 1; i <= 5; i++) {
            System.out.println(threadName + ": " + i);
            try {
                Thread.sleep(500); // Имитация работы
            } catch (InterruptedException e) {
                System.out.println(threadName + " прерван.");
            }
        }
        System.out.println("Поток " + threadName + " завершен.");
    }
}

// Использование класса MyThread
public class ThreadExample {
    public static void main(String[] args) {
        MyThread thread1 = new MyThread("Thread-1");
        thread1.start(); // Запуск потока
    }
}
