package app;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to Banking Application.");
        boolean running = true;
        while(running) {
            System.out.println("""
                    1) Open Account
                    2) Deposit
                    3) Withdraw
                    4) Transfer
                    5) Account Statement
                    6) List Accounts
                    7) Search Accounts by Customer Name
                    0) Exit
                    """);
            System.out.print("CHOOSE: ");
            String choice = input.nextLine().trim();
            System.out.println("CHOICE: " + choice);
        }

    }
}
