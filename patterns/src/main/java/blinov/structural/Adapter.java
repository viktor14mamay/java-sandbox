package blinov.structural;

class XmlParser {
	public Order parseXml(String str, double xmlVersion, String encoding) {
		Order order = new Order();
		System.out.println("Parse xml file.");
		return order;
	}
}

interface Parser {
	Order parse(String order);
}

class XmlParserAdapter implements Parser {
	private XmlParser xmlParser;
	private double xmlVersion;
	private String encoding;

	public XmlParserAdapter(XmlParser xmlParser, double xmlVersion,
			String encoding) {
		this.xmlParser = xmlParser;
		this.xmlVersion = xmlVersion;
		this.encoding = encoding;
	}

	@Override
	public Order parse(String order) {
		return xmlParser.parseXml(order, xmlVersion, encoding);
	}
}

class JsonParser implements Parser {
	public Order parse(String jsonOrder) {
		Order order = new Order();
		System.out.println("Parse JSON.");
		return order;
	}
}

class Order {
	public int getOrderAmount() {
		return 0;
	}
}

class RunDemoAdapter {
	public static void main(String args[]) {
		String jsonOrder = "\"id\": \"1456\", \"firstName\": \"John\", \"lastName\": \"Smith\" ...";
		Parser parser = new JsonParser();
		Order order = parser.parse(jsonOrder);
		System.out.println(order.getOrderAmount());
		String xmlOrder = "<order id=\"1456\"><person firstName=\"John\" lastName=\"Smith\"/></order>";
		parser = new XmlParserAdapter(new XmlParser(), 1.0, "UTF-8");
		order = parser.parse(xmlOrder);
		System.out.println(order.getOrderAmount());
	}
}