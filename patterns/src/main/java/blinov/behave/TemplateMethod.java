package blinov.behave;

import static java.lang.System.out;

import java.util.ArrayList;

class User {
	private String login;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "User[login=" + login + "]";
	}

}

abstract class AbstractFramework {
	public AbstractFramework() {
	}

	protected abstract boolean check(User user);

	protected abstract ListPermission getAvaliablePermissions(User user);

	public void templateMethodLogin(User user) {
		if (!check(user)) {
			out.println("access denied for " + user);
		}
		out.println(getAvaliablePermissions(user));
	}
}

class ListPermission {
	private ArrayList<String> roles = new ArrayList<>();

	public void addRole(String role) {
		roles.add(role);
	}

	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (String role : roles) {
			b.append(role).append("\n");
		}
		return b.toString();
	}

}

class BaseFramework extends AbstractFramework {
	protected boolean check(User user) {
		System.out.println("check User");
		return user.getLogin().startsWith("admin");
	}

	protected ListPermission getAvaliablePermissions(User user) {
		System.out.println("list of user permissions");
		ListPermission p = new ListPermission();
		p.addRole("admin");
		p.addRole("reader");
		return p;
	}
}

class TemplateRunner {
	public static void main(String[] args) {
		User user1 = new User();
		user1.setLogin("admin1");
		AbstractFramework f = new BaseFramework();
		f.templateMethodLogin(user1);
		user1.setLogin("reader");
		f.templateMethodLogin(user1);
	}
}