package irgroupproject.qp;

/**
 * Simple custom exception thrown by our {@link irgroupproject.qp.QueryParser} when some invalid condition is located.
 * @author Ben Tse
 *
 */
public class QueryParserException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * Default Constructor
	 * @author Ben Tse
	 */
	public QueryParserException() {
		super();
	}

	/**
	 * Alernate Constructor
	 * @param message Message to display to the user
	 * @param cause Cause of the exception.
	 * @author Ben Tse
	 */
	public QueryParserException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Alernate Constructor
	 * @param message Message to display to the user.
	 * @author Ben Tse
	 */
	public QueryParserException(String message) {
		super(message);
	}

	/**
	 * Alernate Constructor
	 * @param cause Cause of the exception.
	 * @author Ben Tse
	 */
	public QueryParserException(Throwable cause) {
		super(cause);
	}

}
