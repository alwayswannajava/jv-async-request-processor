package mate.academy;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    private static final int THREADS_COUNT = 10;

    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT);
        AsyncRequestProcessor asyncRequestProcessor = new AsyncRequestProcessor(executor);

        // Simulating multiple concurrent requests
        String[] userIds = {"user1", "user2", "user3", "user1"}; // Note: "user1" is repeated
        CompletableFuture<?>[] futures = new CompletableFuture[userIds.length];

        for (int i = 0; i < userIds.length; i++) {
            String userId = userIds[i];
            futures[i] = asyncRequestProcessor.processRequest(userId)
                    .thenAccept(userData -> System.out.println("Processed: " + userData));
        }

        // Wait for all futures to complete
        CompletableFuture.allOf(futures).join();
        executor.shutdown();
    }
}
