package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Collections;
import java.util.List;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents tree command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class TreeShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Prints directory tree.", "Expect single value-directory.");
	
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
		}catch(IllegalArgumentException exc) {
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
		} catch (InvalidPathException Exc) {
			env.writeln("Invalid path name");
			return ShellStatus.CONTINUE;
		}
		if(Files.notExists(path)) env.writeln("Given directory does not exist");
		else if(Files.isRegularFile(path)) env.writeln("Given argument is not directory");
		else {
			try {
				Files.walkFileTree(path, new FileVisitor<Path>(){

					private int level = 0;
					
					@Override
					public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
						System.out.print(" ".repeat(level * 2));
						System.out.println(dir.getFileName());
						level++;
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
						System.out.print(" ".repeat(level * 2));
						System.out.println(file.getFileName());
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
						return FileVisitResult.CONTINUE;
					}

					@Override
					public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
						level--;
						return FileVisitResult.CONTINUE;
					}
					
				});
			} catch (IOException e) {
				env.writeln("Ann error has occured");
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
		return "tree";
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
