package p2Solution;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TarHeaderFormatterTest {

	@Test
	public void testGetOctalBytesNonZeroValue() {
		byte[] buffer = new byte[12];
		int result = TarHeaderFormatter.getOctalBytes(12345L, buffer, 0, buffer.length);

		assertEquals(12, result);
	}

	@Test
	public void testGetLongOctalBytes() {
		byte[] buffer = new byte[12];
		int result = TarHeaderFormatter.getLongOctalBytes(12345L, buffer, 0, buffer.length);

		assertEquals(12, result);
	}

	@Test
	public void testGetCheckSumOctalBytes() {
		byte[] buffer = new byte[12];
		int result = TarHeaderFormatter.getCheckSumOctalBytes(12345L, buffer, 0, buffer.length);

		assertEquals(12, result);
	}

	@Test
	public void testGetNameBytes() {
		byte[] buffer = new byte[12];
		StringBuffer name = new StringBuffer("testName");
		int result = TarHeaderFormatter.getNameBytes(name, buffer, 0, buffer.length);

		assertEquals(12, result);
	}

}
