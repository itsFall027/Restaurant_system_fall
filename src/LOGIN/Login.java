package LOGIN;
import java.util.List;
import java.util.Scanner;

public class Login {
    private List<Customer> customers;
    private Scanner scanner = new Scanner(System.in);

    public Login(List<Customer> customers) {
        this.customers = customers;
    }

    public void loginCustomer() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Validate username and password
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username) && customer.getPassword().equals(password)) {
                System.out.println("Login successful! Welcome, " + customer.getFirstName() + " " + customer.getLastName());
                return;
            }
        }

        System.out.println("Invalid username or password.");
    }
}
