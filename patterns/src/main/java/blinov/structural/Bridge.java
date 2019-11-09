package blinov.structural;

interface IAction {
	double chargeInterest();

	double defineMaxSum();

	double increasePayment();
}

abstract class Action implements IAction {
	public void operation() {
	}
}

class RegularAction extends Action {
	private final static int MAX_SUM = 100;
	private final static int NORMAL_INTEREST = 3;

	@Override
	public double chargeInterest() {
		// charge NORMAL interest on account"
		return NORMAL_INTEREST;
	}

	@Override
	public double defineMaxSum() {
		// max sum is unbounded"
		return MAX_SUM;
	}

	@Override
	public double increasePayment() {
		return 0; // stub
	}
}

class UrgentAction extends Action {
	final static int MONTHLY_PAYMENT = 10; // read from base
	private final static int MAX_SUM = 50; // read from base

	@Override
	public double chargeInterest() {
		// charge LOW interest on accounts
		return 0; // stub
	}

	@Override
	public double defineMaxSum() {
		// check credit
		// max sum is bounded"
		return MAX_SUM;
	}

	@Override
	public double increasePayment() {
		// MAX increase in monthly payments
		return MONTHLY_PAYMENT;
	}
}

abstract class Account1 {
	private int id;
	private double amount;
	private Action action;

	protected Account1(Action action) {
		this.action = action;
	}

	public Action getAction() {
		return action;
	}

	protected void setAction(Action action) {
		this.action = action;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public abstract boolean takeSum(double sum);
}

class DepositAccount extends Account1 {
	public DepositAccount(Action action) {
		super(action);
	}

	@Override
	public boolean takeSum(double sum) {
		System.out.println("Performing by deposit account:");
		double interest = getAction().chargeInterest();
		double maxSum = getAction().defineMaxSum();
		boolean flag = false;
		if (maxSum > sum) {
			System.out.print("accountID: " + getId() + " : interest is "
					+ interest);
			System.out.print(" : recording of changes in the state accounts");
			System.out.println(": withdrawal : " + sum);
			flag = true;
		}
		return flag;
	}
}

class CreditAccount extends Account1 {
	private double limitCredit;

	public double getLimitCredit() {
		return limitCredit;
	}

	public void setLimitCredit(double limitCredit) {
		this.limitCredit = limitCredit;
	}

	public CreditAccount(Action action) {
		super(action);
	}

	public boolean takeSum(double sum) {
		System.out.println("Performing by credit account:");
		double maxSum = getAction().defineMaxSum();
		double payment = getAction().increasePayment();
		if (maxSum - payment > sum) {
			System.out.print("accountID: " + getId()
					+ " increase monthly payments: " + payment);
			System.out.print(": recording of changes in the state accounts");
			System.out.println(" : withdrawal :" + sum);
		}
		return true;
	}
}

class BridgeClient {
	public static void main(String[] args) {
		Action action = new RegularAction();
		DepositAccount depositAccount = new DepositAccount(action);
		depositAccount.setId(777);
		depositAccount.setAmount(1500);
		depositAccount.takeSum(200);
		action = new UrgentAction();
		depositAccount.setAction(action); // replacement action
		depositAccount.takeSum(100);
		new CreditAccount(action).takeSum(50);
	}
}