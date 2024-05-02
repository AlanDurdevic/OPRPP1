package hr.fer.oprpp1.hw05.shell.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents ls command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LsShellCommand implements ShellCommand {
	
	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Command that writes directory listing.", "Expects single argument-directory.");
	
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
		Path directory = null;
		try {
			directory = Paths.get(arg[0]);	
		}catch(InvalidPathException Exc) {
			env.writeln("Invalid path name");
			return ShellStatus.CONTINUE;
		}
		if(Files.notExists(directory)) env.writeln("Given directory does not exist");
		else if(Files.isRegularFile(directory)) env.writeln("Given argument is not directory");
		else {
			try (Stream<Path> list = Files.list(directory)) {
				list.forEach(path -> {
					try {
						env.writeln(format(path));
					} catch (IOException e) {
						env.writeln("An error has occured");
					}
				});
			} catch (IOException e) {
				env.writeln("An error has occured");
			}
		}

		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method that formats each row.
	 * @param path {@link Path}
	 * @return formated row
	 * @throws IOException if error has occurred
	 * @since 1.0.0.
	 */

	private String format(Path path) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append(Files.isDirectory(path) ? 'd' : '-');
		sb.append(Files.isReadable(path) ? 'r' : '-');
		sb.append(Files.isWritable(path) ? 'w' : '-');
		sb.append(Files.isExecutable(path) ? 'x' : '-');
		String firstColumn = sb.toString();
		long size = Files.size(path);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BasicFileAttributeView faView = Files.getFileAttributeView(path, BasicFileAttributeView.class,
				LinkOption.NOFOLLOW_LINKS);
		BasicFileAttributes attributes = faView.readAttributes();
		FileTime fileTime = attributes.creationTime();
		String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
		String name = path.getFileName().toString();
		return String.format("%s %10d %s %s", firstColumn, size, formattedDateTime, name);
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "ls";
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
