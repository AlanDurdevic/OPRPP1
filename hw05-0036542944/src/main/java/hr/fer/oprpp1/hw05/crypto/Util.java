package hr.fer.oprpp1.hw05.crypto;

import java.util.Objects;

/**
 * Class that provides static methods for encoding from hex to binary and opposite.
 * @author Alan Đurđević
 * @version 1.0.0.
 */

public class Util {
	
	/**
	 * Method that encodes hex number into byte array.
	 * @param keyText hex number for encoding
	 * @return array of encoded bytes
	 * @throws NullPointerException if <code>keyText</code> is <code>null</code>
	 * @throws IllegalArgumentException if <code>keyText</code> length is odd-sized or
	 * <code>keyText</code> contains invalid characters
	 * @since 1.0.0.
	 */

	public static byte[] hextobyte(String keyText) {
		Objects.requireNonNull(keyText, "The given hex can not be null");
		if (keyText.length() % 2 == 1)
			throw new IllegalArgumentException("Key text can not be odd-sized");
		if(keyText.length() == 0) return new byte[0];
		keyText = keyText.toLowerCase();
		byte[] byteArray = new byte[keyText.length() / 2];
		for(int i = 0; i < keyText.length(); i++) {
			char c = keyText.charAt(i);
			byte b;
			if(!((c >= '0' && c <= '9') || (c >= 'a' && c<='f'))) {
				throw new IllegalArgumentException("Key text has invalid character");
			}
			if(Character.isAlphabetic(c)) {
				b = (byte) (c - 'a' + 10);
			}
			else {
				b = (byte) (c - '0');
			}
			if(i % 2 == 0) byteArray[i / 2] =(byte) (b << 4);
			else byteArray[i / 2] = (byte) (byteArray[i / 2] + b);
		}
		return byteArray;
	}
	
	/**
	 * Method that encodes byteArray to hex number.
	 * @param byteArray for encoding
	 * @return encoded hex number
	 * @throws NullPointerException if <code>byteArray</code> is <code>null</code>
	 * @since 1.0.0.
	 */
	
	public static String bytetohex(byte[] byteArray) {
		Objects.requireNonNull(byteArray, "Byte array can not be null");
		StringBuilder sb = new StringBuilder();
		for(byte b : byteArray) {
			int upper = (b & 0xf0) >> 4;
			if(upper < 10) sb.append(upper);
			else sb.append((char)('a' + upper - 10));
			int lower = b & 15;
			if(lower < 10) sb.append(lower);
			else sb.append((char)('a' + lower - 10));
		}
		return sb.toString();
	}
}
