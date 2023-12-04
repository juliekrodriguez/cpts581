package p2Solution;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class InvalidHeaderExceptionTest {

	@Test
	public void testDefaultConstructor() {
		InvalidHeaderException exception = new InvalidHeaderException();
		assertNull(exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	public void testMessageConstructor() {
		String errorMessage = "Invalid header encountered";
		InvalidHeaderException exception = new InvalidHeaderException(errorMessage);
		assertEquals(errorMessage, exception.getMessage());
		assertNull(exception.getCause());
	}

	@Test
	public void testMessageAndCauseConstructor() {
		String errorMessage = "Invalid header encountered";
		InvalidHeaderException exception = new InvalidHeaderException(errorMessage);

		Throwable cause = new RuntimeException("Root cause exception");
		exception.initCause(cause);

		assertEquals(errorMessage, exception.getMessage());
		assertEquals(cause, exception.getCause());
	}

	@Test
	public void testChainedExceptions() {
		String errorMessage = "Invalid header encountered";
		InvalidHeaderException exception = new InvalidHeaderException(errorMessage);

		Throwable rootCause = new RuntimeException("Root cause exception");
		exception.initCause(rootCause);

		assertEquals(errorMessage, exception.getMessage());
		assertEquals(rootCause, exception.getCause());
	}

}