package blinov.behave;

interface IState {
	void learning();

	void toCancel();
}

abstract class AbstractState implements IState {
	protected IState nextState;
}

class Teacher {
}

class Course { // ����� Context
	private long id;
	private String name;
	private Teacher teacher;
	private IState currentState;

	public Course(long id, String name, Teacher teacher) {
		this.id = id;
		this.name = name;
		this.teacher = teacher;
		currentState = new StartState();
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public IState getCurrentState() {
		System.out.println(currentState.getClass().getSimpleName());
		return currentState;
	}

	public void learning() {
		currentState.learning();
	}

	public void cancel() {
		currentState.toCancel();
	}

	// ������ ���������
	class StartState extends AbstractState {
		public void learning() {
			if (Course.this.teacher != null) {
				currentState = new ProcessState();
				System.out.println("�������� ������");
			} else {
				this.toCancel();
				System.out.println("�������� �� ������: ��� �������������");
			}
		}

		public void toCancel() {
			currentState = new CancelledState();
			System.out.println("���� �������� �������");
		}
	}

	class ProcessState extends AbstractState {
		{
			nextState = new EndState();
		}

		public void learning() {
			currentState = nextState;
			System.out.println("�������� ������� ���������");
		}

		public void toCancel() {
			throw new UnsupportedOperationException(
					"���������� �������� ��� ������� ����");
		}
	}

	class EndState extends AbstractState {
		{
			nextState = new StartState();
		}

		public void learning() {
			currentState = nextState;
			Course.this.setTeacher(new Teacher());
			System.out.println("���� ����� � ������ ��������");
		}

		public void toCancel() {
			throw new UnsupportedOperationException(
					"���� ��� ��������. ��� ���������� �������� ��� ������");
		}
	}

	class CancelledState extends AbstractState {
		{
			nextState = new EndState();
		}

		public void learning() {
			currentState = new StartState();
			Course.this.setTeacher(new Teacher());
			System.out.println("���� ����� � ����������� ��������");
		}

		public void toCancel() {
			throw new UnsupportedOperationException("���� ��� �������");
		}
	}
}