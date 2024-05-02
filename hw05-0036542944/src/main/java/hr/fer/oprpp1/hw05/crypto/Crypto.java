package hr.fer.oprpp1.hw05.crypto;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.Scanner;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import static hr.fer.oprpp1.hw05.crypto.Util.bytetohex;
import static hr.fer.oprpp1.hw05.crypto.Util.hextobyte;

/**
 * Class that represents simple application for generating SHA-256 digest and encrypting and decrypting files with
 * symmetric crypto-algorithm AES.
 * @author Alan Đurđević
 * @version 1.0.0. 
 */

public class Crypto {
	
	/**
	 * Size of buffer.
	 * @since 1.0.0.
	 */

	private final static int BUFFER_SIZE = 4096;
	
	/**
	 * Starting method
	 * @param args
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @since 1.0.0.
	 */

	public static void main(String[] args) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		
		if (args.length < 2 || args.length > 3)
			throw new IllegalArgumentException("Invalid number of arguments");
		
		try (Scanner sc = new Scanner(System.in)) {
			
			if (args[0].equals("checksha")) {
				if (args.length != 2)
					throw new IllegalArgumentException();
				System.out.println("Please provide expected sha-256 digest for " + args[1] + ":");
				System.out.print("> ");
				String inputDigest = sc.nextLine().trim();
				String evaluatedDigest = checksha(args[1]);
				System.out.print("Digesting completed. ");
				if (evaluatedDigest.equals(inputDigest)) {
					System.out.printf("Digest of %s matches expected digest.\n", args[1]);
				} else {
					System.out.printf("Digest of %s does not match the expected digest.\nDigest was: %s\n", args[1],
							evaluatedDigest);
				}
			
			} else if (args[0].equals("encrypt") || args[0].equals("decrypt")) {
				if (args.length != 3)
					throw new IllegalArgumentException();
				System.out.println("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):");
				System.out.print("> ");
				String keyText = sc.nextLine().trim();
				System.out.println("Please provide initialization vector as hex-encoded text (32 hex-digits):");
				System.out.print("> ");
				String ivText = sc.nextLine().trim();
				cipher(keyText, ivText, args[0], args[1], args[2]);
				if(args[0].equals("encrypt")){
					System.out.printf("Encryption completed. Generated file %s based on file %s.", args[2], args[1]);
				}
				else {
					System.out.printf("Decryption completed. Generated file %s based on file %s.", args[2], args[1]);
				}
			}
		}
	}
	
	/**
	 * Method that encrypts/decrypts given file.
	 * @param keyText key for encrypting/decrypting
	 * @param ivText vector for encrypting/decrypting
	 * @param init variable that defines if encrypting or decrypting is wanted
	 * @param inputFile input file
	 * @param outputFile output file
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IOException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NullPointerException if one of the parameters is <code>null</code>
	 * @since 1.0.0.
	 */

	private static void cipher(String keyText, String ivText, String init, String inputFile, String outputFile) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IOException, IllegalBlockSizeException, BadPaddingException {
		Objects.requireNonNull(keyText, "Key text can not be null");
		Objects.requireNonNull(ivText, "Iv text can not be null");
		Objects.requireNonNull(init, "Init can not be null");
		Objects.requireNonNull(inputFile, "Input file can not be null");
		Objects.requireNonNull(outputFile, "Output file can not be null");
		SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(hextobyte(ivText));
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		cipher.init(init.equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, keySpec, paramSpec);
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(inputFile)));
				OutputStream os = new BufferedOutputStream(Files.newOutputStream(Paths.get(outputFile)))) {
			byte[] buff = new byte[BUFFER_SIZE];
			while (true) {
				int r = is.read(buff);
				if (r < 1) {
					os.write(cipher.doFinal());
					break;
				}
				os.write(cipher.update(buff, 0, r));
			}
		}
	}
	
	/**
	 * Method that creates SHA-256 digest for given document.
	 * @param document given document
	 * @return SHA-256 digest
	 * @throws NoSuchAlgorithmException
	 * @throws IOException if an I/O error occurs
	 * @throws NullPointerException if <code>document</code> is <code>null</code>
	 * @since 1.0.0.
	 */

	private static String checksha(String document) throws NoSuchAlgorithmException, IOException {
		Objects.requireNonNull(document, "Document can not be null");
		MessageDigest sha = MessageDigest.getInstance("SHA-256");
		try (InputStream is = new BufferedInputStream(Files.newInputStream(Paths.get(document)))) {
			byte[] buff = new byte[BUFFER_SIZE];
			while (true) {
				int r = is.read(buff);
				if (r < 1)
					break;
				sha.update(buff, 0, r);
			}
		}
		return bytetohex(sha.digest());
	}

}
