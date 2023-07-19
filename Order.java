import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private User user;
    private List<Book> books;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;

    public Order(String orderId, User user, List<Book> books, OrderStatus orderStatus, LocalDateTime orderDate) {
        this.orderId = orderId;
        this.user = user;

        // Create a defensive copy of the books list to avoid direct access to the shared list
        this.books = new ArrayList<>(books);

        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public User getUser() {
        return user;
    }

    public List<Book> getBooks() {
        synchronized (books) {
            return new ArrayList<>(books); // Create a defensive copy for read-only access
        }
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public synchronized void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    // More methods can be added as needed

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Order ID: ").append(orderId).append("\n");
        sb.append("User: ").append(user.getUsername()).append("\n");
        sb.append("Order Status: ").append(orderStatus).append("\n");
        sb.append("Order Date: ").append(orderDate).append("\n");
        sb.append("Books:\n");
        for (Book book : books) {
            sb.append(book.getTitle()).append(" (").append(book.getQuantity()).append(")\n");
        }
        return sb.toString();
    }
}
