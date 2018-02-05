import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;
public class Macro 
{
	Mnt mnt = new Mnt();
	ArrayList<Mnt> mntab = new ArrayList<Mnt>();
	Mdt mdt = new Mdt();
	ArrayList<Mdt> mdtab = new ArrayList<Mdt>();
	KPDTAB kpt = new KPDTAB();
	ArrayList<KPDTAB> kpdtab = new ArrayList<KPDTAB>();
	ArrayList<String> aptab = new ArrayList<String>();
	ArrayList<String> pptab = new ArrayList<String>();
	ArrayList<String> evtab = new ArrayList<String>();
	ArrayList<String> evntab = new ArrayList<String>();
	ArrayList<Integer> sstab = new ArrayList<Integer>();
	ArrayList<String> ssntab = new ArrayList<String>();
	String split[];
	FileInputStream in;
	public void processMacro()
	{
		try 
		{
			in = new FileInputStream("macro.txt");
			int k=0;
			String strFile = " ";
			while((k=in.read())!=-1)
			{
				strFile = strFile + (char)k;
			}
			System.out.println(strFile);
			
			split = strFile.split("\\n");
			mnt.setMacroName(split[1].substring(0, split[1].indexOf(" ")).trim());
			String s[] = split[1].split(",|\\s");
			int kp = 0, pp = 0;
			for(int i=1 ; i<s.length ; i++)
			{
				if(s[i].contains("="))
				{
					kp++;
					kpt.setParameterName(s[i].substring( s[i].indexOf('&')+1, s[i].indexOf('=')));
					kpt.setParameterValue(s[i].substring( s[i].indexOf('=')+1, s[i].length()));
					kpdtab.add(kpt);
					aptab.add(s[i].substring( s[i].indexOf('=')+1, s[i].length()));					
					pptab.add(s[i].substring( s[i].indexOf('&')+1, s[i].indexOf('=')));
				}
				else
				{
					pp++;					
					pptab.add(s[i].substring(s[i].indexOf('&')+1, s[i].length()));
				}
			}
			mnt.setNoOfKeywordParameter(kp);
			mnt.setNoOfPositionalParameter(pp);
			mnt.setKpdtabPointer(1);
			mnt.setMdtPointer(1);
			mnt.setSstpPointer(1);
						
			int ev = 0;
			for(int i=2 ; i<split.length ; i++)
			{
				s = split[i].split(",|\\s");
				for(int j=0 ; j<s.length ; j++)
				{
					s[j].toUpperCase();
					if(s[j].equalsIgnoreCase("LCL"))
					{
						if(!(evntab.contains(s[j+1])))
						{
							evtab.add(s[j+1].substring(s[j+1].indexOf('&')+1, s[j+1].length()));
							ev++;
							j++;
						}
					}
					else if(s[j].equalsIgnoreCase("SET"))
					{						
						evntab.add(s[j+1]);
						j++;
					}
					else if(s[j].contains("."))
					{
						String temp = s[j].substring(s[j].indexOf('.')+1, s[j].length());
						if(!(ssntab.contains(temp)))
						{
							ssntab.add(s[j].substring(s[j].indexOf('.')+1, s[j].length()));
							sstab.add(i-1);
						}
					}
				}				
			}
			mnt.setNoOfExpansionVariable(ev);						
			mntab.add(mnt);
			
			setMDT();
			mdt = new Mdt();
			mdt.setLabel("   -   ");
			mdt.setOpode("MEND");
			mdt.setOperand(" - ");
			mdtab.add(mdt);

			viewAllDS();
		} 
		catch (FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void setMDT()
	{
		for(int i=2 ; i<split.length ; i++)
		{			
			String x[] = split[i].split("\\s");
			for(int j=0 ; j<x.length ; j++)
			{
				mdt = new Mdt();
				if(split[i].contains("("))
				{
					StringTokenizer str = new StringTokenizer(split[i],"(|)| |&");
					mdt.setLabel("   -   ");
					mdt.setOpode(str.nextToken());
					String operand = "";
					while(str.hasMoreTokens())
					{
						String token = str.nextToken();
						if(evtab.contains(token))
						{
							int index = (evtab.indexOf(token));
							operand = operand + " (E,"+index+") ";
						}
						if(pptab.contains(token))
						{
							int index = (pptab.indexOf(token));
							operand = operand + " (P,"+index+") ";
						}
						if(token.contains("."))
						{							
							token = token.trim();
							String ss = token.substring(token.indexOf('.')+1, token.length());
							if(ssntab.contains(ss))
							{
								int index = (ssntab.indexOf(ss));
								operand = operand + " (S,"+index+") ";
							}
						}
						mdt.setOperand(operand);
						j+=2;						
					}
				}
				else if(isMnemonic(x[j]))
				{
					mdt.setOpode(x[j]);
					if(j>0)
					{
						if(x[j-1].contains("&"))
						{
							int index = evtab.indexOf(x[j-1].substring(x[j-1].indexOf('&')+1, x[j-1].length())) + 1;
							mdt.setLabel(" (E,"+index+") ");
						}
						else if(x[j-1].contains("."))
						{
							mdt.setLabel("   -   ");
						}
					}
					else
					{
						mdt.setLabel("   -   ");
					}
					j++;
					if(x[j].contains("&"))
					{
						if(evtab.contains(x[j].substring(x[j].indexOf('&')+1, x[j].length())))
						{
							int index = evtab.indexOf(x[j-1].substring(x[j-1].indexOf('&')+1, x[j-1].length())) + 1;
							mdt.setOperand(" (E,"+index+") ");
						}
						if(x[j].contains(",") || x[j].contains("+"))
						{
							StringTokenizer str = new StringTokenizer(x[j], ",|+|&");
							setOperandOfStringTokens(str);
						}
					}	
					else
					{
						mdt.setOperand(x[j]);
					}
				}
				if(!(mdt.isNull()))
					mdtab.add(mdt);						
			}
		}
	}
	public void setOperandOfStringTokens(StringTokenizer str)
	{
		int flag = 0;
		String operand = "";
		while(str.hasMoreTokens())
		{
			flag = 0;
			String token = str.nextToken();								
			if(ssntab.contains(token))
			{
				int index = (ssntab.indexOf(token));
				operand = operand + " (S,"+index+") ";
				flag = 1;
			}
			if(evtab.contains(token))
			{
				int index = (evtab.indexOf(token));
				operand = operand + " (E,"+index+") ";
				flag = 1;
			}
			if(pptab.contains(token))
			{
				int index = (pptab.indexOf(token));
				operand = operand + " (P,"+index+") ";
				flag = 1;
			}
			if(token.contains("=") || flag==0)
				operand = operand + token;								
			mdt.setOperand(operand);
		}
	}
	public void viewAllDS()
	{
		System.out.println("\nData Structures are as follows :- \n");
		System.out.println(mnt.toString());
		for(int i=0;i<mdtab.size();i++)
			System.out.println(mdtab.get(i));
		for(int i=0;i<kpdtab.size();i++)
			System.out.println(kpdtab.get(i));
		System.out.println("APTAB   - "+aptab);
		System.out.println("PNTAB   - "+pptab);
		System.out.println("EVTAB   - "+evtab);
		System.out.println("EVNTAB  - "+evntab);
		System.out.println("SSTAB   - "+sstab);
		System.out.println("SSNTAB  - "+ssntab);

	}
	public boolean isMnemonic(String s)
	{
		if(s.equalsIgnoreCase("LCL") || s.equalsIgnoreCase("AIF") || s.equalsIgnoreCase("MOVEM") || s.equalsIgnoreCase("MOVER")
				|| s.equalsIgnoreCase("SET"))
		{
			return true;
		}
		else
			return false;
	}
	
	public static void main(String[] args) 
	{
		new Macro().processMacro();
	}
}
