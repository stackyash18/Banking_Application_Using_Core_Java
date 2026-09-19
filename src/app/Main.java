package app;

import service.BankService;
import service.impl.BankServiceImpl;

import java.util.Scanner;

public class Main {
    static void main() {
        Scanner input = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
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

            switch(choice)
            {
                case "1" -> openAccount(input, bankService);
                case "2" -> deposit(input, bankService);
                case "3" -> withdraw(input, bankService);
                case "4" -> transfer(input, bankService);
                case "5" -> getStatement(input, bankService);
                case "6" -> listAccount(input, bankService);
                case "7" -> searchAccountsByCustomerName(input, bankService);
                case "0" -> running = false;
            }
        }

    }

    private static void openAccount(Scanner input, BankService bankService) {
        System.out.print("Customer Name: ");
        String name = input.nextLine().trim();
        System.out.print("Customer email: ");
        String email = input.nextLine().trim();
        System.out.print("Account Type (SAVINGS/CURRENT): ");
        String accountType = input.nextLine().trim();
        System.out.print("Initial deposit(Optional, leave it for 0): ");
        String amountStr = input.nextLine().trim();
        Double initialAmount = Double.valueOf(amountStr);
        String accountNumber = bankService.openAccount(name, email, accountType);
        if(initialAmount > 0)
            bankService.deposit(accountNumber, initialAmount, "Initial Deposit");
        System.out.println("Account Opened Successfully... \nYour Account Number is: " + accountNumber);
    }


    private static void deposit(Scanner input, BankService bankService) {
        System.out.print("Account Number: ");
        String  accountNumber = input.nextLine().trim();
        System.out.print("Enter the amount(in ₹): ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.deposit(accountNumber, amount, "Deposit");
        System.out.println("Amount deposited successfully..");
    }

    private static void withdraw(Scanner input, BankService bankService) {
        System.out.print("Account Number: ");
        String  accountNumber = input.nextLine().trim();
        System.out.print("Enter the amount(in ₹): ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "Withdrawal");
        System.out.println("Amount Withdrawn successfully..");
    }

    private static void transfer(Scanner input, BankService bankService) {
        System.out.print("From Account: ");
        String from = input.nextLine().trim();
        System.out.print("To Account: ");
        String to = input.nextLine().trim();
        System.out.print("Enter the Amount: ");
        Double amount = Double.valueOf(input.nextLine().trim());
        bankService.transfer(from, to, amount, "Transfer");
        System.out.println("The amount has been transferred successfully from the account: " + from + "\nto the account: " + to);

    }

    private static void getStatement(Scanner input, BankService bankService) {
        System.out.print("Enter Your Account Number: ");
        String account = input.nextLine().trim();
        bankService.getStatement(account).forEach(t -> {
            System.out.println(t.getTimeStamp() + " | " + t.getType() + " | " + t.getAmount() + " | " + t.getNote());
        });
    }

    private static void listAccount(Scanner input, BankService bankService) {
        bankService.listAccounts().forEach(a -> {
            System.out.println("Account Number: " + a.getAccountNumber() + " |  Account Type:  " + a.getAccountType() + " |  Balance: " + a.getBalance());
        });
    }

    private static void searchAccountsByCustomerName(Scanner input, BankService bankService) {
        System.out.println("Customer Name contains: ");
        String p = input.nextLine().trim();
        bankService.searchAccountsByCustomerName(p).forEach(account ->
                System.out.println(account.getAccountNumber() + " | " + account.getAccountType() + " | " + account.getBalance())
        );
    }
}
