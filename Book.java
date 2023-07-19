public class Book {
    private int id;         // New ID attribute
    private String title;
    private String author;
    private double price;
    private int quantity;

    public Book(int id, String title, String author, double price, int quantity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void updateQuantity(int quantityToAdd) {
        synchronized (this) {
            this.quantity -= quantityToAdd;
        }
    }

    public double getTotalPrice(int quantityToBuy) {
        // Synchronization is not required here because we are only reading data (no modification)
        return price * quantityToBuy;
    }

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author + ", price=" + price + ", quantity="
				+ quantity + "]";
	}
    
}
