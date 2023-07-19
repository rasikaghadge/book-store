import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Simulator {
	public static void main(String[] args) {
		// Create the inventory with some books
		Inventory inventory = new Inventory();
		inventory.addBook(new Book(1, "Book Title 1", "Author 1", 20.99, 100));
		inventory.addBook(new Book(2, "Book Title 2", "Author 2", 30.50, 50));
		inventory.addBook(new Book(3, "Book Title 3", "Author 3", 15.75, 80));

		// Create users
		User user1 = new User("user1", "user1@example.com");
		User user2 = new User("user2", "user2@example.com");
		User user3 = new User("user3", "user3@example.com");

		// Create a list of ordered books with quantities for each user
		List<Pair<Book, Integer>> user1OrderedBooks = new ArrayList<>();
		user1OrderedBooks.add(new Pair<>(inventory.searchBookById(1), 2));
		user1OrderedBooks.add(new Pair<>(inventory.searchBookById(2), 1));

		List<Pair<Book, Integer>> user2OrderedBooks = new ArrayList<>();
		user2OrderedBooks.add(new Pair<>(inventory.searchBookById(2), 3));
		user2OrderedBooks.add(new Pair<>(inventory.searchBookById(3), 1));

		List<Pair<Book, Integer>> user3OrderedBooks = new ArrayList<>();
		user3OrderedBooks.add(new Pair<>(inventory.searchBookById(1), 1));
		user3OrderedBooks.add(new Pair<>(inventory.searchBookById(3), 2));

		// Create ExecutorService with a fixed thread pool size
		int numThreads = 3;
		ExecutorService executorService = Executors.newFixedThreadPool(numThreads);

		// Submit tasks to the ExecutorService for order creation
		executorService.submit(() -> createOrder(user1, user1OrderedBooks, inventory));
		executorService.submit(() -> createOrder(user2, user2OrderedBooks, inventory));
		executorService.submit(() -> createOrder(user3, user3OrderedBooks, inventory));

		// Shutdown the ExecutorService
		executorService.shutdown();

		// Wait for all tasks to complete
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Display the final inventory and order history for each user
		System.out.println("\nFinal Inventory:\n" + inventory);
//		System.out.println("\nOrder History for User 1:\n" + user1);
//		System.out.println("\nOrder History for User 2:\n" + user2);
//		System.out.println("\nOrder History for User 3:\n" + user3);
	}

	private static void createOrder(User user, List<Pair<Book, Integer>> orderedBooks, Inventory inventory) {
		String orderId = "Order-" + LocalDateTime.now().toString();
		OrderStatus orderStatus = OrderStatus.PENDING; // Initialize order status as PENDING
		LocalDateTime orderDate = LocalDateTime.now();

		List<Book> booksInOrder = new ArrayList<>();
		for (Pair<Book, Integer> orderedBook : orderedBooks) {
			Book book = orderedBook.getFirst();
			int quantity = orderedBook.getSecond();
			List<Book> availableBooks = inventory.searchBooksById(book.getId());
			if (!availableBooks.isEmpty()) {
				int availableQuantity = availableBooks.get(0).getQuantity();
//                int actualQuantity = Math.min(quantity, availableQuantity);
				if (availableQuantity >= quantity) {
					booksInOrder
							.add(new Book(book.getId(), book.getTitle(), book.getAuthor(), book.getPrice(), quantity));
				} else {
					System.out.println("Could not process request. Insufficient stock");
					Thread.currentThread().interrupt();
				}

			}
		}

		Order order = new Order(orderId, user, booksInOrder, orderStatus, orderDate);
		user.addOrder(order);

		// Introduce delay to simulate order processing
		try {
			Thread.sleep(2000); // Sleep for 2 seconds (adjust as needed)
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// Update the order status and inventory stock after order processing
		synchronized (order) {
			order.setOrderStatus(OrderStatus.COMPLETED); // Set the order status to COMPLETED
		}

		for (Book bookInOrder : booksInOrder) {
			int bookId = bookInOrder.getId();
			int quantity = bookInOrder.getQuantity();
			inventory.updateStockQuantity(bookId, quantity);
		}

		System.out.println("Order created for User: " + user.getUsername() + ", Order ID: " + order.getOrderId()
				+ ", Order Date: " + order.getOrderDate() + ", Books in Order: " + booksInOrder.size());
		
		System.out.println("Order History for user " + user.getUsername());
		user.printOrderHistory();
	}
	
	

}
