package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import static java.lang.Math.*;

/**
 * Class that represents model for unmodifiable complex number.
 * 
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Complex {

	/**
	 * Real part of complex number.
	 * 
	 * @since 1.0.0.
	 */

	private final double re;

	/**
	 * Imaginary part of complex number.
	 * 
	 * @since 1.0.0.
	 */

	private final double im;

	/**
	 * Object that represents 0+0i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public static final Complex ZERO = new Complex(0, 0);

	/**
	 * Object that represents 1+0i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public static final Complex ONE = new Complex(1, 0);

	/**
	 * Object that represents -1+0i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public static final Complex ONE_NEG = new Complex(-1, 0);

	/**
	 * Object that represents 0+1i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public static final Complex IM = new Complex(0, 1);

	/**
	 * Object that represents 0-1i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public static final Complex IM_NEG = new Complex(0, -1);

	/**
	 * Default constructor that creates 0+0i complex number.
	 * 
	 * @since 1.0.0.
	 */

	public Complex() {
		this(0, 0);
	}

	/**
	 * Constructor that gets real and imaginary part of complex number.
	 * 
	 * @param re real part
	 * @param im imaginary part
	 * @since 1.0.0.
	 */

	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}

	/**
	 * Method that returns module of complex number.
	 * 
	 * @return module of complex number
	 * @since 1.0.0.
	 */

	public double module() {
		return hypot(re, im);
	}

	/**
	 * Method that multiplies complex number with given complex number.
	 * 
	 * @param c given complex number
	 * @return {@link Complex} result of multiplication
	 * @since 1.0.0.
	 */

	public Complex multiply(Complex c) {
		Objects.requireNonNull(c, "Complex number can not be null");
		return new Complex(re * c.re - im * c.im, re * c.im + im * c.re);
	}

	/**
	 * Method that divides complex number with given complex number.
	 * 
	 * @param c given complex number
	 * @return {@link Complex} result of division
	 * @since 1.0.0.
	 */

	public Complex divide(Complex c) {
		Objects.requireNonNull(c, "Complex number can not be null");
		if (c.re * c.re + c.im * c.im == 0)
			throw new ArithmeticException("Can not divide with zero");
		return new Complex((re * c.re + im * c.im) / (c.re * c.re + c.im * c.im),
				(im * c.re - re * c.im) / (c.re * c.re + c.im * c.im));
	}

	/**
	 * Method that adds complex number to given complex number.
	 * 
	 * @param c given complex number
	 * @return {@link Complex} result of adding
	 * @since 1.0.0.
	 */

	public Complex add(Complex c) {
		Objects.requireNonNull(c, "Complex number can not be null");
		return new Complex(re + c.re, im + c.im);
	}

	/**
	 * Method that subtract complex number with given complex number.
	 * 
	 * @param c given complex number
	 * @return {@link Complex} result of subtraction
	 * @since 1.0.0.
	 */

	public Complex sub(Complex c) {
		Objects.requireNonNull(c, "Complex number can not be null");
		return new Complex(re - c.re, im - c.im);
	}

	/**
	 * Method that negates complex number.
	 * 
	 * @return negated {@link Complex} number
	 * @since 1.0.0.
	 */

	public Complex negate() {
		return new Complex(-re, -im);
	}

	/**
	 * Method that gives n-th power of complex number.
	 * 
	 * @param n desired power
	 * @return n-th power of complex number
	 * @throws IllegalArgumentException if <code>n</code> is negative
	 * @since 1.0.0.
	 */

	public Complex power(int n) {
		if (n < 0)
			throw new IllegalArgumentException("n can not be negative");
		double r = pow(this.module(), n);
		double angle = getAngle(re, im, n);
		return new Complex(r * cos(angle), r * sin(angle));
	}

	/**
	 * Method that returns angle of complex number.
	 * 
	 * @param re real part of complex number
	 * @param im imaginary part of complex number
	 * @param n  multiplication integer
	 * @return angle
	 * @since 1.0.0.
	 */

	private static double getAngle(double re, double im, int n) {
		double angle = (atan2(im, re) * n) % (2 * PI);
		if (angle < 0)
			angle = angle + 2 * PI;
		return angle;
	}

	/**
	 * Method that returns list of all desired roots of complex number.
	 * 
	 * @param n desired root
	 * @return {@link List} of roots
	 * @throws IllegalArgumentException if <code>n</code> is not positive
	 * @since 1.0.0.
	 */

	public List<Complex> root(int n) {
		if (n <= 0)
			throw new IllegalArgumentException("n must be positive");
		List<Complex> list = new ArrayList<>();
		double r = pow(this.module(), 1. / n);
		for (int i = 0; i < n; i++) {
			double angle = getAngle(re, im, 1) / n;
			angle = angle + 2 * i * PI / n % (2 * PI);
			list.add(new Complex(r * cos(angle), r * sin(angle)));
		}
		return list;
	}

	/**
	 * {@inheritDoc}
	 */

	@Override
	public String toString() {
		if (im < 0)
			return String.format(Locale.US, "%f-i%f", re, abs(im));
		// return String.format(Locale.US, "%.1f+i%.1f", re, im);
		return String.format(Locale.US, "%f+i%f", re, im);
	}
	
	/**
	 * Method that parses String to complex number.
	 * @param line line to be parsed
	 * @return {@link Optional<Complex>} if parsing is successful it contains value; otherwise is empty
	 * @throws NullPointerException if <code>line</code> is <code>null</code>
	 */

	public static Optional<Complex> parse(String line) {
		Objects.requireNonNull(line, "Line can not be null");
		if (line.isEmpty())
			return Optional.empty();
		else if (line.equals("0") || line.equals("i0") || line.equals("0+i0") || line.equals("0-i0")) {
			return Optional.of(Complex.ZERO);
		}
		double realPart = 0, imaginaryPart = 0;
		if (!line.contains("i")) {
			try {
				realPart = Double.parseDouble(line);
				return Optional.of(new Complex(realPart, 0));
			} catch (NumberFormatException exc) {
				return Optional.empty();
			}
		} else {
			if (line.indexOf("i") == 0 || line.indexOf("i") == 1) {
				line = line.replaceFirst("i", "");
				if (line.isEmpty() || line.equals("+") || line.equals("-"))
					line = line.concat("1");
				try {
					imaginaryPart = Double.parseDouble(line);
					return Optional.of(new Complex(0, imaginaryPart));
				} catch (NumberFormatException exc) {
					return Optional.empty();
				}
			} else {
				int count = 0;
				for (int i = 0; i < line.length(); i++) {
					if (line.charAt(i) == 'i')
						count++;
				}
				if (count == 1) {
					int index = line.indexOf("i");
					char c = line.charAt(index - 1);
					if (c != '+' && c != '-')
						return Optional.empty();
					String real = line.substring(0, index - 1);
					String imaginary = line.substring(index - 1, line.length());
					imaginary = imaginary.replaceFirst("i", "");
					if(imaginary.length() == 1) imaginary = imaginary + 1;
					try {
						realPart = Double.parseDouble(real);
						imaginaryPart = Double.parseDouble(imaginary);
						return Optional.of(new Complex(realPart, imaginaryPart));
					} catch (NumberFormatException exc) {
						return Optional.empty();
					}
				}
			}
		}
		return Optional.empty();

	}
}
