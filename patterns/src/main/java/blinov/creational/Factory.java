package blinov.creational;

abstract class AbstractOrder {
	public abstract void perform();
}

class SimpleOrder extends AbstractOrder {
	public void perform() {
		System.out.println("Simple order");
	}
}

class ExtendedOrder extends AbstractOrder {
	public void perform() {
		System.out.println("Extended order");
	}
}

abstract class AbstractOrderFactory {
	public abstract AbstractOrder getOrder();

	public void anOperation() {
		System.out.println("operation");
	}
}

class SimpleOrderFactory extends AbstractOrderFactory {
	@Override
	public SimpleOrder getOrder() {
		return new SimpleOrder();
	}
}

class ExtendedOrderFactory extends AbstractOrderFactory {
	@Override
	public ExtendedOrder getOrder() {
		return new ExtendedOrder();
	}
}

class Client1 {
	public void someOperation(AbstractOrderFactory factory) {
		AbstractOrder order = factory.getOrder();
		order.perform();
	}
}

class RunFactoryMethodSimplest {
	public static void main(String args[]) {
		
		Client1 c = new Client1();
		c.someOperation(new ExtendedOrderFactory());
		
		Client2 c2 = new Client2();
		c2.makeMediaFactoryWork(new VideoFactory());
	}
}