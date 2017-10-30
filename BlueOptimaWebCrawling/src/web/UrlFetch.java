package web;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;


import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UrlFetch {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		//reader for reading "pageLink.txt" file in which number of URL that we have to fect is stord
		BufferedReader reader=new BufferedReader(new FileReader("pageLink.txt"));
		//fetching the link code(link number) of industries in "pageLink.txt" file
		String link=reader.readLine();
		
		//creating new output file
		File file =new File("output"+System.currentTimeMillis()+".csv");
		file.createNewFile();
		
		BufferedWriter writer=new BufferedWriter(new FileWriter(file));
		writer.write("Industry Group , Industry\n");
		try{
			while(link!=null){
				
				
				Connection con=Jsoup.connect("http://in.reuters.com/sectors/industries/rankings?view=size&industryCode="+link);
				con.timeout(40000);	
				String str="https://www.reuters.com/sectors/industries/rankings";
				
				/* "Show All" string for finding "Show All" button in the parsed page
				 */
				String showAll="Show All";
				Document doc = con.get();
				
				
				/*finding Organisation Name from parsed reuters.com and writing Organisation Name in output file 
				 * */
				Element title = doc.getElementById("sectionHeader");
				System.out.println(title.child(0).child(0).text());
				writer.write(title.child(0).child(0).text()+"\n");
				
				/*
				 * finding show all anchor tag for getting all industry list on the same page
				 */
				Element kk=new Element(showAll);
				Elements es=doc.select("li[tns=no]");
				Iterator<Element> it=es.listIterator();
				while(it.hasNext()){
					Element elem=it.next();
					if(elem.text().equals(showAll)){
						//System.out.println(elem);
					kk=	 elem.select("a[href]").first();
					}
				}
				String sss=kk.attr("href");
				sss=str+sss;
				
				//it's getting all industry list page
				doc=Jsoup.connect(sss).get();
				
				/*
				 * fetching industry list from above parsed URL
				 */
				Element industryListTable=doc.getElementById("dataTable");
				industryListTable=industryListTable.child(0);
				
				//loop to iterate complete industry list form fetched table
				//terminating condition because it's(JSoup parser) counting each child as 2 child like (<th> </th>)
				for(int i=0;i<industryListTable.childNodeSize()/2;i++){
					
					//industry list table info row JSoup Element Node located at table's i'th index 
					Element industryListTableInfo=industryListTable.child(i);
					
					//if condition because at table's 1st location employee data are not available
					if(industryListTableInfo.child(0).childNodeSize()>0 && !industryListTableInfo.child(0).equals(null) && i!=1){
						
						//fetching industry list information form industry list parsed table
						String temp=","+industryListTableInfo.child(0).text().replaceAll(",", " ")
								+","+industryListTableInfo.child(1).text().replaceAll(",", " ")
								+","+industryListTableInfo.child(2).text().replaceAll(",", " ")
								+","+industryListTableInfo.child(3).text().replaceAll(",", " ")
								+","+industryListTableInfo.child(4).text().replaceAll(",", " ");
						
						System.out.println(temp);
						
						//writing fetched industry list information row in ouput+current_time+.csv file
						writer.write(temp+"\n");
						
						//EmployeeDetail class nextPage() to extract each employee detail
						EmployeeDetail.nextPage(industryListTableInfo.child(0).text(),writer);
						}else{
							//fetching industry list information form industry list parsed table
							String tem=","+industryListTableInfo.child(0).text().replaceAll(",", " ")
									+","+industryListTableInfo.child(1).text().replaceAll(",", " ")
									+","+industryListTableInfo.child(2).text().replaceAll(",", " ")
									+","+industryListTableInfo.child(3).text().replaceAll(",", " ")
									+","+industryListTableInfo.child(4).text().replaceAll(",", " ");
						
							System.out.println(tem);
							writer.write(tem+"\n");
				}
	
			
			}
				//fetching the link code(link number) of industries in "pageLink.txt" file
				link=reader.readLine();
			}
		}
		catch(Exception e){
			writer.flush();
			writer.close();
			reader.close();
			e.printStackTrace();
		}
		writer.flush();
		writer.close();
		reader.close();
	}
}

