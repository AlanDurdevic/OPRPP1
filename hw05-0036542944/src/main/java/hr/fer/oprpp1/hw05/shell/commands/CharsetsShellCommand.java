package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents charsets command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CharsetsShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Takes no arguments and lists names of supported charsets for your Java platform.");

	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */
	
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		UtillCommands.checkEnvAndArg(env, arguments);
		String[] arg = UtillCommands.parseEmptySpace(arguments);
		if(arg.length == 0) {
			for(Charset charset : Charset.availableCharsets().values()) {
				env.writeln(charset.toString());
			}
		}
		else env.writeln("Invalid number of arguments");
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "charsets";
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
