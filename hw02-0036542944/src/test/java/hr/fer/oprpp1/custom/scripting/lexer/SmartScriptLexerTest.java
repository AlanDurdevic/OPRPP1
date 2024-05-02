package hr.fer.oprpp1.custom.scripting.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

public class SmartScriptLexerTest {

	SmartScriptLexer lexer;

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
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void test2() {
		String text = readExample(2);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void test3() {
		String text = readExample(3);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void test4() {
		String text = readExample(4);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void test5() {
		String text = readExample(5);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void test6() {
		String text = readExample(6);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TEXT);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void test7() {
		String text = readExample(7);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TEXT);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void test8() {
		String text = readExample(8);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void test9() {
		String text = readExample(9);
		SmartScriptLexer lexer = new SmartScriptLexer(text);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testNullDocument() {
		assertThrows(NullPointerException.class, () -> new SmartScriptLexer(null));
	}

	@Test
	public void testEmpyDocument() {
		lexer = new SmartScriptLexer("");
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testNextAfterEOF() {
		lexer = new SmartScriptLexer("");
		lexer.nextToken();
		assertThrows(SmartScriptLexerException.class, () -> lexer.nextToken());
	}

	@Test
	public void testEchoTag() {
		lexer = new SmartScriptLexer("{$= i i * @sin \"0.000\" @decfmt $}");
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.OPERATOR, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.FUNCTION, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void TestExample1() {
		lexer = new SmartScriptLexer("Example { bla } blu \\{$=1$}. Nothing interesting {=here}.");
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testExample2() {
		lexer = new SmartScriptLexer("Example \\{$=1$}. Now actually write one {$=1$}");
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.INTEGER, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

	@Test
	public void testExample3() {
		lexer = new SmartScriptLexer("A tag follows {$= \"Joe \\\"Long\\\" Smith\"$}.");
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_START, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TAG);
		assertEquals(SmartScriptTokenType.VARIABLE, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.STRING, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.TAG_END, lexer.nextToken().getType());
		lexer.setState(SmartScriptLexerState.TEXT);
		assertEquals(SmartScriptTokenType.TEXT, lexer.nextToken().getType());
		assertEquals(SmartScriptTokenType.EOF, lexer.nextToken().getType());
	}

}