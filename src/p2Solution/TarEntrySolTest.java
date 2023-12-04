package p2Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TarEntrySolTest {

	@Before
	public void setUp() throws IOException {
		new File("testdirectory").mkdir();
		// Create a dummy file for the file test
		new File("testfile.txt").createNewFile();
	}

	@After
	public void tearDown() {
		new File("testdirectory").delete();
		new File("testfile.txt").delete();
	}

	@Test
	public void testGetName() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertEquals("testfile.txt", tarEntry.getName());
	}

	@Test
	public void testSetName() {
		TarEntrySol tarEntry = new TarEntrySol("originalName.txt");
		tarEntry.setName("newName.txt");
		assertEquals("newName.txt", tarEntry.getName());
	}

	@Test
	public void testIsDirectoryForFile() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertFalse(tarEntry.isDirectory());
	}

	@Test
	public void testIsDirectoryForDirectory() {
		TarEntrySol tarEntry = new TarEntrySol("testdirectory/");
		assertTrue(tarEntry.isDirectory());
	}

	@Test
	public void testGetFile() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertNull(tarEntry.getFile());
	}

	@Test
	public void testGetHeader() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertNotNull(tarEntry.getHeader());
	}

	@Test
	public void testGetUserId() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertEquals(0, tarEntry.getUserId());
	}

	@Test
	public void testSetUserId() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		tarEntry.setUserId(1001);
		assertEquals(1001, tarEntry.getUserId());
	}

	@Test
	public void testGetGroupId() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		assertEquals(0, tarEntry.getGroupId());
	}

	@Test
	public void testSetGroupId() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		tarEntry.setGroupId(1001);
		assertEquals(1001, tarEntry.getGroupId());
	}

	@Test
	public void testSetModTime() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");

		// Set the modification time to the current time
		tarEntry.setModTime(new Date());

		// Get the modification time and compare only the seconds part
		Date expectedModTime = new Date();
		Date actualModTime = tarEntry.getModTime();

		assertEquals(expectedModTime.getTime() / 1000, actualModTime.getTime() / 1000);
	}

	@Test
	public void testSetSize() {
		TarEntrySol tarEntry = new TarEntrySol("testfile.txt");
		tarEntry.setSize(1024);
		assertEquals(1024, tarEntry.getSize());
	}
	// Add more test cases as needed

}