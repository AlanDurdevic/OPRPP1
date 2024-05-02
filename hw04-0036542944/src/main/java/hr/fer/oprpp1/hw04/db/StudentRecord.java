package hr.fer.oprpp1.hw04.db;

import java.util.Objects;

/**
 * Class which instances represent record for each student.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class StudentRecord {
	
	/**
	 * Student's JMBAG.
	 * @since 1.0.0.
	 */
	
	private String jmbag;
	
	/**
	 * Student's last name.
	 * @since 1.0.0.
	 */
	
	private String lastName;
	
	/**
	 * Student's first name.
	 * @since 1.0.0.
	 */
	
	private String firstName;
	
	/**
	 * Student's final grade.
	 * @since 1.0.0.
	 */
	
	private int finalGrade;
	
	/**
	 * Constructor with all parameters for student.
	 * @param jmbag student's JMBAG
	 * @param lastName student's last name
	 * @param firstName student's first name
	 * @param finalGrade student's final grade
	 * @throws NullPointerException if <code>JMBAG</code> or <code>lastName</code> or <code>firstName</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public StudentRecord(String jmbag, String lastName, String firstName, int finalGrade) {
		super();
		this.jmbag = Objects.requireNonNull(jmbag, "JMBAG can not be null!");
		this.lastName = Objects.requireNonNull(lastName, "LastName can not be null!");
		this.firstName = Objects.requireNonNull(firstName, "FirstName can not be null!");
		this.finalGrade = finalGrade;
	}
	
	/**
	 * Getter for student's JMBAG.
	 * @return student's JMBAG
	 * @since 1.0.0.
	 */

	public String getJmbag() {
		return jmbag;
	}

	/**
	 * Getter for student's last name.
	 * @return student's last name
	 * @since 1.0.0.
	 */

	public String getLastName() {
		return lastName;
	}

	/**
	 * Getter for student's first name.
	 * @return student's first name
	 * @since 1.0.0.
	 */

	public String getFirstName() {
		return firstName;
	}

	/**
	 * Getter for student's final grade.
	 * @return student's final grade
	 * @since 1.0.0.
	 */

	public int getFinalGrade() {
		return finalGrade;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public int hashCode() {
		return Objects.hash(jmbag);
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StudentRecord other = (StudentRecord) obj;
		return Objects.equals(jmbag, other.jmbag);
	}
	
	

}
