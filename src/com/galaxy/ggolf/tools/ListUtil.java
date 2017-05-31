package com.galaxy.ggolf.tools;

import java.util.ArrayList;
import java.util.Collection;

public class ListUtil {
	
	public ListUtil(){
		
	}

	public Collection<String> StringToList(String str,String ss){
		Collection<String> list = new ArrayList<String>();
		if(str != null){
			if(str.indexOf(ss)!=-1){
				String[] string = str.split(ss);
				for(String s : string){
					if(!s.equals("")){
						list.add(s);
					}
				}
			}else{
				if(!str.equals("")){
					list.add(str);
				}
			}
		}
		return list;
	}
	
	public String ListToString(Collection<String> list){
		String str = "";
		for(String str1 : list){
			str = str +";"+str1;
		}
		if(str.indexOf(";")!=-1){
			str = str.substring(1 , str.length());
		}
		return str;
	}
	
	public String ListToString(Collection<String> list, String s){
		String str = "";
		for(String str1 : list){
			str = str + s + str1;
		}
		if(str.indexOf(s)!=-1){
			str = str.substring(1 , str.length());
		}
		return str;
	}

}
