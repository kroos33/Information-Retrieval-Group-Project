package irgroupproject.qp.token;

public class Operator extends Token {
	private OperatorType operatorType;

	public Operator(String operatorType) {
		super.type = Type.OPERATOR;
		this.operatorType = OperatorType.parse(operatorType);
	}

	public OperatorType getOperatorType() {
		return operatorType;
	}

	@Override
	public String toString() {
		return type + ":" + operatorType;
	}

}
