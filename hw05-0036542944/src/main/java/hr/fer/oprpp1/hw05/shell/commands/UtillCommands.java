package hr.fer.oprpp1.hw05.shell.commands;

import java.util.Arrays;
import java.util.Objects;

import hr.fer.oprpp1.hw05.shell.Environment;

/**
 * Class that provides multiple static method for shell commands.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class UtillCommands {
	
	/**
	 * Method that check if env and arguments is not null.
	 * @param env env
	 * @param arguments arguments
	 * @throws NullPointerException if <code>env</code> or arguments is <code>null</code>
	 * @since 1.0.0.
	 */

	public static void checkEnvAndArg(Environment env, String arguments) {
		Objects.requireNonNull(env, "Environment can not be null");
		Objects.requireNonNull(arguments, "Arguments can not be null");
	}
	
	/**
	 * Method that parses line based of empty space.
	 * @param line line to be parsed
	 * @return parsed array
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public static String[] parseEmptySpace(String line) {
		Objects.requireNonNull(line, "Line can not be null");
		if(line.isBlank()) {
			return new String[0];
		}
		return line.split(" ");
	}
	
	/**
	 * Method that parses line like file names.
	 * @param line line to be parsed
	 * @return parsed array
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 * @throws IllegalArgumentException if arguments are illegal
	 * @since 1.0.0.
	 */

	public static String[] parseFileParameter(String line) {
		Objects.requireNonNull(line, "Line can not be null");
		String[] arr = new String[0];
		int numberOfElements = 0;
		line = line.trim();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < line.length(); i++) {
			boolean found = false;
			if (numberOfElements >= arr.length) {
				arr = Arrays.copyOf(arr, arr.length + 1);
			}
			i = skipBlanks(i, line);
			if (i < line.length() && line.charAt(i) == '"') {
				i++;
				while (i < line.length() && line.charAt(i) != '"') {
					found = true;
					if (i + 1 < line.length() && ((line.charAt(i) == '\\' && line.charAt(i + 1) == '"') ||
					    (line.charAt(i) == '\\' && line.charAt(i + 1) == '\\'))) {
						i++;
					}
					sb.append(line.charAt(i));
					i++;
				}
				if(i == line.length()) throw new IllegalArgumentException();
				i++;
				if(i < line.length() && line.charAt(i) != ' ') throw new IllegalArgumentException();
			} else {
				while (i < line.length() && line.charAt(i) != ' ') {
					if(line.charAt(i) == '"') throw new IllegalArgumentException();
					sb.append(line.charAt(i));
					i++;
					found = true;
				}
			}
			if(found) arr[numberOfElements++] = sb.toString();
			sb.setLength(0);
		}
		return arr;
	}
	
	/**
	 * Method that skips all blanks
	 * @param i starting index
	 * @param line line
	 * @return next index
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	private static int skipBlanks(int i, String line) {
		Objects.requireNonNull(line, "Line can not be null");
		while (i < line.length()) {
			char c = line.charAt(i);
			if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
				i++;
			} else
				break;
		}
		return i;
	}

}
