package blinov.behave;

public interface Visitor {
	void visit(DVDRenting service);

	void visit(ApplianceRenting service);
}

class StandardVisitor implements Visitor {
	@Override
	public void visit(DVDRenting service) {
		System.out.println("Standard DVD renting service");
	}

	@Override
	public void visit(ApplianceRenting service) {
		System.out.println("Standard Appliance renting service");
	}
}

class DiscountVisitor implements Visitor {
	@Override
	public void visit(DVDRenting service) {
		System.out.println("Discount DVD renting service");
	}

	@Override
	public void visit(ApplianceRenting service) {
		System.out.println("Discount Appliance renting service");
	}
}

class PenaltyRateVisitor implements Visitor {
	@Override
	public void visit(DVDRenting service) {
		System.out.println("Penalty DVD renting service");
	}

	@Override
	public void visit(ApplianceRenting service) {
		System.out.println("Penalty Appliance renting service");
	}
}

interface Visitable {
	void accept(Visitor v);
}

abstract class Renting implements Visitable {
	private int term;

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
}

class DVDRenting extends Renting {
	private int numberDisks;

	public int getNumberDisks() {
		return numberDisks;
	}

	public void setNumberDisks(int numberDisks) {
		this.numberDisks = numberDisks;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

class ApplianceRenting extends Renting {
	private double redemptionPrice;

	public double getRedemptionPrice() {
		return redemptionPrice;
	}

	public void setRedemptionPrice(double redemptionPrice) {
		this.redemptionPrice = redemptionPrice;
	}

	@Override
	public void accept(Visitor v) {
		v.visit(this);
	}
}

