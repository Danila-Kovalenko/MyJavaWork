public class SharedVariableExample {

    // Общая глобальная переменная
    private static int i = 0;

    public static void main(String[] args) {
        Runnable incrementTask = new IncrementTask();

        Thread thread1 = new Thread(incrementTask, "Thread-1");
        Thread thread2 = new Thread(incrementTask, "Thread-2");

        thread1.start();
        thread2.start();
    }

    static class IncrementTask implements Runnable {
        @Override
        public void run() {
            for (int j = 0; j < 100; j++) {
                synchronized (SharedVariableExample.class) { // Синхронизация для избежания гонки
                    i++;
                    System.out.println(Thread.currentThread().getName() + " - i: " + i);
                }
                try {
                    Thread.sleep(10); // Имитация работы
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
