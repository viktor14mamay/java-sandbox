package blinov.behave;

import java.util.ArrayList;
import java.util.EventObject;
import java.util.Iterator;

class Bid {
	private int id;
	private double price;
	private AuctionObserver observer;
	private boolean leader = false;
	private static double currentPrice;

	public Bid(int id, double price) {
		super();
		this.id = id;
		this.price = price;
	}

	public void changePrice(double price) {
		this.price = price;
		notifyObservers();
	}

	public int getId() {
		return id;
	}

	public double getPrice() {
		return price;
	}

	public static double getCurrentPrice() {
		return currentPrice;
	}

	public static void setCurrentPrice(double currentPrice) {
		Bid.currentPrice = currentPrice;
	}

	public void setLeader(boolean leader) {
		this.leader = leader;
	}

	public boolean isLeader() {
		return leader;
	}

	public void addObserver(AuctionObserver observer) {
		this.observer = observer;
		observer.addObservable(this);
	}

	public void removeObserver() {
		observer.removeObservable(this);
		observer = null;
	}

	private void notifyObservers() {
		if (observer != null) {
			observer.handleEvent(new BidEvent(this));
		}
	}

	@Override
	public String toString() {
		return "Bid [id=" + id + ", price=" + price + ", leader=" + leader
				+ "]";
	}
}

class BidEvent extends EventObject {
	public BidEvent(Bid source) {
		super(source);
	}

	@Override
	public Bid getSource() {
		return (Bid) super.getSource();
	}
}

interface Observer {
	void handleEvent(BidEvent event);
}

class AuctionObserver implements Observer {
	private ArrayList<Bid> list = new ArrayList<Bid>();

	public void addObservable(Bid bid) {
		list.add(bid);
	}

	public void removeObservable(Bid bid) {
		list.remove(bid);
	}

	public void handleEvent(BidEvent event) {
		Bid sourceBid = event.getSource();
		double newPrice = sourceBid.getPrice();
		double price = 0;
		Iterator<Bid> iterator = list.iterator();
		boolean lead = true;
		while (iterator.hasNext()) {
			Bid bid = iterator.next();
			price = bid.getPrice();
			if (newPrice > price) {
				bid.setLeader(false);
			} else if (newPrice < price) {
				lead = false;
			}
		}
		if (lead) {
			sourceBid.setLeader(true);
			Bid.setCurrentPrice(newPrice);
			System.out.println("Leading Price " + newPrice);
		}
	}
}

class DemoAuction {
	public static void main(String[] args) {
		Bid bid1 = new Bid(1, 34);
		Bid bid2 = new Bid(2, 35);
		Bid bid3 = new Bid(3, 14);
		Bid bid4 = new Bid(4, 20);
		Bid bid5 = new Bid(5, 39);
		AuctionObserver observer = new AuctionObserver();
		bid1.addObserver(observer);
		bid2.addObserver(observer);
		bid3.addObserver(observer);
		bid4.addObserver(observer);
		bid5.addObserver(observer);
		ArrayList<Bid> list = new ArrayList<Bid>();
		list.add(bid1);
		list.add(bid2);
		list.add(bid3);
		list.add(bid4);
		list.add(bid5);
		System.out.println("First:");
		bid3.changePrice(45); // ����������� ������ �������������
		for (Bid bid : list) {
			System.out.println(bid);
		}
		System.out.println("Second:");
		bid2.changePrice(40); // ����������� ������ �������������
		for (Bid bid : list) {
			System.out.println(bid);
		}
		System.out.println("Third:");
		bid4.changePrice(45); // ����������� ������ �������������
		for (Bid bid : list) {
			System.out.println(bid);
		}
	}
}