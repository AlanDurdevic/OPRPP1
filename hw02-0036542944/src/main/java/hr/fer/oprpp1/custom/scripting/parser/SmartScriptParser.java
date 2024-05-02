package hr.fer.oprpp1.custom.scripting.parser;

import java.util.Arrays;

import hr.fer.oprpp1.custom.collections.ArrayIndexedCollection;
import hr.fer.oprpp1.custom.collections.ObjectStack;
import hr.fer.oprpp1.custom.scripting.elems.*;
import hr.fer.oprpp1.custom.scripting.lexer.*;
import hr.fer.oprpp1.custom.scripting.nodes.*;


/**
 * Class that represents parser.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class SmartScriptParser {
	
	/**
	 * {@link SmartScriptLexer} for parser.
	 * @since 1.0.0.
	 */

	private final SmartScriptLexer lexer;
	
	/**
	 * {@link ObjectStack} for parser.
	 * @since 1.0.0.
	 */

	private final ObjectStack stack;
	
	/**
	 * {@link DocumentNode} for parser.
	 * @since 1.0.0.
	 */

	private final DocumentNode documentNode;
	
	/**
	 * Constructor that gets document to be parsed.
	 * @param documentBody to be parsed.
	 * @throws NullPointerException if <code>document</code> is <code>null</code>
	 * @throws SmartScriptLexerException if something went wrong in parsing
	 * @since 1.0.0.
	 */

	public SmartScriptParser(String documentBody) {
		if (documentBody == null)
			throw new NullPointerException();
		this.lexer = new SmartScriptLexer(documentBody);
		this.stack = new ObjectStack();
		this.documentNode = new DocumentNode();
		this.stack.push(documentNode);
		try {
			this.parse();
		}
		catch(Exception exc) {
			throw new SmartScriptParserException(exc.getMessage());
		}
		if (this.stack.size() > 1) throw new SmartScriptParserException();
	}
	
	/**
	 * Private method for parsing.
	 * @throws SmartScriptLexerException if something went wrong in parsing.
	 * @since 1.0.0.
	 */

	private void parse() {
		while (lexer.nextToken().getType() != SmartScriptTokenType.EOF) {
			Node node = (Node) stack.peek();
			SmartScriptToken token = lexer.getToken();
			if (token.getType() == SmartScriptTokenType.TEXT) {
				node.addChildNode(new TextNode(token.getValue().toString()));
			} else if (token.getType() == SmartScriptTokenType.TAG_START) {
				lexer.setState(SmartScriptLexerState.TAG);
				token = lexer.nextToken();
				if (token.getValue().toString().toLowerCase().equals("for")) {
					ForLoopNode forLoopNode = this.forLoop();
					node.addChildNode(forLoopNode);
					stack.push(forLoopNode);
					lexer.setState(SmartScriptLexerState.TEXT);
				} else if (token.getValue().toString().toLowerCase().equals("end")) {
					if (lexer.nextToken().getType() == SmartScriptTokenType.TAG_END) {
						lexer.setState(SmartScriptLexerState.TEXT);
						stack.pop();
						if (stack.isEmpty())
							throw new SmartScriptParserException();
					} else
						throw new SmartScriptParserException();
				} else {
					node.addChildNode(this.findEchoNode());
					lexer.setState(SmartScriptLexerState.TEXT);
				}
			}
			else throw new SmartScriptParserException();
		}
	}
	
	/**
	 * Private method that find {@link EchoNode}.
	 * @return EchoNode
	 * @throws SmartScriptParserException if something went wrong in parsing.
	 * @since 1.0.0.
	 */
	
	private EchoNode findEchoNode() {
		ArrayIndexedCollection array = new ArrayIndexedCollection();
		if(lexer.getToken().getType() != SmartScriptTokenType.VARIABLE && lexer.getToken().toString().equals("="))
			throw new SmartScriptParserException();	
		array.add(new ElementVariable(lexer.getToken().getValue().toString()));
		while (lexer.nextToken().getType() != SmartScriptTokenType.TAG_END) {
			switch (lexer.getToken().getType()) {
			case DOUBLE:
				array.add(
						new ElementConstantDouble(Double.valueOf(lexer.getToken().getValue().toString())));
				break;
			case INTEGER:
				array.add(new ElementConstantInteger(
						Integer.valueOf(lexer.getToken().getValue().toString())));
				break;
			case FUNCTION:
				array.add(new ElementFunction(lexer.getToken().getValue().toString()));
				break;
			case OPERATOR:
				array.add(new ElementOperator(lexer.getToken().getValue().toString()));
				break;
			case STRING:
				array.add(new ElementString(lexer.getToken().getValue().toString()));
				break;
			case VARIABLE:
				array.add(new ElementVariable(lexer.getToken().getValue().toString()));
				break;
			default:
				throw new SmartScriptParserException();
			}
		}
		return new EchoNode(Arrays.copyOf(array.toArray(), array.size(), Element[].class));
	}
	
	/**
	 * Private method that find {@link ForLoopNode}.
	 * @return ForLoopNode
	 * @throws SmartScriptParserException if something went wrong in parsing.
	 * @since 1.0.0.
	 */

	private ForLoopNode forLoop() {
		if (lexer.nextToken().getType() != SmartScriptTokenType.VARIABLE)
			throw new SmartScriptParserException();
		ElementVariable variable = new ElementVariable(lexer.getToken().getValue().toString());
		Element startExpression = getElementForLoop();
		Element endExpression = getElementForLoop();
		Element stepExpression = null;
		try {
			stepExpression = getElementForLoop();
		} catch (SmartScriptParserException exc) {
			if(lexer.getToken().getType() != SmartScriptTokenType.TAG_END) {
				throw new SmartScriptParserException();
			}
		}
		if(stepExpression != null && lexer.nextToken().getType() != SmartScriptTokenType.TAG_END) {
			throw new SmartScriptParserException();
		}
		if(startExpression == null || endExpression == null || variable == null) throw new SmartScriptParserException();
		ForLoopNode forLoopNode = new ForLoopNode(variable, startExpression, endExpression, stepExpression);
		return forLoopNode;
	}
	
	/**
	 * Method that return {@link ForLoopNode} element.
	 * @return element
	 * @throws SmartScriptParserException if something went wrong while parsing.
	 * @since 1.0.0.
	 */

	private Element getElementForLoop() {
		if (this.checkTypeForLoop(lexer.nextToken().getType())) {
			if (lexer.getToken().getType() == SmartScriptTokenType.INTEGER) {
				return new ElementConstantInteger((int) lexer.getToken().getValue());
			} else if (lexer.getToken().getType() == SmartScriptTokenType.DOUBLE) {
				return new ElementConstantDouble((double) lexer.getToken().getValue());
			} else
				return new ElementVariable((String) lexer.getToken().getValue());

		} else
			throw new SmartScriptParserException();
	}
	
	/**
	 * Method that checks type of {@link ForLoopNode} element.
	 * @return true if type is variable, integer, double, string; false otherwise
	 * @since 1.0.0.
	 */

	private boolean checkTypeForLoop(SmartScriptTokenType type) {
		return type == SmartScriptTokenType.VARIABLE || type == SmartScriptTokenType.INTEGER
				|| type == SmartScriptTokenType.DOUBLE || type == SmartScriptTokenType.STRING;
	}
	
	/**
	 * Method that returns {@link DocumentNode} of {@link SmartScriptParserException}.
	 * @return {@link DocumentNode}
	 * @since 1.0.0.
	 */

	public DocumentNode getDocumentNode() {
		return this.documentNode;
	}

}
