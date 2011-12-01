package irgroupproject.qp.token;

/**
 * Utility class providing some static methods used to distinguish operator type tokens stored as strings.
 * @author Ben Tse
 *
 */
public enum OperatorType {
	AND, OR, NOT, NEAR;

	/**
	 * Checks to see if a given string is a valid boolean operator.
	 * @param str String we want to check.
	 * @return True if the string represents an operator, False otherwise.
	 * @author Ben Tse
	 */
	public static boolean isOpeator(String str) {
		for (OperatorType type : OperatorType.values()) {
			if (type.toString().equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

	/**
	 * Parses a string and returns the relevant operator type enumeration value.
	 * @param str String containing the operator we want to parse.
	 * @return One of our OperatorType enumeration values.
	 * @author Ben Tse
	 */
	public static OperatorType parse(String str) {
		for (OperatorType type : OperatorType.values()) {
			if (type.toString().equalsIgnoreCase(str))
				return type;
		}
		throw new IllegalArgumentException("Unable to parse argument: " + str);
	}

	@Override
	public String toString() {
		switch (this) {
		case AND:
			return "and";
		case OR:
			return "or";
		case NEAR:
			return "near";
		case NOT:
			return "not";

		default:
			break;
		}
		return super.toString();
	}
}
