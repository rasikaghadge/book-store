import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
@Execution(ExecutionMode.CONCURRENT)
public class YourTestClass {
    private Inventory inventory;
    private ExecutorService executorService;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        // Add books to the inventory
        // ...

        // Create a single-threaded ExecutorService with 3 threads
        executorService = Executors.newFixedThreadPool(3);
    }

    @RepeatedTest(3) // Repeat the test 3 times
    public void testBookOrder() throws InterruptedException {
        // Create a user
        User user = new User("testuser", "testuser@example.com");

        // Create the ordered books
        List<Pair<Book, Integer>> orderedBooks = new ArrayList<>();
        orderedBooks.add(new Pair<>(new Book(1, "Book Title 1", "Author 1", 20.99, 1), 2));
        orderedBooks.add(new Pair<>(new Book(2, "Book Title 2", "Author 2", 30.5, 1), 3));

        // Submit the order creation task to the ExecutorService for concurrent execution
        executorService.submit(() -> createOrder(user, orderedBooks, inventory));
    }

    // Helper method to create an order
    private void createOrder(User user, List<Pair<Book, Integer>> orderedBooks, Inventory inventory) {
        // Your order creation logic here
        // ...

        // Assertions (verify the result after the order is processed)
        int finalInventoryCount = inventory.calculateTotalInventoryCount();
        System.out.println("Final Inventory Count: " + finalInventoryCount);
        Assertions.assertEquals(125, finalInventoryCount, "Incorrect final inventory count.");

        // Assert individual book quantities (assuming they are unique for testing purposes)
        Book book1 = inventory.searchBooksById(1).get(0);
        Book book2 = inventory.searchBooksById(2).get(0);

        Assertions.assertEquals(98, book1.getQuantity(), "Incorrect quantity for Book ID 1.");
        Assertions.assertEquals(47, book2.getQuantity(), "Incorrect quantity for Book ID 2.");
    }
}
