package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class that represents student database. Students are stored in instance of this class.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class StudentDatabase {
	
	/**
	 * Map where are students stored by their index.
	 * @since 1.0.0.
	 */
	
	private Map<String, StudentRecord> indexMapOfStudents;
	
	/**
	 * List of all students in database.
	 * @since 1.0.0.
	 */
	
	private List<StudentRecord> listOfStudents;
	
	/**
	 * Constructor which gets {@link List} of all rows from students database.
	 * @param rows of database
	 * @throws NullPointerException if <code>rows</code> is <code>null</code>
	 * @throws IllegalArgumentException if there are duplicate JMBAG's or student data is invalid.
	 * @since 1.0.0.
	 */
	
	public StudentDatabase(List<String> rows) {
		if(rows == null) throw new NullPointerException("List of rows can not be null!");
		indexMapOfStudents = new HashMap<>();
		listOfStudents = new ArrayList<>();
		for(String row : rows) {
			StudentRecord newStudent = parseRow(row);
			StudentRecord tempRecord = indexMapOfStudents.putIfAbsent(newStudent.getJmbag(), newStudent);
			if(tempRecord != null) throw new IllegalArgumentException("Duplicate JMBAG in database!");
			listOfStudents.add(newStudent);
		}
		
	}
	
	/**
	 * Method that parses row of data.
	 * @param row row to be parsed
	 * @return parsed {@link StudentRecord}
	 * @throws NullPointerException if <code>row</code> is <code>null</code> or row has invalid data
	 * @since 1.0.0.
	 */
	
	private StudentRecord parseRow(String row) {
		if(row == null) throw new NullPointerException("Row can not be null!");
		String[] separatedRowElements = row.split("\t");
		if(separatedRowElements.length != 4) throw new IllegalArgumentException("Invalid number of arguments in row!");
		int finalGrade;
		try {
			finalGrade = Integer.valueOf(separatedRowElements[3]);
		}
		catch(NumberFormatException exc) {
			throw new IllegalArgumentException("Row does not contain finalGrade as integer!");
		}
		if(finalGrade < 1 || finalGrade > 5) {
			throw new IllegalArgumentException("Final grade must be 1-5!");
		}
		return new StudentRecord(separatedRowElements[0], separatedRowElements[1], separatedRowElements[2], finalGrade);
	}
	
	/**
	 * Method that finds student for given JMBAG.
	 * @param jmbag given jmbag
	 * @return {@link StudentRecord} of given JMBAG if student exist; otherwise <code>null</code>
	 * @since 1.0.0.
	 */
	
	public StudentRecord forJMBAG(String jmbag){
		return indexMapOfStudents.get(jmbag);
	}
	
	/**
	 * Method that filters student database based on given filter.
	 * @param filter given {@link IFilter}
	 * @return {@link List} of filtered students
	 * @since 1.0.0.
	 */
	
	public List<StudentRecord> filter(IFilter filter){
		List<StudentRecord> filteredList = new ArrayList<>();
		for(StudentRecord student : listOfStudents) {
			if(filter.accepts(student)) {
				filteredList.add(student);
			}
		}
		return filteredList;
	}
	

}
