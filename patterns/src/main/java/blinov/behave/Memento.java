package blinov.behave;

public class Memento {
	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}

class Originator {
	private String state = "Initial state";

	public void setMemento(Memento memento) {
		state = memento.getState();
	}

	public Memento createMemento() {
		Memento memento = new Memento();
		memento.setState(state);
		return memento;
	}

	public void execute() {
		state = "New state";
	}

	@Override
	public String toString() {
		return state;
	}
}

class Caretaker {
	private Memento memento;

	public Caretaker(Memento memento) {
		this.memento = memento;
	}

	public Memento getMemento() {
		return memento;
	}
}

class RunnerMemento {
	public static void main(String[] args) {
		Originator originator = new Originator();
		Caretaker caretaker = new Caretaker(originator.createMemento());
		System.out.println(originator);
		originator.execute();
		System.out.println(originator);
		originator.setMemento(caretaker.getMemento());
		System.out.println(originator);
	}
}