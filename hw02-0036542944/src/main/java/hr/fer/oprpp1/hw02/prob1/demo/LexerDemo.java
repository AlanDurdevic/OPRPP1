package hr.fer.oprpp1.hw02.prob1.demo;

import hr.fer.oprpp1.hw02.prob1.Lexer;

/**
 * Class that gives some examples for lexer.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class LexerDemo {

	public static void main(String[] args) {
		String ulaz1 =  "Ovo je 123ica, ab57.\nKraj";
		String ulaz2 =  "\\1\\2 ab\\\\\\2c\\3\\4d";
		Lexer lexer1 = new Lexer(ulaz1);
		Lexer lexer2 = new Lexer(ulaz2);
		
		System.out.println("Lexer1: ");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
		lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println("(" + lexer1.nextToken().getType().toString() + ", " +
				lexer1.getToken().getValue() + ")");
		System.out.println();
		System.out.println("Lexer2: ");
		System.out.println("(" + lexer2.nextToken().getType().toString() + ", " +
				lexer2.getToken().getValue() + ")");
		System.out.println("(" + lexer2.nextToken().getType().toString() + ", " +
				lexer2.getToken().getValue() + ")");
		System.out.println("(" + lexer2.nextToken().getType().toString() + ", " +
				lexer2.getToken().getValue() + ")");
	}
	
}
