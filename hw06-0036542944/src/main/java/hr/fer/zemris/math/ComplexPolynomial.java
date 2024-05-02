package hr.fer.zemris.math;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Class that represents model of complex polynomial in form
 * zn*zn+zn-1*zn-1+...+z2*z2+z1*z+z0.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class ComplexPolynomial {

	/**
	 * {@link List} of polynomial factors.
	 * 
	 * @since 1.0.0.
	 */

	private final List<Complex> factors;

	/**
	 * Constructor that gets all factors of polynomial.
	 * 
	 * @param factors factors of polynomial
	 * @throws NullPointerException     if one <code>factor</code> is
	 *                                  <code>null</code>
	 * @throws IllegalArgumentException if number of factors is less than 1
	 * @since 1.0.0.
	 */

	public ComplexPolynomial(Complex... factors) {
		Objects.requireNonNull(factors, "Factors can not be null");
		Stream.of(factors).forEach((f) -> Objects.requireNonNull(f, "Factor can not be null"));
		if (factors.length < 1)
			throw new IllegalArgumentException("Number of factors can not be less than 1");
		this.factors = Arrays.asList(factors);
	}

	/**
	 * Method that returns order of polynomial.
	 * 
	 * @return order of polynomial.
	 * @since 1.0.0.
	 */

	public short order() {
		return (short) (factors.size() - 1);
	}

	/**
	 * Method that multiplies {@link ComplexPolynomial} with another
	 * {@link ComplexPolynomial}.
	 * 
	 * @param p other {@link ComplexPolynomial}
	 * @return result of multiplication
	 * @throws NullPointerException if <code>p</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	public ComplexPolynomial multiply(ComplexPolynomial p) {
		Objects.requireNonNull(p, "p can not be null");
		Complex[] newFactors = new Complex[this.order() + p.order() + 1];
		for (int i = 0; i < this.factors.size(); i++) {
			for (int j = 0; j < p.factors.size(); j++) {
				if (newFactors[i + j] == null)
					newFactors[i + j] = Complex.ZERO;
				newFactors[i + j] = newFactors[i + j].add(this.factors.get(i).multiply(p.factors.get(j)));
			}
		}
		return new ComplexPolynomial(newFactors);
	}

	/**
	 * Method that derives {@link ComplexPolynomial}.
	 * 
	 * @return derived {@link ComplexPolynomial}
	 * @since 1.0.0.
	 */

	public ComplexPolynomial derive() {
		Complex[] newFactors = new Complex[factors.size() - 1];
		for (int i = 1; i < factors.size(); i++) {
			newFactors[i - 1] = factors.get(i).multiply(new Complex(i, 0));
		}
		return new ComplexPolynomial(newFactors);
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
		Complex result = Complex.ZERO;
		for (int i = 0; i < factors.size(); i++) {
			result = result.add(factors.get(i).multiply(z.power(i)));
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < factors.size(); i++) {
			if (i != 0)
				sb.append("+");
			if (i != factors.size() - 1)
				sb.append("(" + factors.get(factors.size() - i - 1) + ")*z^" + (factors.size() - i - 1));
			else
				sb.append("(" + factors.get(factors.size() - i - 1) + ")");
		}
		return sb.toString();
	}

}