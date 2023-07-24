import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class Test4 {
    private Inventory inventory;
    private Book book1;
    private Book book2;
    private Book book3;

    @BeforeEach
    public void setUp() {
        inventory = new Inventory();
        book1 = new Book(1, "Book C", "Author C", 10.99, 50);
        book2 = new Book(2, "Book B", "Author B", 5.99, 30);
        book3 = new Book(3, "Book A", "Author A", 15.99, 20);
        inventory.addBook(book1);
        inventory.addBook(book2);
        inventory.addBook(book3);
    }

    @Test
    public void testSortBooksByTitle() {
        List<Book> expectedSortedBooks = Arrays.asList(book3, book2, book1);
        List<Book> sortedBooks = inventory.sortBooksByTitle();
        Assertions.assertEquals(expectedSortedBooks, sortedBooks);
    }
    
    @Test
    public void testSortBooksByPrice() {
        List<Book> expectedSortedBooks = Arrays.asList(book2, book1, book3);
        List<Book> sortedBooks = inventory.sortBooksByPrice();
        Assertions.assertEquals(expectedSortedBooks, sortedBooks);
    }
}
