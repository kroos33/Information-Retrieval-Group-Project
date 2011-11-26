package irgroupproject.qp.token;

public enum OperatorType {
	AND, OR, NOT, NEAR;

	public static boolean isOpeator(String str) {
		for (OperatorType type : OperatorType.values()) {
			if (type.toString().equalsIgnoreCase(str))
				return true;
		}
		return false;
	}

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
