package blinov.behave;

import java.util.HashMap;

public interface Memento2 {
}

class RequestParameter {
	private HashMap<String, String> param = new HashMap<String, String>();

	public RequestParameter(HashMap<String, String> param) {
		this.param = param;
	}

	public Memento2 getMemento() {
		HashMap<String, String> state = (HashMap<String, String>) (param
				.clone());
		return new RequestParameterMemento(state);
	}

	public void setMemento(Memento2 object) {
		if (object instanceof RequestParameterMemento) {
			RequestParameterMemento memento = (RequestParameterMemento) object;
			param = memento.state;
		}
	}

	private class RequestParameterMemento implements Memento2 {
		private HashMap<String, String> state;

		RequestParameterMemento(HashMap<String, String> state) {
			this.state = state;
		}
	}

	public void addParam(String key, String value) {
		param.put(key, value);
	}

	public HashMap<String, String> getParams() {
		return param;
	}

	public void removeParam(String key) {
		param.remove(key);
	}

	public void clearParams() {
		param = new HashMap<String, String>();
	}
}

class Caretaker2 {
	private Memento2 memento;

	public Caretaker2(Memento2 memento) {
		this.memento = memento;
	}

	public Memento2 getMemento() {
		return memento;
	}
}

class RequestRunner {
	public static void main(String[] args) {
		HashMap<String, String> p = new HashMap<String, String>() {
			{
				this.put("1", "Winner");
			}
		};
		RequestParameter req = new RequestParameter(p); // originator
		System.out.println("first " + req.getParams());
		Memento2 memento = req.getMemento();
		Caretaker2 care = new Caretaker2(memento);

		req.addParam("1", "Loser");
		System.out.println("second " + req.getParams());
		
		memento = care.getMemento();
		req.setMemento(memento);
		System.out.println("undo to first " + req.getParams());
	}
}