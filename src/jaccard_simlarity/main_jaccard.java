package jaccard_simlarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class main_jaccard {
	private static Logger L;
	public static void jaccard_comparison()
	{
		ArrayList<String> file1=file_lister.list_files(Global.prop.get(0),L);
		ArrayList<String> file2=file_lister.list_files(Global.prop.get(1),L);
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
				//System.out.println(actual_result+"\t"+file1.get(i));
				if(actual_file!="" && (actual_result*100)>Float.valueOf(Global.prop.get(6)))
				{
					L.info("File "+file1.get(i)+" matched to "+ actual_file+" with % = "+(int)(actual_result*100));
					output+=(file1.get(i)+"\t"+actual_file+"\t" +(int)(actual_result*100)+"\n");
				}
				
			}
			output_writer.txt_writer(output, Global.prop.get(5)+"/"+Global.prop.get(4));
			L.info("output written to "+Global.prop.get(5)+"/"+Global.prop.get(4));
		}
	}
	public static float jaccardian_computations(String file1,String file2)
	{
		String[] split1=file1.split(" ");
		String[] split2=file2.split(" ");
		file1="";
		file2="";
		Set<String> hashSet1 = new HashSet<String>(Arrays.asList(split1));
		
		Set<String> hashSet2 = new HashSet<String>(Arrays.asList(split2));
		Set<String> intersection = new HashSet<String>(hashSet1);
		intersection.retainAll(hashSet2);
		Set<String> union = new HashSet<String>();
		union.addAll(hashSet2);
		union.addAll(hashSet1);
		if(Global.prop.get(7).matches("yes"))
		{
			//L.info("Stopwords removed");
			//System.out.println("Stop size:"+Global.stop_words.size());
			//System.out.println("before size:"+intersection.size()+" "+union.size());
			intersection.removeAll(Global.stop_words);
			union.removeAll(Global.stop_words);
			//System.out.println("after size:"+intersection.size()+" "+union.size());
		}
		float result=((float)intersection.size()/(float)union.size());
		//System.out.println(intersection.size()+"\t"+union.size()+"\t"+result);
		hashSet1.clear();
		hashSet2.clear();
		union.clear();
		intersection.clear();
		
		return result;
	}
	public static void main(String[] args) {
		L=Logger.getLogger("log_test");		
		FileHandler fh;		
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
		System.out.println("Main program started");
		L.info("main program started");
		jaccard_comparison();
	}
	public static  class Global
	{
		public static ArrayList<String> prop=config_reader.get_prop();
		public static Set<String> stop_words=new HashSet<String>(Arrays.asList(read_contents.read_from_txt("stop_words.txt").split(",")));
	}
}
