package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
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
 * Class that represents cat command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CatShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Command opens given file and writes its content to console.",
			"It takes one or two arguments.",
			"The first argument is path to some file and is mandatory.",
			"The second argument is charset name that should be used to interpret chars from bytes.");

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
		if (arg.length == 1 || arg.length == 2) {
			Path p = null;
			try {
				p = Paths.get(arg[0]);
			} catch (InvalidPathException Exc) {
				env.writeln("Invalid path name");
				return ShellStatus.CONTINUE;
			}
			try {
				Charset c = arg.length == 1 ? StandardCharsets.UTF_8 : Charset.forName(arg[1]);
				readFile(p, c, env);
			} catch (RuntimeException exc) {
				env.writeln("Given charset does not exist");
			}
		} else
			env.writeln("Invalid number of arguments");
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method that reads file and writes with {@link Environment} write.
	 * @param p {@link Path} of file.
	 * @param c {@link Charset} charset
	 * @param env {@link Environment}
	 * @since 1.0.0.
	 */

	private void readFile(Path p, Charset c, Environment env) {
		if (Files.notExists(p) || Files.isDirectory(p)) {
			env.writeln("Given file does not exist");
			return;
		}
		try (InputStream is = new BufferedInputStream(Files.newInputStream(p))) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				env.write(new String(buff, c));
			}
			env.writeln("");
		} catch (IOException exc) {
			env.writeln("An error has occured");
		}
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		return Collections.unmodifiableList(description);
	}

}
