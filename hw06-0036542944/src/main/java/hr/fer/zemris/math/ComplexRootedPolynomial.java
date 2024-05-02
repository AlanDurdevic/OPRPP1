package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class that represents model of complex polynomial in form
 * z0*(z-z1)*(z-z2)*...*(z-zn).
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ComplexRootedPolynomial {

	/**
	 * Constant z0 of polynomial.
	 * 
	 * @since 1.0.0.
	 */

	private final Complex constant;

	/**
	 * {@link List} of polynomial roots.
	 * 
	 * @since 1.0.0.
	 */

	private final List<Complex> roots;

	/**
	 * Constructor that gets z0 constant and all roots of polynomial.
	 * 
	 * @param constant z0 constant
	 * @param roots    all roots
	 * @throws NullPointerException if <code>constant</code> or one
	 *                              <code>root</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public ComplexRootedPolynomial(Complex constant, Complex... roots) {
		this.constant = Objects.requireNonNull(constant, "Constant can not be null");
		Objects.requireNonNull(roots, "Roots can not be null");
		Stream.of(roots).forEach((r) -> Objects.requireNonNull(r, "Root can not be null"));
		this.roots = Arrays.asList(roots);
	}

	/**
	 * Method that computes polynomial value for specific z.
	 * 
	 * @param z z value
	 * @return computed {@link Complex} value
	 * @throws NullPointerException if <code>z</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public Complex apply(Complex z) {
		Objects.requireNonNull(z, "z can not be null");
		List<Complex> list = new ArrayList<>();
		for (Complex root : roots) {
			list.add(z.sub(root));
		}
		Complex result = constant;
		for (Complex c : list) {
			result = result.multiply(c);
		}
		return result;
	}

	/**
	 * Method that converts this {@link ComplexRootedPolynomial} to
	 * {@link ComplexPolynomial}.
	 * 
	 * @return converted {@link ComplexPolynomial}
	 * @since 1.0.0.
	 */

	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial cp = new ComplexPolynomial(constant);
		for (Complex root : roots) {
			cp = cp.multiply(new ComplexPolynomial(root.negate(), Complex.ONE));
		}
		return cp;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(" + constant + ")");
		sb.append("*(z-(");
		int i = 0;
		for (Complex c : roots) {
			if (i == 0) {
				sb.append(c);
			} else
				sb.append("))*(z-(" + c);
			i++;
		}
		sb.append("))");
		return sb.toString();
	}

	/**
	 * Method that finds index of closest root for given complex number that is
	 * within threshold.
	 * 
	 * @param z        complex number
	 * @param threshold threshold
	 * @return index of closest root; -1 if there is no such root
	 * @throws NullPointerException if <code>z</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public int indexOfClosestRootFor(Complex z, double threshold) {
		Objects.requireNonNull("z can not be null");
		int index = -1;
		int i = -1;
		double minDistance = Double.MAX_VALUE;
		for (Complex c : roots) {
			i++;
			double newDistance = z.sub(c).module();
			if (newDistance < minDistance) {
				minDistance = newDistance;
				index = i;
			}
		}
		return minDistance < threshold && index != -1 ? index : -1;
	}

}
