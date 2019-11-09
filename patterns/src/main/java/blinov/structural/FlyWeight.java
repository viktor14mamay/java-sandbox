package blinov.structural;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

abstract class AbstarctAnt {
	protected int strength;
	protected int dexterity;
	protected int speed;

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDexterity() {
		return dexterity;
	}

	public void setDexterity(int dexterity) {
		this.dexterity = dexterity;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}

class Termes extends AbstarctAnt implements Serializable {
}

class Warrior extends Termes implements Serializable {
	public Warrior() {
		strength = 10;
		dexterity = 4;
		speed = 6;
	}
}

class Worker extends Termes implements Serializable {
	public Worker() {
		strength = 6;
		dexterity = 9;
		speed = 10;
	}
}

enum TermesType {
	WORKER, WARRIOR
}

class EuTermes implements Serializable {
	private Termes ant;
	private int health;
	private float xCoord;
	private float yCoord;

	public EuTermes(String type, float xCoord, float yCoord) {
		this.ant = TermesFactory.getClassFromFactory(type);
		health = 180;
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public float getXCoord() {
		return xCoord;
	}

	public void setXCoord(float xCoord) {
		this.xCoord = xCoord;
	}

	public float getYcoord() {
		return yCoord;
	}

	public void setYcoord(float ycoord) {
		this.yCoord = ycoord;
	}

	public void go() {
	}

	public void fight() {
	}
}

class TermesFactory {
	private static TreeMap<String, Termes> map = new TreeMap<>();

	public static Termes getClassFromFactory(String name) {
		if (map.containsKey(name)) {
			return map.get(name);
		} else {
			TermesType type = TermesType.valueOf(name.toUpperCase());
			switch (type) {
			case WORKER: {
				Worker worker = new Worker();
				map.put(name, worker);
				return worker;
			}
			case WARRIOR: {
				Warrior warrior = new Warrior();
				map.put(name, warrior);
				return warrior;
			}
			default:
				throw new EnumConstantNotPresentException(TermesType.class,
						type.name());
			}
		}
	}
}

class FlyweightRunner {
	private final static int SIZE = 4_000_000;

	public static void main(String[] args) {
		ArrayList<EuTermes> legion = new ArrayList<>();
		int i = 0;
		try {
			for (i = 0; i < SIZE; i++) {
				legion.add(new EuTermes("Worker", 12.3f, 10.1f));
			}
		} catch (OutOfMemoryError e) {
			System.err.println("OutOfMemoryError for Termes Worker");
		} finally {
			System.out.println("Instance: " + i);
		}
	}
}