
public class Mnt {
	private String macroName;
	private int noOfPositionalParameter,noOfKeywordParameter, noOfExpansionVariable, mdtPointer, kpdtabPointer, sstpPointer;
	public String getMacroName() {
		return macroName;
	}
	public void setMacroName(String macroName) {
		this.macroName = macroName;
	}
	public int getNoOfPositionalParameter() {
		return noOfPositionalParameter;
	}
	public void setNoOfPositionalParameter(int noOfPositionalParameter) {
		this.noOfPositionalParameter = noOfPositionalParameter;
	}
	public int getNoOfKeywordParameter() {
		return noOfKeywordParameter;
	}
	public void setNoOfKeywordParameter(int noOfKeywordParameter) {
		this.noOfKeywordParameter = noOfKeywordParameter;
	}
	public int getNoOfExpansionVariable() {
		return noOfExpansionVariable;
	}
	public void setNoOfExpansionVariable(int noOfExpansionVariable) {
		this.noOfExpansionVariable = noOfExpansionVariable;
	}
	public int getMdtPointer() {
		return mdtPointer;
	}
	public void setMdtPointer(int mdtPointer) {
		this.mdtPointer = mdtPointer;
	}
	public int getKpdtabPointer() {
		return kpdtabPointer;
	}
	public void setKpdtabPointer(int kpdtabPointer) {
		this.kpdtabPointer = kpdtabPointer;
	}
	public int getSstpPointer() {
		return sstpPointer;
	}
	public void setSstpPointer(int sstpPointer) {
		this.sstpPointer = sstpPointer;
	}
	@Override
	public String toString() {
		return "MNT     - [macroName=" + macroName + ", noOfPositionalParameter=" + noOfPositionalParameter
				+ ", noOfKeywordParameter=" + noOfKeywordParameter + ", noOfExpansionVariable=" + noOfExpansionVariable
				+ ", mdtPointer=" + mdtPointer + ", kpdtabPointer=" + kpdtabPointer + ", sstpPointer=" + sstpPointer
				+ "]";
	}
	
}
