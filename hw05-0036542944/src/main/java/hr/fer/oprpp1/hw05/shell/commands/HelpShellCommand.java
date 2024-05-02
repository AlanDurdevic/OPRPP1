package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Collections;
import java.util.List;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents help command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class HelpShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("If started with no arguments it lists all shell commands.",
			"Otherwise lists description of desired command.");

	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		UtillCommands.checkEnvAndArg(env, arguments);
		String[] arg = new String[0];
		try {
			arg = UtillCommands.parseFileParameter(arguments);
		} catch (IllegalArgumentException exc) {
			env.writeln("Illegal arguments");
			return ShellStatus.CONTINUE;
		}
		if (arg.length != 0 && arg.length != 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		if (arg.length == 0) {
			for (ShellCommand command : env.commands().values()) {
				env.writeln(command.getCommandName());
			}
		} else {
			ShellCommand command = env.commands().get(arg[0]);
			if (command == null)
				env.writeln("Command \"" + arg[0] + "\" does not exist");
			else {
				for (String row : command.getCommandDescription()) {
					env.writeln(row);
				}
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
		return "help";
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
