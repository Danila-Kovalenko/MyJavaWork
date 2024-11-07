public class ThreadSynchronizationExample {

    // Глобальная переменная, требующая синхронизации
    private static int count = 0;
    private static final Object lock = new Object();
    private static final int TARGET_COUNT = 10;

    public static void main(String[] args) {
        Thread threadA = new Thread(new ThreadA(), "Thread-A");
        Thread threadB = new Thread(new ThreadB(), "Thread-B");

        threadA.start();
        threadB.start();
    }

    // Поток A
    static class ThreadA implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (count < TARGET_COUNT) {
                    try {
                        System.out.println(Thread.currentThread().getName() + " ожидает. count: " + count);
                        lock.wait(); // Блокирующее ожидание сигнала от Thread B
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " прерван.");
                    }
                }
                System.out.println(Thread.currentThread().getName() + " проснулся. count достиг: " + count);
            }
        }
    }

    // Поток B
    static class ThreadB implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (count < TARGET_COUNT) {
                    count++;
                    System.out.println(Thread.currentThread().getName() + " увеличил count до " + count);
                    if (count >= TARGET_COUNT) {
                        lock.notify(); // Сигнал потоку A
                        System.out.println(Thread.currentThread().getName() + " сигнализировал Thread A.");
                    }
                    try {
                        Thread.sleep(500); // Имитация работы
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println(Thread.currentThread().getName() + " прерван.");
                    }
                }
            }
        }
    }
}
