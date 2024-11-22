package ORDER_SYSTEM;

import MENU_DATA_HANDLING.MenuData;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

class HandleMyOrder {
    private Scanner scanner;
    private Order[] orders;
    private OrderCount orderCount;
    private int dineInOrTakeOut;
    private static int orderNumber = 1;  // Starting order number

    public HandleMyOrder(Scanner scanner, Order[] orders, OrderCount orderCount, int dineInOrTakeOut) {
        this.scanner = scanner;
        this.orders = orders;
        this.orderCount = orderCount;
        this.dineInOrTakeOut = dineInOrTakeOut;
    }

    public void handleMyOrder() {
        if (orderCount.count == 0) {
            System.out.println("\nNo orders to display.");
            waitForEnter();
            return;
        }

        System.out.println("\nYour Order Summary:");
        int total = 0;
        for (int i = 0; i < orderCount.count; i++) {
            if (orders[i] == null) continue;

            String itemName = getItemName(orders[i]);
            System.out.printf("Item: %s\n", itemName);
            System.out.printf("Quantity: %d\n", orders[i].getQuantity());
            System.out.printf("Price: %d PHP\n", orders[i].getPrice());
            total += orders[i].getPrice();
            System.out.println();
        }
        System.out.printf("Total Price: %d PHP\n", total);
        System.out.println("Dining Option: " + (dineInOrTakeOut == 1 ? "Dine In" : "Take Out"));

        System.out.print("Input 1 to check out, 2 to cancel, or 0 to go back: ");
        int action = scanner.nextInt();

        if (action == 0) return;
        if (action == 1) handleCheckout(total);
        else if (action == 2) {
            System.out.println("Cancelling all orders...");
            resetOrders();
        } else {
            System.out.println("Invalid option selected.");
        }
    }

    private void waitForEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine(); // Consume the leftover newline
        scanner.nextLine(); // Wait for the user to press Enter
    }

    private String getItemName(Order order) {
        switch (order.getCategory()) {
            case "Breakfast":
                return MenuData.breakfastItemNames[order.getItemIndex()];
            case "ChickenAndPlatters":
                return MenuData.chickenAndPlattersItemNames[order.getItemIndex()];
            case "Burger":
                return MenuData.burgerItemNames[order.getItemIndex()];
            case "DrinksAndDesserts":
                return MenuData.drinksAndDessertsItemNames[order.getItemIndex()];
            case "Coffee":
                return MenuData.coffeeItemNames[order.getItemIndex()];
            case "Fries":
                return MenuData.friesItemNames[order.getItemIndex()];
            default:
                return "Unknown Item";
        }
    }

    private void handleCheckout(int total) {
        System.out.println("\nSelect Payment Method:");
        System.out.println("1. Cash");
        System.out.println("2. E-money");
        System.out.print("Select payment method: ");
        int paymentMethod = scanner.nextInt();

        if (paymentMethod != 1 && paymentMethod != 2) {
            System.out.println("Invalid payment method selected.");
            return;
        }

        File directory = new File("OrderRecords");
        if (!directory.exists()) {
            directory.mkdirs();
        }

        try {
            File file = new File(directory, "order_records.csv");
            FileWriter writer = new FileWriter(file, true);

            if (file.length() == 0) {
                writer.append("Order Number,Item,Quantity,Total Amount,Payment Method,Status,Date\n");
            }

            String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String paymentMethodStr = (paymentMethod == 1) ? "Cash" : "E-money";
            String status = "pending";
            String orderNumberStr = String.valueOf(orderNumber);

            for (int i = 0; i < orderCount.count; i++) {
                if (orders[i] == null) continue;

                String itemName = getItemName(orders[i]);
                int itemTotal = orders[i].getPrice();
                writer.append(String.format("%s,%s,%d,%d PHP,%s,%s,%s\n",
                        orderNumberStr, itemName, orders[i].getQuantity(), itemTotal, paymentMethodStr, status, currentDate));
            }

            writer.close();
            System.out.println("Order record saved successfully.");
            generateReceipt(orderNumberStr, paymentMethodStr, total);
            resetOrders();
            orderNumber++;
        } catch (IOException e) {
            System.out.println("Error saving the order record: " + e.getMessage());
        }
    }

    private void generateReceipt(String orderNumberStr, String paymentMethod, int total) throws IOException {
        File receiptDirectory = new File("Receipt");
        if (!receiptDirectory.exists()) {
            receiptDirectory.mkdirs();
        }

        File receiptFile = new File(receiptDirectory, "receipt.txt");
        FileWriter receiptWriter = new FileWriter(receiptFile, false);

        receiptWriter.write("====================================\n");
        receiptWriter.write("              ORDER #" + orderNumberStr + "\n");
        receiptWriter.write("====================================\n");

        receiptWriter.write("Order Summary:\n");
        for (int i = 0; i < orderCount.count; i++) {
            if (orders[i] == null) continue;

            String itemName = getItemName(orders[i]);
            receiptWriter.write(String.format("Item: %s\n", itemName));
            receiptWriter.write(String.format("Quantity: %d\n", orders[i].getQuantity()));
            receiptWriter.write(String.format("Price: %d PHP\n", orders[i].getPrice()));
            receiptWriter.write("\n");
        }

        receiptWriter.write(String.format("Total Price: %d PHP\n", total));
        receiptWriter.write("Dining Option: " + (dineInOrTakeOut == 1 ? "Dine In" : "Take Out") + "\n");
        receiptWriter.write("Payment Method: " + paymentMethod + "\n");
        receiptWriter.write("====================================\n");

        receiptWriter.close();
        System.out.println("Receipt saved successfully.");
    }

    private void resetOrders() {
        for (int i = 0; i < orders.length; i++) {
            orders[i] = null;
        }
        orderCount.count = 0;
    }
}
