package jaccard_simlarity;

/* Developer Info :
 * Author : KIRAN
 * Creation date : 14-05-2014
 * Name : Jaccardian Simalrity
 *  
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class read_contents {
	
	public static String read_from_html(String filename) 
	{
		
		File input = new File(filename);
		org.jsoup.nodes.Document doc;
		String output ="";
		try {
			
			doc = Jsoup.parse(input, "UTF-8", "http://example.com/");
			Elements links = doc.getElementsByTag("body");
			output = links.text();
		
		
		} catch (IOException e) {
			System.out.println("error reading html file" + filename);
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//temporary url 
		
		return output;
		
	}
	
	public static String read_from_xml(String filename)
	{
		String content="";
		 try {

			 FileInputStream file = new FileInputStream(new File(filename));
             
	            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	             
	            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
	             
	            Document xmlDocument = builder.parse(file);
	 
	            XPath xPath =  XPathFactory.newInstance().newXPath();
	            String expression = "//HeadLine";
	            //System.out.println(expression);
	            content = xPath.compile(expression).evaluate(xmlDocument)+ ". ";
	            expression = "//SlugLine";
	            //System.out.println(expression);
	            content += xPath.compile(expression).evaluate(xmlDocument)+". ";
	            expression = "//ByLine";
	            //System.out.println(expression);
	            content += xPath.compile(expression).evaluate(xmlDocument)+". ";
	            //System.out.println("*************************");
	             expression = "//body";
	            //System.out.println(expression);
	            content += xPath.compile(expression).evaluate(xmlDocument);
	            //System.out.println(content);
	            file.close();
	         
		 }
		 catch (SAXParseException err) {
			 	System.out.println("Error : read from xml failed:"+filename);
			   
		        System.out.println ("Error : ** Parsing error" + ", line " 
		             + err.getLineNumber () + ", uri " + err.getSystemId ());
		       
		        }catch (SAXException e) {
		        	System.out.println("Error : read from xml failed:"+filename);
		        	
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        	System.out.println("Error : read from xml failed:"+filename);
		        	t.printStackTrace ();
		        }
		return content;
	}
	
	
	public static String read_from_txt(String fileName)
	{
		String line = "";
		String output="";	
		try
		{			
			// FileReader reads text files in the default encoding.
			FileReader fileReader = new FileReader(fileName);        
			// Always wrap FileReader in BufferedReader.
			BufferedReader bufferedReader =new BufferedReader(fileReader);								
			while((line = bufferedReader.readLine()) != null) 
			{	
				output+=line+"\n";
			}			
			bufferedReader.close();
			//System.out.println(output);
		
		}
		
        catch(FileNotFoundException ex) 
        {
        	System.out.println( "Unable to open file '" + fileName + "'");
        	ex.printStackTrace();
        }
        catch(IOException ex)
        {
        	 System.out.println("Error reading file '"+ fileName + "'");            // Or we could just do this:
             ex.printStackTrace();
        }
		//System.out.println(output);
        return output;
	}
	
}
