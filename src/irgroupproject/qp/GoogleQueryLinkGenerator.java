package irgroupproject.qp;

/**
 * Google Query Link Generator<BR><BR>
 * Generates the google link necssary to open show the user the expanded query search results.
 * @author Ben Tse
 *
 */
public class GoogleQueryLinkGenerator {

	private static String PREFIX = "http://www.google.com/search?q=";

	/**
	 * Generates the Goolge results link for a given expanded query.
	 * @param query Query terms to return results for.
	 * @return URL of Google results as String.
	 */
	public String generateLink(String query) {
		return PREFIX + query.replaceAll("\\s+", "+");
	}
}
