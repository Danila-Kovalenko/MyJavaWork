import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class FileReadAnalyzeExample {

    private static final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private static final String FILE_PATH = "FileReadAnalyzeExample.java"; // Имя файла программы
    private static final String PATTERN = "public"; // Образец для поиска

    public static void main(String[] args) {
        Thread readerThread = new Thread(new ReaderThread(), "Reader-Thread");
        Thread analyzerThread = new Thread(new AnalyzerThread(), "Analyzer-Thread");

        readerThread.start();
        analyzerThread.start();
    }

    // Поток-читатель
    static class ReaderThread implements Runnable {
        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
                String line;
                while ((line = br.readLine()) != null) {
                    queue.put(line);
                }
                // Индикатор окончания файла
                queue.put("EOF");
            } catch (IOException | InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("ReaderThread прерван или произошла ошибка.");
            }
        }
    }

    // Поток-аналитик
    static class AnalyzerThread implements Runnable {
        @Override
        public void run() {
            try {
                while (true) {
                    String line = queue.take();
                    if (line.equals("EOF")) {
                        break;
                    }
                    if (line.contains(PATTERN)) {
                        System.out.println(Thread.currentThread().getName() + " - Совпадение: " + line);
                    }
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("AnalyzerThread прерван.");
            }
        }
    }
}
