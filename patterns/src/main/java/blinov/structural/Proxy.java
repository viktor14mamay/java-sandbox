package blinov.structural;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;

class Connection {
	public void performTask() {

	}
}

class ProxyConnection extends Connection {
	private Connection con;

	public ProxyConnection(Connection con) {
		this.con = con;
	}

	@Override
	public void performTask() {
		con.performTask();
	}
}

class ConnectionPool<T extends Connection> {
	private ArrayBlockingQueue<T> connectionQueue;

	public ConnectionPool(final int POOL_SIZE) throws SQLException {
		connectionQueue = new ArrayBlockingQueue<T>(POOL_SIZE);
		for (int i = 0; i < POOL_SIZE; i++) {
			T connection = null;
			connectionQueue.offer(connection);
		}
	}

	public T getConnection() throws InterruptedException {
		T connection = null;
		connection = connectionQueue.take();
		return connection;
	}

	public void closeConnection(T connection) {
		connectionQueue.offer(connection); // �������� ������ ����������
	}
}

class RunnerProxy {
	public static void main(String[] args) {
		try {
			ConnectionPool<ProxyConnection> pool = new ConnectionPool<>(20);
			ProxyConnection con = pool.getConnection();
			// some code here...
			pool.closeConnection(con);
		} catch (SQLException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}