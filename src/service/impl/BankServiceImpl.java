package service.impl;

import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import exceptions.AccountNotFoundException;
import exceptions.InsufficientFundException;
import exceptions.ValidationException;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;
import service.BankService;
import util.Validation;

import javax.swing.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class BankServiceImpl implements BankService {

    private final AccountRepository accountRepository = new AccountRepository();
    private final TransactionRepository transactionRepository = new TransactionRepository();
    private final CustomerRepository customerRepository = new CustomerRepository();

    private final Validation<String> validateName = name -> {
        if(name == null || name.isBlank()) throw new ValidationException("Name is required");
    };

    private final Validation<String> validateEmail = email -> {
            if(email == null || !email.contains("@")) throw new ValidationException("Email is required");
    };

    private final Validation<String> validateAccountType = type -> {
        if(type == null || !(type.equalsIgnoreCase("SAVINGS") || !type.equalsIgnoreCase("CURRENT")))
            throw new ValidationException("Type must be SAVINGS or CURRENT");
    };

    private final Validation<Double> validateAmountPositive = amount -> {
        if(amount == null || amount < 0)
            throw new ValidationException("Amount must be positive");
    };

    @Override
    public String openAccount(String name, String email, String accountType) {
        validateName.validate(name);
        validateEmail.validate(email);
        validateAccountType.validate(accountType);

        // Create customer
        String customerId = UUID.randomUUID().toString();
        Customer c = new Customer(customerId, name, email);
        customerRepository.save(c);

        //change later --> 10 + 1 = AC11
//        String accountNumber = UUID.randomUUID().toString();
        int temp = accountRepository.findAll().size() + 1;
        String accountNumber = String.format("AC%06d", temp);
        Account account = new Account(accountNumber, customerId, (double) 0, accountType);
        accountRepository.save(account);
        return accountNumber;
    }

    @Override
    public List<Account> listAccounts() {
        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        validateAmountPositive.validate(amount);
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found: " + accountNumber));
        account.setBalance(account.getBalance() + amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(), Type.DEPOSIT);
        transactionRepository.add(transaction);
    }

    @Override
    public void withdraw(String accountNumber, Double amount, String note) {
        validateAmountPositive.validate(amount);
        Account account = accountRepository.findByNumber(accountNumber)
                .orElseThrow(() -> new InsufficientFundException("Account Not Found: " + accountNumber));
        if(account.getBalance().compareTo(amount) < 0)
        {
            throw new InsufficientFundException("Insufficient Balance");
        }
        account.setBalance(account.getBalance() - amount);
        Transaction transaction = new Transaction(account.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note, LocalDateTime.now(),Type.WITHDRAW);
        transactionRepository.add(transaction);

    }

    @Override
    public void transfer(String fromAcc , String toAcc, Double amount, String note) {
        validateAmountPositive.validate(amount);
        if(fromAcc.equals(toAcc))
            throw new ValidationException("Cannot transfer to your own account.");

        Account from = accountRepository.findByNumber(fromAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found: " + fromAcc));
        Account to = accountRepository.findByNumber(toAcc)
                .orElseThrow(() -> new AccountNotFoundException("Account Not Found: " + toAcc));
        if(from.getBalance().compareTo(amount) < 0)
            throw new InsufficientFundException("Insufficient Balance");

        from.setBalance(from.getBalance() - amount);
        to.setBalance(to.getBalance() +  amount);

        transactionRepository.add(new Transaction(from.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(),Type.TRANSFER_OUT));

        transactionRepository.add(new Transaction(to.getAccountNumber(),
                amount, UUID.randomUUID().toString(), note,
                LocalDateTime.now(),Type.TRANSFER_IN));
    }

    @Override
    public List<Transaction> getStatement(String account) {
        return transactionRepository.findByAccount(account).stream()
                .sorted(Comparator.comparing(Transaction::getTimeStamp))
                .collect(Collectors.toList());
    }

    @Override
    public List<Account> searchAccountsByCustomerName(String p) {
        String query = (p == null) ? "" : p.toLowerCase();
        List<Account> result = new ArrayList<>();
        for(Customer c : customerRepository.findAll()) {
            if(c.getName().toLowerCase().contains(query))
            {
                result.addAll(accountRepository.findByCustomerId(c.getId()));
            }
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }

    private String getAccountNumber() {
        int size = accountRepository.findAll().size() + 1;
        return String.format("AC%06d", size);
    }
}
