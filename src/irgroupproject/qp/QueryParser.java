package irgroupproject.qp;

import irgroupproject.qp.token.InvalidTokenRelationshipException;
import irgroupproject.qp.token.Operator;
import irgroupproject.qp.token.Term;
import irgroupproject.qp.token.TokenList;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueryParser {

	private static Pattern LAST_WORD = Pattern.compile(
			"([\\sa-zA-Z_0-9]+)(\\[[ABNR]+\\])?$", Pattern.CASE_INSENSITIVE);
	private static Pattern WORD_OP = Pattern.compile(
			"([\\sa-zA-Z_0-9]+?)(\\[[ABNR]+\\])?\\s+(and|or|not|near)\\s+",
			Pattern.CASE_INSENSITIVE);
	private static Pattern OPERATOR = Pattern.compile("^(and|or|not|near)",
			Pattern.CASE_INSENSITIVE);

	public TokenList parse(String query) throws QueryParserException {
		
		
		String trimedQuery = query.trim();
		
		if(!this.isBalanced(trimedQuery))
		{
			throw new UnbalancedExpansionOperatorException("Expansion Operators Not Balanced");
		}

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
		return tokens;
	}
	
	
	private boolean isBalanced(String query)
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
		return countOpen == countClose;
		
	}
	
	private void checkQueryNotStartingWithOps(String query)
			throws InvalidQueryException {
		Matcher matcher = OPERATOR.matcher(query);
		boolean matchFound = matcher.find();
		if (matchFound) {
			throw new InvalidQueryException(0);
		}
	}

	private int findNextWordAndOp(String trimedQuery, int lastWordEndIndex,
			TokenList tokens) throws InvalidTokenRelationshipException {
		Matcher matcher = WORD_OP.matcher(trimedQuery);
		boolean matchFound = matcher.find(lastWordEndIndex);
		if (matchFound) {
			lastWordEndIndex = matcher.end(0);
			String word = matcher.group(1);
			String related = matcher.group(2);
			String op = matcher.group(3);


			tokens.add(new Term(word, related));
			tokens.add(new Operator(op));
		}
		return lastWordEndIndex;
	}

	private int findLastWord(String trimedQuery, int lastWordEndIndex,
			TokenList tokens) throws InvalidQueryException,
			InvalidTokenRelationshipException {
		Matcher matcher = LAST_WORD.matcher(trimedQuery);
		boolean matchFound = matcher.find(lastWordEndIndex);
		if (matchFound) {
			lastWordEndIndex = matcher.end(0);
			String lastWord = matcher.group(1);
			String related = matcher.group(2);
			
			/*if(trimedQuery.indexOf("[") > -1 && trimedQuery.indexOf("]") < 0)
			{
				throw new InvalidQueryException(lastWordEndIndex);
			}
			*/

			tokens.add(new Term(lastWord, related));
		} else {
			throw new InvalidQueryException(lastWordEndIndex);
		}
		return lastWordEndIndex;
	}
}
