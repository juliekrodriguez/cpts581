package p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TarHeaderTest {

	@Test
	public void testParseName() throws InvalidHeaderException {
		byte[] nameBytes = { 'H', 'e', 'l', 'l', 'o', 0, 'W', 'o', 'r', 'l', 'd' };
		StringBuffer result = TarHeader.parseName(nameBytes, 0, nameBytes.length);
		// Assert the parsed name
		assertEquals("Hello", result.toString());
	}

	@Test
	public void testGetNameBytes() {
		StringBuffer name = new StringBuffer("Test");
		byte[] resultBytes = new byte[10];
		int newOffset = TarHeader.getNameBytes(name, resultBytes, 2, 5);
		// Assert the result bytes and new offset
		assertEquals(7, newOffset);
		assertEquals('T', resultBytes[2]);
		assertEquals('e', resultBytes[3]);
		assertEquals('s', resultBytes[4]);
		assertEquals('t', resultBytes[5]);
		assertEquals(0, resultBytes[6]);
	}

	@Test
	public void testParseOctal() throws InvalidHeaderException {
		// Test parsing octal values
		byte[] octalBytes = { '1', '2', '3', '4', '5', '6', 0, '7', '0' };
		long result = TarHeader.parseOctal(octalBytes, 0, octalBytes.length);

		// The expected result should be 42798
		assertEquals(42798, result);

		// Test parsing zero
		byte[] zeroBytes = { '0', 0 };
		result = TarHeader.parseOctal(zeroBytes, 0, zeroBytes.length);
		assertEquals(0, result);
	}

	@Test
	public void testGetCheckSumOctalBytes() {
		// Arrange
		long value = 123456L;
		int offset = 0;
		int length = 8;
		byte[] buf = new byte[length];

		// Act
		int result = TarHeader.getCheckSumOctalBytes(value, buf, offset, length);

		// Assert
		assertEquals("Expected result length", offset + length, result);

		// Validate the bytes in the buffer
		assertEquals("Last byte should be a space", (byte) ' ', buf[offset + length - 1]);
		assertEquals("Second to last byte should be 0", (byte) 0, buf[offset + length - 2]);
	}

	@Test
	public void testParseNameWithMaxLength() throws InvalidHeaderException {
		// Test when the name is at the maximum allowed length
		byte[] nameBytes = new byte[TarHeader.NAMELEN];
		for (int i = 0; i < TarHeader.NAMELEN - 1; i++) {
			nameBytes[i] = (byte) ('A' + i % 26);
		}
		nameBytes[TarHeader.NAMELEN - 1] = 0;
		StringBuffer result = TarHeader.parseName(nameBytes, 0, TarHeader.NAMELEN);
		// Assert the parsed name
		assertEquals(new String(nameBytes, 0, TarHeader.NAMELEN - 1), result.toString());
	}

	@Test
	public void testParseNameWithNullTerminator() throws InvalidHeaderException {
		// Test when the name contains a null terminator before the maximum length
		byte[] nameBytes = { 'F', 'i', 'l', 'e', 0, 'W', 'i', 't', 'h', 'N', 'u', 'l', 'l' };
		StringBuffer result = TarHeader.parseName(nameBytes, 0, nameBytes.length);
		// Assert the parsed name
		assertEquals("File", result.toString());
	}

	/*
	 * @Test public void testParseNameWithPadding() throws InvalidHeaderException {
	 * // Test when the name has spaces as padding characters byte[] nameBytes = {
	 * 'F', 'i', 'l', 'e', ' ', ' ', ' ', ' ', ' ', 0 }; StringBuffer result =
	 * TarHeader.parseName(nameBytes, 0, nameBytes.length); // Assert the parsed
	 * name assertEquals("File", result.toString()); }
	 */

	@Test
	public void testGetNameBytesWithLongName() {
		// Test when the name is longer than the specified length
		StringBuffer name = new StringBuffer("ThisIsALongFileName");
		byte[] resultBytes = new byte[10];
		int newOffset = TarHeader.getNameBytes(name, resultBytes, 2, 5);
		// Assert the result bytes and new offset
		assertEquals(7, newOffset);
		assertEquals('T', resultBytes[2]);
		assertEquals('h', resultBytes[3]);
		assertEquals('i', resultBytes[4]);
		assertEquals('s', resultBytes[5]);
	}

	@Test
	public void testGetters() {
		// Test the getter methods
		TarHeader header = new TarHeader();
		header.name.append("TestFile");
		header.mode = 0644;
		header.userId = 1001;
		header.groupId = 1001;
		header.size = 1024;
		header.modTime = System.currentTimeMillis();
		header.checkSum = 12345;
		header.linkFlag = TarHeader.LF_NORMAL;
		header.linkName.append("LinkedFile");
		header.magic = new StringBuffer("ustar");
		header.userName.append("user");
		header.groupName.append("group");
		header.devMajor = 8;
		header.devMinor = 1;

		// Assert the getter methods
		assertEquals("TestFile", header.getName());
		assertEquals(0644, header.mode);
		assertEquals(1001, header.userId);
		assertEquals(1001, header.groupId);
		assertEquals(1024, header.size);
		assertTrue(System.currentTimeMillis() - header.modTime < 1000); // Check within a second
		assertEquals(12345, header.checkSum);
		assertEquals(TarHeader.LF_NORMAL, header.linkFlag);
		assertEquals("LinkedFile", header.linkName.toString());
		assertEquals("ustar", header.magic.toString());
		assertEquals("group", header.groupName.toString());
		assertEquals(8, header.devMajor);
		assertEquals(1, header.devMinor);
	}

	@Test
	public void testGettersWithDefaultValues() {
		// Test the getter methods with default values
		TarHeader header = new TarHeader();

		// Assert the getter methods with default values
		assertEquals("", header.getName());
		assertEquals(0, header.mode);
		assertEquals(0, header.userId);
		assertEquals(0, header.groupId);
		assertEquals(0, header.size);
		assertEquals(0, header.modTime);
		assertEquals(0, header.checkSum);
		assertEquals((byte) 0, header.linkFlag);
		assertEquals("ustar", header.magic.toString());
		assertEquals(System.getProperty("user.name", ""), header.userName.toString());
		assertEquals("", header.groupName.toString());
		assertEquals(0, header.devMajor);
		assertEquals(0, header.devMinor);
	}

	@Test
	public void testClone() {
		// Test the clone method
		TarHeader original = new TarHeader();
		original.name.append("Original");
		TarHeader clone = (TarHeader) original.clone();
		// Assert that the clone is a different object
		assertNotSame(original, clone);
	}
}
