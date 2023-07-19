import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Test1 {
    private Inventory inventory;

    @BeforeEach
    public void setUp() {
        // Set up the inventory with some initial books for testing
        inventory = new Inventory();
        inventory.addBook(new Book(1, "Book Title 1", "Author 1", 20.99, 100));
        inventory.addBook(new Book(2, "Book Title 2", "Author 2", 30.5, 50));
        inventory.addBook(new Book(3, "Book Title 3", "Author 3", 15.75, 80));
    }

    @Test
    public void testSearchBooksById() {
        int bookIdToSearch = 2;

        List<Book> foundBooks = inventory.searchBooksById(bookIdToSearch);

        // Assert that the list of found books is not empty
        Assertions.assertFalse(foundBooks.isEmpty(), "No books found with ID: " + bookIdToSearch);

        // Assert that the correct book(s) with the specified ID are returned
        for (Book book : foundBooks) {
            Assertions.assertEquals(bookIdToSearch, book.getId(), "Invalid book ID");
        }
    }
}
