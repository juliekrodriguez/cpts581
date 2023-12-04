package p2Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FileHandlerTest {

	private File testFile;
	private File testDirectory;
	private FileHandler fileHandler;

	@Before
	public void setUp() throws IOException {
		// Set up a test file and directory
		testFile = new File("testFile.txt");
		testFile.createNewFile();

		testDirectory = new File("testDir");
		testDirectory.mkdir();

		fileHandler = new FileHandler(testFile);
	}

	@After
	public void tearDown() {
		testFile.delete();
		testDirectory.delete();
	}

	@Test
	public void testConstructor() {
		assertNotNull(fileHandler);
	}

	@Test
	public void testGetFileTarHeaderValidFile() throws InvalidHeaderException {
		TarHeaderSol hdr = new TarHeaderSol();
		try {
			FileHandler.getFileTarHeader(hdr, testFile);
		} catch (p2.InvalidHeaderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertEquals("testFile.txt", hdr.name.toString());
		// Additional assertions based on expected behavior
	}

	@Test
	public void testIsDirectoryWithFile() {
		assertFalse(fileHandler.isDirectory());
	}

	@Test
	public void testIsDirectoryWithDirectory() {
		FileHandler dirHandler = new FileHandler(testDirectory);
		assertTrue(dirHandler.isDirectory());
	}

}
