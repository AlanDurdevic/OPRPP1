package hr.fer.oprpp1.custom.collections.demo;

import hr.fer.oprpp1.custom.collections.Tester;

/**
 * Class that test behavior of {@link Tester}. 
 * @author Alan Đurđević
 * @since 1.0.0.
 */


class EvenIntegerTester implements Tester {
	
	/**
	 * Tests if obj is integer and even number.
	 */
	
	public boolean test(Object obj) {
		if (!(obj instanceof Integer))
			return false;
		Integer i = (Integer) obj;
		return i % 2 == 0;
	}

	public static void main(String[] args) {
		Tester t = new EvenIntegerTester();
		System.out.println(t.test("Ivo"));
		System.out.println(t.test(22));
		System.out.println(t.test(3));
	}
}