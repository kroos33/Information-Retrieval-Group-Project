package irgroupproject.qp;

import irgroupproject.qp.token.InvalidTokenRelationshipException;
import irgroupproject.qp.token.Operator;
import irgroupproject.qp.token.Term;
import irgroupproject.qp.token.TokenList;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Main Query parsing class.  Uses a two pass parsing system. The first is designed to reject a query based on syntax violations.
 * <BR><BR>
 * The second pass is designed to break the query into it's various parts using a series of Regular Expressions and expand them as needed.
 * 
 * @author Ben Tse
 *
 */
public class QueryParser {

	private static Pattern LAST_WORD = Pattern.compile(
			"([\\sa-zA-Z_0-9]+)(\\[[ABNR]+\\])?$", Pattern.CASE_INSENSITIVE);
	private static Pattern WORD_OP = Pattern.compile(
			"([\\sa-zA-Z_0-9]+?)(\\[[ABNR]+\\])?\\s+(and|or|not|near)\\s+",
			Pattern.CASE_INSENSITIVE);
	private static Pattern OPERATOR = Pattern.compile("^(and|or|not|near)",
			Pattern.CASE_INSENSITIVE);
	private boolean foundFirst = false;

	/**
	 * Main query parsing method.  Takes a given query, checks to make sure it passes our first pass through syntax parsing and then applies our Regular Expressions to break the query into individual parts.
	 * 
	 * @author Ben Tse
	 * @param query User supplied query.
	 * @throws QueryParserException Indicates that something, syntactically is wrong with the query.
	 *
	 */
	public TokenList parse(String query) throws QueryParserException {
		
		
		String trimedQuery = query.trim();
		
		if(!this.isBalanced(trimedQuery))
		{
			throw new UnbalancedExpansionOperatorException("Expansion Operators Not Balanced");
		}
		trimedQuery = trimedQuery.replace("[]", "");
		int lastEndIndex = 0;
		TokenList tokens = new TokenList();

		checkQueryNotStartingWithOps(trimedQuery);
		boolean done = false;
		
		while (!done) {
			int temp = findNextWordAndOp(trimedQuery, lastEndIndex, tokens);
			done = temp == lastEndIndex;
			lastEndIndex = temp;
		}

		lastEndIndex = findLastWord(trimedQuery, lastEndIndex, tokens);
		foundFirst = false;
		return tokens;
	}
	
	
	/**
	 * First pass parser.  Quickly parses the query looking for unbalanced operators or invalid syntax.
	 * @author kurtisthompson
	 * @param query User supplied query.
	 * @return True if query is valid, False otherwise.
	 * @throws UnbalancedExpansionOperatorException Thrown if the expansion operators are not properly balanced.
	 * @throws InvalidQueryException Thrown for all other error types (syntax, invalid operators, etc).
	 */
	private boolean isBalanced(String query) throws UnbalancedExpansionOperatorException, InvalidQueryException
	{
		int countOpen =0;
		int countClose = 0;
		for (int i = 0 ; i < query.length() ; ++i ) {
			  if (query.charAt(i) == '[' ) {
			    countOpen += 1;
			  }
			  else if(query.charAt(i) == ']'){
				  countClose +=1;
			  }
		}
		if(countOpen != countClose)
			return false;
		
		/*
		 * This is really ugly but there are a series of invalid queries the Regex won't find such as:
		 * Jackie Robinson[R] F T - Missing operands.
		 * 
		 * This just adds a quick layer of syntax parsing so we can report an error correctly.
		 * Basically, it assumes any series of words, such as Super Bowl that do not end with a ] are a single phrase and allows it.
		 * If a term ends with a ], then the next character HAS to be an operator.
		 * 
		 */
		StringTokenizer st = new StringTokenizer(query, " ");
		boolean wasLastOperand = false;
		boolean isFirst = false;
		String lastToken = new String();
		while(st.hasMoreElements())
		{
			String token = st.nextElement().toString().trim();
			System.out.println(token);
			if(!isFirst && !wasLastOperand)
			{
				if(token.equalsIgnoreCase("and") || token.equalsIgnoreCase("or") || token.equalsIgnoreCase("near") || token.equalsIgnoreCase("not"))
				{
					throw new InvalidQueryException(0);
				}
				isFirst = true;
				wasLastOperand = false;
			}
			else
			{
				if(!wasLastOperand)
				{
					if(!token.toLowerCase().equalsIgnoreCase("and") && !token.toLowerCase().equalsIgnoreCase("or") && !token.toLowerCase().equalsIgnoreCase("near") && !token.toLowerCase().equalsIgnoreCase("not") && lastToken.endsWith("]"))
					{
						throw new UnbalancedExpansionOperatorException("Unsupported or Missing Operator in Query");
					}
					if(lastToken.endsWith("]"))	
						wasLastOperand = true;
				}
				else
				{
					if(token.equalsIgnoreCase("and") || token.equalsIgnoreCase("or") || token.equalsIgnoreCase("near") || token.equalsIgnoreCase("not"))
					{
						throw new UnbalancedExpansionOperatorException("Operand Following Operand.  Invalid Query Syntax.");
					}
					wasLastOperand = false;
				}
			}
			lastToken = token;
		}
		
		/*Allow the query to end with an operator since it doesn't break anything */
		
		return true;
		
		
		
	}
	
	/**
	 * Checks to ensure our query does not start with one of our boolean operators.  To do so, would be invalid.
	 * 
	 * @author Ben Tse
	 * @param query User supplied query.
	 * @throws InvalidQueryException Thrown if the query begins with a boolean operator.
	 */
	private void checkQueryNotStartingWithOps(String query)
			throws InvalidQueryException {
		Matcher matcher = OPERATOR.matcher(query);
		boolean matchFound = matcher.find();
		if (matchFound) {
			throw new InvalidQueryException(0);
		}
	}

	/**
	 * Parses the next word (and possibly expansion operator) in the user supplied query, begining at lastWordIndex.
	 * @param trimedQuery User supplied query with whitespace removed.
	 * @param lastWordEndIndex Current position to begin parsing from within the query. 
	 * @param tokens Current list of located {@link irgroupproject.qp.token.Token} objects.
	 * @return Updated position within the query.
	 * @throws InvalidTokenRelationshipException
	 * @throws InvalidQueryException
	 * @author Ben Tse
	 */
	private int findNextWordAndOp(String trimedQuery, int lastWordEndIndex,
			TokenList tokens) throws InvalidTokenRelationshipException, InvalidQueryException {
		Matcher matcher = WORD_OP.matcher(trimedQuery);
		boolean matchFound = matcher.find(lastWordEndIndex);
		if (matchFound) {
			lastWordEndIndex = matcher.end(0);
			String word = matcher.group(1);
			
			String related = matcher.group(2);
			String op = matcher.group(3);
			
			System.out.println(word);


			tokens.add(new Term(word, related));
			tokens.add(new Operator(op));
			foundFirst = true;
		}
		return lastWordEndIndex;
	}

	/**
	 * Parses the <b>final</b> word (and possibly expansion operator) in the user supplied query.
	 * <BR><BR>
	 * We handle this as a special case to account for single term queries.
	 * @param trimedQuery User supplied query with whitespace removed.
	 * @param lastWordEndIndex Current position to begin parsing from within the query. 
	 * @param tokens Current list of located {@link irgroupproject.qp.token.Token} objects.
	 * @return Updated position within the query.
	 * @throws InvalidTokenRelationshipException
	 * @throws InvalidQueryException
	 * @author Ben Tse
	 */
	private int findLastWord(String trimedQuery, int lastWordEndIndex,
			TokenList tokens) throws InvalidQueryException,
			InvalidTokenRelationshipException {
		Matcher matcher = LAST_WORD.matcher(trimedQuery);
		boolean matchFound = matcher.find(lastWordEndIndex);
		if (matchFound) {
			lastWordEndIndex = matcher.end(0);
			String lastWord = matcher.group(1);
			String related = matcher.group(2);
			tokens.add(new Term(lastWord, related));
		} else {
			throw new InvalidQueryException(lastWordEndIndex);
		}
		return lastWordEndIndex;
	}
}
