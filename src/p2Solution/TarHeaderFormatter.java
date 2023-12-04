package p2Solution;

/*
 * This class will be responsible for formatting tasks. 
 * Move methods like getOctalBytes, getLongOctalBytes, getCheckSumOctalBytes,
 *  and similar from TarHeader to this class.
 *  */

public class TarHeaderFormatter {
	/**
	 * Parse an octal integer from a header buffer.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The integer value of the octal bytes.
	 */
	public static int getOctalBytes(long value, byte[] buf, int offset, int length) {
		byte[] result = new byte[length];

		int idx = length - 1;

		buf[offset + idx] = 0;
		--idx;
		buf[offset + idx] = (byte) ' ';
		--idx;

		if (value == 0) {
			buf[offset + idx] = (byte) '0';
			--idx;
		} else {
			for (long val = value; idx >= 0 && val > 0; --idx) {
				buf[offset + idx] = (byte) ((byte) '0' + (byte) (val & 7));
				val = val >> 3;
			}
		}

		for (; idx >= 0; --idx) {
			buf[offset + idx] = (byte) ' ';
		}

		return offset + length;
	}

	/**
	 * Parse an octal long integer from a header buffer.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The long value of the octal bytes.
	 */
	public static int getLongOctalBytes(long value, byte[] buf, int offset, int length) {
		byte[] temp = new byte[length + 1];
		TarHeaderFormatter.getOctalBytes(value, temp, 0, length + 1);
		System.arraycopy(temp, 0, buf, offset, length);
		return offset + length;
	}

	/**
	 * Parse the checksum octal integer from a header buffer.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The integer value of the entry's checksum.
	 */
	public static int getCheckSumOctalBytes(long value, byte[] buf, int offset, int length) {
		TarHeaderFormatter.getOctalBytes(value, buf, offset, length);
		buf[offset + length - 1] = (byte) ' ';
		buf[offset + length - 2] = 0;
		return offset + length;
	}

	/**
	 * Determine the number of bytes in an entry name.
	 *
	 * @param header The header buffer from which to parse.
	 * @param offset The offset into the buffer from which to parse.
	 * @param length The number of header bytes to parse.
	 * @return The number of bytes in a header's entry name.
	 */
	public static int getNameBytes(StringBuffer name, byte[] buf, int offset, int length) {
		int i;

		for (i = 0; i < length && i < name.length(); ++i) {
			buf[offset + i] = (byte) name.charAt(i);
		}

		for (; i < length; ++i) {
			buf[offset + i] = 0;
		}

		return offset + length;
	}

}
