package blinov.behave;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

interface PhoneOperatorMediator {
	long generatePhoneNumber();

	void addPhoneSubscriber(PhoneSubsciber ps);

	boolean isSupported(PhoneSubsciber ps);

	boolean connect(PhoneSubsciber ps1, PhoneSubsciber ps2);

	boolean disconnect(PhoneSubsciber ps);
}

class ConcretePhoneOperatorMediator implements PhoneOperatorMediator {
	private Set<PhoneSubsciber> subscribers;
	private Map<PhoneSubsciber, PhoneSubsciber> connections;
	private long nextPhoneNumber;

	public ConcretePhoneOperatorMediator() {
		subscribers = new HashSet<PhoneSubsciber>();
		connections = new HashMap<PhoneSubsciber, PhoneSubsciber>();
		nextPhoneNumber = 200_00_01;
	}

	@Override
	public long generatePhoneNumber() {
		return nextPhoneNumber++;
	}

	@Override
	public void addPhoneSubscriber(PhoneSubsciber ps) {
		subscribers.add(ps);
	}

	@Override
	public boolean connect(PhoneSubsciber ps1, PhoneSubsciber ps2) {
		if (!isSupported(ps1) || !isSupported(ps2)) {
			return false;
		}
		if (connections.containsKey(ps1) || connections.containsKey(ps2)) {
			System.out.println("Line is busy...");
			return false;
		}
		connections.put(ps1, ps2);
		connections.put(ps2, ps1);
		System.out.println(ps1 + " connected to " + ps2);
		return true;
	}

	@Override
	public boolean disconnect(PhoneSubsciber ps1) {
		if (connections.containsKey(ps1)) {
			PhoneSubsciber ps2 = connections.get(ps1);
			connections.remove(ps1);
			connections.remove(ps2);
			System.out.println(ps1 + " disconnected fom " + ps2);
			return true;
		} else {
			System.out.println(ps1 + " not connected to any subscriber");
			return false;
		}
	}

	@Override
	public boolean isSupported(PhoneSubsciber ps) {
		boolean isSupported = subscribers.contains(ps);
		if (!isSupported) {
			System.out.println("Not supported " + ps);
		}
		return isSupported;
	}
}

class PhoneSubsciber {
	private PhoneOperatorMediator phoneOperator;
	private long phoneNumber;

	public PhoneSubsciber(PhoneOperatorMediator phoneOperator) {
		this.phoneOperator = phoneOperator;
		this.phoneNumber = phoneOperator.generatePhoneNumber();
	}

	public boolean call(PhoneSubsciber otherPhoneSubsciber) {
		return phoneOperator.connect(this, otherPhoneSubsciber);
	}

	public boolean finishCall() {
		return phoneOperator.disconnect(this);
	}

	public long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "subscriber #" + phoneNumber;
	}

	@Override
	public int hashCode() {
		return (int)phoneNumber;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PhoneSubsciber other = (PhoneSubsciber) obj;
		if (phoneNumber != other.phoneNumber)
			return false;
		return true;
	}
}

class RunnerPhoneMediator {
	public static void main(String[] args) {
		ConcretePhoneOperatorMediator phoneOperator = new ConcretePhoneOperatorMediator();
		PhoneSubsciber ps1 = new PhoneSubsciber(phoneOperator);
		PhoneSubsciber ps2 = new PhoneSubsciber(phoneOperator);
		PhoneSubsciber ps3 = new PhoneSubsciber(phoneOperator);
		PhoneSubsciber ps4 = new PhoneSubsciber(phoneOperator);
		phoneOperator.addPhoneSubscriber(ps1);
		phoneOperator.addPhoneSubscriber(ps2);
		phoneOperator.addPhoneSubscriber(ps3);

		ps1.call(ps2);
		ps3.call(ps2);

		ps2.finishCall();
		ps3.call(ps2);

		ps4.call(ps1);
	}
}