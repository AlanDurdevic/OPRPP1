package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class that represents object that contains row and column location.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class RCPosition {
	
	/**
	 * Row number.
	 * @since 1.0.0.
	 */
	
	private final int row;
	
	/**
	 * Column number.
	 * @since 1.0.0.
	 */
	
	private final int column;
	
	/**
	 * Constructor with row and column parameter.
	 * @param row row
	 * @param column column
	 * @since 1.0.0.
	 */
	
	public RCPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	
	/**
	 * Method that creates {@link RCPosition} object based on text.
	 * @param text text to be parsed
	 * @return {@link RCPosition}
	 * @throws NullPointerException if <code>text</code> is <code>null</code>.
	 * @throws IllegalArgumentException if <code>text</code> can not be parsed.
	 * @since 1.0.0.
	 */
	
	public static RCPosition parse(String text) {
		String[] args = Objects.requireNonNull(text, "Text can not be null!").split(",");
		int row, column;
		try {
			row = Integer.parseInt(args[0].trim());
			column = Integer.parseInt(args[1].trim());
		}catch(NumberFormatException ex) {
			throw new IllegalArgumentException("Invalid text format!");
		}
		return new RCPosition(row, column);
	}
	
	/**
	 * Getter for row.
	 * @return row
	 * @since 1.0.0.
	 */

	public int getRow() {
		return row;
	}
	
	/**
	 * Getter for column.
	 * @return column
	 * @since 1.0.0.
	 */

	public int getColumn() {
		return column;
	}
	
	/**
	 * {@inheritDoc}
	 */

	@Override
	public int hashCode() {
		return Objects.hash(column, row);
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
		RCPosition other = (RCPosition) obj;
		return column == other.column && row == other.row;
	}
	
}
