package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that represents program for creating fractals derived from Newton-Raphson iteration,
 * uses sequential implementation.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Newton {
	
	/**
	 * Number of iterations.
	 * @since 1.0.0.
	 */
	
	private static final int NUMBER_OF_ITERATIONS = 16 * 16 * 16;
	
	/**
	 * Convergence threshold.
	 * @since 1.0.0.
	 */
	
	private static final double THRESHOLD = 1e-3;
	
	/**
	 * Root-distance.
	 * @since 1.0.0.
	 */
	
	private static final double ROOT_DISTANCE = 0.002;
	
	/**
	 * Main method of program.
	 * @param args starting arguments
	 * @since 1.0.0.
	 */

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		Scanner sc = new Scanner(System.in);
		String line;
		int noOfRoot = 1;
		List<Complex> roots = new LinkedList<>();
		do {
			System.out.print("Root " + noOfRoot + "> ");
			line = sc.nextLine().replaceAll(" ", "");
			Optional<Complex> parsedComplex = Complex.parse(line);
			if (parsedComplex.isPresent()) {
				roots.add(parsedComplex.get());
				noOfRoot++;
			} else if (!line.equals("done")) {
				System.out.println("Wrong input, enter again!");
			}
		} while (!line.equals("done"));
		if (roots.size() > 0) {
			FractalViewer.show(new MyProducer(roots.toArray(new Complex[0])));
		}
		sc.close();
	}
	
	/**
	 * Class that represents producer of data for fractal image.
	 * @since 1.0.0.
	 */
	
	private static class MyProducer implements IFractalProducer{
		
		/**
		 * Rooted polynomial.
		 * @since 1.0.0.
		 */
		
		private ComplexRootedPolynomial rootedPolynomial;
		
		/**
		 * Polynomial.
		 * @since 1.0.0.
		 */
		
		private ComplexPolynomial polynomial;
		
		/**
		 * Derived polynomial.
		 * @since 1.0.0.
		 */
		
		private ComplexPolynomial derived;
		
		/**
		 * Constructor with roots argument.
		 * @param roots roots
		 * @throws NullPointerException if <code>roots</code> is <code>null</code>
		 * @since 1.0.0.
		 */
		
		public MyProducer(Complex ... roots) {
			Objects.requireNonNull(roots, "Roots can not be null");
			this.rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
			this.polynomial = rootedPolynomial.toComplexPolynom();
			this.derived = polynomial.derive();
		}

		/**
		 * Method that produces data for fractal image.
		 * @since 1.0.0.
		 */
		
		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height, long requestNo,
				IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Image of fractal will appear shortly. Thank you.");
			int offset = 0;
			short[] data = new short[width * height];
			for(int y = 0; y < height; y++) {
				if(cancel.get()) break;
				for(int x = 0; x < width; x++) {
					Complex zn = new Complex(x / (width - 1.0) * (reMax - reMin) + reMin, (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin);
					Complex znold;
					double module;
					int iter = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iter++;
					}while(module > THRESHOLD && iter < NUMBER_OF_ITERATIONS);
					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_DISTANCE);
					data[offset++] =(short) (index + 1);
				}
			}
			observer.acceptResult(data, (short)(polynomial.order() + 1), requestNo);
		}
	}
}
