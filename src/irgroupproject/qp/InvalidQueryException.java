package irgroupproject.qp;

/**
 * Invalid Query Term Exception 
 * <BR><BR>
 * Exception thrown when the user supplied query is in some way invalid. Reports the place in the query where the error occured.
 * 
 * @author Ben Tse
 */
public class InvalidQueryException extends QueryParserException {

	/**
	 * Default Constructor
	 * @param errorIndex Location of the error in the user supplied set of query terms.
	 */
	public InvalidQueryException(int errorIndex) {
		super("Error at:" + errorIndex);
	}

	private static final long serialVersionUID = -3496216629441634990L;

}
