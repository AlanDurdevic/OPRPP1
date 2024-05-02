package hr.fer.zemris.java.fractals;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;

/**
 * Class that represents program for creating fractals derived from Newton-Raphson iteration,
 * uses multithreaded implementation.
 * @author Alan Đurđević
 * @version 1.0.0.
*/

public class NewtonParallel {

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
	 * Number of threads.
	 * Default is <code>Runtime.getRuntime().availableProcessors()</code>
	 * @since 1.0.0.
	 */
	
	private static int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
	
	/**
	 * Number of tracks (jobs).
	 * Default is <code>Runtime.getRuntime().availableProcessors() * 4</code>
	 * @since 1.0.0.
	 */
	
	private static int NUMBER_OF_TRACKS = Runtime.getRuntime().availableProcessors() * 4;

	/**
	 * Main method of program.
	 * @param args starting arguments
	 * @since 1.0.0.
	 */
	
	public static void main(String[] args) {
		try {
			parseArguments(args);
		}catch(NumberFormatException exc) {
			System.out.println("Invalid number type " + exc.getMessage());
			return;
		}catch(IllegalArgumentException exc) {
			System.out.println(exc.getMessage());
			return;
		}
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
			FractalViewer.show(new MyProducerParallel(roots.toArray(new Complex[0])));
		}
		sc.close();
	}
	
	/**
	 * Method for parsing starting arguments.
	 * @param args starting arguments
	 * @throws IllegalArgumentException if arguments are illegal
	 * @throws NumberFormatException if argument is not number as it should
	 * @since 1.0.0.
	 */

	private static void parseArguments(String[] args) {
		boolean hasWorkers = false;
		boolean hasTracks = false;
		for(int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(arg.startsWith("--workers=")) {
				if(hasWorkers) throw new IllegalArgumentException("Can not duplicate workers argument");
				NUMBER_OF_THREADS = Integer.parseInt(arg.substring(arg.indexOf("=") + 1, arg.length()));
				hasWorkers = true;
			}
			else if(arg.startsWith("--tracks=")) {
				if(hasTracks) throw new IllegalArgumentException("Can not duplicate tracks argument");
				NUMBER_OF_TRACKS = Integer.parseInt(arg.substring(arg.indexOf("=") + 1, arg.length()));
				hasTracks = true;
			}
			else if(arg.equals("-w")) {
				if(hasWorkers) throw new IllegalArgumentException("Can not duplicate workers argument");
				if(i == args.length - 1) throw new IllegalArgumentException("Missing value for workers");
				NUMBER_OF_THREADS = Integer.parseInt(args[i + 1]);
				hasWorkers = true;
				i++;
			}
			else if(arg.equals("-t")) {
				if(hasTracks) throw new IllegalArgumentException("Can not duplicate tracks argument");
				if(i == args.length - 1) throw new IllegalArgumentException("Missing value for tracks");
				NUMBER_OF_TRACKS = Integer.parseInt(args[i + 1]);
				hasTracks = true;
				i++;
			}
			else throw new IllegalArgumentException("Illegal argument " + arg);
		}
		if (NUMBER_OF_THREADS < 1) throw new IllegalArgumentException("Number of  workers can not be less than 1");
		if (NUMBER_OF_TRACKS < 1) throw new IllegalArgumentException("Number of  tracks can not be less than 1");
	}
	
	/**
	 * Class that represents work for calculating fractals.
	 * @since 1.0.0.
	 */

	public static class WorkCalculation implements Runnable {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;
		ComplexRootedPolynomial rootedPolynomial;

		ComplexPolynomial polynomial;

		ComplexPolynomial derived;

		public static WorkCalculation NO_JOB = new WorkCalculation();

		private WorkCalculation() {
		}

		public WorkCalculation(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rootedPolynomial) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.rootedPolynomial = rootedPolynomial;
			this.polynomial = rootedPolynomial.toComplexPolynom();
			this.derived = polynomial.derive();
		}

		@Override
		public void run() {
			int offset = yMin * width;
			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					Complex zn = new Complex(x / (width - 1.0) * (reMax - reMin) + reMin, (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin);;
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
					} while (module > THRESHOLD && iter < NUMBER_OF_ITERATIONS);
					int index = rootedPolynomial.indexOfClosestRootFor(zn, ROOT_DISTANCE);
					data[offset++] = (short) (index + 1);
				}
			}
		}
	}
	
	/**
	 * Class that represents producer of data for fractal image.
	 * @since 1.0.0.
	 */

	public static class MyProducerParallel implements IFractalProducer {
		
		/**
		 * Rooted polynomial.
		 * @since 1.0.0.
		 */

		private ComplexRootedPolynomial rootedPolynomial;
		
		/**
		 * Constructor with roots argument.
		 * @param roots roots
		 * @throws NullPointerException if <code>roots</code> is <code>null</code>
		 * @since 1.0.0.
		 */

		public MyProducerParallel(Complex... roots) {
			Objects.requireNonNull(roots, "Roots can not be null");
			this.rootedPolynomial = new ComplexRootedPolynomial(Complex.ONE, roots);
		}
		
		/**
		 * Method that produces data for fractal image.
		 * @since 1.0.0.
		 */

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			int m = NUMBER_OF_ITERATIONS;
			short[] data = new short[width * height];
			final int numberOfTracks = NUMBER_OF_TRACKS > height ? height : NUMBER_OF_TRACKS;
			int numberOfYPerTrack = height / numberOfTracks;
			System.out.println("Number of threads: " + NUMBER_OF_THREADS);
			System.out.println("Number of tracks: " + numberOfTracks);
			final BlockingQueue<WorkCalculation> queue = new LinkedBlockingQueue<>();
			Thread[] workers = new Thread[NUMBER_OF_THREADS];
			for (int i = 0; i < workers.length; i++) {
				workers[i] = new Thread(new Runnable() {
					@Override
					public void run() {
						while (true) {
							WorkCalculation p = null;
							try {
								p = queue.take();
								if (p == WorkCalculation.NO_JOB)
									break;
							} catch (InterruptedException e) {
								continue;
							}
							p.run();
						}
					}
				});
			}
			for (int i = 0; i < workers.length; i++) {
				workers[i].start();
			}

			for (int i = 0; i < numberOfTracks; i++) {
				int yMin = i * numberOfYPerTrack;
				int yMax = (i + 1) * numberOfYPerTrack - 1;
				if (i == numberOfTracks - 1) {
					yMax = height - 1;
				}
				WorkCalculation posao = new WorkCalculation(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m,
						data, cancel, rootedPolynomial);
				while (true) {
					try {
						queue.put(posao);
						break;
					} catch (InterruptedException e) {
					}
				}
			}
			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						queue.put(WorkCalculation.NO_JOB);
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			for (int i = 0; i < workers.length; i++) {
				while (true) {
					try {
						workers[i].join();
						break;
					} catch (InterruptedException e) {
					}
				}
			}

			observer.acceptResult(data, (short) (rootedPolynomial.toComplexPolynom().order() + 1), requestNo);
		}
	}
}
