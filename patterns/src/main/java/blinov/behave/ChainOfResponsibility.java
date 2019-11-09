package blinov.behave;

import static java.lang.System.out;

import java.util.HashMap;

abstract class AbstractHandler {
	protected MapEmployee db = new MapEmployee();
	protected AbstractHandler successor = DefaultHandleRequest
			.getHandleRequest();

	public AbstractHandler(AbstractHandler sucessor) {
		this.successor = sucessor;
	}

	public AbstractHandler() {
	}

	public void setSuccessor(AbstractHandler successor) {
		this.successor = successor;
	}

	abstract public void handleRequest(Employee2 emp);

	public void chain(Employee2 user) {
		if (db.containsUser(user)) {
			handleRequest(user);
			successor.chain(user);
		} else {
			System.out.println("user doesn't exist");
		}
	}

	private static class DefaultHandleRequest extends AbstractHandler {
		private static DefaultHandleRequest handler = new DefaultHandleRequest();

		private DefaultHandleRequest() {
		}

		public static DefaultHandleRequest getHandleRequest() {
			return handler;
		}

		@Override
		public void chain(Employee2 user) { // always empty
			handleRequest(user);
		}

		@Override
		public void handleRequest(Employee2 user) {
			out.println("default handler");
		}
	}
}

class Authentification extends AbstractHandler {
	public Authentification() { // more code
	}

	@Override
	public void handleRequest(Employee2 user) {
		if (checkStatus(user)) {
			out.println("auth handler");
		}
	}

	public boolean checkStatus(Employee2 user) {
		boolean flag = true;
		System.out.println("check user status " + user);
		// check user status
		return flag;
	}
}

class RoleManager extends AbstractHandler {
	public RoleManager() {
	}

	@Override
	public void handleRequest(Employee2 user) {
		checkPermission();
		out.println("role handler");
	}

	public void checkPermission() {
		System.out.println("checking role");
		// checking role
	}
}

class TaskManager extends AbstractHandler {
	public TaskManager() { // more code
	}

	@Override
	public void handleRequest(Employee2 user) {
		assignTask();
		out.println("task handler");
	}

	public void assignTask() {
		System.out.println("assign task");
	}
}

class Employee2 {
	private int id;
	private String login;
	private String password;

	public Employee2(int id, String login, String password) {
		this.id = id;
		this.login = login;
		this.password = password;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public int getId() {
		return id;
	}

	@Override
	public int hashCode() {
		int result = id + ((login == null) ? 0 : login.hashCode());
		result = 31 * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee2 other = (Employee2) obj;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null) {
				return false;
			}
		} else if (!password.equals(other.password)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", login=" + login + ", password="
				+ password + "]";
	}
}

class MapEmployee {
	private HashMap<Integer, Employee2> users = new HashMap<Integer, Employee2>();

	public MapEmployee() {
		users.put(1, new Employee2(10, "admin", "passwordAdmin"));
		users.put(2, new Employee2(20, "employee", "passwordEmployee"));
		users.put(3, new Employee2(30, "user", "passwordUser"));
	}

	public HashMap<Integer, Employee2> getUsers() {
		return users;
	}

	public boolean containsUser(Employee2 emp) {
		return users.containsValue(emp);
	}
}

class ChainDemo {
	static public void main(String[] args) {
		Employee2 user = new Employee2(30, "user", "passwordUser");
		
		RoleManager rm = new RoleManager();
		Authentification auth = new Authentification();
		TaskManager tm = new TaskManager();
		auth.setSuccessor(rm);
		//rm.setSuccessor(tm);
		System.out.println("----chain--start----");
		auth.chain(user);
	}
}