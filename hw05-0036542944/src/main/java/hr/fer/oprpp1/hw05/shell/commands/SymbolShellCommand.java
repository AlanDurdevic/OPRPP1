package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents symbol command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class SymbolShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Command to change or view current shell symbols.", "If single argument is provided it shows current symbol value.",
			"If two arguments are provided it changes shell symbol value.");
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		UtillCommands.checkEnvAndArg(env, arguments);
		String[] list = UtillCommands.parseEmptySpace(arguments);
		if((list.length != 1 && list.length != 2)) {
			env.writeln("Invalid number of arguments");
		}
		else {
			char symbol;
			switch(list[0]) {
			case "PROMPT":
				symbol = env.getPromptSymbol();
				break;
			case "MORELINES":
				symbol = env.getMorelinesSymbol();
				break;
			case "MULTILINE":
				symbol = env.getMultilineSymbol();
				break;
			default:
				env.writeln("Invalid symbol name");
				return ShellStatus.CONTINUE;
			}
			if(list.length == 1) {
				env.writeln("Symbol for " + list[0] + " is '" + symbol + "'");
			}
			else {
				if(list[1].length() != 1) {
					env.writeln("Invalid new symbol");
					return ShellStatus.CONTINUE;
				}
				switch(list[0]) {
				case "PROMPT":
					env.setPromptSymbol(list[1].charAt(0));
					break;
				case "MORELINES":
					env.setMorelinesSymbol(list[1].charAt(0));
					break;
				case "MULTILINE":
					env.setMultilineSymbol(list[1].charAt(0));
					break;
				default:
					env.writeln("Invalid symbol name");
					return ShellStatus.CONTINUE;
				}
				env.writeln("Symbol for " + list[0] + " changed from '" + symbol + "' to '" + list[1].charAt(0) + "'");
			}
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "symbol";
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
