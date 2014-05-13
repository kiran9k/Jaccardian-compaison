package jaccard_simlarity;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

public class config_reader {

	public config_reader() {
		// TODO Auto-generated constructor stub
	}
	 public static ArrayList<String> get_prop()
	    {
	    	Properties prop = new Properties();
	    	ArrayList<String> s= new ArrayList<String>();
	    	try {
	               //load a properties file
	    		prop.load(new FileInputStream("config"));    			
	    		//get the property value and print it out
	    		s.add(prop.getProperty("ip1_folder_name"));
	    		s.add(prop.getProperty("ip2_folder_name"));
	    		s.add(prop.getProperty("ip_file_type"));	            
	            s.add(prop.getProperty("output_file_type"));            
	            s.add(prop.getProperty("output_filename"));	
	            
	            s.add(prop.getProperty("output_folder_name"));
	            s.add(prop.getProperty("cut-off_percent"));
	            s.add(prop.getProperty("use-stop_words"));
	              
	    	} catch (IOException ex) {
	    		
	    		ex.printStackTrace();
	        }
			return s;
	    }
}
