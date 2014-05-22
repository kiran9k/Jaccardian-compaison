package jaccard_simlarity;

/* Developer Info :
 * Author : KIRAN
 * Creation date : 14-05-2014
 * Name : Jaccardian Simalrity
 *  
 */

import jaccard_simlarity.main_jaccard.Global;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public class file_lister {
	public static ArrayList<String> list_files(String f,Logger L)
	{
		
		File f1=new File(f);
		//System.out.println("main :"+f1.getAbsolutePath());
		List<String> output=new ArrayList<String>();
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
			Collections.sort(output, new Comparator<String>() {
		       
				@Override
				public int compare(String arg0, String arg1) {
					// TODO Auto-generated method stub
					//if(arg0.split(".")[0].contains("\\d+"))
					return arg0.compareTo(arg1);
					//return 1;
				}
		    });
			
		}
		L.info("files present in "+f1.getAbsolutePath()+ "  is : "+output.size());
		return (new ArrayList<String>(output));
		
	}
}
