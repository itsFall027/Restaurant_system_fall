package ORDER_SYSTEM;
import MENU_DATA_HANDLING.MenuData;
import java.util.Scanner;

public class MainOrderSystem {
    static Scanner scanner = new Scanner(System.in);
    static Order[] orders = new Order[100];
    static OrderCount orderCount = new OrderCount(0);
    static int dineInOrTakeOut;
    static boolean isUserLoggedIn = false;  // Track user login status

    public static void main(String[] args) {
        int mainChoice;
        BreakfastMenu breakfastMenu = new BreakfastMenu(orders, orderCount);
        ChickenAndPlattersMenu chickenAndPlattersMenu = new ChickenAndPlattersMenu(orders, orderCount);
        Burger burger = new Burger(orders, orderCount);
        DrinksAndDessert drinksAndDessert = new DrinksAndDessert(orders, orderCount);
        Coffee coffee = new Coffee(orders, orderCount);
        Fries fries = new Fries(orders, orderCount);
        HandleMyOrder handleOrder = new HandleMyOrder(scanner, orders, orderCount, dineInOrTakeOut);

        displayDineInOrTakeOut();  // Display dine in or take out options

        do {
            System.out.println("\nWelcome to the Restaurant!");
            System.out.println("1. Breakfast Menu");
            System.out.println("2. Chicken And Platters");
            System.out.println("3. Burger Menu");
            System.out.println("4. Drinks & Desserts Menu");
            System.out.println("5. Coffee Menu");
            System.out.println("6. Fries Menu");
            System.out.println("7. My Order");
            System.out.println("0. Go Back");
            System.out.print("Please select an option: ");
            mainChoice = scanner.nextInt();

            switch (mainChoice) {
                case 1:
                    breakfastMenu.displayBreakfastMenu();
                    break;
                case 2:
                    chickenAndPlattersMenu.displayChickenAndPlatters();
                    break;
                case 3:
                    burger.displayBurgerMenu();
                    break;
                case 4:
                    drinksAndDessert.displayDrinksAndDessertsMenu();
                    break;
                case 5:
                    coffee.displayCoffeeMenu();
                    break;
                case 6:
                    fries.displayFriesMenu();
                    break;
                case 7:
                    handleOrder.handleMyOrder();
                    break;
                case 0:
                    displayDineInOrTakeOut();
                    break;
                default:
                    System.out.println("Invalid choice. Please select again.");
            }
        } while (true);
    }

    // Display Login and Register menu before Dine In/Take Out
    public static void displayLoginRegisterMenu() {
        int choice;
        while (!isUserLoggedIn) {
            System.out.println("\nWelcome! Please login or register to continue.");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    loginUser();
                    break;
                case 2:
                    registerUser();
                    break;
                case 3:
                    System.out.println("Exiting system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }

    // Simulate user login (can be connected to actual data storage)
    public static void loginUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // For demonstration, we assume any username/password combination is valid
        System.out.println("Login successful!");
        isUserLoggedIn = true;
    }

    // Simulate user registration (can be connected to actual data storage)
    public static void registerUser() {
        System.out.print("Enter first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter last name: ");
        String lastName = scanner.nextLine();
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Display success message for now (no actual storage handling in this snippet)
        System.out.println("Registration successful! You can now login.");
    }

    // Display Dine In or Take Out menu
    public static void displayDineInOrTakeOut() {
        int choice;
        while (true) {
            System.out.println("\nSelect an option:");
            System.out.println("1. Dine In");
            System.out.println("2. Take Out");
            System.out.println("3. Exit");
            System.out.print("Please select an option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    dineInOrTakeOut = 1;
                    return;
                case 2:
                    dineInOrTakeOut = 2;
                    return;
                case 3:
                    System.out.println("Exiting system.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please select again.");
            }
        }
    }

    public static void displayOrderSummary() {
        System.out.println("\nOrder Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count; i++) {
            String itemName = "";
            switch (orders[i].getCategory()) {
                case "Breakfast":
                    itemName = MenuData.breakfastItemNames[orders[i].getItemIndex()];
                    break;
                case "ChickenAndPlatters":
                    itemName = MenuData.chickenAndPlattersItemNames[orders[i].getItemIndex()];
                    break;
                case "Burger":
                    itemName = MenuData.burgerItemNames[orders[i].getItemIndex()];
                    break;
                case "DrinksAndDesserts":
                    itemName = MenuData.drinksAndDessertsItemNames[orders[i].getItemIndex()];
                    break;
                case "Coffee":
                    itemName = MenuData.coffeeItemNames[orders[i].getItemIndex()];
                    break;
                case "Fries":
                    itemName = MenuData.friesItemNames[orders[i].getItemIndex()];
                    break;
            }

            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
    }

    public static void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
        scanner.nextLine();
    }
}

class Order {
    private String category;
    private int itemIndex;
    private int quantity;
    private int price;

    public Order(String category, int itemIndex, int quantity, int price) {
        this.category = category;
        this.itemIndex = itemIndex;
        this.quantity = quantity;
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public int getItemIndex() {
        return itemIndex;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getPrice() {
        return price;
    }
}
