package blinov.behave;

import static java.lang.System.out;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

interface Conversion {
	void convert(URL urlFileImg);
}

class DefaultConversion implements Conversion {
	@Override
	public void convert(URL urlFileImg) {
		out.println("default conversion");
	}
}

class ConversionGif implements Conversion {
	@Override
	public void convert(URL urlFileImg) {
		out.println("GIF Conversion");
	}
}

class ConversionJpg implements Conversion {
	@Override
	public void convert(URL urlFileImg) {
		out.println("JPG Conversion");
	}
}

class ConversionPng implements Conversion {
	@Override
	public void convert(URL urlFileImg) {
		out.println("PNG Conversion");
	}
}

class Convert {
	public final static int DEFAULT_ALGORITHM = 0;
	private Map<Integer, Conversion> algorithms = new HashMap<Integer, Conversion>();

	public Convert(Conversion conversion) {
		algorithms.put(DEFAULT_ALGORITHM, conversion);
	}

	public Convert() {
		this(new DefaultConversion());
	}

	public void registerAlgorithm(int key, Conversion conversion) {
		if (key != 0) {
			algorithms.put(key, conversion);
		}
	}

	public void registerDefaultAlgorithm(Conversion conversion) {
		algorithms.put(DEFAULT_ALGORITHM, conversion);
	}

	public void convert(int key, URL url) {
		if (!algorithms.containsKey(key)) {
			key = 0;
		}
		algorithms.get(key).convert(url);
	}
}

class DemoStrategy {
	public static void main(String[] args) throws MalformedURLException {
		URL fileUrl = new URL("http://google.com/");
		Convert con = new Convert(new ConversionJpg());
		con.registerDefaultAlgorithm(new ConversionPng());
		con.registerAlgorithm(1, new ConversionGif());
		con.registerAlgorithm(3, new ConversionJpg());
		con.convert(1, fileUrl);
		con.convert(0, fileUrl);
		con.convert(2, fileUrl);
	}
}