package hr.fer.oprpp1.hw02.prob1;

/**
 * Class <code>Lexer</code> it class that represents lexical analyzer.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Lexer {

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

	private Token token;

	/**
	 * Index of first not tokenized character.
	 * 
	 * @since 1.0.0.
	 */

	private int currentIndex;

	/**
	 * Current <code>Lexer</code> state.
	 * 
	 * @since 1.0.0.
	 */

	private LexerState state;

	/**
	 * Constructor that gets text to be tokenized.
	 * 
	 * @param text to be tokenized.
	 * @throws NullPointerException if <code>text</code> is <code>null<code>
	 * @since 1.0.0.
	 */

	public Lexer(String text) {
		if (text == null)
			throw new NullPointerException();
		this.data = text.toCharArray();
		this.currentIndex = 0;
		this.setState(LexerState.BASIC);
	}

	/**
	 * Method that generates and returns next token.
	 * @return next token.
	 * @throws LexerException if no tokens are available or invalid escaping is used.
	 * @since 1.0.0.
	 */

	public Token nextToken() {
		if (this.token != null && this.token.getType() == TokenType.EOF)
			throw new LexerException();
		skipBlanks();
		if (this.currentIndex >= this.data.length) {
			this.token = new Token(TokenType.EOF, null);
			return this.token;
		}
		if (this.state == LexerState.BASIC) {
			this.token = basicState();
		} else {
			this.token = extendedState();
		}
		return this.token;
	}
	
	/**
	 * Method that returns last token.
	 * @return last Token.
	 * @throws LexerException if last token is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public Token getToken() {
		if (this.token == null)
			throw new LexerException();
		return this.token;
	}

	/**
	 * Method that tokenises when lexer is in basic state.
	 * @since 1.0.0.
	 * @return next {@link Token}
	 * @throws LexerException
	 * @since 1.0.0.
	 */

	private Token basicState() {
		if (this.data[this.currentIndex] == '\\' && this.currentIndex == (this.data.length - 1)) {
			throw new LexerException();
		}
		char c = this.data[this.currentIndex];
		if (this.currentIndex < (this.data.length - 1) && c == '\\'
				&& Character.isLetter(this.data[this.currentIndex + 1])) {
			throw new LexerException();
		} else if (Character.isLetter(c) || this.checkEscaping()) {
			this.token = findWord();
		} else if (this.currentIndex < this.data.length && c >= '0' && c <= '9') {
			this.token = findNumber();
		} else {
			this.token = new Token(TokenType.SYMBOL, c);
			this.currentIndex++;
		}
		return this.token;
	}
	
	/**
	 * Method that tokenises when lexer is in extended state.
	 * @since 1.0.0.
	 * @return next {@link Token}
	 * @throws LexerException
	 * @since 1.0.0.
	 */
	
	private Token extendedState() {
		StringBuilder sb = new StringBuilder();
		if(this.data[this.currentIndex] == '#') {
			this.currentIndex++;
			return new Token(TokenType.SYMBOL, '#');
		}
		while (this.currentIndex < this.data.length && this.data[this.currentIndex] != ' ' && this.data[this.currentIndex] != '#') {
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new Token(TokenType.WORD, sb.toString());
	}
	
	/**
	 * Private method that returns {@link Token} that {@link TokenType} is number.
	 * @return Token
	 * @throws LexerException if number is not <code>long</code>
	 * @since 1.0.0.
	 */

	private Token findNumber() {
		long number = 0;
		while (this.currentIndex < this.data.length && this.data[this.currentIndex] >= '0'
				&& this.data[this.currentIndex] <= '9') {
			if (number >= (number * 10 + Character.getNumericValue(this.data[this.currentIndex]))) {
				throw new LexerException();
			}
			number = number * 10 + Character.getNumericValue(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new Token(TokenType.NUMBER, number);
	}
	
	/**
	 * Private method that returns {@link Token} that {@link TokenType} is word.
	 * @return Token
	 * @since 1.0.0.
	 */
	
	private Token findWord() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.data[this.currentIndex]);
		this.currentIndex++;
		while (this.currentIndex < this.data.length
				&& (Character.isLetter(this.data[this.currentIndex]) || this.checkEscaping())) {
			sb.append(this.data[this.currentIndex]);
			this.currentIndex++;
		}
		return new Token(TokenType.WORD, sb.toString());
	}
	
	/**
	 * Method that checks are there valid escapings.
	 * @return <code>true</code> if there are valid escaping; <code>false</code> otherwise
	 * @since 1.0.0.
	 */

	private boolean checkEscaping() {
		if (this.currentIndex < this.data.length - 1 && this.data[this.currentIndex] == '\\'
				&& (this.isDigit(this.data[this.currentIndex + 1]) || this.data[this.currentIndex] == '\\')) {
			this.currentIndex++;
			return true;
		}
		return false;
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
	
	/**
	 * Method that sets {@link LexerState} of lexer.
	 * @param state desired state
	 * @throws NullPointerException if desired state is <code>null</code>
	 * @since 1.0.0.
	 */

	public void setState(LexerState state) {
		if (state == null)
			throw new NullPointerException();
		this.state = state;
	}

}
