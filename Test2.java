import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OrderProcessingTest {
    private Inventory inventory;
    private Book testBook;
    private User testUser;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        // Add books to the inventory
        // ...

        testBook = new Book(1, "Test Book", "Test Author", 20.99, 100);
        testUser = new User("testuser", "testuser@example.com");
    }

    @Test
    public void testOrderCreationValidBookQuantities() {
        inventory.addBook(testBook);
        List<Pair<Book, Integer>> orderedBooks = new ArrayList<>();
        orderedBooks.add(new Pair<>(testBook, 2));

        // Assuming createOrder method exists to process the order
        createOrder(testUser, orderedBooks, inventory);
        Order userOrder = testUser.getOrderHistory().get(0);

        // Verify order status and quantity
        Assertions.assertEquals(OrderStatus.COMPLETED, userOrder.getStatus());
        Assertions.assertEquals(2, userOrder.getOrderedBooks().get(0).getSecond());
    }

    @Test
    public void testOrderCreationInsufficientBookQuantities() {
        inventory.addBook(testBook);
        List<Pair<Book, Integer>> orderedBooks = new ArrayList<>();
        orderedBooks.add(new Pair<>(testBook, 200));

        createOrder(testUser, orderedBooks, inventory);
        Order userOrder = testUser.getOrderHistory().get(0);

        // Verify order status and quantity
        Assertions.assertEquals(OrderStatus.PENDING, userOrder.getStatus());
    }
}
