package repository;

import domain.Account;

import java.util.*;

public class AccountRepository {
    private static final Map<String, Account> accountsByNumber = new HashMap<>();

    public static List<Account> findByCustomerId(String customerId) {
        List<Account> result = new ArrayList<>();
        for(Account c : accountsByNumber.values())
        {
            if(c.getCustomerId().toLowerCase().contains(customerId))
            {
                result.add(c);
            }
        }
        return result;
    }

    public void save(Account account)
    {
        accountsByNumber.put(account.getAccountNumber(), account);
    }

    public List<Account> findAll() {
        return new ArrayList<>(accountsByNumber.values());
    }

    public Optional<Account> findByNumber(String accountNumber) {
        return Optional.ofNullable(accountsByNumber.get(accountNumber));
    }
}
