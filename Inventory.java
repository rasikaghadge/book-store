import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Inventory {
    private List<Book> books;

    public Inventory() {
        this.books = new ArrayList<>();
    }

    public void addBook(Book book) {
        synchronized (books) {
            books.add(book);
        }
    }
    
    public synchronized Book searchBookById(int bookId) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                return book;
            }
        }
        return null; // Book with the specified ID not found in the inventory
    }
    
    public synchronized List<Book> searchBooksById(int bookId) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
            if (book.getId() == bookId) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

//    public List<Book> searchBooksById(String genre) {
//        Predicate<Book> filterByGenre = book -> book.getGenre().equalsIgnoreCase(genre);
//        synchronized (books) {
//            return books.stream()
//                    .filter(filterByGenre)
//                    .collect(Collectors.toList());
//        }
//    }

    public List<String> getAllBookTitles() {
        synchronized (books) {
            return books.stream()
                    .map(Book::getTitle)
                    .collect(Collectors.toList());
        }
    }

    public List<Book> sortBooksByPrice() {
        synchronized (books) {
            return books.stream()
                    .sorted(Comparator.comparingDouble(Book::getPrice))
                    .collect(Collectors.toList());
        }
    }

    public synchronized void updateStockQuantity(int bookId, int quantity) {
        for (Book book : books) {
            if (book.getId() == bookId) {
                book.updateQuantity(quantity);
                break;
            }
        }
    }

    // More methods can be added as needed

    @Override
    public String toString() {
        synchronized (books) {
            return "Inventory:\n" + books.stream()
                    .map(Book::toString)
                    .collect(Collectors.joining("\n"));
        }
    }
}
