import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Test1 {
    private Inventory inventory;
    private Book testBook;
    private User testUser;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        testBook = new Book(1, "Test Book", "Test Author", 20.99, 100);
        testUser = new User("testuser", "testuser@example.com");
    }

    @Test
    public void testBookAddition() {
        inventory.addBook(testBook);
        Assertions.assertTrue(inventory.getBooks().contains(testBook));
    }

    @Test
    public void testBookRetrieval() {
        inventory.addBook(testBook);
        Book foundBooks = inventory.searchBookById(testBook.getId());
        Assertions.assertEquals(1, foundBooks.getId());
        Assertions.assertEquals(testBook, foundBooks);
    }

    @Test
    public void testUserCreation() {
        Assertions.assertEquals("testuser", testUser.getUsername());
        Assertions.assertEquals("testuser@example.com", testUser.getEmail());
    }
}
