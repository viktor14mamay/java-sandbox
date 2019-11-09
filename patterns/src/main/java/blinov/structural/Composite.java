package blinov.structural;

import java.util.ArrayList;

interface Component {
	void send();

	void receive();

	int countChannels();
}

abstract class ChannelLeaf implements Component {
	private int id;

	public ChannelLeaf(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int countChannels() {
		return 1;
	}
}

class TCPChannel extends ChannelLeaf {
	public TCPChannel(int id) {
		super(id);
	}

	@Override
	public void send() {
		System.out.println("\ttcp send " + getId());
	}

	@Override
	public void receive() {
		System.out.println("\ttcp receive");
	}
}

class UDPChannel extends ChannelLeaf {
	public UDPChannel(int id) {
		super(id);
	}

	@Override
	public void send() {
		System.out.println("\tudp send " + getId());
	}

	@Override
	public void receive() {
		System.out.println("\tudp receive");
	}
}

class CompositeTool implements Component {
	private int id;
	private ArrayList<Component> channels;

	public CompositeTool(int toolId) {
		this.id = toolId;
		channels = new ArrayList<Component>();
	}

	public void add(Component channel) {
		channels.add(channel);
	}

	public void remove(Component channel) {
		channels.remove(channel);
	}

	public int countChannels() {
		int count = 0;
		for (Component channel : channels) {
			count += channel.countChannels();
		}
		return count;
	}

	@Override
	public void send() {
		System.out.println("Composite Tool #" + id + ", size tool: "
				+ channels.size() + ", number channels: " + countChannels());
		for (Component channel : channels) {
			channel.send();
		}
	}

	@Override
	public void receive() {
		// similar to send
	}
}

class RunComposite {
	public static void main(String[] args) {
		TCPChannel channel1 = new TCPChannel(1);
		TCPChannel channel2 = new TCPChannel(2);
		UDPChannel channel3 = new UDPChannel(3);
		UDPChannel channel4 = new UDPChannel(4);
		UDPChannel channel9 = new UDPChannel(9);
		CompositeTool mainTool = new CompositeTool(777);
		CompositeTool childTool1 = new CompositeTool(10);
		CompositeTool childTool2 = new CompositeTool(11);
		childTool1.add(channel1);
		childTool1.add(channel2);
		childTool1.add(channel3);
		childTool2.add(channel4);
		mainTool.add(childTool1); // add channels tool (1,2,3)
		mainTool.add(childTool2); // add channels tool (4)
		mainTool.add(channel9); // add single channel 9
		System.out.println("main tool send:");
		mainTool.send();
		childTool1.remove(channel2);
		mainTool.remove(childTool2);
		System.out.println("main tool send after remove:");
		mainTool.send();
	}
}