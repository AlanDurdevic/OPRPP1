package hr.fer.oprpp1.hw05.shell;

import java.util.SortedMap;

/**
 * Interface that provides methods for environment of {@link ShellCommand}.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public interface Environment {
	
	/**
	 * Method that reads line.
	 * @return read line
	 * @throws ShellIOException if error occurred while reading
	 * @since 1.0.0.
	 */

	String readLine() throws ShellIOException;
	
	/**
	 * Method that writes desired text.
	 * @param text desired text
	 * @throws ShellIOException if error occurred while writing
	 * @since 1.0.0.
	 */

	void write(String text) throws ShellIOException;
	
	/**
	 * Method that writes desired text and new line symbol.
	 * @param text desired text
	 * @throws ShellIOException if error occurred while writing
	 * @since 1.0.0.
	 */

	void writeln(String text) throws ShellIOException;
	
	/**
	 * Method that returns all commands of environment.
	 * @return {@link SortedMap} of commands
	 * @since 1.0.0.
	 */

	SortedMap<String, ShellCommand> commands();
	
	/**
	 * Method that gets MULTILINESSYMBOL.
	 * @return MULTILINESSYMBOL
	 * @since 1.0.0.
	 */

	Character getMultilineSymbol();
	
	/**
	 * Method that sets MULTILINESSYMBOL.
	 * @param symbol MULTILINESSYMBOL
	 * @since 1.0.0.
	 */

	void setMultilineSymbol(Character symbol);
	
	/**
	 * Method that gets PROMPTSYMBOL.
	 * @return PROMPTSYMBOL
	 * @since 1.0.0.
	 */

	Character getPromptSymbol();
	
	/**
	 * Method that sets PROMPTSYMBOL.
	 * @param symbol PROMPTSYMBOL
	 * @since 1.0.0.
	 */

	void setPromptSymbol(Character symbol);
	
	/**
	 * Method that gets MORELINESSYMBOL.
	 * @return MORELINESSYMBOL
	 * @since 1.0.0.
	 */

	Character getMorelinesSymbol();
	
	/**
	 * Method that sets MORELINESSYMBOL.
	 * @param symbol MORELINESSYMBOL
	 * @since 1.0.0.
	 */

	void setMorelinesSymbol(Character symbol);

}
