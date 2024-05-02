package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;

/**
 * Class that represents {@link LayoutManager2} used for basic calculator. It
 * has 5 rows and 7 columns.
 * 
 * @author Alan Đurđević
 * @since 1.0.0.
 */

public class CalcLayout implements LayoutManager2 {

	/**
	 * Number of rows.
	 * 
	 * @since 1.0.0.
	 */

	private static final int NUMBER_OF_ROWS = 5;

	/**
	 * Number of columns.
	 * 
	 * @since 1.0.0.
	 */

	private static final int NUMBER_OF_COLUMNS = 7;

	/**
	 * Space between components.
	 * 
	 * @since 1.0.0.
	 */

	private final int space;

	/**
	 * {@link Map} of components and their positions.
	 * 
	 * @since 1.0.0.
	 */

	private Map<RCPosition, Component> componentList = new HashMap<>();

	/**
	 * Constructor that sets space between components.
	 * 
	 * @param space space
	 * @throws IllegalArgumentException if <code>space</code> is less than 0.
	 * @since 1.0.0.
	 */

	public CalcLayout(int space) {
		if (space < 0)
			throw new IllegalArgumentException("Space can not be less than 0!");
		this.space = space;
	}

	/**
	 * Default constructor. Sets space to 0.
	 * 
	 * @since 1.0.0.
	 */

	public CalcLayout() {
		this(0);
	}

	/**
	 * {@inheritDoc} Not implemented.
	 * 
	 * @throws UnsupportedOperationException
	 * @since 1.0.0.
	 */

	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException();
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if <code>comp</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	@Override
	public void removeLayoutComponent(Component comp) {
		Objects.requireNonNull(comp, "Component can not be null!");
		Set<RCPosition> set = new HashSet<>();
		for (Entry<RCPosition, Component> e : componentList.entrySet()) {
			if (e.getValue().equals(comp)) {
				set.add(e.getKey());
			}
		}
		for (RCPosition rc : set) {
			componentList.remove(rc);
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.0.
	 */

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		return getDimension(parent, Component::getPreferredSize);
	}

	/**
	 * Method that returns dimension based on given function.
	 * 
	 * @param parent parent
	 * @param func   given func
	 * @return dimension
	 * @throws NullPointerException if <code>parent</code> or <code>func</code> is
	 *                              <code>null</code>
	 * @since 1.0.0.
	 */

	private Dimension getDimension(Container parent, Function<Component, Dimension> func) {
		Objects.requireNonNull(parent, "Container can not be null!");
		Objects.requireNonNull(func, "Function can not be null!");
		if (componentList.isEmpty())
			return new Dimension(0, 0);
		Insets insets = parent.getInsets();
		int width = componentList.entrySet().stream()
				.filter(e -> e.getKey().getRow() != 1 || e.getKey().getColumn() != 1)
				.mapToInt(e -> func.apply(e.getValue()).width).max().getAsInt();
		int height = componentList.entrySet().stream().mapToInt((e) -> func.apply(e.getValue()).height).max()
				.getAsInt();
		if (componentList.containsKey(new RCPosition(1, 1))) {
			int w1 = (func.apply(componentList.get(new RCPosition(1, 1))).width - 4 * space) / 5;
			if (w1 > width)
				width = w1;
		}
		int preferredWidth = width * NUMBER_OF_COLUMNS + space * (NUMBER_OF_COLUMNS - 1) + insets.left + insets.right;
		int preferredHeight = height * NUMBER_OF_ROWS + space * (NUMBER_OF_ROWS - 1) + insets.top + insets.bottom;
		return new Dimension(preferredWidth, preferredHeight);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.0.
	 */

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		return getDimension(parent, Component::getMinimumSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if <code>parent</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	@Override
	public void layoutContainer(Container parent) {
		Objects.requireNonNull(parent, "Parent container can not be null!");
		Insets insets = parent.getInsets();
		int width = parent.getWidth() - insets.left - insets.right - space * (NUMBER_OF_COLUMNS - 1);
		int height = parent.getHeight() - insets.top - insets.bottom - space * (NUMBER_OF_ROWS - 1);
		int componentWidth = width / NUMBER_OF_COLUMNS;
		int componentHeight = height / NUMBER_OF_ROWS;
		int wp = (int) (parent.getWidth() - insets.left - insets.right - NUMBER_OF_COLUMNS * componentWidth
				- (NUMBER_OF_COLUMNS - 1) * space);
		int hp = (int) (parent.getHeight() - insets.top - insets.bottom - NUMBER_OF_ROWS * componentHeight
				- (NUMBER_OF_ROWS - 1) * space);
		int[] wa = createUniformArrayWidth(wp);
		int[] ha = createUniformArrayHeight(hp);
		for (Entry<RCPosition, Component> e : componentList.entrySet()) {
			int row = e.getKey().getRow();
			int column = e.getKey().getColumn();
			if (row == 1 && column == 1) {
				e.getValue().setBounds(insets.left, insets.top, (int) componentWidth * 5 + 4 * space + wa[0],
						(int) componentHeight + ha[0]);
			} else
				e.getValue().setBounds(
						(int) (insets.left + (column - 1) * (componentWidth + space) + numberOfOne(wa, column - 1)),
						(int) (insets.top + (row - 1) * (componentHeight + space) + +numberOfOne(ha, row - 1)),
						(int) componentWidth + wa[column - 1], (int) componentHeight + ha[row - 1]);
		}
	}

	/**
	 * Method that count number of 1 in array before index n.
	 * 
	 * @param arr array
	 * @param n   n
	 * @return number of 1
	 * @throws NullPointerException if <code>arr</code> is <code>null</code>ž
	 * @since 1.0.0.
	 */

	private int numberOfOne(int[] arr, int n) {
		Objects.requireNonNull(arr, "Arr can not be null!");
		int number = 0;
		for (int i = 0; i < n; i++) {
			number += arr[i];
		}
		return number;
	}

	/**
	 * Method that creates uniform array for extra pixels positions (height).
	 * 
	 * @param hp number of 1
	 * @return array
	 * @throws IllegalArgumentException if hp is > 4
	 * @since 1.0.0.
	 */

	private int[] createUniformArrayHeight(int hp) {
		switch (hp) {
		case (0):
			return new int[] { 0, 0, 0, 0, 0 };
		case (1):
			return new int[] { 0, 0, 1, 0, 0 };
		case (2):
			return new int[] { 1, 0, 0, 0, 1 };
		case (3):
			return new int[] { 1, 0, 1, 0, 1 };
		case (4):
			return new int[] { 1, 1, 0, 1, 1 };
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Method that creates uniform array for extra pixels positions (width).
	 * 
	 * @param wp number of 1
	 * @return array
	 * @throws IllegalArgumentException if wp is > 6
	 * @since 1.0.0.
	 */

	private int[] createUniformArrayWidth(int wp) {
		switch (wp) {
		case (0):
			return new int[] { 0, 0, 0, 0, 0, 0, 0 };
		case (1):
			return new int[] { 0, 0, 0, 1, 0, 0, 0 };
		case (2):
			return new int[] { 1, 0, 0, 0, 0, 0, 1 };
		case (3):
			return new int[] { 1, 0, 0, 1, 0, 0, 1 };
		case (4):
			return new int[] { 1, 0, 1, 0, 1, 0, 1 };
		case (5):
			return new int[] { 1, 0, 1, 1, 1, 0, 1 };
		case (6):
			return new int[] { 1, 1, 1, 0, 1, 1, 1 };
		default:
			throw new IllegalArgumentException();
		}
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @throws NullPointerException if <code>comp</code> or <code>constraints</code>
	 *                              is <code>null</code>
	 * @throws CalcLayoutException  if constraint is invalid (row or column number
	 *                              is invalid) or {@link CalcLayout} contains
	 *                              element with given constraint
	 * @since 1.0.0.
	 */

	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		Objects.requireNonNull(comp, "Component can not be null!");
		Objects.requireNonNull(constraints, "Constraint can not be null!");
		if (!(constraints instanceof String) && !(constraints instanceof RCPosition)) {
			throw new IllegalArgumentException("Invalid constraint type!");
		}
		RCPosition constraint = (constraints instanceof String) ? RCPosition.parse((String) constraints)
				: (RCPosition) constraints;
		if (componentList.containsKey(constraint))
			throw new CalcLayoutException("Element with given constraint already exists!");
		int row = constraint.getRow();
		int column = constraint.getColumn();
		if (row < 1 || row > NUMBER_OF_ROWS || column < 1 || column > NUMBER_OF_COLUMNS)
			throw new CalcLayoutException();
		if (row == 1 && (column > 1 && column < 6))
			throw new CalcLayoutException();
		if (componentList.containsKey(constraint))
			throw new CalcLayoutException();
		componentList.put(constraint, comp);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.0.
	 */

	@Override
	public Dimension maximumLayoutSize(Container target) {
		return getDimension(target, Component::getMaximumSize);
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return 0
	 * @since 1.0.0.
	 */

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @return 0
	 * @since 1.0.0.
	 */

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @since 1.0.0.
	 */

	@Override
	public void invalidateLayout(Container target) {

	}

}
