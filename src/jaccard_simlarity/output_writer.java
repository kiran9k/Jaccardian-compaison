package jaccard_simlarity;

/* Developer Info :
 * Author : KIRAN
 * Creation date : 14-05-2014
 * Name : Jaccardian Simalrity
 *  
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class output_writer {

	public output_writer() {
		// TODO Auto-generated constructor stub
	}
	public static void txt_writer(String content,String filename)	{
		
		try
			{				
				File file = new File(filename+".txt");
				// if file doesnt exists, then create it				
				if (!file.exists()) 
				{
					file.createNewFile();
				} 
			
				FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
				BufferedWriter bw = new BufferedWriter(fw);
				
				bw.write(content);
				bw.close(); 
				
				
			} 
		catch (IOException e)
			{
				e.printStackTrace();
			
			}
		
		}
	
	public static void write_to_excel(String content,String filename)
	{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Sample sheet");
		String[] temp=content.split("\n");
		int row_no=0;
		Row row;
		int col_length=temp[0].split("\t").length;
		CellStyle style = workbook.createCellStyle();
		Font font = workbook.createFont();
        font.setColor(IndexedColors.BLUE.getIndex());
        style.setFont(font);
		row=sheet.createRow(row_no);
		row_no++;
		String[] col_headers={"Article Name","Matched Article","Percentage"};
		int cell_no=0;
		Cell c;
		for(int i =0;i<col_headers.length && i<col_length;i++)
		{
			c=row.createCell(cell_no);
			cell_no++;
			c.setCellValue(col_headers[i]);
			c.setCellStyle(style);
		}
		for(String x:temp)
		{
			cell_no=0;
			row=sheet.createRow(row_no);
			row_no++;
			for(String y:x.split("\t"))
			{
				c=row.createCell(cell_no);
				cell_no++;
				c.setCellValue(y);
			}
		}
		
		try
		{
		    FileOutputStream out =
		            new FileOutputStream(new File(filename+".xls"));
		    workbook.write(out);
		    out.close();
		    //System.out.println("Excel writting successfull");
		     
		} catch (FileNotFoundException e) {
			System.out.println("Unable to write to excel file");
		    e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Unable to write to excel file");
		    e.printStackTrace();
		}
	}
}
