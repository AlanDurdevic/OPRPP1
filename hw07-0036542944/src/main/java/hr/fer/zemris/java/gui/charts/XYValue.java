package hr.fer.zemris.java.gui.charts;

/**
 * Class that has two read-only properties.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class XYValue {
	
	/**
	 * X property.
	 * @since 1.0.0.
	 */
	
	private final int x;
	
	/**
	 * Y property.
	 * @since 1.0.0.
	 */
	
	private final int y;
	
	/**
	 * Constructor with both properties.
	 * @param x x property
	 * @param y y property
	 * @since 1.0.0.
	 */
	
	public XYValue(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Getter for x.
	 * @return x
	 * @since 1.0.0.
	 */
	
	public int getX() {
		return x;
	}
	
	/**
	 * Getter for y.
	 * @return y
	 * @since 1.0.0.
	 */
	
	public int getY() {
		return y;
	}

}
