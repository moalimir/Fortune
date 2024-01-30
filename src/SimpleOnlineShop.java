/**
 *
 * @moalimir
 * java doc:
 *
 *
 * */


import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Product {
    private String name;
    private double price;

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

class User {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private double balance;
    private Map<String, Integer> shoppingCart;

    public User(String firstName, String lastName, String phoneNumber, double balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.balance = balance;
        this.shoppingCart = new HashMap<>();
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public double getBalance() {
        return balance;
    }

    public Map<String, Integer> getShoppingCart() {
        return shoppingCart;
    }

    public void addToCart(String productName, int quantity) {
        shoppingCart.put(productName, shoppingCart.getOrDefault(productName, 0) + quantity);
    }

    public void deductBalance(double amount) {
        balance -= amount;
    }
}

class OnlineShop {
    private Map<String, Product> products;
    private Map<String, User> users;

    public OnlineShop() {
        products = new HashMap<>();
        users = new HashMap<>();
        initializeProducts();
    }

    public void displayProducts() {
        System.out.println("Available Products:");
        products.forEach((name, product) ->
                System.out.println(name + ": $" + product.getPrice()));
    }


    private void initializeProducts() {

        products.put("Product1", new Product("Product1", 10.99));
        products.put("Product2", new Product("Product2", 15.99));
        products.put("Product3", new Product("Product3", 20.49));
        products.put("Product4", new Product("Product4", 8.75));
        products.put("Product5", new Product("Product5", 12.50));
    }

    public void addUser(String firstName, String lastName, String phoneNumber, double balance) {
        User user = new User(firstName, lastName, phoneNumber, balance);
        users.put(phoneNumber, user);
    }

    public Map<String, Product> getProducts() {
        return products;
    }

    public Map<String, User> getUsers() {
        return users;
    }

    public void displayBalance(String phoneNumber) {
        User user = users.get(phoneNumber);
        if (user != null) {
            System.out.println("User: " + user.getFullName());
            System.out.println("Balance: $" + user.getBalance());
        } else {
            System.out.println("Invalid user.");
        }
    }

    public void addToCart(String phoneNumber, String productName, int quantity) {
        User user = users.get(phoneNumber);
        if (user != null) {
            Product product = products.get(productName);
            if (product != null) {
                double totalCost = product.getPrice() * quantity;
                if (user.getBalance() >= totalCost) {
                    user.addToCart(productName, quantity);
                    user.deductBalance(totalCost);
                    System.out.println("Product added to the cart successfully.");
                } else {
                    System.out.println("Insufficient balance to add the product to the cart.");
                }
            } else {
                System.out.println("Invalid product.");
            }
        } else {
            System.out.println("Invalid user.");
        }
    }

    public void viewCart(String phoneNumber) {
        User user = users.get(phoneNumber);
        if (user != null) {
            Map<String, Integer> shoppingCart = user.getShoppingCart();
            if (!shoppingCart.isEmpty()) {
                System.out.println("Shopping Cart for " + user.getFullName() + ":");
                shoppingCart.forEach((productName, quantity) ->
                        System.out.println(productName + ": " + quantity));
            } else {
                System.out.println("The shopping cart is empty.");
            }
        } else {
            System.out.println("Invalid user.");
        }
    }

    public void purchaseCart(String phoneNumber) {
        User user = users.get(phoneNumber);
        if (user != null) {
            Map<String, Integer> shoppingCart = user.getShoppingCart();
            double totalCost = 0;

            System.out.println("User: " + user.getFullName());
            System.out.println("Shopping Cart:");

            for (Map.Entry<String, Integer> entry : shoppingCart.entrySet()) {
                String productName = entry.getKey();
                int quantity = entry.getValue();
                Product product = products.get(productName);

                if (product != null) {
                    double productCost = product.getPrice() * quantity;
                    totalCost += productCost;

                    System.out.println(productName + ": " + quantity + " - $" + productCost);
                }
            }

            if (totalCost > 0) {
                if (user.getBalance() >= totalCost) {
                    user.getShoppingCart().clear();
                    user.deductBalance(totalCost);
                    System.out.println("Total Cost: $" + totalCost);
                    System.out.println("Remaining Balance: $" + user.getBalance());
                    System.out.println("Thank you for your purchase!");
                } else {
                    System.out.println("Insufficient balance to complete the purchase.");
                }
            } else {
                System.out.println("The shopping cart is empty.");
            }
        } else {
            System.out.println("Invalid user.");
        }
    }
}

// ... (previous code)

public class SimpleOnlineShop {

    public static void main(String[] args) {
        OnlineShop onlineShop = new OnlineShop();
        Scanner scanner = new Scanner(System.in);

        System.out.println("==============    Welcome to the FORTUNE SHOP   ============== \n\n");
        System.out.println("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.println("Enter your last name:");
        String lastName = scanner.nextLine();

        System.out.println("Enter your phone number:");
        String phoneNumber = scanner.nextLine();

        User currentUser = onlineShop.getUsers().get(phoneNumber);

        if (currentUser == null) {
            System.out.println("You are not registered. Let's register!");

            System.out.println("Enter your initial balance:");
            double initialBalance = scanner.nextDouble();

            onlineShop.addUser(firstName, lastName, phoneNumber, initialBalance);

            System.out.println("Registration successful! You are now logged in.");

            currentUser = onlineShop.getUsers().get(phoneNumber);

        } else {
            System.out.println("Welcome back, " + currentUser.getFullName() + "!");
        }

        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    onlineShop.displayBalance(currentUser.getPhoneNumber());
                    break;
                case 2:
                    onlineShop.viewCart(currentUser.getPhoneNumber());
                    break;
                case 3:
                    onlineShop.purchaseCart(currentUser.getPhoneNumber());
                    break;
                case 4:
                    //  onlineShop.viewPurchaseHistory(currentUser.getPhoneNumber());
                    break;
                case 5:
                    System.out.println("Enter the product name to add to the cart:");
                    String productToAdd = scanner.nextLine();
                    System.out.println("Enter the quantity:");
                    int quantityToAdd = scanner.nextInt();
                    onlineShop.addToCart(currentUser.getPhoneNumber(), productToAdd, quantityToAdd);
                    break;
                case 6:
                    System.out.println("Enter the discount percentage:");
                    double discountPercentage = scanner.nextDouble();
                    //   onlineShop.applyDiscount(currentUser.getPhoneNumber(), discountPercentage);
                    break;
                case 7:
                    System.out.println("Enter the points to earn:");
                    int pointsToEarn = scanner.nextInt();
                    //  onlineShop.earnLoyaltyPoints(currentUser.getPhoneNumber(), pointsToEarn);
                    break;
                case 8:
                    onlineShop.displayProducts();
                    break;
                case 9:
                    System.out.println("Register/Login option selected.");
                    handleRegisterLogin(onlineShop, scanner);
                    break;
                case 10:
                    exit = true;
                    System.out.println("Exiting the Online Shop. Thank you!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Display Balance");
        System.out.println("2. View Cart");
        System.out.println("3. Purchase Cart");
        System.out.println("4. View Purchase History");
        System.out.println("5. Add to Cart");
        System.out.println("6. Apply Discount");
        System.out.println("7. Earn Loyalty Points");
        System.out.println("8. Display Products");
        System.out.println("9. Register/Login");
        System.out.println("10. Exit");
        System.out.println("Enter your choice:");
    }

    private static void handleRegisterLogin(OnlineShop onlineShop, Scanner scanner) {
        System.out.println("Enter your phone number to login or register:");
        String phoneNumber = scanner.nextLine();
        User user = onlineShop.getUsers().get(phoneNumber);

        if (user == null) {
            // User is not registered, prompt for registration
            System.out.println("You are not registered. Let's register!");

            System.out.println("Enter your first name:");
            String firstName = scanner.nextLine();

            System.out.println("Enter your last name:");
            String lastName = scanner.nextLine();

            System.out.println("Enter your initial balance:");
            double initialBalance = scanner.nextDouble();

            // Register the user
            onlineShop.addUser(firstName, lastName, phoneNumber, initialBalance);

            System.out.println("Registration successful! You are now logged in.");
        } else {
            // User is registered
            System.out.println("Welcome back, " + user.getFullName() + "!");
        }
    }
}
