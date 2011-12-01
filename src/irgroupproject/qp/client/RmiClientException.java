package irgroupproject.qp.client;

import irgroupproject.qp.QueryParserException;

/**
 * Custom exception for signifying when RMI related communication errors have prevented the execution of term expansion.
 * 
 * @author Ben Tse
 *
 */
public class RmiClientException extends QueryParserException {

	private static final long serialVersionUID = 1L;

	public RmiClientException() {
		super();
	}

	public RmiClientException(String message, Throwable cause) {
		super(message, cause);
	}

	public RmiClientException(String message) {
		super(message);
	}

	public RmiClientException(Throwable cause) {
		super(cause);
	}

}
