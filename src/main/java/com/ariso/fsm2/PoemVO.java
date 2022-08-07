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
		System.out.println("/H:"+Header+"/A:"+Author+"/P:"+Poem);
	}
}
