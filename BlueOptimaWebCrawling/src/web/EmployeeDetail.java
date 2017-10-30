package web;



import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class EmployeeDetail {
	
	public static void nextPage(String urlString,BufferedWriter writer) {
		
		Document doc = null;
		try {
			//employee details fetched HTML page
			doc = Jsoup.connect("https://www.reuters.com/finance/stocks/company-officers/"+urlString).timeout(60000).get();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		Elements elems=doc.select("tbody[class=dataSmall]");
		Iterator<Element> it =elems.iterator();
		
		
		try{
		Element employeeInformationTable=it.next();
		Element employeeDescriptionTable=it.next();
		StringBuilder sb=new StringBuilder("");
		
		for(int i=0;i<employeeInformationTable.childNodeSize()/2;i++){
			String tem=(",,,,,,"+employeeInformationTable.child(i).child(0).text().replaceAll(",", " ")
					+" ,  "+employeeInformationTable.child(i).child(1).text().replaceAll(",", " ")
					+" ,  "+employeeInformationTable.child(i).child(2).text().replaceAll(",", " ")
					+" , "+employeeInformationTable.child(i).child(3).text().replaceAll(",", " ")
					+" , "+employeeDescriptionTable.child(i).child(1).text().replaceAll(",", " ")+"\n");
			System.out.println(tem);
			sb.append(tem);
	}
		writer.write(sb.toString());
		System.out.println("\n");
		
		}catch(Exception e){}
	}


}
