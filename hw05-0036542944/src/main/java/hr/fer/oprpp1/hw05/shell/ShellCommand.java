package hr.fer.oprpp1.hw05.shell;

import java.util.List;

/**
 * Interface that provides methods for {@link MyShell} commands.
 * @author Alan Đurđević
 * @version 1.0.0.
 */ 

public interface ShellCommand {
	
	/**
	 * Method for executing command.
	 * @param env {@link Environment} of command
	 * @param arguments given arguments
	 * @return {@link ShellStatus}
	 * @since 1.0.0.
	 */

	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * Method that gets command name.
	 * @return command name
	 * @since 1.0.0.
	 */

	String getCommandName();
	
	/**
	 * Method that gets command description.
	 * @return command description
	 * @since 1.0.0.
	 */

	List<String> getCommandDescription();

}
