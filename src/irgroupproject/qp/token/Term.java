package irgroupproject.qp.token;

import irgroupproject.tbn.Concept;
import irgroupproject.tbn.Relationship;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Term extends Token {
	private Concept concept;
	private Set<Relationship> relationships = new HashSet<Relationship>();

	private static Pattern RELATED_PATTERN = Pattern.compile("\\[([ABNR])+\\]",
			Pattern.CASE_INSENSITIVE);
	private static Pattern RELATIONSHIP = Pattern.compile("[ABNR]",
			Pattern.CASE_INSENSITIVE);

	public Term(String term, String related)
			throws InvalidTokenRelationshipException {
		super.type = Type.TERM;
		concept = new Concept(term);
		loadRelationships(related);
	}

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

	public Concept getConcept() {
		return concept;
	}

	public Set<Relationship> getRelationships() {
		return relationships;
	}

	@Override
	public String toString() {
		return type + ":" + concept.getConcept() + ":" + relationships;
	}

}
