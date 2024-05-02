package hr.fer.zemris.java.gui.charts;

import java.util.List;
import java.util.Objects;

/**
 * Class that represents basic BarChart.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class BarChart {
	
	/**
	 * {@link List} of {@link XYValue} objects.
	 * @since 1.0.0.
	 */
	
	private final List<XYValue> valuesList;
	
	/**
	 * Description for x-axis.
	 * @since 1.0.0.
	 */
	
	private final String xDescription;
	
	/**
	 * Description for y-axis.
	 * @since 1.0.0.
	 */
	
	private final String yDescription;
	
	/**
	 * Minimal value on y-axis.
	 * @since 1.0.0.
	 */
	
	private final int yMin;
	
	/**
	 * Maximum value on y-axis.
	 * @since 1.0.0.
	 */
	
	private final int yMax;
	
	/**
	 * Distance between values on y-axis.
	 * @since 1.0.0.
	 */
	
	private final int yDistance;
	
	/**
	 * Constructor with all parameters for BarChart.
	 * @param valuesList list of {@link XYValue} objects
	 * @param xDescription description for x-axis
	 * @param yDescription description for y-axis
	 * @param yMin minimal value on y-axis
	 * @param yMax maximum value on y-axis
	 * @param yDistance distance between values on y-axis
	 * @throws NullPointerException if <code>valueList</code> or <code>xDescription</code>
	 * or <code>yDescription</code> or any {@link XYValue} object in <code>valueList</code>
	 * is <code>null</code>.
	 * @throws IllegalArgumentException if <code>yMin</code> is negative or <code>xMin</code>
	 * is not greater than <code>yMin</code> or any {@link XYValue} object in <code>valuesList</code>
	 * contains <code>y</code> value less than <code>yMin</code>.
	 * @since 1.0.0.
	 */

	public BarChart(List<XYValue> valuesList, String xDescription, String yDescription, int yMin, int yMax,
			int yDistance) {
		this.valuesList = Objects.requireNonNull(valuesList, "List of values can not be null!");
		valuesList.forEach((v) -> Objects.requireNonNull(v, "Value in list of values can not be null!"));
		this.xDescription = Objects.requireNonNull(xDescription, "Description of x-axis can not be null!");
		this.yDescription = Objects.requireNonNull(yDescription, "Description of y-axis can not be null!");
		if(yMin < 0) throw new IllegalArgumentException("Minimal y can not be negative!");
		if(!(yMax > yMin)) throw new IllegalArgumentException("Maximal y must be greater than minimal y!");
		this.yMin = yMin;
		this.yMax = (yMax - yMin) % yDistance == 0 ? yMax : (((yMax - yMin) / yDistance) + 1) * yDistance + yMin;
		this.yDistance = yDistance;
		for(XYValue v : valuesList) {
			if(v.getY() < yMin) {
				throw new IllegalArgumentException("List of values contains y that is less than minimal y!");
			}
		}
	}
	
	/**
	 * Getter for valuesList.
	 * @return valuesList
	 * @since 1.0.0.
	 */

	public List<XYValue> getValuesList() {
		return valuesList;
	}
	
	/**
	 * Getter for xDescription.
	 * @return xDescription
	 * @since 1.0.0.
	 */

	public String getxDescription() {
		return xDescription;
	}
	
	/**
	 * Getter for yDescription.
	 * @return yDescription
	 * @since 1.0.0.
	 */

	public String getyDescription() {
		return yDescription;
	}
	
	/**
	 * Getter for yMin.
	 * @return yMin
	 * @since 1.0.0.
	 */

	public int getyMin() {
		return yMin;
	}
	
	/**
	 * Getter for yMax.
	 * @return yMax
	 * @since 1.0.0.
	 */

	public int getyMax() {
		return yMax;
	}
	
	/**
	 * Getter for yDistance.
	 * @return yDistance
	 * @since 1.0.0.
	 */

	public int getyDistance() {
		return yDistance;
	}
	
}
