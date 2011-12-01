package irgroupproject.qp.token;

/**
 * Simple POJO used to represent one our allowed query operators {AND, OR, NEAR, NOT}.  Extends {@link irgroupproject.qp.token.Token}
 * @author Ben Tse
 *
 */
public class Operator extends Token {
	private OperatorType operatorType;

	/**
	 * Default constructor.
	 * @param operatorType Type of operator from the set {AND, OR, NEAR, NOT} that we extracted from the query.
	 * @author Ben Tse
	 */
	public Operator(String operatorType) {
		super.type = Type.OPERATOR;
		this.operatorType = OperatorType.parse(operatorType);
	}

	/**
	 * Returns operator type of this object.
	 * @return operatorType of type {@link irgroupproject.qp.token.OperatorType}
	 * @author Ben Tse
	 */
	public OperatorType getOperatorType() {
		return operatorType;
	}

	@Override
	public String toString() {
		return type + ":" + operatorType;
	}

}
