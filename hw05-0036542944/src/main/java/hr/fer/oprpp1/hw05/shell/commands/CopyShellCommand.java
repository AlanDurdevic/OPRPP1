package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;


/**
 * Class that represents copy command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class CopyShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Command that copies content of one file to another.",
			"Expects two arguments: source file name and destination file name.");
	
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
		if (arg.length != 2) {
			env.writeln("Invalid number of arguments");
			return ShellStatus.CONTINUE;
		}
		Path sourceFile = Paths.get(arg[0]);
		if (!Files.exists(sourceFile) || Files.isDirectory(sourceFile)) {
			env.writeln("Source file does not exist");
			return ShellStatus.CONTINUE;
		}
		Path destinationFile = Paths.get(arg[1]);
		if(Files.exists(destinationFile)) {
			if(Files.isDirectory(destinationFile)) {
				if(Files.exists(destinationFile.resolve(sourceFile))) {
					env.writeln("Can given destination file be overwritten?");
					if(env.readLine().equalsIgnoreCase("yes")) {
						copy(sourceFile, destinationFile.resolve(sourceFile).normalize(), env);
					}
				}
				else {
					try {
						Files.createFile(destinationFile.resolve(sourceFile).normalize());
					} catch (IOException e) {
						env.writeln("An error has occured");
					}
					copy(sourceFile, destinationFile.resolve(sourceFile).normalize(), env);
				}
			}
			else {
				env.writeln("Can given destination file be overwritten?");
				if(env.readLine().equalsIgnoreCase("yes")) {
					copy(sourceFile, destinationFile, env);
				}
			}
		}
		else {
			try {
				Files.createFile(destinationFile);
			} catch (IOException e) {
				env.writeln("An error has occured");
			}
			copy(sourceFile, destinationFile, env);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method that copies one file to another.
	 * @param sourceFile source file
	 * @param destinationFile destination file
	 * @param env {@link Environment}
	 * @since 1.0.0.
	 */

	private void copy(Path sourceFile, Path destinationFile, Environment env) {
		try (InputStream is = new BufferedInputStream(Files.newInputStream(sourceFile));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(destinationFile)) {
				}) {
			byte[] buff = new byte[1024];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				os.write(buff);
			}
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
		return "copy";
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
