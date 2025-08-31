public class LocalVariableExample {

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
            int i = 0; // Локальная переменная
            for (int j = 0; j < 100; j++) {
                i++;
                System.out.println(Thread.currentThread().getName() + " - i: " + i);
                try {
                    Thread.sleep(10); // Имитация работы
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}
