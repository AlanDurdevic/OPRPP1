package hr.fer.zemris.java.gui.charts;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Objects;

import javax.swing.JComponent;

/**
 * Class that represents BarChart component.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class BarChartComponent extends JComponent {

	private static final long serialVersionUID = 1L;

	/**
	 * {@link BarChart}.
	 * 
	 * @since 1.0.0.
	 */

	private final BarChart barChart;

	/**
	 * Space that is used between description of y-axis and values on y-axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int FIRST_LEFT_DISTANCE = 4;

	/**
	 * Space that is used between values on y-axis and y-axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int SECOND_LEFT_DISTANCE = 2;

	/**
	 * Space that is used between description of x-axis and values on x-axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int FIRST_DOWN_DISTANCE = 4;

	/**
	 * Space that is used between values on x-axis and x-axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int SECOND_DOWN_DISTANCE = 1;

	/**
	 * Space that is used between arrows at the end of each axis and end of window.
	 */

	private static final int ARROW_DISTANCE = 4;

	/**
	 * Length of small dashes on both axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int DASH_LENGTH = 6;

	/**
	 * Length of arrows on both axis.
	 * 
	 * @since 1.0.0.
	 */

	private static final int ARROW_LENGTH = 10;

	/**
	 * Parameter that is used for creating shadows.
	 * 
	 * @since 1.0.0.
	 */

	private static final int SHADOW_PARAMETER = 25;

	/**
	 * Constructor with BarChart parameter.
	 * 
	 * @param barChart barChart
	 * @throws NullPointerException if <code>barChart</code> is <code>null</code>.
	 * @since 1.0.0.
	 */

	public BarChartComponent(BarChart barChart) {
		this.barChart = Objects.requireNonNull(barChart, "BarChart can not be null!");
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.0.
	 */

	@Override
	public void paintComponent(Graphics g) {
		// getting basic parameters for drawing
		Graphics2D g2d = (Graphics2D) g;
		Stroke s = g2d.getStroke(); // default stroke
		Color c = g2d.getColor(); // default color
		Color shadowColor = new Color(192, 192, 192); // color used for shadow
		FontMetrics fm1 = g2d.getFontMetrics(); // default FontMetrics
		Font f1 = g2d.getFont(); // default font
		Font f2 = g2d.getFont().deriveFont(14f); // font used for values on both axis
		g2d.setFont(f2);
		FontMetrics fm2 = g2d.getFontMetrics(); // FontMetrics used for values on both axis
		g2d.setFont(f1);

		List<XYValue> valuesList = barChart.getValuesList();
		String xDescription = barChart.getxDescription();
		String yDescription = barChart.getyDescription();
		int yMin = barChart.getyMin();
		int yMax = barChart.getyMax();
		int yDistance = barChart.getyDistance();

		int xLeftAxis = fm1.getHeight() + FIRST_LEFT_DISTANCE + fm2.stringWidth(String.valueOf(yMax))
				+ SECOND_LEFT_DISTANCE + DASH_LENGTH; // starting x coordinate for x-axis
		int xRightAxis = getWidth() - (ARROW_DISTANCE + ARROW_LENGTH); // ending x coordinate for x-axis
		int yDownAxis = getHeight() - (fm1.getHeight() + FIRST_DOWN_DISTANCE + fm2.getHeight() + SECOND_DOWN_DISTANCE); // starting y coordinate for y-axis
		int yUpAxis = ARROW_DISTANCE + ARROW_LENGTH; // ending y coordinate for y-axis
		
		//drawing xDescription
		g2d.drawString(xDescription, xLeftAxis + (xRightAxis - xLeftAxis - fm1.stringWidth(xDescription)) / 2,
				getHeight() - fm1.getDescent());
		
		//drawing yDescription
		AffineTransform oldAt = g2d.getTransform();
		AffineTransform at = new AffineTransform(oldAt);
		at.rotate(-Math.PI / 2);
		g2d.setTransform(at);
		g2d.drawString(yDescription, -(yDownAxis - (yDownAxis - yUpAxis - fm1.stringWidth(yDescription)) / 2),
				fm1.getAscent());
		g2d.setTransform(oldAt);

		// draw00Dashe
		g2d.drawLine(xLeftAxis, yDownAxis, xLeftAxis, yDownAxis + DASH_LENGTH);

		// drawXdashesAndValues
		int xAxisLength = xRightAxis - xLeftAxis;
		int xColumnSize = xAxisLength / valuesList.size();
		g2d.setFont(f2);
		for (int i = 0; i < valuesList.size(); i++) {
			int x = xLeftAxis + (i + 1) * xColumnSize; //starting x for each column
			int xValue = xLeftAxis + i * xColumnSize
					+ (x - xLeftAxis - i * xColumnSize - fm2.stringWidth(String.valueOf(valuesList.get(i).getX()))) / 2; //coordinate for each value on x-axis
			g2d.drawString(String.valueOf(valuesList.get(i).getX()), xValue,
					yDownAxis + DASH_LENGTH + SECOND_DOWN_DISTANCE + fm2.getHeight());
			g2d.drawLine(x, yDownAxis + DASH_LENGTH, x, yDownAxis); //draw small dash
			//drawing net
			g2d.setColor(Color.GRAY);
			g2d.drawLine(x, yDownAxis, x, yUpAxis);
			g2d.setColor(c);
		}
		// drawYdashesAnadValues
		int yAxisLength = yDownAxis - yUpAxis;
		int yColumnSize = yAxisLength / ((yMax - yMin) / yDistance);
		for (int i = 0; i <= (yMax - yMin) / yDistance; i++) {
			int y = yDownAxis - i * yColumnSize; //starting y for each value
			g2d.drawString(String.valueOf(yMin + i * yDistance), xLeftAxis - DASH_LENGTH - SECOND_LEFT_DISTANCE
					- fm2.stringWidth(String.valueOf(yMin + i * yDistance)), y + fm2.getAscent() / 2);//coordinate for each value on y-axis
			g2d.drawLine(xLeftAxis - DASH_LENGTH, y, xLeftAxis, y);
			//drawing net
			g2d.setColor(Color.GRAY);
			g2d.drawLine(xLeftAxis, y, xRightAxis, y);
			g2d.setColor(c);
		}
		g2d.setFont(f1);
		int shadow = xColumnSize / SHADOW_PARAMETER;
		BasicStroke bs = new BasicStroke(shadow);
		// drawColumns
		for (int i = 0; i < valuesList.size(); i++) {
			XYValue v = valuesList.get(i);
			int y = (int) ((v.getY() - yMin) / Double.valueOf(yDistance) * yColumnSize);
			int x = xLeftAxis + i * xColumnSize;
			// drawShadow
			g2d.setStroke(bs);
			g2d.setColor(shadowColor);
			g2d.drawLine(x + xColumnSize + shadow / 2, yDownAxis - shadow, x + xColumnSize + shadow / 2,
					yDownAxis - y + shadow);
			g2d.setStroke(s);
			//filling columns
			g2d.setColor(c);
			g2d.setColor(Color.ORANGE);
			g2d.fillRect(x, yDownAxis - y, xColumnSize, y);
			//border around column
			g2d.setColor(Color.WHITE);
			g2d.drawRect(x, yDownAxis - y, xColumnSize, y);
		}
		g2d.setColor(c);

		// drawX-AXIS
		g2d.drawLine(xLeftAxis, yDownAxis, xRightAxis, yDownAxis);
		// drawY_AXIS
		g2d.drawLine(xLeftAxis, yDownAxis, xLeftAxis, yUpAxis);

		// drawArrows
		int[] ar1x = { xLeftAxis - DASH_LENGTH, xLeftAxis + DASH_LENGTH, xLeftAxis };
		int[] ar1y = { yUpAxis, yUpAxis, yUpAxis - DASH_LENGTH };
		Polygon p1 = new Polygon(ar1x, ar1y, 3);
		g2d.drawPolygon(p1);
		g2d.fillPolygon(p1);
		int[] ar2x = { xRightAxis + DASH_LENGTH, xRightAxis, xRightAxis };
		int[] ar2y = { yDownAxis, yDownAxis - DASH_LENGTH, yDownAxis + DASH_LENGTH };
		Polygon p2 = new Polygon(ar2x, ar2y, 3);
		g2d.drawPolygon(p2);
		g2d.fillPolygon(p2);

	}

}
