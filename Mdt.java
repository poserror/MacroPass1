
public class Mdt 
{
	private String label, opode, operand;

	@Override
	public String toString() {
		return "MDT   - [" + label + ", " + opode + " , " + operand + "]";
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getOpode() {
		return opode;
	}

	public void setOpode(String opode) {
		this.opode = opode;
	}

	public String getOperand() {
		return operand;
	}

	public void setOperand(String operand) {
		this.operand = operand;
	}
	public boolean isNull()
	{
		if(label==null && opode==null && operand==null)
			return true;
		else
			return false;
	}
}
