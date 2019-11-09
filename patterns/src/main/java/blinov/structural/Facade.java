package blinov.structural;

import static java.lang.System.out;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

class FacadeValidator {
	public boolean validateSAXwithXSD(String xmlFileName,
			String schemaFileName, DefaultHandler handler) {
		boolean result = false;
		SchemaFactory factory = SchemaFactory
				.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		try {
			Schema schema = factory.newSchema(new File(schemaFileName));
			Validator validator = schema.newValidator();
			Source source = new StreamSource(xmlFileName);
			validator.setErrorHandler(handler);
			validator.validate(source);
			result = true;
		} catch (SAXException | IOException e) {
			out.println(e);
		}
		return result;
	}
}

class ValidatorSAXwithXSD {
	public static void main(String[] args) {
		FacadeValidator v = new FacadeValidator();
		if (v.validateSAXwithXSD("data/students.xml", "data/students.xsd",
				new StudentHandler()))
			System.out.println("Validating is ended correctly");
	}
}

class StudentHandler extends DefaultHandler {
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
	}

	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
	}
}