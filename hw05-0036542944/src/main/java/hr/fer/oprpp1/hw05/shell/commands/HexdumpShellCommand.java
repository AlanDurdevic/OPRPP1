package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.crypto.Util;
import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellCommand;
import hr.fer.oprpp1.hw05.shell.ShellStatus;

/**
 * Class that represents hexdump command.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class HexdumpShellCommand implements ShellCommand {

	/**
	 * Command description
	 * @since 1.0.0.
	 */
	
	private List<String> description = List.of("Produces hex-output of desired file.", "Expects single argument-file.");
	
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
		Path file = Paths.get(arg[0]);
		if (!Files.exists(file) || Files.isDirectory(file)) {
			env.writeln("File does not exist");
			return ShellStatus.CONTINUE;
		}
		try (InputStream is = new BufferedInputStream(Files.newInputStream(file))){
			int row = 0;
			byte[] buff = new byte[16];
			while(true) {
				int r = is.read(buff);
				if(r < 1)break;
				format(buff, row, env, r);
				row++;
			}
		}catch(IOException exc) {
			env.writeln("An error has ocurred");
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Method that formats and writes.
	 * @param buff buffer
	 * @param row row of text
	 * @param env {@link Environment}
	 * @param r number of bytes
	 * @since 1.0.0.
	 */

	private void format(byte[] buff, int row, Environment env, int r) {
		String stringRow = String.format("%08x:", row*16);
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < 16; i++) {
			if(i != 8) {
				sb.append(" ");
			}
			else sb.append("|");
			if(i < r) {
				sb.append(Util.bytetohex(new byte[] {buff[i]}));
			}
			else sb.append("  ");
		}
		sb.append("| ");
		for(int i = 0; i < r; i++) {
			if(buff[i] < 32 || buff[i] > 127) {
				sb.append(".");
			}
			else sb.append((char) buff[i]);
		}
		env.writeln(stringRow + sb.toString());
	}
	
	/**
	 * {@inheritDoc}
	 * @since 1.0.0.
	 */

	@Override
	public String getCommandName() {
		return "hexdump";
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
