package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {
	 private String client;
	 //private Product[] products;
	 ArrayList<Product> products = new ArrayList<Product>();
	 private Amount amount;
	 LocalDateTime date;
	
	
	    public Sale(String client, ArrayList<Product> products, Amount amount,LocalDateTime date) {
		super();
		this.client = client;
        this.products = products;
        this.amount = amount;
        this.date = date;
	}
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}


	@Override
	public String toString() {
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = date.format(myFormatObj);
		return "Sale [client=" + client + ", products=" + products.toString() + ", amount=" + amount + "]" + formattedDate ;
	}
}
