package blinov.structural;

interface LoggerImplementor {
	void logToConsole();

	void logToFile();

	void logToSocket();
}

class MultiThreadedLogger implements LoggerImplementor {
	@Override
	public void logToConsole() {
		System.out.println("Multithreaded console log");
	}

	@Override
	public void logToFile() {
		System.out.println("Multithreaded file log");
	}

	@Override
	public void logToSocket() {
		System.out.println("Multithreaded socket log");
	}
}

class SingleThreadedLogger implements LoggerImplementor {
	@Override
	public void logToConsole() {
		System.out.println("Singlethreaded console log");
	}

	@Override
	public void logToFile() {
		System.out.println("Singlethreaded file log");
	}

	@Override
	public void logToSocket() {
		System.out.println("Singlethreaded socket log");
	}
}

abstract class Logger {
	protected LoggerImplementor logger;

	public Logger() {
	}

	public Logger(LoggerImplementor logger) {
		this.logger = logger;
	}

	public void setLogger(LoggerImplementor logger) {
		this.logger = logger;
	}

	public abstract void log();
}

class ConsoleLogger extends Logger {
	public ConsoleLogger() {
	}

	public ConsoleLogger(LoggerImplementor logger) {
		super(logger);
	}

	public void log() {
		logger.logToConsole();
	}
}

class FileLogger extends Logger {
	public FileLogger() {
	}

	public FileLogger(LoggerImplementor logger) {
		super(logger);
	}

	public void log() {
		logger.logToFile();
	}
}

class BridgeClientMain {
	public static void main(String[] args) {
		Logger logger = new ConsoleLogger(new SingleThreadedLogger());
		logger.log();
		logger.setLogger(new MultiThreadedLogger());
		logger.log();
		new FileLogger(new MultiThreadedLogger()).log();
	}
}