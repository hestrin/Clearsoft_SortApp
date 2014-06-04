package sortApp;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class ExportToFile {

private
String FileName= null; 
PrintWriter out= null;
String 	export= null;
String 	format_choice = null;
Export 	format = Export.TXT;

public ExportToFile() throws FileNotFoundException{
	String beginingL = "\\documentclass[10pt,a4paper]{article} " + "\n" + "\\usepackage[utf8]{inputenc}" + "\n" + "\\begin{document}"+"\n \\begin{itemize}";
	String beginingH = "<html>"+"\n" + "<body>"; 
	String file_path = "result";
	format_choice = null;
	Scanner scanIn = new Scanner(System.in);
	
	
	
	System.out.println("Export result to file?  (Yes (Y) /  No (N))");
	export = scanIn.nextLine();
	
	if (export.equals("Y")){
		System.out.println("File name:");
			try{
				file_path  = scanIn.nextLine();
				}
			catch(Exception e){
				System.out.println("Could not get proper file name. Using default name:" + file_path);
				}
		System.out.println("File format: TXT (T), HTML (H), Latex (L)");
			format_choice = scanIn.nextLine();
		if(format_choice.equals("T")){
				FileName = file_path + ".txt";
				format = Export.TXT;
				}
		if(format_choice.equals("H")){
				FileName = file_path + ".html";
				format = Export.HTML;
				}
		if(format_choice.equals("L")){
				FileName = file_path + ".tex";
				format = Export.LATEX;
				}
		if(!format_choice.equals("L") && !format_choice.equals("H") && !format_choice.equals("T")){
			System.out.println("Wrong choice of format. Using defalut format: TXT");
			FileName = file_path + ".txt";
			}
		try{
			out = new PrintWriter(FileName);
			switch (format) {
					case LATEX:
							out.println(beginingL);
							break;
					case HTML:
							out.println(beginingH);
							break;
					default:
							break;
							};
		}
		catch(Exception e){
			System.out.println("Unexpected exception");
		}
	}
	else{
		if(!export.equals("N"))
			System.out.println("Unexpected input. Result will not be saved to file.");
	}
		
}

// Write search result to file, accordingly to chosen file format
public void Write(String arg) {
	if(export.equals("Y")){
		switch (format) {
		case LATEX:
				arg = "\\item " + arg;
				break;
		case HTML:
				arg = arg + " <br>" ;
				break;
		default:
				break;
				};
		out.println(arg);
		}
}

// Method to correctly end the process of writing data to file 
public void End() {
	// TODO Auto-generated method stub
	String endingL ="\\end{itemize}"+"\\end{document}";
	String endingH = "</body>"+"\n" + "</html>";
	
	if(export.equals("Y")){
		
		switch (format) {
		case LATEX:
				out.println("\n" + endingL);
				break;
		case HTML:
				out.println(endingH);
				break;
		default:
				break;
				};
		try{
				out.close();
				System.out.println("Search result succesfully written to file:  " + FileName);
			}
		catch(Exception e){
			System.out.println("Unexpected exception");
			}
	}
	
}

public void WriteAll(String arg) {
	String lines[] = arg.split("\\r?\\n");
	for(int i=0; i<lines.length; i++)	
		Write(lines[i]);
	End();
}


}