package com.epam.rd.autotasks.wallet;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

public class WalletImplementation implements Wallet {
    private final List<Account> accounts;
    private final PaymentLog log;
    private final ReentrantLock lock = new ReentrantLock();

    public WalletImplementation(List<Account> accounts, PaymentLog log) {
        this.accounts = accounts;
        this.log = log;
    }

    @Override
    public void pay(String recipient, long amount) throws Exception {
        lock.lock();
        try {
            Account account = getAccount(recipient, amount);

            account.pay(amount);
            log.add(account, recipient, amount);
        } finally {
            lock.unlock();
        }
    }

    private Account getAccount(String recipient, long amount) throws ShortageOfMoneyException {
        Optional<Account> accountOptional = accounts
                .stream()
                .filter((account) -> account.balance() >= amount)
                .findFirst();

        if (accountOptional.isEmpty()) {
            throw new ShortageOfMoneyException(recipient, amount);
        }

        return accountOptional.get();
    }
}
