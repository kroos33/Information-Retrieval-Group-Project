package irgroupproject.qp.token;

import irgroupproject.tbn.Concept;
import irgroupproject.tbn.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Term object, representing a word in a user query.  Extends our {@link irgroupproject.qp.token.Token} object.
 * @author Ben Tse
 *
 */
public class Term extends Token {
	private Concept concept;
	private Set<Relationship> relationships = new HashSet<Relationship>();

	private static Pattern RELATED_PATTERN = Pattern.compile("\\[([ABNR])+\\]",
			Pattern.CASE_INSENSITIVE);
	private static Pattern RELATIONSHIP = Pattern.compile("[ABNR]",
			Pattern.CASE_INSENSITIVE);

	/**
	 * Default Constructor
	 * @param term String representation of the TBN term we are processing.
	 * @param related String representation of the {@link irgroupproject.tbn.Relationship} type.
	 * @throws InvalidTokenRelationshipException
	 */
	public Term(String term, String related)
			throws InvalidTokenRelationshipException {
		super.type = Type.TERM;
		concept = new Concept(term);
		loadRelationships(related);
	}

	/**
	 * Parses the term and loads all located relationships found in it.
	 * <BR><BR>
	 * Converts [ANBR] representation to that of {@link irgroupproject.tbn.Relationship}
	 * @param related Relationship part of the term.  Everything between the expansion operators.
	 * @throws InvalidTokenRelationshipException
	 * @author Ben Tse
	 */
	private void loadRelationships(String related)
			throws InvalidTokenRelationshipException {
		if (related == null)
			return;

		Matcher patternMatcher = RELATED_PATTERN.matcher(related);
		boolean matchPattern = patternMatcher.find();

		if (!matchPattern)
			throw new InvalidTokenRelationshipException();

		Matcher matcher = RELATIONSHIP.matcher(related);
		while (matcher.find()) {
			if ("A".equals(matcher.group()))
				relationships.add(Relationship.ANY);
			else if ("B".equals(matcher.group()))
				relationships.add(Relationship.BROAD);
			else if ("N".equals(matcher.group()))
				relationships.add(Relationship.NARROW);
			else if ("R".equals(matcher.group()))
				relationships.add(Relationship.RELATED);
		}

	}

	/**
	 * Returns the concept portion of the Term as {@link irgroupproject.tbn.Concept}
	 * @return {@link irgroupproject.tbn.Concept} object.
	 * @author Ben Tse
	 */
	public Concept getConcept() {
		return concept;
	}

	/**
	 * Returns the relationship portion of the Term as {@link irgroupproject.tbn.Relationship}
	 * @return {@link irgroupproject.tbn.Relationship} object.
	 * @author Ben Tse
	 */
	public Set<Relationship> getRelationships() {
		return relationships;
	}

	@Override
	public String toString() {
		return type + ":" + concept.getConcept() + ":" + relationships;
	}

}
