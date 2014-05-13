package jaccard_simlarity;

import jaccard_simlarity.main_jaccard.Global;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

public class file_lister {
	public static ArrayList<String> list_files(String f,Logger L)
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
		L.info("files present in "+f1.getAbsolutePath()+ "  is : "+output.size());
		return output;
		
	}
}
