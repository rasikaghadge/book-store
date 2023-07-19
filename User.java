import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class User {
    private String username;
    private String email;
    private List<Order> orderHistory;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
        this.orderHistory = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public List<Order> getOrderHistory() {
        synchronized (orderHistory) {
            return new ArrayList<>(orderHistory); // Create a defensive copy for read-only access
        }
    }

    public void addOrder(Order order) {
        synchronized (orderHistory) {
            orderHistory.add(order);
        }
    }

    // More methods can be added as needed

    @Override
    public synchronized String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Username: ").append(username).append("\n");
        sb.append("Email: ").append(email).append("\n");
        sb.append("Order History:\n");
        for (Order order : orderHistory) {
            sb.append(order).append("\n");
        }
        return sb.toString();
    }
    
 // Inside the User class
    public void printOrderHistory() {
    	Random random = new Random();
    	try {
			Thread.sleep(random.nextInt(5000 - 3000 + 1) + 3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        System.out.println("Order History for " + username + ":");
        System.out.println("Username: " + username);
        System.out.println("Email: " + email);
        System.out.println("Order History:");
        for (Order order : orderHistory) {
        	System.out.println(order);
        }
        System.out.println();
    }

}
