package com.ariso.fsm2;

public class PoemVO {

	public PoemVO() {
		 Header = new StringBuffer();
		 Author = new StringBuffer();
		 Poem = new StringBuffer();
		 
	}

	public StringBuffer Header;
	public StringBuffer Author;
	public StringBuffer Poem;
	
	public void Print()
	{
		System.out.println("/H:"+Header+"/A:"+Author+"/P:"+Poem+"/R:"+ReversePoem());
	}
	
	
	public boolean valid()
	{
		if (this.Poem.length()>1 && this.Header.length()>1 )
		return true;
		else
		return false;
	}
	

	static String TestReversePoem ="采樵入深山，山深树重叠。桥崩卧槎拥，路险垂藤接。日落伴将稀，山风拂萝衣。长歌负轻策，平野望烟归。";
	public String ReversePoem()
	{
		String p = Poem.toString();
		String[] ps= p.split("[,|.|，|。]");
		int wl = ps.length;
		
		int psl = ps[0].length();
		StringBuffer finalreturn = new StringBuffer();

		int maxPsL = 0;
		for (int j=0;j<wl;j++)
		{
			if (ps[j].length()>maxPsL)
			{
				maxPsL = ps[j].length();
			}
		}
		StringBuffer[] rev = new StringBuffer[maxPsL];
		for (int j=0;j<maxPsL;j++)
		{
			rev[j] = new StringBuffer();
		}
		for(int i=0;i<wl;i++)
		{
			for (int j=0;j<ps[i].length();j++)
			{
				rev[j].append(ps[i].charAt(j));					
			}
		}
		
		for(int k =0;k<psl;k++)
		{
			finalreturn.append(rev[k]).append(".");
		}
		
		return finalreturn.toString();
	}
	
	
}
