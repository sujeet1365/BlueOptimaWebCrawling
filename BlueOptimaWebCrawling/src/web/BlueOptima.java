package web;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class BlueOptima {
	static Document doc;
	static String str="https://www.reuters.com/sectors/industries/rankings";
	static String str1 = "https://www.reuters.com";

	public void	groupIndustryLink() throws IOException {
		
		
		File file =new File("pageLink.txt");
		BufferedWriter bw=new BufferedWriter(new FileWriter(file));;
		for(int link =1; link <300 ; link++)
		{
		
		Connection con=Jsoup.connect("http://in.reuters.com/sectors/industries/rankings?view=size&industryCode="+link);
//		con.setConnectTimeout(10000); 
        con.timeout(40000);
		
		Document doc = con.get();
		Element title = doc.getElementById("sectionHeader");
		
		String pageTitle = title.text();
		
		if(!pageTitle.equals(""))
		{
					
			bw.write(""+link);
			bw.newLine();
		}
		
		System.out.println("###   "+link+"     "+pageTitle);
	
	}
		bw.flush();
		bw.close();
	}

}
