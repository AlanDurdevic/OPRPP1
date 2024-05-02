package hr.fer.oprpp1.custom.scripting.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import hr.fer.oprpp1.custom.scripting.elems.ElementConstantInteger;
import hr.fer.oprpp1.custom.scripting.elems.ElementString;
import hr.fer.oprpp1.custom.scripting.elems.ElementVariable;
import hr.fer.oprpp1.custom.scripting.nodes.DocumentNode;
import hr.fer.oprpp1.custom.scripting.nodes.EchoNode;
import hr.fer.oprpp1.custom.scripting.nodes.TextNode;

public class SmartScriptParserTest {
	
	public SmartScriptParser parser;
	
	
	private String readExample(int n) {
		try (InputStream is = this.getClass().getClassLoader().getResourceAsStream("extra/primjer" + n + ".txt")) {
			if (is == null)
				throw new RuntimeException("Datoteka extra/primjer" + n + ".txt je nedostupna.");
			byte[] data = is.readAllBytes();
			String text = new String(data, StandardCharsets.UTF_8);
			return text;
		} catch (IOException ex) {
			throw new RuntimeException("Greška pri čitanju datoteke.", ex);
		}
	}

	@Test
	public void test1() {
		String text = readExample(1);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode documentNode = parser.getDocumentNode();
		assertEquals(1, documentNode.numberOfChildren());
		assertEquals(documentNode.getChild(0).getClass(), TextNode.class);
	}

	@Test
	public void test2() {
		String text = readExample(2);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode documentNode = parser.getDocumentNode();
		assertEquals(1, documentNode.numberOfChildren());
		assertEquals(documentNode.getChild(0).getClass(), TextNode.class);
	}

	@Test
	public void test3() {
		String text = readExample(3);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode documentNode = parser.getDocumentNode();
		assertEquals(1, documentNode.numberOfChildren());
		assertEquals(documentNode.getChild(0).getClass(), TextNode.class);
	}

	@Test
	public void test4() {
		String text = readExample(4);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void test5() {
		String text = readExample(5);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}

	@Test
	public void test6() {
		String text = readExample(6);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode documentNode = parser.getDocumentNode();
		assertEquals(3, documentNode.numberOfChildren());
		assertEquals(documentNode.getChild(0).getClass(), TextNode.class);
		assertEquals(documentNode.getChild(1).getClass(), EchoNode.class);
	}

	@Test
	public void test7() {
		String text = readExample(7);
		SmartScriptParser parser = new SmartScriptParser(text);
		DocumentNode documentNode = parser.getDocumentNode();
		assertEquals(3, documentNode.numberOfChildren());
		assertEquals(documentNode.getChild(0).getClass(), TextNode.class);
		assertEquals(documentNode.getChild(1).getClass(), EchoNode.class);
	}

	@Test
	public void test8() {
		String text = readExample(8);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
 
	@Test
	public void test9() {
		String text = readExample(9);
		assertThrows(SmartScriptParserException.class, () -> new SmartScriptParser(text));
	}
	
	@Test
	public void testNullDocument() {
		assertThrows(NullPointerException.class, ()->new SmartScriptParser(null));
	}
	
	@Test
	public void testEmptyDocument() {
		parser = new SmartScriptParser("");
		assertEquals(0, parser.getDocumentNode().numberOfChildren());
	}
	
	@Test
	public void testExample1() {
		parser = new SmartScriptParser("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
		DocumentNode document = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(document.toString());
		assertEquals(1, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(document, parser2.getDocumentNode());
	}
	
	@Test
	public void testExample2() {
		parser = new SmartScriptParser("Example \\{$=1$}. Now actually write one {$=1$}");
		DocumentNode document = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(document.toString());
		assertEquals(2, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
		assertEquals(2,((EchoNode) document.getChild(1)).getElements().length);
		assertEquals(new ElementVariable("="),((EchoNode) document.getChild(1)).getElements()[0]);
		assertEquals(new ElementConstantInteger(1),((EchoNode) document.getChild(1)).getElements()[1]);
		assertEquals(2,((EchoNode) document.getChild(1)).getElements().length);
		assertEquals(document, parser2.getDocumentNode());
	}
	
	@Test
	public void testExample3() {
		parser = new SmartScriptParser("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
		DocumentNode document = parser.getDocumentNode();
		SmartScriptParser parser2 = new SmartScriptParser(document.toString());
		assertEquals(3, document.numberOfChildren());
		assertEquals(TextNode.class, document.getChild(0).getClass());
		assertEquals(EchoNode.class, document.getChild(1).getClass());
		assertEquals(TextNode.class, document.getChild(2).getClass());
		assertEquals(2,((EchoNode) document.getChild(1)).getElements().length);
		assertEquals(new ElementVariable("="),((EchoNode) document.getChild(1)).getElements()[0]);
		assertEquals(new ElementString("\"Joe \\\"Long\\\" Smith\""),((EchoNode) document.getChild(1)).getElements()[1]);
		assertEquals(document, parser2.getDocumentNode());
	}
	
}
