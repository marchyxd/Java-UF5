package main;


import model.Amount;
import model.Product;
import model.Sale;
import java.util.Scanner;


public class Shop {
    private Amount cash = new Amount(100.00);
    private Product[] inventory;
    private int numberProducts;
    private int numberSale;
    private Sale[] sales;

    // Constants
    final static double TAX_RATE = 1.04;

    // Constructor
    public Shop() {
        inventory = new Product[10];
        sales = new Sale[10];
    }

    // Main method
    public static void main(String[] args) {
        // Instance of Shop
        Shop shop = new Shop();
        // Load initial inventory
        shop.loadInventory();

        // Scanner for user input
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;
        boolean exit = false;

        // Main menu loop
        do {
            // Display main menu options
            System.out.println("\n");
            System.out.println("===========================");
            System.out.println("Main Menu myStore.com");
            System.out.println("===========================");
            System.out.println("1) Count cash");
            System.out.println("2) Add product");
            System.out.println("3) Add stock");
            System.out.println("4) Set product expiration");
            System.out.println("5) View inventory");
            System.out.println("6) Sale");
            System.out.println("7) View sales");
            System.out.println("8) Exit program");
            System.out.print("Select an option: ");
            opcion = scanner.nextInt();

            // Switch statement to handle user input
            switch (opcion) {
                case 1:
                    shop.showCash();
                    break;

                case 2:
                    shop.addProduct();
                    break;

                case 3:
                    shop.addStock();
                    break;

                case 4:
                    shop.setExpired();
                    break;

                case 5:
                    shop.showInventory();
                    break;

                case 6:
                    shop.sale();
                    break;

                case 7:
                    shop.showSales();
                    break;

                case 8:
                    exit = true;
                    System.out.println("EXIT");
                    break;
            }

        } while (!exit);

    }

    // Method to load initial inventory
    public void loadInventory() {
        addProduct(new Product("Apple", new Amount(10.00), new Amount(5.00), true, 10));
        addProduct(new Product("Pear", new Amount(20.00), new Amount(10.00), true, 20));
        addProduct(new Product("Hamburger", new Amount(30.00), new Amount(15.00), true, 30));
        addProduct(new Product("Strawberry", new Amount(5.00), new Amount(2.50), true, 20));
    }

    // Method to display current cash
    private void showCash() {
        System.out.println("Current cash: " + cash );
    }
    
    // Method to update cash amount
    private void updateCash(double amount) {
        cash.setValue(cash.getValue() + amount);
        System.out.println("Cash updated: " + cash);
    }
    // Method to add a product
    public void addProduct() {
        // Check if inventory is full
        if (isInventoryFull()) {
            System.out.println("No more products can be added");
            return;
        }
        // Scanner to read user input
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Precio mayorista: ");
        double wholesalerPrice = scanner.nextDouble();
        // Calculate public price based on wholesale price
        double publicPrice = wholesalerPrice * 2;
        System.out.print("Stock: ");
        int stock = scanner.nextInt();

        // Add product to inventory
        addProduct(new Product(name, new Amount(publicPrice), new Amount(wholesalerPrice), true, stock));
        
        // Update cash amount
        updateCash(publicPrice);

        // Show updated cash
        showCash();
    }

    // Method to add stock for a product
    public void addStock() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a product name: ");
        String name = scanner.next();
        Product product = findProduct(name);
        //check if the product is null, them add it. 
        if (product != null) {
            System.out.print("Select the quantity to add: ");
            int stockToAdd = scanner.nextInt();
            product.setStock(product.getStock() + stockToAdd);
            System.out.println("The stock of product " + name + " has been updated to " + product.getStock());
          //else not exist the product. 
        } else {
            System.out.println("Product with name " + name + " not found");
        }
    }

    // Method to set a product as expired
    private void setExpired() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Select a product name: ");
        String name = scanner.next();

        Product product = findProduct(name);

        if (product != null) {
            product.expire();
            System.out.println("The price of product " + name + " has been updated to " + product.getPublicPrice() + "€");
        } else {
            System.out.println("Product with name " + name + " not found");
        }
    }

    // Method to display current inventory
    public void showInventory() {
        System.out.println("Current content of the store:");
        for (int i = 0; i < numberProducts; i++) {
            if (inventory[i] != null) {
                System.out.println(inventory[i].toString());
            }
        }
    }

 // Method to perform a sale
    public void sale() {
        // Create a Scanner object to read user input
        Scanner sc = new Scanner(System.in);
        // Prompt message to ask the user to enter the customer's name
        System.out.println("Make sale, enter customer name");
        // Read input for customer name
        String client = sc.nextLine();
        // Create an array to store the products for the sale
        Product[] fixedProduct = new Product[10];
        // Initialize a counter for the products
        int productCounter = 0;
        // Initialize the total amount of the sale
        double totalAmount = 0.0;
        String name = "";
        // Loop until the user enters "0" to finish adding products
        while (!name.equals("0")) {
            // Prompt the user to enter the product name or "0" to finish
            System.out.println("Enter product name, write 0 to finish:");
            // Read the product name input
            name = sc.nextLine();

            // Check if the user entered "0" to finish
            if (name.equals("0")) {
                break;
            }

            // Find the product with the entered name
            Product product = findProduct(name);
            // Store the found product in the array of fixed products
            fixedProduct[productCounter] = product;

            // Flag to indicate if the product is available
            boolean productAvailable = false;

            // Check if the product is not null, available, and has stock
            if (product != null && product.isAvailable() && product.getStock() > 0) {
                // Set the flag to true indicating the product is available
                productAvailable = true;
                // Add the public price of the product to the total amount
                totalAmount += product.getPublicPrice().getValue();
                // Decrement the stock of the product by 1
                product.setStock(product.getStock() - 1);
                // Check if the product's stock has become zero
                if (product.getStock() == 0) {
                    product.setAvailable(false);
                }
                // Print a success message indicating the product was added successfully
                System.out.println("Product added successfully");
            }

            // Check if the product is not available
            if (!productAvailable) {
                System.out.println("Product not found or out of stock");
            }
            // Increment the product counter
            productCounter++;
        }

        // Calculate the total amount after applying the tax rate
        totalAmount *= TAX_RATE;
        updateCash(totalAmount);
        // Create a new sale object with the customer name, products, and total amount
        Sale sale = new Sale(client, fixedProduct, new Amount(totalAmount));
        // Add the sale to the sales array
        addSale(sale);
        // Update the cash amount by adding the total amount of the sale
        cash.setValue(cash.getValue() + totalAmount);
        // Print a success message indicating the sale was completed and display the total amount
        System.out.println("Sale successful, total: " + totalAmount);
    }

    // Method to display all sales
    private void showSales() {
        System.out.println("Sales list:");
        for (Sale sale : sales) {
            if (sale != null) {
                String clientUpperCase = sale.getClient().toUpperCase(); 
                System.out.println("Client: " + clientUpperCase  + " - Price: " + sale.getAmount() );
            }
        }
        showTotalSales();
    }
    
    // Method to display total sales amount
    public void showTotalSales() {
        Amount totalSalesAmount = new Amount( 0.0);
        for (Sale sale : sales) {
            if (sale != null) {
                totalSalesAmount.setValue(totalSalesAmount.getValue() + sale.getAmount().getValue());
            }
        }
        System.out.println("Total sales made: " + totalSalesAmount + "€");
    }

    // Method to add a product to inventory
    public void addProduct(Product product) {
        if (isInventoryFull()) {
            System.out.println("No more products can be added, maximum reached: " + inventory.length);
            return;
        }
        inventory[numberProducts] = product;
        numberProducts++;
    }

    // Method to check if inventory is full
    public boolean isInventoryFull() {
        return numberProducts == 10;
    }

    // Method to add a sale
    public void addSale(Sale sale) {
        if (isSaleFull()) {
            System.out.println("No more sales can be added, maximum reached: " + sales.length);
            return;
        }
        sales[numberSale] = sale;
        numberSale++;
    }

    // Method to check if sales array is full
    public boolean isSaleFull() {
        return numberSale == 10;
    }

    // Method to find a product by name
    public Product findProduct(String name) {
        for (int i = 0; i < numberProducts; i++) {
            if (inventory[i] != null && inventory[i].getName().equalsIgnoreCase(name)) {
                return inventory[i];
            }
        }
        return null;
    }
    
    
    
}

