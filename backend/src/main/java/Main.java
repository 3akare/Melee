import service.Melee;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        System.out.println("Backend service started...");

        // Create a ScheduledExecutorService with a thread pool size of 2
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

        Runnable task1 = () -> {
            System.out.println("Melee is running at: " + System.currentTimeMillis());
            new Melee().run();
        };
        executorService.scheduleAtFixedRate(task1, 0, 60, TimeUnit.SECONDS);

//        Runnable task2 = () -> {
//            System.out.println("Task 2 is running at: " + System.currentTimeMillis());
//            // Add your task 2 logic here
//        };
//        executorService.scheduleAtFixedRate(task2, 0, 2, TimeUnit.HOURS);

        // Add a shutdown hook to handle shutdown events
//        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
//            System.out.println("Shutdown hook triggered...");
//            executorService.shutdown();
//            try {
//                if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
//                    executorService.shutdownNow();
//                }
//            } catch (InterruptedException e) {
//                executorService.shutdownNow();
//            }
//            System.out.println("Backend service stopped...");
//        }));

        // Keep the application alive
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
