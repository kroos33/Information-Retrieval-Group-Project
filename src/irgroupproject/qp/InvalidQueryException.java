package irgroupproject.qp;

public class InvalidQueryException extends QueryParserException {

	public InvalidQueryException(int errorIndex) {
		super("Error at:" + errorIndex);
	}

	private static final long serialVersionUID = -3496216629441634990L;

}
