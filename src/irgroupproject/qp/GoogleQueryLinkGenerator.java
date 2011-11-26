package irgroupproject.qp;

public class GoogleQueryLinkGenerator {

	private static String PREFIX = "http://www.google.com/search?q=";

	public String generateLink(String query) {
		return PREFIX + query.replaceAll("\\s+", "+");
	}
}
