package p3;

import static org.junit.Assert.assertEquals;

import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.Resource;
import org.apache.tools.ant.types.ResourceCollection;
import org.junit.Before;
import org.junit.Test;

public class JarTest {

	private Jar jar;
	// idk how to make more detailed set up

	@Before
	public void setUp() {
		jar = new Jar();
	}

	@Test
	public void testEmptyResourceCollectionForGrabManifests() {
		ResourceCollection[] rcs = new ResourceCollection[0];
		Resource[][] result = jar.grabManifests(rcs);
		assertEquals(0, result.length);
	}

	@Test
	public void testEmptyResourcesForGrabNonFileSetResources() {
		ResourceCollection[] rcs = new ResourceCollection[0];
		Resource[][] result = jar.grabNonFileSetResources(rcs);
		assertEquals(0, result.length);
	}

	@Test
	public void testEmptyFileSetsForGrabResources() {
		FileSet[] fileSets = new FileSet[0];
		Resource[][] result = jar.grabResources(fileSets);
		assertEquals(0, result.length);
	}

}
