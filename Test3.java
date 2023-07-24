import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConcurrentOrderProcessingTest {
    private Inventory inventory;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        // Add books to the inventory
        // ...

        executorService = Executors.newFixedThreadPool(3);
    }

    @Test
    public void testConcurrentOrderProcessing() throws InterruptedException {
        User user1 = new User("user1", "user1@example.com");
        User user2 = new User("user2", "user2@example.com");
        User user3 = new User("user3", "user3@example.com");

        List<Pair<Book, Integer>> orderedBooks1 = new ArrayList<>();
        orderedBooks1.add(new Pair<>(new Book(1, "Book Title 1", "Author 1", 20.99, 1), 2));
        orderedBooks1.add(new Pair<>(new Book(2, "Book Title 2", "Author 2", 30.5, 1), 3));

        List<Pair<Book, Integer>> orderedBooks2 = new ArrayList<>();
        orderedBooks2.add(new Pair<>(new Book(1, "Book Title 1", "Author 1", 20.99, 1), 2));
        orderedBooks2.add(new Pair<>(new Book(3, "Book Title 3", "Author 3", 15.75, 1), 1));

        List<Pair<Book, Integer>> orderedBooks3 = new ArrayList<>();
        orderedBooks3.add(new Pair<>(new Book(2, "Book Title 2", "Author 2", 30.5, 1), 3));
        orderedBooks3.add(new Pair<>(new Book(3, "Book Title 3", "Author 3", 15.75, 1), 2));

        executorService.submit(() -> createOrder(user1, orderedBooks1, inventory));
        executorService.submit(() -> createOrder(user2, orderedBooks2, inventory));
        executorService.submit(() -> createOrder(user3, orderedBooks3, inventory));

        executorService.shutdown();
        if (!executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            executorService.shutdownNow();
        }

        // Verify the final inventory count
        int finalInventoryCount = inventory.calculateTotalInventoryCount();
        System.out.println("Final Inventory Count: " + finalInventoryCount);
        Assertions.assertEquals(125, finalInventoryCount, "Incorrect final inventory count.");

        // Verify individual book quantities (assuming they are unique for testing purposes)
        Book book1 = inventory.searchBooksById(1).get(0);
        Book book2 = inventory.searchBooksById(2).get(0);

        Assertions.assertEquals(98, book1.getQuantity(), "Incorrect quantity for Book ID 1.");
        Assertions.assertEquals(47, book2.getQuantity(), "Incorrect quantity for Book ID 2.");
    }

    // Helper method to create an order
    private void createOrder(User user, List<Pair<Book, Integer>> orderedBooks, Inventory inventory) {
        // Your order creation logic here
        // ...
    }
}
