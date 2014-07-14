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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * @author Kiran K 
 */
public class main_jaccard
{
	
	private static Logger L;
	
	public static Map<String, Float> sortByValue(HashMap<String,Float> map) {
        List list = new LinkedList(map.entrySet());
        Collections.sort(list, new Comparator() {

            @Override
            public int compare(Object o1, Object o2) {
                return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
            }
        });

        Map result = new LinkedHashMap();
        for (Iterator it = list.iterator(); it.hasNext();) {
            Map.Entry entry = (Map.Entry) it.next();
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
	
	public static void jaccard_comparison()
	{
		boolean de_dupe=false;
		ArrayList<String> file1=file_lister.list_files(Global.prop.get(0),L);
		ArrayList<String> file2=file_lister.list_files(Global.prop.get(1),L);
		if(Global.prop.get(0).matches(Global.prop.get(1)))
			de_dupe=true;
		//System.out.println(file1.size()+"\t"+file2.size());
		//System.out.println(Global.prop.get(0)+Global.prop.get(1));
		String content1="";
		String content2="";
		float prev_result=0;
		float curr_result=0;
		float actual_result=0;
		String actual_file="";
		String output="";
		HashMap<String, Float> map = new LinkedHashMap<String, Float>();
		for(int  i=0;i<file1.size();i++)
		{
			map.clear();
			//System.out.println(file1.get(i));
			if(Global.prop.get(2).matches("xml"))
				content1=read_contents.read_from_xml(file1.get(i));
			//System.out.println(content1);
			actual_result=0;
			prev_result=0;
			actual_file="";
			curr_result=0;
			for(int j=0;j<file2.size();j++)
			{
				if(new File(file1.get(i)).getName().contains(new File(file2.get(j)).getName()))
				{
				/*	System.out.println("same");
					System.out.println(file1.get(i));
					System.out.println(file2.get(j));
					System.out.println(new File(file1.get(i)).getName());*/
					continue;
				}
				else
				{
					if(Global.prop.get(2).matches("xml"))
						content2=read_contents.read_from_xml(file2.get(j));
					if(content1.length()>0 && content2.length()>0)
						curr_result=jaccardian_computations(content1,content2,true);
					if((curr_result*100>Float.valueOf(Global.prop.get(6))) && ((curr_result*100)<=Float.valueOf(Global.prop.get(7))))
					{
						if(actual_result<curr_result)
							actual_result=curr_result;
						actual_file=file2.get(j);
						map.put(file2.get(j),curr_result*100);
						/*System.out.println(file1.get(i));
						System.out.println(file2.get(j));
						System.out.println("mappped");*/
					}
				}
			}				
			//2nd stage filter 
			if(Global.prop.get(14).contains("yes") && actual_result*100<Float.valueOf(Global.prop.get(15)))
			{
				map.clear();
				System.out.println("Two stage-enterd" +actual_result);
				actual_file="";
				//actual_result=0;
				curr_result=0;
				for(int j=0;j<file2.size();j++)
				{
					if(new File(file1.get(i)).getName().contains(new File(file2.get(j)).getName()))
					{
						continue;
					}
					else
					{
						if(Global.prop.get(2).matches("xml"))
							content2=read_contents.read_from_xml(file2.get(j));
						if(content1.length()>0 && content2.length()>0)
							curr_result=jaccardian_computations(content1,content2,false);
						if((curr_result*100>Float.valueOf(Global.prop.get(6))) && ((curr_result*100)<=Float.valueOf(Global.prop.get(7))))
						{
							actual_result=curr_result;
							actual_file=file2.get(j);
							map.put(file2.get(j),curr_result*100);
						}
					}
				}					
			}
			//output+="\n";
			Map<String,Float> sortedMap = sortByValue(map);
			int count=0;
			for (Map.Entry<String, Float> entry : sortedMap.entrySet())
			{
				if(count<Integer.valueOf(Global.prop.get(16)))
				{
					String n1= new File(file1.get(i)).getName();
					String n2= new File(entry.getKey()).getName();
					float p=entry.getValue();
					if(output.contains(n2+Global.prop.get(17)+n1))
					{
						//already present
					}
					else
					{
						output+=n1+Global.prop.get(17)+n2;
						//	add percentage or not					
						if(Global.prop.get(12).contains("yes"))
						{
							output+=Global.prop.get(17)+String.valueOf((int)p);
						}
						output+="\n";
					}
		    		L.info("File "+n1+" matched to "+ n2+" with % = "+(int)(p));
		    		count++;
				}
		    }
			sortedMap.clear();
		}
		//Duplicate files removal 
		if(Global.prop.get(9).contains("yes") && de_dupe)
		{
			//move files
			String[] x=null;
			if(output.length()>0)
			{
				x=output.split("\n");
				String file=Global.prop.get(1);
				String moved=Global.prop.get(10);
				for(String i1:x)
				{
					//	move_files(file+i.split("\t")[1],moved+i.split("\t")[1]);
					int a=Integer.valueOf(i1.split("\t")[0].replaceAll("[^0-9]+",""));
					int b=Integer.valueOf(i1.split("\t")[1].replaceAll("[^0-9]+",""));
					File afile;					
					File bfile;
					if(a>b)
					{
						afile=new File(file+File.separator+i1.split("\t")[1]);	
						bfile=new File(moved+File.separator+i1.split("\t")[1]);
					}
					else
					{
						afile=new File(file+File.separator+i1.split("\t")[0]);	
						bfile=new File(moved+File.separator+i1.split("\t")[0]);
					}
					L.info("File "+afile.getAbsolutePath()+" moved to "+bfile.getAbsolutePath());
					System.out.println(afile.renameTo(bfile));
				}
			}
		}
		
		//delete files from directory 
		if(Global.prop.get(18).matches("yes"))
		{
			for (String i:file1)
			{
				new File(i).delete();
			}
			for (String i:file2)
			{
				new File(i).delete();
			}
			L.info("All files deleted from the input directory");
		}
		
		//write output to txt/xls
		//if output == null dont write to file 
		/*if(output.length()==0)
		{
			L.info("Nothing present to write to output file");
			return ;		
		}*/
		file1.clear();
		file2.clear();
		if(Global.prop.get(3).contains("txt"))
			output_writer.txt_writer(output, Global.prop.get(5)+File.separator+Global.prop.get(4));
		else if(Global.prop.get(3).contains("xls"))
			output_writer.write_to_excel(output, Global.prop.get(5)+File.separator+Global.prop.get(4));
		else if(Global.prop.get(3).contains("all"))
		{
			output_writer.write_to_excel(output, Global.prop.get(5)+File.separator+Global.prop.get(4));
			output_writer.txt_writer(output, Global.prop.get(5)+File.separator+Global.prop.get(4));
		}
		L.info("output written to "+Global.prop.get(5)+File.separator+Global.prop.get(4));
		
	}
		
		
	
	public static float jaccardian_computations(String file1,String file2,boolean actual)
	{
		String[] split1;
		String[] split2;
		Set<String> hashSet1 ;
		Set<String> hashSet2 ;
		boolean stop_words_removed=false;
		if(Global.prop.get(19).contains("yes"))
		{
			//makes use of bigram s
			//replaces all special charc's except nos & alphabet
			//then splits at end of each words
			List<String> list1 = new ArrayList<String>(Arrays.asList((file1.replaceAll("\\p{P}", "")).split("\\s+")));
			List<String> list2 = new ArrayList<String>(Arrays.asList((file2.replaceAll("\\p{P}", "")).split("\\s+")));
			//removes stop words 
			if(Global.prop.get(8).matches("yes") )
			{
				list1.removeAll(Global.stop_words);
				list2.removeAll(Global.stop_words);
				stop_words_removed=true;
				
			}
			List<String> temp=new ArrayList<String>();
			
			for (int i=0;i<list1.size()-1;i++)
			{
				temp.add(list1.get(i)+" "+list1.get(i+1));
			}
			hashSet1 = new HashSet<String>(temp);
			temp.clear();
			for (int i=0;i<list2.size()-1;i++)
			{
				temp.add(list2.get(i)+" "+list2.get(i+1));
			}
			hashSet2 = new HashSet<String>(temp);			
			temp=null;
			
		}
		else
		{
			//doesnt use bigram
			split1=file1.split("\\s+");
			split2=file2.split("\\s+");
			file1="";
			file2="";
			hashSet1 = new HashSet<String>(Arrays.asList(split1));		
			hashSet2 = new HashSet<String>(Arrays.asList(split2));
			
		}
		
		split1=null;
		split2=null;
		file1="";
		file2="";
		Set<String> intersection = new HashSet<String>(hashSet1);
		intersection.retainAll(hashSet2);
		Set<String> union = new HashSet<String>();
		union.addAll(hashSet2);
		union.addAll(hashSet1);
		if(Global.prop.get(8).matches("yes") && !stop_words_removed)
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
		if(Global.prop.get(11).contains("actual") && actual)
			result=((float)intersection.size()/(float)union.size());
		else if(Global.prop.get(11).contains("modified")&& actual)
			result=((float)intersection.size()/(float)hashSet1.size());
		else if(!actual)
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
		//System.out.println("Main program started");
		L.info("Main Program started");
		L.info("Developer Info : version 1.7.1");
		L.info("developer Info : Last modification date:14-07-2014");
		L.info("Developer Info : Last comment :Added Bi-gram support ,v1.7.1 : 14-07-2014");
		jaccard_comparison();
		L.info("Program completed ");
	}
	public static  class Global
	{
		public static ArrayList<String> prop=config_reader.get_prop();
		public static Set<String> stop_words=new HashSet<String>(Arrays.asList(read_contents.read_from_txt("stop_words.txt").split(",")));
	}
}
