import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ATM atm = new ATM();

        System.out.println("===== WELCOME TO ATM SIMULATOR =====");
        System.out.print("Account No: ");
        int accNo = sc.nextInt();
        System.out.print("PIN: ");
        int pin = sc.nextInt();

        Account acc = atm.login(accNo, pin);
        if (acc == null) {
            System.out.println("Invalid account number or PIN.");
            return;
        }
        System.out.println("\nHello, " + acc.getName() + "!");

        int choice;
        do {
            System.out.println("\n1. Check Balance\n2. Deposit\n3. Withdraw\n4. Mini Statement\n5. Exit");
            System.out.print("Choose: ");
            choice = sc.nextInt();
            switch (choice) {
                case 1: System.out.println("Balance: " + atm.checkBalance(accNo)); break;
                case 2: System.out.print("Amount: "); atm.deposit(accNo, sc.nextDouble()); break;
                case 3: System.out.print("Amount: "); atm.withdraw(accNo, sc.nextDouble()); break;
                case 4: atm.miniStatement(accNo); break;
                case 5: System.out.println("Thank you for using our ATM!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 5);
    }
}