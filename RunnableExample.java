// Класс, реализующий Runnable
class MyRunnable implements Runnable {
    private String threadName;

    public MyRunnable(String name) {
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

// Использование класса MyRunnable
public class RunnableExample {
    public static void main(String[] args) {
        MyRunnable runnable = new MyRunnable("Runnable-1");
        Thread thread = new Thread(runnable);
        thread.start(); // Запуск потока
    }
}
