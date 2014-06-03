package jaccard_simlarity;

/* Developer Info :
 * Author : KIRAN
 * Creation date : 14-05-2014
 * Name : Jaccardian Simalrity
 *  
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class main_jaccard
{
	private static Logger L;
	public static void jaccard_comparison()
	{
		boolean de_dupe=false;
		ArrayList<String> file1=file_lister.list_files(Global.prop.get(0),L);
		ArrayList<String> file2=file_lister.list_files(Global.prop.get(1),L);
		if(Global.prop.get(0).matches(Global.prop.get(1)))
			de_dupe=true;
		System.out.println(file1.size()+"\t"+file2.size());
		System.out.println(Global.prop.get(0)+Global.prop.get(1));
		if(Global.prop.get(2).matches("xml"))
		{			
			String content1="";
			String content2="";
			float prev_result=0;
			float curr_result=0;
			float actual_result=0;
			String actual_file="";
			String output="";
			for(int  i=0;i<file1.size();i++)
			{
				//System.out.println(file1.get(i));
				content1=read_contents.read_from_xml(file1.get(i));
				//System.out.println(content1);
				actual_result=0;
				prev_result=0;
				actual_file="";
				curr_result=0;
				for(int j=0;j<file2.size();j++)
				{
					if(new File(file1.get(i)).getName().matches(new File(file2.get(j)).getName()))
					{
						continue;
					}
					else
					{
						content2=read_contents.read_from_xml(file2.get(j));
						curr_result=jaccardian_computations(content1,content2);
						if(curr_result>actual_result)
						{
							actual_result=curr_result;
							actual_file=file2.get(j);
						}
					}
				}
				L.info("File "+file1.get(i)+" matched to "+ actual_file+" with % = "+(int)(actual_result*100));
				//System.out.println(actual_result+"\t"+file1.get(i));
				if(actual_file!="" && (actual_result*100)>=Float.valueOf(Global.prop.get(6)) && (actual_result*100)<=Float.valueOf(Global.prop.get(7)) )
				{
					if(de_dupe)
					{
						String n1= new File(file1.get(i)).getName();
						String n2= new File(actual_file).getName();
						if(output.contains(n2+"\t"+n1))
						{
							//already present							
						}
						else
						{
							if(Global.prop.get(12).contains("yes"))
								output+=(n1+"\t"+n2+"\t" +(int)(actual_result*100)+"\n");
							else if(Global.prop.get(12).contains("no"))
								output+=(n1+"\t"+n2+"\n");
						}
					}
					else
					{
						if(Global.prop.get(12).contains("yes"))
							output+=(new File(file1.get(i)).getName()+"\t"+new File(actual_file).getName()+"\t" +(int)(actual_result*100)+"\n");
						else if(Global.prop.get(12).contains("no"))
							output+=(new File(file1.get(i)).getName()+"\t"+new File(actual_file).getName()+"\n");
					}	
				}
				
			}
			if(Global.prop.get(9).contains("yes") && de_dupe)
			{
				//move files
				String[] x=output.split("\n");
				String file=Global.prop.get(1);
				String moved=Global.prop.get(10);
				for(String i:x)
				{
					//move_files(file+i.split("\t")[1],moved+i.split("\t")[1]);
					int a=Integer.valueOf(i.split("\t")[0].replaceAll("[^0-9]+",""));
					int b=Integer.valueOf(i.split("\t")[1].replaceAll("[^0-9]+",""));
					File afile;					
					File bfile;
					if(a>b)
					{
							afile=new File(file+"/"+i.split("\t")[1]);	
							bfile=new File(moved+"/"+i.split("\t")[1]);
					}
					else
					{
						 afile=new File(file+"/"+i.split("\t")[0]);	
						 bfile=new File(moved+"/"+i.split("\t")[0]);
					}
					L.info("File "+afile.getAbsolutePath()+" moved to "+bfile.getAbsolutePath());
					System.out.println(afile.renameTo(bfile));
				}
				
			}
			output_writer.txt_writer(output, Global.prop.get(5)+"/"+Global.prop.get(4));
			L.info("output written to "+Global.prop.get(5)+"/"+Global.prop.get(4));
		}
	}
	public static float jaccardian_computations(String file1,String file2)
	{
		String[] split1=file1.split("\\s+");
		String[] split2=file2.split("\\s+");
		file1="";
		file2="";
		Set<String> hashSet1 = new HashSet<String>(Arrays.asList(split1));
		
		Set<String> hashSet2 = new HashSet<String>(Arrays.asList(split2));
		Set<String> intersection = new HashSet<String>(hashSet1);
		intersection.retainAll(hashSet2);
		Set<String> union = new HashSet<String>();
		union.addAll(hashSet2);
		union.addAll(hashSet1);
		if(Global.prop.get(8).matches("yes"))
		{
			//L.info("Stopwords removed");
			//System.out.println("Stop size:"+Global.stop_words.size());
			//System.out.println("before size:"+intersection.size()+" "+union.size());
			//System.out.println(intersection);
			intersection.removeAll(Global.stop_words);
			union.removeAll(Global.stop_words);
			//System.out.println("after size:"+intersection.size()+" "+union.size());
		}
		
		float result;//=((float)intersection.size()/(float)union.size());
		if(Global.prop.get(11).contains("actual"))
			result=((float)intersection.size()/(float)union.size());
		else if(Global.prop.get(11).contains("modified"))
			result=((float)intersection.size()/(float)hashSet1.size());
		else
		{
			System.out.println("Config property not properly set for Jaccardian_method");
			L.info("Config property not properly set for Jaccardian_method");
			result= -1;
		}
			
		//System.out.println(intersection.size()+"\t"+union.size()+"\t"+result);
		hashSet1.clear();
		hashSet2.clear();
		union.clear();
		intersection.clear();
		
		return result;
	}
	public static void main(String[] args) {
		L=Logger.getLogger("log_test");		
		FileHandler fh = null;		
		try
		{			
			fh = new FileHandler("MainLogFile.log",true);			
			L.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();  
		    fh.setFormatter(formatter);  
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {			
			e.printStackTrace();
		}	
		// TODO Auto-generated method stub
		if(Global.prop.get(13).contains("no"))
			L.removeHandler(fh);
		System.out.println("Main program started");
		L.info("Main Program started");
		L.info("Developer Info : version 1.4.1");
		L.info("developer Info : Last modification date:03-06-2014");
		L.info("Developer Info : Last comment :Made changes in moving files ,v 1.4.1 : 03-06-2014");
		jaccard_comparison();
		L.info("Program completed ");
	}
	public static  class Global
	{
		public static ArrayList<String> prop=config_reader.get_prop();
		public static Set<String> stop_words=new HashSet<String>(Arrays.asList(read_contents.read_from_txt("stop_words.txt").split(",")));
	}
}
