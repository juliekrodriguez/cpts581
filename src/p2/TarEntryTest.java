package p2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.Test;

public class TarEntryTest {

	@Test
	public void testGetName() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertEquals("testfile.txt", tarEntry.getName());
	}

	@Test
	public void testSetName() {
		TarEntry tarEntry = new TarEntry("originalName.txt");
		tarEntry.setName("newName.txt");
		assertEquals("newName.txt", tarEntry.getName());
	}

	@Test
	public void testIsDirectoryForFile() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertFalse(tarEntry.isDirectory());
	}

	@Test
	public void testIsDirectoryForDirectory() {
		TarEntry tarEntry = new TarEntry("testdirectory/");
		assertTrue(tarEntry.isDirectory());
	}

	@Test
	public void testGetFile() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertNull(tarEntry.getFile());
	}

	@Test
	public void testGetHeader() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertNotNull(tarEntry.getHeader());
	}

	@Test
	public void testGetUserId() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertEquals(0, tarEntry.getUserId());
	}

	@Test
	public void testSetUserId() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		tarEntry.setUserId(1001);
		assertEquals(1001, tarEntry.getUserId());
	}

	@Test
	public void testGetGroupId() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		assertEquals(0, tarEntry.getGroupId());
	}

	@Test
	public void testSetGroupId() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		tarEntry.setGroupId(1001);
		assertEquals(1001, tarEntry.getGroupId());
	}

	@Test
	public void testSetModTime() {
		TarEntry tarEntry = new TarEntry("testfile.txt");

		// Set the modification time to the current time
		tarEntry.setModTime(new Date());

		// Get the modification time and compare only the seconds part
		Date expectedModTime = new Date();
		Date actualModTime = tarEntry.getModTime();

		assertEquals(expectedModTime.getTime() / 1000, actualModTime.getTime() / 1000);
	}

	@Test
	public void testSetSize() {
		TarEntry tarEntry = new TarEntry("testfile.txt");
		tarEntry.setSize(1024);
		assertEquals(1024, tarEntry.getSize());
	}
	// Add more test cases as needed

}