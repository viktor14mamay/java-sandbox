package blinov.structural;

interface IEmployee {
	void openTask();

	void reopenTask();

	void resolveTask();
}

abstract class EmployeeDecorator implements IEmployee {
	protected Employee employee;

	public EmployeeDecorator() {
		super();
	}

	public EmployeeDecorator(Employee employee) {
		this.employee = employee;
	}

	@Override
	public void resolveTask() {
		employee.resolveTask();
	}

	@Override
	public void openTask() {
		employee.openTask();
	}

	@Override
	public void reopenTask() {
		employee.reopenTask();
	}
}

final class Employee implements IEmployee {
	private String name;

	public Employee() {
	}

	public Employee(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	@Override
	public void openTask() {
		System.out.println(this.getName() + " open task");
	}

	@Override
	public void reopenTask() {
		System.out.println(this.getName() + " reopen task");
	}

	@Override
	public void resolveTask() {
		System.out.println(this.getName() + " resolve task");
	}
}

class DeveloperDecorator extends EmployeeDecorator {
	public DeveloperDecorator(Employee employee) {
		super(employee);
	}

	@Override
	public void openTask() {
		super.openTask();
		startProgress();
	}

	@Override
	public void reopenTask() {
		super.reopenTask();
		startProgress();
	}

	@Override
	public void resolveTask() {
		super.resolveTask();
		stopProgress();
	}

	public void startProgress() {
		System.out.println(employee.getName() + " starting task");
	}

	public void stopProgress() {
		System.out.println(employee.getName() + " stopping task");
	}
}

class TesterDecorator extends EmployeeDecorator {
	public TesterDecorator(Employee employee) {
		super(employee);
	}

	@Override
	public void openTask() {
		super.openTask();
		testing();
	}

	@Override
	public void reopenTask() {
		super.reopenTask();
		testing();
	}

	@Override
	public void resolveTask() {
		reporting();
		super.resolveTask();
	}

	public void testing() {
		System.out.println(employee.getName() + " testing task");
	}

	public void reporting() {
		System.out.println(employee.getName() + " create report");
	}
}

class TeamLeadDecorator extends EmployeeDecorator {
	public TeamLeadDecorator(Employee employee) {
		super(employee);
	}

	@Override
	public void openTask() {
		super.openTask();
		assignTask();
	}

	@Override
	public void reopenTask() {
		super.reopenTask();
		changeEmployee();
	}

	@Override
	public void resolveTask() {
		super.resolveTask();
		closeTask();
	}

	public void assignTask() {
		System.out.println(employee.getName() + " is assigning task");
	}

	public void changeEmployee() {
		System.out.println(employee.getName() + " is changing employee");
	}

	public void closeTask() {
		System.out.println(employee.getName() + " is closing task");
	}
}

class RunnerDecorator {
	public static void main(String[] args) {
		IEmployee employee = new TesterDecorator(new Employee("Ivanov"));
		employee.reopenTask();
		employee = new TeamLeadDecorator(new Employee("Petrov"));
		employee.openTask();
	}
}