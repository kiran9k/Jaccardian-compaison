package jaccard_trial;

import jaccard_simlarity.main_jaccard.Global;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class jaccard_test {
	
	public static String read_xml_files(String filename)
	{
		String content="";
		 try {

			 FileInputStream file = new FileInputStream(new File(filename));
             
	            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
	             
	            DocumentBuilder builder =  builderFactory.newDocumentBuilder();
	             
	            Document xmlDocument = builder.parse(file);
	 
	            XPath xPath =  XPathFactory.newInstance().newXPath();
	 
	            //System.out.println("*************************");
	            String expression = "//body";
	            //System.out.println(expression);
	            content = xPath.compile(expression).evaluate(xmlDocument);
	            //System.out.println(content);
	            file.close();
		 }
		 catch (SAXParseException err) {
		        System.out.println ("** Parsing error" + ", line " 
		             + err.getLineNumber () + ", uri " + err.getSystemId ());
		        System.out.println(" " + err.getMessage ());

		        }catch (SAXException e) {
		        Exception x = e.getException ();
		        ((x == null) ? e : x).printStackTrace ();

		        }catch (Throwable t) {
		        t.printStackTrace ();
		        }
		return content;
	}
	
	public static void jaccardian_computations(String file1,String file2)
	{
		String s1,s2;
		s1=read_xml_files(file1);
		s2=read_xml_files(file2);
		String[] split1=s1.split(" ");
		String[] split2=s2.split(" ");
		Set<String> hashSet1 = new HashSet<String>(Arrays.asList(split1));
		Set<String> hashSet2 = new HashSet<String>(Arrays.asList(split2));
		Set<String> intersection = new HashSet<String>(hashSet1);
		intersection.retainAll(hashSet2);
		Set<String> union = new HashSet<String>();
		union.addAll(hashSet2);
		union.addAll(hashSet1);
		System.out.println((float)intersection.size()/union.size());
	}
	
	public static ArrayList<String> list_files(String f)
	{
		
		File f1=new File(f);
		//System.out.println("main :"+f1.getAbsolutePath());
		ArrayList<String> output=new ArrayList<String>();
		int index=2;
		if(f1.exists())
		{
			for(File file:f1.listFiles())
			{
				if(file.getName().endsWith("."+Global.prop.get(index)))
				{
					output.add(file.getAbsolutePath());
				}
				else if(file.getName().endsWith("."+Global.prop.get(index)))
				{
					output.add(file.getAbsolutePath());
				}
				else if(file.getName().endsWith("."+Global.prop.get(index)))
				{
					output.add(file.getAbsolutePath());
				}
				else
				{
					//System.out.println("format not supported");
				}
			}
			
		}
		return output;
		
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*String file1="/home/kiran/Documents/input/noun_extract/sorted-articles/69/17621.xml";
		String file2="/home/kiran/Documents/input/noun_extract/sorted-articles/69/mas_1790.xml";
		
		jaccardian_computations(file1,file2);*/
		System.out.println("begin");
		list_files("/home/kiran/Documents/input/noun_extract/sorted-articles/");
	}

}
