import java.util.*;

// Represents a single product available in the shop
class Product {
    int id;
    String name;
    double price;

    Product(int id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return id + ". " + name + " - Rs." + price;
    }
}

// Represents one line item in the cart (product + quantity)
class CartItem {
    Product product;
    int quantity;

    CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    double getSubtotal() {
        return product.price * quantity;
    }
}

// Handles all cart operations
class Cart {
    private Map<Integer, CartItem> items = new LinkedHashMap<>();

    void addItem(Product product, int quantity) {
        if (items.containsKey(product.id)) {
            items.get(product.id).quantity += quantity;
        } else {
            items.put(product.id, new CartItem(product, quantity));
        }
        System.out.println(quantity + " x " + product.name + " added to cart.");
    }

    void removeItem(int productId) {
        if (items.remove(productId) != null) {
            System.out.println("Item removed from cart.");
        } else {
            System.out.println("Item not found in cart.");
        }
    }

    void updateQuantity(int productId, int newQuantity) {
        CartItem item = items.get(productId);
        if (item == null) {
            System.out.println("Item not found in cart.");
            return;
        }
        if (newQuantity <= 0) {
            items.remove(productId);
            System.out.println("Item removed from cart.");
        } else {
            item.quantity = newQuantity;
            System.out.println("Quantity updated.");
        }
    }

    void viewCart() {
        if (items.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n----- Your Cart -----");
        double total = 0;
        for (CartItem item : items.values()) {
            System.out.printf("%-20s Qty:%-3d Rs.%-8.2f Subtotal: Rs.%.2f%n",
                    item.product.name, item.quantity, item.product.price, item.getSubtotal());
            total += item.getSubtotal();
        }
        System.out.printf("Total: Rs.%.2f%n", total);
        System.out.println("----------------------");
    }

    void checkout() {
        if (items.isEmpty()) {
            System.out.println("Cart is empty. Add items before checkout.");
            return;
        }
        viewCart();
        System.out.println("Order placed successfully. Thank you for shopping!");
        items.clear();
    }
}

// Entry point — menu-driven console application
public class ShoppingCart {
    public static void main(String[] args) {
        List<Product> catalog = Arrays.asList(
                new Product(1, "Wireless Headphones", 1499),
                new Product(2, "Smart Watch", 2999),
                new Product(3, "Bluetooth Speaker", 1299),
                new Product(4, "Laptop Backpack", 999),
                new Product(5, "Mechanical Keyboard", 2499),
                new Product(6, "USB-C Hub", 799)
        );

        Cart cart = new Cart();
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== SHOPPING CART MENU =====");
            System.out.println("1. View Products");
            System.out.println("2. Add to Cart");
            System.out.println("3. Update Quantity");
            System.out.println("4. Remove from Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a number.");
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n----- Products -----");
                    for (Product p : catalog) {
                        System.out.println(p);
                    }
                    break;

                case 2:
                    System.out.print("Enter product id: ");
                    int addId = Integer.parseInt(sc.nextLine().trim());
                    Product toAdd = findProduct(catalog, addId);
                    if (toAdd == null) {
                        System.out.println("Invalid product id.");
                        break;
                    }
                    System.out.print("Enter quantity: ");
                    int qty = Integer.parseInt(sc.nextLine().trim());
                    if (qty <= 0) {
                        System.out.println("Quantity must be positive.");
                        break;
                    }
                    cart.addItem(toAdd, qty);
                    break;

                case 3:
                    System.out.print("Enter product id to update: ");
                    int updId = Integer.parseInt(sc.nextLine().trim());
                    System.out.print("Enter new quantity: ");
                    int newQty = Integer.parseInt(sc.nextLine().trim());
                    cart.updateQuantity(updId, newQty);
                    break;

                case 4:
                    System.out.print("Enter product id to remove: ");
                    int remId = Integer.parseInt(sc.nextLine().trim());
                    cart.removeItem(remId);
                    break;

                case 5:
                    cart.viewCart();
                    break;

                case 6:
                    cart.checkout();
                    break;

                case 7:
                    running = false;
                    System.out.println("Thank you for visiting!");
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
        sc.close();
    }

    private static Product findProduct(List<Product> catalog, int id) {
        for (Product p : catalog) {
            if (p.id == id) return p;
        }
        return null;
    }
}
