import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class Test2 {
    private Inventory inventory;
    private User user;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        // Add books to the inventory
        // ...

        // Create a user
        user = new User("testuser", "testuser@example.com");

        // Create a single-threaded ExecutorService with 3 threads
        executorService = Executors.newFixedThreadPool(3);
    }

    @Test
    public void testConcurrentBookOrders() throws InterruptedException {
        List<Pair<Book, Integer>> orderedBooks1 = new ArrayList<>();
        orderedBooks1.add(new Pair<>(new Book(1, "Book Title 1", "Author 1", 20.99, 1), 2));
        orderedBooks1.add(new Pair<>(new Book(2, "Book Title 2", "Author 2", 30.5, 1), 3));

        List<Pair<Book, Integer>> orderedBooks2 = new ArrayList<>();
        orderedBooks2.add(new Pair<>(new Book(1, "Book Title 1", "Author 1", 20.99, 1), 2));
        orderedBooks2.add(new Pair<>(new Book(3, "Book Title 3", "Author 3", 15.75, 1), 1));

        List<Pair<Book, Integer>> orderedBooks3 = new ArrayList<>();
        orderedBooks3.add(new Pair<>(new Book(2, "Book Title 2", "Author 2", 30.5, 1), 3));
        orderedBooks3.add(new Pair<>(new Book(3, "Book Title 3", "Author 3", 15.75, 1), 2));

        // Submit tasks to the ExecutorService for concurrent execution
        executorService.submit(() -> createOrder(user, orderedBooks1, inventory));
        executorService.submit(() -> createOrder(user, orderedBooks2, inventory));
        executorService.submit(() -> createOrder(user, orderedBooks3, inventory));

        // Shutdown the ExecutorService
        executorService.shutdown();

        // Wait for all tasks to complete or a timeout
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            // Forcibly shutdown the ExecutorService if it takes too long
            executorService.shutdownNow();
        }

        // Display the final inventory count
//        int finalInventoryCount = inventory.calculateTotalInventoryCount();
//        System.out.println("Final Inventory Count: " + finalInventoryCount);

        // Assertions (if needed)
        // ...
    }

    // Helper method to create an order
    private void createOrder(User user, List<Pair<Book, Integer>> orderedBooks, Inventory inventory) {
        // Your order creation logic here
        // ...
    }
}
