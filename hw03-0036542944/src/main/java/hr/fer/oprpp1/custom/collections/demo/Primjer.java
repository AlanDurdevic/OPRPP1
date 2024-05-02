package hr.fer.oprpp1.custom.collections.demo;

import java.util.Iterator;

import hr.fer.oprpp1.custom.collections.SimpleHashtable;

public class Primjer {
	public static void main(String[] args) {
		// create collection:
		SimpleHashtable<String, Integer> examMarks = new SimpleHashtable<>(2);
		// fill data:
		examMarks.put("Ivana", 2);
		examMarks.put("Ante", 2);
		examMarks.put("Jasna", 2);
		examMarks.put("Kristina", 5);
		examMarks.put("Ivana", 5); // overwrites old grade for Ivana
		// query collection:
		Integer kristinaGrade = examMarks.get("Kristina");
		System.out.println("Kristina's exam grade is: " + kristinaGrade); // writes: 5
		// What is collection's size? Must be four!
		System.out.println("Number of stored pairs: " + examMarks.size()); // writes: 4

//		for (SimpleHashtable.TableEntry<String, Integer> pair : examMarks) {
//			System.out.printf("%s => %d%n", pair.getKey(), pair.getValue());
//		}

//		for (SimpleHashtable.TableEntry<String, Integer> pair1 : examMarks) {
//			for (SimpleHashtable.TableEntry<String, Integer> pair2 : examMarks) {
//				System.out.printf("(%s => %d) - (%s => %d)%n", pair1.getKey(), pair1.getValue(), pair2.getKey(),
//						pair2.getValue());
//			}
//		}

//		System.out.println(examMarks.toString());
//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove(); // sam iterator kontrolirano uklanja trenutni element
//			}
//		}
//		System.out.println(examMarks.toString());

//		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
//		while (iter.hasNext()) {
//			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
//			if (pair.getKey().equals("Ivana")) {
//				iter.remove();
//				iter.remove();
//			}
//		}

		Iterator<SimpleHashtable.TableEntry<String, Integer>> iter = examMarks.iterator();
		while (iter.hasNext()) {
			SimpleHashtable.TableEntry<String, Integer> pair = iter.next();
			if (pair.getKey().equals("Ivana")) {
				examMarks.remove("Ivana");
			}
		}
	}
}