package hr.fer.oprpp1.hw04.db;

import java.util.LinkedList;
import java.util.List;

/**
 * Class for formating StudentRecords.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class RecordFormatter {
	
	/**
	 * Method that formats given list of StudentRecords.
	 * @param list of students records
	 * @return formated records
	 * @since 1.0.0.
	 */
	
	public static List<String> format(List<StudentRecord> list){
		List<String> lineList = new LinkedList<>();
		if(list.size() != 0) {
			int maxLastName = 0;
			int maxFirstName = 0;
			for(StudentRecord sr : list) {
				if(sr.getLastName().length() > maxLastName) {
					maxLastName = sr.getLastName().length();
				}
				if(sr.getFirstName().length() > maxFirstName) {
					maxFirstName = sr.getFirstName().length();
				}
			}
			StringBuilder sb = new StringBuilder();
			sb.append("+============+=");
			for(int i = 0; i < maxLastName; i++) {
				sb.append("=");
			}
			sb.append("=+=");
			for(int i = 0; i < maxFirstName; i++) {
				sb.append("=");
			}
			sb.append("=+===+");
			String begEnd = sb.toString();
			lineList.add(begEnd);
			sb.setLength(0);
			for(StudentRecord sr : list) {
				sb.append("| ");
				sb.append(sr.getJmbag());
				sb.append(" | ");
				sb.append(sr.getLastName());
				for(int i = sr.getLastName().length(); i < maxLastName; i++) {
					sb.append(" ");
				}
				sb.append(" | ");
				sb.append(sr.getFirstName());
				for(int i = sr.getFirstName().length(); i < maxFirstName; i++) {
					sb.append(" ");
				}
				sb.append(" | ");
				sb.append(sr.getFinalGrade());
				sb.append(" |");
				lineList.add(sb.toString());
				sb.setLength(0);
			}
			lineList.add(begEnd);
		}
		lineList.add("Records selected: " + list.size() + "\n");
		return lineList;
	}

}
