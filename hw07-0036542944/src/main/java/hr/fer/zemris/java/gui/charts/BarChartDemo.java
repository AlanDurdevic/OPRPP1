package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Class that represents basic program for creating BarChart.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class BarChartDemo extends JFrame {

	private static final long serialVersionUID = 1L;
	
	/**
	 * {@link BarChart}.
	 * @since 1.0.0.
	 */

	private BarChart barChart;
	
	/**
	 * Path of file with data for BarChart.
	 * @since 1.0.0.
	 */

	private String path;
	
	/**
	 * Constructor with BarChart and path parameter.
	 * @param barChart BarChart
	 * @param path path
	 * @throws NullPointerException if <code>barChart</code> or <code>path</code>
	 * is <code>null</code>.
	 * @since 1.0.0.
	 */

	public BarChartDemo(BarChart barChart, String path) {
		this.barChart = Objects.requireNonNull(barChart, "BarChart can not be null!");
		this.path = Objects.requireNonNull(path, "Path can not be null!");
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setSize(700, 300);
		setLocationRelativeTo(null);
		initGUI();
	}
	
	/**
	 * Method for initializing GUI.
	 * @since 1.0.0.
	 */

	private void initGUI() {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		cp.add(new JLabel(path, SwingConstants.CENTER), BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(barChart), BorderLayout.CENTER);
	}
	
	/**
	 * Main method.
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) throws IOException {
		if (args.length != 1)
			throw new IllegalArgumentException("Invalid number of arguments!");
		try {
			
		Scanner sc = new Scanner(Paths.get(args[0]));
		String xDescription = sc.nextLine();
		String yDescription = sc.nextLine();
		String[] xy = sc.nextLine().split(" ");
		List<XYValue> valuesList = new ArrayList<>();
		for (String s : xy) {
			String[] sp = s.split(",");
			if(sp.length != 2) throw new IllegalArgumentException("Wrong file format!");
			valuesList.add(new XYValue(Integer.parseInt(sp[0].trim()), Integer.parseInt(sp[1].trim())));
		}
		int yMin = Integer.parseInt(sc.nextLine().trim());
		int yMax = Integer.parseInt(sc.nextLine().trim());
		int yDistance = Integer.parseInt(sc.nextLine().trim());
		SwingUtilities.invokeLater(
				() -> new BarChartDemo(new BarChart(valuesList, xDescription, yDescription, yMin, yMax, yDistance),
						args[0]).setVisible(true));
		sc.close();
		}catch(Exception exc) {
			System.err.println("An error has occured! " + exc.getMessage());
		}
	}
}
