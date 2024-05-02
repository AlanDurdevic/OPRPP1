package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents exit command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ExitShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Command that terminates shell.");
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		UtillCommands.checkEnvAndArg(env, arguments);
		String[] arg = UtillCommands.parseEmptySpace(arguments);
		if(arg.length != 0) {
			env.writeln("Invalid arguments");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "exit";
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
