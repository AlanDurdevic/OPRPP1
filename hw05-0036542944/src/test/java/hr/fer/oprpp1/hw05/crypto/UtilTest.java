package hr.fer.oprpp1.hw05.crypto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;

import static hr.fer.oprpp1.hw05.crypto.Util.bytetohex;
import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;

import org.junit.jupiter.api.Test;

public class UtilTest {

	@Test
	public void testHexToByteThrows() {
		assertThrows(NullPointerException.class, () -> hextobyte(null));
		assertThrows(IllegalArgumentException.class, () -> hextobyte("aaa"));
		assertThrows(IllegalArgumentException.class, () -> hextobyte("aaaz"));
	}

	@Test
	public void testHexToByteFunctionality() {
		assertEquals(true, Arrays.equals(new byte[0], hextobyte("")));
		assertEquals(true, Arrays.equals(new byte[] { 1, -82, 34 }, hextobyte("01aE22")));
		assertEquals(true, Arrays.equals(new byte[] { 0, 0, 0 }, hextobyte("000000")));
	}
	
	@Test
	public void testByteToHexThrows() {
		assertThrows(NullPointerException.class, () -> bytetohex(null));
	}
	
	@Test
	public void testByteToHexFunctionality() {
		assertEquals("", bytetohex(new byte[0]));
		assertEquals("01ae22", bytetohex(new byte[] { 1, -82, 34 }));
		assertEquals("000000", bytetohex(new byte[] {0, 0, 0}));
	}

}
