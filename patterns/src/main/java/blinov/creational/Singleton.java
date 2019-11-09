package blinov.creational;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class VolatileImpl {
	private static VolatileImpl instance = null;
	private static AtomicBoolean instanceCreated = new AtomicBoolean(false);
	private static Lock lock = new ReentrantLock();

	private VolatileImpl() {
	}

	public static VolatileImpl getInstance() {
		if (!instanceCreated.get()) {
			lock.lock();
			try {
				if (!instanceCreated.get()) {
					instance = new VolatileImpl();
					instanceCreated.set(true);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}
}
