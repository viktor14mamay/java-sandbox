package blinov.behave;

import static java.lang.System.out;

interface ICommand {
	void execute();
}

enum CommandTypes {
	CREDITING, WITHDRAWING, BLOCKING;
}

class CreditingCommand implements ICommand {
	private Receiver receiver;

	public CreditingCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	public void execute() {
		receiver.action(CommandTypes.CREDITING);
	}
}

class WithdrawingCommand implements ICommand {
	private Receiver receiver;

	public WithdrawingCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	public void execute() {
		receiver.action(CommandTypes.WITHDRAWING);
	}
}

class BlockingCommand implements ICommand {
	private Receiver receiver;

	public BlockingCommand(Receiver receiver) {
		this.receiver = receiver;
	}

	public void execute() {
		receiver.action(CommandTypes.BLOCKING);
	}
}

class Receiver {
	private Account account;
	private double amount;
	private double interestRate;

	public Receiver(Account account, int amount, double interestRate) {
		this.account = account;
		this.amount = amount;
		this.interestRate = interestRate;
	}

	public void action(CommandTypes cmd) {
		switch (cmd) {
		case CREDITING:
			if (account.isBlocked()) {
				out.println("Sorry, the crediting account #" + account.getId()
						+ " is blocked!");
			} else {
				double balance = account.getBalance() * interestRate / 100;
				account.setBalance(balance);
				out.println("Crediting with " + interestRate
						+ " % interest rate to the account #" + account.getId());
			}
			break;
		case WITHDRAWING:
			if (account.isBlocked()) {
				out.println("Sorry, the withdraw account#" + account.getId()
						+ " is blocked!");
			} else {
				double balance = account.getBalance() - amount;
				account.setBalance(balance);
				out.println(amount + " is withdrawed from the account #"
						+ account.getId());
			}
			break;
		case BLOCKING:
			if (account.isBlocked()) {
				account.setBlocked(false);
				out.println("The account #" + account.getId() + " is unblocked");
			} else {
				account.setBlocked(true);
				out.println("The account #" + account.getId() + " is blocked");
			}
			break;
		}
	}
}

class Client {
	private Receiver cReceiver;

	public Client(Receiver receiver) {
		cReceiver = receiver;
	}

	public ICommand initCommand(CommandTypes cmd) {
		ICommand command = null;
		switch (cmd) {
		case CREDITING:
			out.println("Creating command CREDITING");
			command = new CreditingCommand(cReceiver);
			break;
		case WITHDRAWING:
			out.println("Creating command WITHDRAWING");
			command = new WithdrawingCommand(cReceiver);
			break;
		case BLOCKING:
			out.println("Creating command BLOCKING");
			command = new BlockingCommand(cReceiver);
			break;
		}
		return command;
	}
}

class Invoker {
	private ICommand mCommand;

	public Invoker(ICommand command) {
		mCommand = command;
	}

	public void invokeCommand() {
		System.out.println("Refer to command for execution");
		mCommand.execute();
	}
}

class Account {
	private int id;
	private double balance;
	private boolean blocked;

	public Account(int id, double balance, boolean blocked) {
		this.id = id;
		this.balance = balance;
		this.blocked = blocked;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}

class CommandRunner {
	public static void main(String[] args) {
		Account account = new Account(210012, 1100, false);
		Receiver receiver = new Receiver(account, 107, 10);
		Client client = new Client(receiver);

		ICommand cmdCrediting = client.initCommand(CommandTypes.CREDITING);
		Invoker invokerCrediting = new Invoker(cmdCrediting);
		invokerCrediting.invokeCommand();
		
		ICommand cmdWithdrawing = client.initCommand(CommandTypes.WITHDRAWING);
		Invoker invokerWithdrawing = new Invoker(cmdWithdrawing);
		invokerWithdrawing.invokeCommand();
		
		ICommand cmdBlocking = client.initCommand(CommandTypes.BLOCKING);
		Invoker invokerBlocking = new Invoker(cmdBlocking);
		invokerBlocking.invokeCommand();
	}
}