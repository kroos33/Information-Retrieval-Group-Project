package irgroupproject.qp;

/**
 * Exception designed to be thrown when the query contains an uneven (or unbalanced) number of query expansion operators (ex. [ or ]).
 * 
 * @author kurtisthompson
 *
 */
public class UnbalancedExpansionOperatorException extends QueryParserException {

	public UnbalancedExpansionOperatorException(String error)
	{
		super(error);
	}
	
	private static final long serialVersionUID = 1L;

}
