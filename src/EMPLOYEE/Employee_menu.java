package EMPLOYEE;
import java.util.Scanner;

public class Employee_menu {
	    public static void main(String[] args) {
	        Scanner scanner = new Scanner(System.in);
	        boolean running = true;

	        while (running) {
	            System.out.println("Employee Menu:");
	            System.out.println("1. Ticket Order");
	            System.out.println("2. View Pending Orders");
	            System.out.println("3. Mark Orders as Completed");
	            System.out.println("4. Cancel Orders");
	            System.out.println("5. Logout");
	            System.out.print("Choose an option: ");

	            int choice = scanner.nextInt();

	            switch (choice) {
	                case 1:
	                    TicketOrder.handleOrder();
	                    break;
	                case 2:
	                    ViewPendingOrders.displayPendingOrders();
	                    break;
	                case 3:
	                    MarkOrdersCompleted.completeOrder();
	                    break;
	                case 4:
	                    CancelOrders.cancelOrder();
	                    break;
	                case 5:
	                    System.out.println("Logging out...");
	                    running = false;
	                    break;
	                default:
	                    System.out.println("Invalid option. Please try again.");
	            }
	        }

	        scanner.close();
	    }
	}


