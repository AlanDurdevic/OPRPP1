package hr.fer.oprpp1.custom.scripting.lexer;

import hr.fer.oprpp1.hw02.prob1.LexerException;
/**
 * Class <code>SmartScriptLexer</code> it class that represents lexical analyzer.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class SmartScriptLexer {
	
	/**
	 * Input text.
	 * 
	 * @since 1.0.0
	 */
	
	private char[] data;
	
	/**
	 * Current token.
	 * 
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken token;
	
	/**
	 * Index of first not tokenized character.
	 * 
	 * @since 1.0.0.
	 */

	
	private int currentIndex;
	
	/**
	 * Current <code>SmartScriptLexer</code> state.
	 * 
	 * @since 1.0.0.
	 */
	
	private SmartScriptLexerState state;
	
	/**
	 * Constructor that gets text to be tokenized.
	 * 
	 * @param text to be tokenized.
	 * @throws NullPointerException if <code>text</code> is <code>null<code>
	 * @since 1.0.0.
	 */
	
	public SmartScriptLexer(String text) {
		if(text == null) throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.setState(SmartScriptLexerState.TEXT);
	}
	
	/**
	 * Method that generates and returns next token.
	 * @return next token.
	 * @throws LexerException if no tokens are available or invalid escaping is used.
	 * @since 1.0.0.
	 */
	
	public SmartScriptToken nextToken() {
		if(this.token != null && this.token.getType() == SmartScriptTokenType.EOF) {
			throw new SmartScriptLexerException();
		}
		if(this.currentIndex >= this.data.length) {
			this.token = new SmartScriptToken(SmartScriptTokenType.EOF, null);
			return this.token;
		}
		if(this.state == SmartScriptLexerState.TEXT) {
			this.token = this.textState();
		}
		else if(this.state == SmartScriptLexerState.TAG) {
			this.token = this.tagState();
		}
		return this.token;
	}
	

	/**
	 * Method that returns last token.
	 * @return last SmartScriptToken.
	 * @throws SmartScriptLexerException if last token is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public SmartScriptToken getToken() {
		if(this.token == null) throw new SmartScriptLexerException();
		return this.token;
	}
	
	/**
	 * Method that tokenises when lexer is in TEXT state.
	 * @since 1.0.0.
	 * @return next {@link SmartScriptToken}
	 * @throws SmartScriptLexerException
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken textState() {
		//check if it is TAG_START
		if(this.currentIndex < this.data.length - 1 && this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
			this.currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$");
		}
		StringBuilder sb = new StringBuilder();
		while(this.currentIndex < this.data.length) {
			if(this.currentIndex < this.data.length - 1 && this.data[this.currentIndex] == '{' && this.data[this.currentIndex + 1] == '$') {
				break;
			}
			if(this.data[this.currentIndex] == '\\') {
				if((this.currentIndex + 1) < this.data.length && (this.data[this.currentIndex + 1] == '{' || this.data[this.currentIndex + 1] == '\\')) {
					this.currentIndex++;
					//treat \\ \ i \{ {
				}
				else throw new SmartScriptLexerException();	
			}
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new SmartScriptToken(SmartScriptTokenType.TEXT, sb.toString());
	}
	
	/**
	 * Method that tokenises when lexer is in TAG state.
	 * @since 1.0.0.
	 * @return next {@link SmartScriptToken}
	 * @throws SmartScriptLexerException
	 * @since 1.0.0.
	 */

	private SmartScriptToken tagState() {
		this.skipBlanks();
		char c = this.data[this.currentIndex];
		//negative number
		if((this.currentIndex + 1) < this.data.length && c == '-' && isDigit(this.data[this.currentIndex + 1])) {
			return this.findNegativeNumber();
		}
		//positive number
		else if (this.currentIndex < this.data.length && isDigit(c)) {
			return this.findPositiveNumber();
		}
		//function @
		else if(this.currentIndex + 1 < this.data.length && c == '@' && Character.isLetter(this.data[this.currentIndex + 1])) {
			return this.findFunction();
		}
		//operator +, -, *, /, ^
		else if(this.currentIndex < this.data.length && this.isOperator(c)) {
			this.currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.OPERATOR, String.valueOf(c));
		}
		//quotation
		else if(this.currentIndex < this.data.length && c == '"') {
			return this.findQuotation();
		}
		// =
		else if(c == '=') {
			this.currentIndex++;
			return new SmartScriptToken(SmartScriptTokenType.VARIABLE,"=");
		}
		//variable
		else if(Character.isLetter(c)){
			return this.findVariable();
		}
		//TAG_START
		else if(this.currentIndex < this.data.length - 1 && c == '{' && this.data[this.currentIndex + 1] == '$'){
			this.currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.TAG_START, "{$");
		}
		//TAG_END
		else if(this.currentIndex < this.data.length - 1 && c == '$' && this.data[this.currentIndex + 1] == '}'){
			this.currentIndex += 2;
			return new SmartScriptToken(SmartScriptTokenType.TAG_END, "$}");
		}
		else throw new SmartScriptLexerException();
	}
	
	/**
	 * Private method that returns {@link SmartScriptToken} that {@link SmartScriptTokenType} is variable.
	 * @return SmartScriptToken
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken findVariable() {
		StringBuilder sb = new StringBuilder();
		while(this.currentIndex < this.data.length &&( Character.isLetter(this.data[this.currentIndex]) || 
				this.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '_')) {
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new SmartScriptToken(SmartScriptTokenType.VARIABLE, sb.toString());
	}
	
	/**
	 * Private method that returns {@link SmartScriptToken} that {@link SmartScriptTokenType} is string.
	 * @return SmartScriptToken
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken findQuotation() {
		StringBuilder sb = new StringBuilder();
		this.currentIndex++;
		sb.append('"');
		while(this.currentIndex < this.data.length && this.data[this.currentIndex] != '"') {
			if(this.currentIndex + 1 < this.data.length && this.data[this.currentIndex] == '\\' && this.isEscaping(this.data[this.currentIndex + 1])) {
				sb.append('\\');
				sb.append(this.data[this.currentIndex + 1]);
				this.currentIndex += 2;
			}
			else if(this.data[this.currentIndex] == '\\') throw new SmartScriptLexerException();
			else {
				sb.append(this.data[this.currentIndex]);
				this.currentIndex++;
			}
		}
		sb.append('"');
		this.currentIndex++;
		return new SmartScriptToken(SmartScriptTokenType.STRING, sb.toString());
	}
	
	/**
	 * Private method that returns {@link SmartScriptToken} that {@link SmartScriptTokenType} is function.
	 * @return SmartScriptToken
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken findFunction() {
		StringBuilder sb = new StringBuilder();
		sb.append('@');
		this.currentIndex++;
		while(this.currentIndex < this.data.length &&( Character.isLetter(this.data[this.currentIndex]) || 
				this.isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '_')) {
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new SmartScriptToken(SmartScriptTokenType.FUNCTION, sb.toString());
	}
	
	/**
	 * Private method that returns {@link SmartScriptToken} that {@link SmartScriptTokenType} is double or integer.
	 * @return SmartScriptToken
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken findPositiveNumber() {
		StringBuilder sb = new StringBuilder();
		boolean oneDot = false;
		while(this.currentIndex < this.data.length && (isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '.')) {
			if(this.data[this.currentIndex] == '.' && oneDot) break;
			if(this.data[this.currentIndex] == '.') oneDot = true;
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		if(oneDot) {
			return new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(sb.toString()));
		}
		else {
			return new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(sb.toString()));
		}
	}
	
	/**
	 * Private method that returns {@link SmartScriptToken} that {@link SmartScriptTokenType} is double or integer. Used fo negative numbers.
	 * @return SmartScriptToken
	 * @since 1.0.0.
	 */
	
	private SmartScriptToken findNegativeNumber() {
		StringBuilder sb = new StringBuilder();
		sb.append('-');
		this.currentIndex++;
		boolean oneDot = false;
		while(this.currentIndex < this.data.length && (isDigit(this.data[this.currentIndex]) || this.data[this.currentIndex] == '.')) {
			if(this.data[this.currentIndex] == '.' && oneDot) break;
			if(this.data[this.currentIndex] == '.') oneDot = true;
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		if(oneDot) return new SmartScriptToken(SmartScriptTokenType.DOUBLE, Double.valueOf(sb.toString()));
		else return new SmartScriptToken(SmartScriptTokenType.INTEGER, Integer.valueOf(sb.toString()));
	}
	
	/**
	 * Method that checks are there valid escapings.
	 * @return <code>true</code> if there are valid escaping; <code>false</code> otherwise
	 * @since 1.0.0.
	 */
	
	private boolean isEscaping(char c) {
		return c == '\\' || c == '"' || c == 'n' || c == 'r' || c == 't';
	}
	
	/**
	 * Method that checks if given character is operator.
	 * @param c given character
	 * @return <code>true</code> if it is operator; <code>false</code> otherwise
	 * @since 1.0.0.
	 */
	
	private boolean isOperator(char c) {
		return c == '+' || c == '-' || c == '/' || c == '^' || c == '*';
	}
	
	/**
	 * Method that checks if given character is number.
	 * @param c given character
	 * @return <code>true</code> if it is number; <code>false</code> otherwise
	 * @since 1.0.0.
	 */

	private boolean isDigit(char c) {
		return c >= '0' && c <= '9';
	}
	
	/**
	 * Method that sets {@link SmartScriptLexerState} of SmartScriptLexer.
	 * @param state desired state
	 * @throws NullPointerException if desired state is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public void setState(SmartScriptLexerState state) {
		if(state == null) throw new NullPointerException();
		this.state = state;
	}
	
	/**
	 * Method that skips all connected blanks in data.
	 * @since 1.0.0.
	 */
	
	private void skipBlanks() {
		while (this.currentIndex < this.data.length) {
			char c = data[this.currentIndex];
			if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
				this.currentIndex++;
			} else
				break;
		}
	}

}
