package blinov.behave;

import static java.lang.System.out;

import java.util.HashMap;
import java.util.Iterator;

interface CustomIterator<T> {
	void first();

	void next();

	boolean isDone();

	T currentItem();
}

class ExamsIterator implements CustomIterator<String> {
	private StudentSession session;

	private String current;
	private java.util.Iterator<String> iterator;
	private boolean done;

	public ExamsIterator(StudentSession session) {
		this.session = session;
	}

	public String currentItem() {
		return new StringBuilder(current).reverse().toString();
	}

	public void first() {
		iterator = session.getExams().keySet().iterator();
		next();
	}

	public boolean isDone() {
		return done;
	}

	public void next() {
		if (iterator.hasNext()) {
			current = iterator.next();
		} else {
			done = true;
		}
	}
}

interface Aggregate {
	CustomIterator<String> createIterator();
}

class StudentSession implements Aggregate, Iterable<String> {
	private HashMap<String, Integer> exams = new HashMap<String, Integer>();

	public CustomIterator<String> createIterator() {
		CustomIterator<String> iterator = new ExamsIterator(this);
		iterator.first();
		return iterator;
	}

	public void addExam(String name, Integer mark) {
		exams.put(name, mark);
	}

	public Integer getMark(String name) {
		return exams.get(name);
	}

	public HashMap<String, Integer> getExams() {
		return exams;
	}

	@Override
	public Iterator<String> iterator() {
		return exams.keySet().iterator();
	}
}

class IteratorExample {
	public static void main(String[] args) {
		StudentSession session = new StudentSession();
		session.addExam("mathematics", 9);
		session.addExam("russian", 10);
		session.addExam("philosophy", 8);
		System.out.println("The list of exams: ");
		CustomIterator<String> iterator = session.createIterator();
		while (!iterator.isDone()) {
			out.println(iterator.currentItem());
			iterator.next();
		}

		// ����� ��������� ���� ��������!!!
		for (String str : session) {
			out.println(str);
		}
	}
}
