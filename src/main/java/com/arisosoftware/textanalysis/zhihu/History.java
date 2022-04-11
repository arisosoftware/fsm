package com.arisosoftware.textanalysis.zhihu;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

public class History {
	LinkedList<String> history = new LinkedList<String>();
	int historyCount;

	public void clean()
	{
		historyCount=0;
		history = new LinkedList<String>(); 
	}
	
	public static String replace (String source, String os, String ns) {
	    if (source == null) {
	        return null;
	    }
	    int i = 0;
	    if ((i = source.indexOf(os, i)) >= 0) {
	        char[] sourceArray = source.toCharArray();
	        char[] nsArray = ns.toCharArray();
	        int oLength = os.length();
	        StringBuilder buf = new StringBuilder (sourceArray.length);
	        buf.append (sourceArray, 0, i).append(nsArray);
	        i += oLength;
	        int j = i;
	        // Replace all remaining instances of oldString with newString.
	        while ((i = source.indexOf(os, i)) > 0) {
	            buf.append (sourceArray, j, i - j).append(nsArray);
	            i += oLength;
	            j = i;
	        }
	        buf.append (sourceArray, j, sourceArray.length - j);
	        source = buf.toString();
	        buf.setLength (0);
	    }
	    return source;
	}
	
	public int popCount =0;
	public void addHistory(String line) {

	//	System.out.println("#" + historyCount + ">>" + line + "<<");

		history.add(line);
		historyCount++;
		if (historyCount > 9999) {
			historyCount--;
			String pop = history.pop();
			popCount++;
		//	System.out.println("#Pop" + pop);
		}
	}
	
	public String GetIndex(int index)
	{
	    
		return history.get(index-popCount);
	}

	public String GetLast(int index) {

		int relIdx = historyCount - index - 1;
		if (relIdx >= 0) {
			return history.get(relIdx);
		}
		return null;
	}

	public int LookupInRange(String match, int maxRange) {
		int i = historyCount - 2;
		int beginLine = i;
		int endLine = beginLine - maxRange;
		if (endLine < 0) {
			endLine = 0;
		}
		int theline = -1;
		for (; i > endLine; i--) {
			String data = history.get(i);
			if (data.equals(match)) {
				theline = i;
				break;
			}
		}

		return theline;

	}

	
	public int LookupInRange(String[] match, int maxRange, char equalsType) {
		int i = historyCount - 2;
		int beginLine = i;
		int endLine = beginLine - maxRange;
		int theline = -1;
		for (; i > endLine; i--) {
			String data = history.get(i);
			for (int v = 0; v < match.length; v++) {
				
				switch(equalsType)
				{
				case '=':
					if (data.equals(match[v])) {
						theline = i;
						break;
					};
					break;

				case '^':
					if (data.startsWith(match[v])) {
						theline = i;
						break;
					};
					break;
					

				case '$':
					if (data.endsWith(match[v])) {
						theline = i;
						break;
					};
					break;
					
				}
				
				
			}
			if (theline != -1)
				break;
		}

		return theline;

	}
	
 
	
	public String GetLastMatchInRange(String[] matchs, int range,char equalsType)
	{
		int foundIdx= LookupInRange(matchs,range,equalsType);
		if (foundIdx!=-1)
		{
			return history.get(foundIdx);
		}
		return null;
	}

	public StringBuilder GetLastUntilMatch(String match) {
		return GetLastUntilMatch(match,0);
	}

	
	public StringBuilder GetLastUntilMatch(String match, int skipIdx) {
		StringBuilder sb = new StringBuilder();

		LookupInRange(match, historyCount);
		int i = historyCount - 2- skipIdx;
		int beginLine = i;
		for (; i > 0; i--) {
			String data = history.get(i);
			if (data.equals(match)) {
				break;
			}
		}
		for (int x = i + 1; x < beginLine; x++) {
			sb.append(history.get(x));
			sb.append("\n");
		}

		return sb;
	}
	
	
	public StringBuilder GetLastUntilLastPositionNum(int LastLineNum) {
		return GetLastUntilLastPositionNum(LastLineNum,0);
	}
	public StringBuilder GetLastUntilLastPositionNum(int LastLineNum, int skipIdx) {
		StringBuilder sb = new StringBuilder();
		int position = LastLineNum;
		if (LastLineNum> historyCount)
		{
			position= position - popCount;
		}
	 
		int beginLine = historyCount - 2- skipIdx;
		for (int x = position; x < beginLine; x++) {
			sb.append(history.get(x));
			sb.append("\n");
		}

		return sb;
	}
	
	
	
	public String GetLastUntilMatch__old1(String match) {
		StringBuilder sb = new StringBuilder();
		int i = historyCount - 2;
		int beginLine = i;
		for (; i > 0; i--) {
			String data = history.get(i);
			if (data.equals(match)) {
				break;
			}
		}
		for (int x = i + 1; x < beginLine; x++) {
			sb.append(history.get(x));

		}

		return sb.toString();
	}

	public String GetDuplicateStringInRange(int range) {

		int reidx = historyCount - range - 1;
		Set<String> store = new HashSet<>();

		for (int i = historyCount - 1; i > reidx; i--) {
			String data = history.get(i);
			if (data.length() >= 1) {
				if (store.add(data) == false) {
					return data;

				}

			}

		}

		return null;
	}
	
	public String FindFirstSameSubseqString(int range) {

		int reidx = historyCount - range - 1;
		String pData = "";

		for (int i = historyCount - 1; i > reidx; i--) {
			String data = history.get(i);
			if (data.length() >= 1) {
				if (data.equals(pData)) {
					return data;

				}
				
				pData = data;
			}

		}

		return null;
	}
	
	

}
