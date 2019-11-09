package blinov.creational;

import static java.lang.System.out;

abstract class AbstractMediaFactory {
	public abstract TCPMediaContent createTCPObject();

	public abstract UDPMediaContent createUDPObject();
}

class AudioFactory extends AbstractMediaFactory {
	public TCPMediaContent createTCPObject() {
		return new TCPAudioContent();
	}

	public UDPMediaContent createUDPObject() {
		return new UDPAudioContent();
	}
}

class VideoFactory extends AbstractMediaFactory {
	public TCPMediaContent createTCPObject() {
		return new TCPVideoContent();
	}

	public UDPMediaContent createUDPObject() {
		return new UDPVideoContent();
	}
}

abstract class TCPMediaContent {
	abstract void play();
}

class TCPAudioContent extends TCPMediaContent {
	void play() {
		out.println("tcp audio");
	}
}

class TCPVideoContent extends TCPMediaContent {
	void play() {
		out.println("tcp video");
	}
}

abstract class UDPMediaContent {
	abstract void play();

	abstract void transform();
}

class UDPAudioContent extends UDPMediaContent {
	void play() {
		transform();
		out.println("udp audio");
	}

	void transform() {
		out.print("transform\n\t");
	}
}

class UDPVideoContent extends UDPMediaContent {
	void play() {
		transform();
		out.println("udp video");
	}

	void transform() {
		out.print("transform\n\t");
	}
}

class Client2 {
	private UDPMediaContent contentUDP;
	private TCPMediaContent contentTCP;

	public void makeMediaFactoryWork(AbstractMediaFactory factory) {
		contentUDP = factory.createUDPObject();
		contentTCP = factory.createTCPObject();
		contentTCP.play();
		contentUDP.play();
	}
}

class AbstractFactoryRunner {
	public static void main(String[] args) {
		Client2 cl2 = new Client2();
		cl2.makeMediaFactoryWork(new AudioFactory());
		cl2.makeMediaFactoryWork(new VideoFactory());
	}
}