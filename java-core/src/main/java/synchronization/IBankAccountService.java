package synchronization;

public interface IBankAccountService {
    void deposit(long amount);

    void withdraw(long amount);

    long getBalance();
}
