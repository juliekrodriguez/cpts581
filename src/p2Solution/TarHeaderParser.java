package p2Solution;

/*
 * This class will handle parsing responsibilities. 
 * You'll move methods like parseOctal, parseName, and any other 
 * parsing-related methods from TarHeader to this class.
 * 
 * */

public class TarHeaderParser {
	/**
	 * Parse an octal string from a header buffer. This is used for the file
	 * permission mode value.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The long value of the octal string.
	 * @throws InvalidHeaderException
	 */
	public static long parseOctal(byte[] header, int offset, int length) throws InvalidHeaderException {
		long result = 0;

		for (int i = offset; i < offset + length; ++i) {
			byte currentByte = header[i];

			if (currentByte == 0) {
				break; // Stop parsing if we encounter a null byte.
			}

			if (currentByte >= '0' && currentByte <= '7') {
				result = (result << 3) + (currentByte - '0');
			} else {
				throw new InvalidHeaderException("Invalid octal character in header: " + (char) currentByte);
			}
		}

		return result;
	}

	/**
	 * Parse an entry name from a header buffer.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The header's entry name.
	 */
	public static StringBuffer parseName(byte[] header, int offset, int length) {
		StringBuffer result = new StringBuffer(length);

		int end = offset + length;
		for (int i = offset; i < end; ++i) {
			if (header[i] == 0)
				break;
			result.append((char) header[i]);
		}

		return result;
	}

}
