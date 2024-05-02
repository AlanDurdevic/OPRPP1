package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents mkdir command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class MkdirShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Creates appropriate directory structure.", "Expects one argument.");
	
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
		if (arg.length != 1) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		Path path = null;
		try {
			path = Paths.get(arg[0]);	
		}catch(InvalidPathException Exc) {
			env.writeln("Invalid path name");
			return ShellStatus.CONTINUE;
		}
		try {
			Files.createDirectories(path);
		} catch (IOException e) {
			env.writeln("An error has ocurred");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "mkdir";
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
