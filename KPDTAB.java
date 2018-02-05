
public class KPDTAB {
	private String parameterName;
	private String parameterValue;
	@Override
	public String toString() {
		return "KPDTAB  - [" + parameterName + ", " + parameterValue + "]";
	}
	public String getParameterName() {
		return parameterName;
	}
	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}
	public String getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}
}
