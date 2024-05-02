package hr.fer.oprpp1.hw04.db.demo;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import hr.fer.oprpp1.hw04.db.QueryFilter;
import hr.fer.oprpp1.hw04.db.QueryParser;
import hr.fer.oprpp1.hw04.db.QueryParserException;
import hr.fer.oprpp1.hw04.db.RecordFormatter;
import hr.fer.oprpp1.hw04.db.StudentDatabase;
import hr.fer.oprpp1.hw04.db.StudentRecord;

/**
 * Class that represents simple query application.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class QueryDemo {
	
	/**
	 * Starting method of application.
	 * @param args
	 * @throws IOException
	 * @since 1.0.0.
	 */
	
	public static void main(String[] args) throws IOException {
		List<String> lines = Files.readAllLines(
				 Paths.get("./database.txt"),
				 StandardCharsets.UTF_8
				);
		StudentDatabase database = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print("> ");
			String line = sc.nextLine();
			if(line.equals("exit")) {
				System.out.println("Goodbye!");
				sc.close();
				System.exit(0);
			}
			line = line.trim();
			if(line.length() < 5 || !line.substring(0, 5).equals("query")) {
				System.out.println("Unknown command!");
			}
			else {
				line = line.substring(5, line.length());
				try {
					QueryParser parser = new QueryParser(line);
					List<StudentRecord> records = getFilteredDatabase(parser, database);
					List<String> output = RecordFormatter.format(records);
					output.forEach(System.out::println);
				}catch(QueryParserException exc) {
					System.out.println(exc.getMessage());
				}
			}
		}
	}
	
	/**
	 * Method that filters students based on query
	 * @param parser parser for query
	 * @param database database to be filtered
	 * @return filtered list of students.
	 * @since 1.0.0.
	 */

	private static List<StudentRecord> getFilteredDatabase(QueryParser parser, StudentDatabase database) {
		List<StudentRecord> records;
		if(parser.isDirectQuery()) {
			records = new ArrayList<>();
			StudentRecord r = database.forJMBAG(parser.getQueriedJMBAG());
			if(r != null) {
				records.add(r);
			}
		}
		else {
			records = database.filter(new QueryFilter(parser.getQuery()));
		}
		return records;
	}


}
