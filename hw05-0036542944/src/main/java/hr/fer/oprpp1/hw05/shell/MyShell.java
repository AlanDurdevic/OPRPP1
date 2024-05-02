package hr.fer.oprpp1.hw05.shell;

import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;
import hr.fer.oprpp1.hw05.shell.commands.*;

/**
 * Class that represents simple shell implementation.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class MyShell implements Environment {
	
	/**
	 * {@link SortedMap} of all shell's commands.
	 * @since 1.0.0.
	 */

	private SortedMap<String, ShellCommand> commands;
	
	/**
	 * {@link ShellStatus} of shell.
	 * @since 1.0.0.
	 */

	private ShellStatus status;
	
	/**
	 * {@link Scanner} of shell;
	 * @since 1.0.0.
	 */

	private Scanner sc;
	
	/**
	 * PROMPTSYMBOL symbol
	 * @since 1.0.0
	 */

	private Character promptSymbol;
	
	/**
	 * MULTILINESSYMBOL symbol
	 * @since 1.0.0.
	 */

	private Character multiLineSymbol;
	
	/**
	 * MORELINESSYMBOL symbol
	 * @since 1.0.0.
	 */

	private Character moreLinesSymbol;
	
	/**
	 * Constructor for shell.
	 * @param sc {@link Scanner} of shell.
	 * @throws NullPointerException if <code>scanner</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public MyShell(Scanner sc) {
		buildEnvironment();
		commands = Collections.unmodifiableSortedMap(commands);
		this.sc = Objects.requireNonNull(sc, "Scanner can not be null");
		promptSymbol = Character.valueOf('>');
		multiLineSymbol = Character.valueOf('|');
		moreLinesSymbol = Character.valueOf('\\');
	}
	
	/**
	 * Method that adds all commands to shell and sets starting {@link ShellStatus}.
	 * @since 1.0.0.
	 */

	private void buildEnvironment() {
		commands = new TreeMap<>();
		commands.put("cat", new CatShellCommand());
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("symbol", new SymbolShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("copy", new CopyShellCommand());
		status = ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String readLine() throws ShellIOException {
		try {
			StringBuilder sb = new StringBuilder();
			while (true) {
				String line = sc.nextLine().trim();
				if (!line.endsWith(moreLinesSymbol.toString()))
					return sb.append(line).toString();
				sb.append(line, 0, line.length() - 1);
				write(getMultilineSymbol().toString() + " ");
			}

		} catch (RuntimeException e) {
			throw new ShellIOException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void writeln(String text) throws ShellIOException {
		try {
			write(Objects.requireNonNull(text) + '\n');
		} catch (RuntimeException e) {
			throw new ShellIOException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void write(String text) throws ShellIOException {
		try {
			System.out.print(Objects.requireNonNull(text));
		} catch (RuntimeException e) {
			throw new ShellIOException();
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public SortedMap<String, ShellCommand> commands() {
		return commands;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Character getMultilineSymbol() {
		return multiLineSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setMultilineSymbol(Character symbol) {
		multiLineSymbol = Objects.requireNonNull(symbol, "MultiLineSymbol can not be null");
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Character getPromptSymbol() {
		return promptSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setPromptSymbol(Character symbol) {
		promptSymbol = Objects.requireNonNull(symbol, "PromptSymbol can not be null");
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public Character getMorelinesSymbol() {
		return moreLinesSymbol;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public void setMorelinesSymbol(Character symbol) {
		moreLinesSymbol = Objects.requireNonNull(symbol, "MoreLinesSymbol can not be null");
	}
	
	/**
	 * Method that represents simple application that uses {@link MyShell}.
	 * @param args
	 * @throws ShellIOException if error in shell has occurred.
	 * @since 1.0.0.
	 */

	public static void main(String[] args) {
		MyShell shell = new MyShell(new Scanner(System.in));
		shell.write("Welcome to MyShell v 1.0\n");
		try {
			do {
				shell.write(shell.promptSymbol + " ");
				String l = shell.readLine();
				if(!l.isBlank()) {
					String commandName = extractCommandName(l);
					String arguments = extractArguments(l);
					ShellCommand command = shell.commands.get(commandName);
					if (command == null) {
						shell.writeln("Unknown command");
					} else {
						shell.status = command.executeCommand(shell, arguments);
					}
				}
			} while (shell.status != ShellStatus.TERMINATE);
		} catch (ShellIOException exc) {
			System.err.print("Error has occured");
			System.exit(0);
		}
	}
	
	/**
	 * Method that extracts arguments from line.
	 * @param line line
	 * @return String of extracted arguments
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	private static String extractArguments(String line) {
		String arg = Objects.requireNonNull(line, "Line can not be null").substring(line.indexOf(' ') + 1, line.length());
		return arg.equals(line) ? "" : arg;
	}
	
	/**
	 * Method that extracts command from line.
	 * @param line line
	 * @return String of extracted command
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	private static String extractCommandName(String line) {
		return Objects.requireNonNull(line, "Line can not be null").split(" ")[0];
	}

}
