package sortApp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;



public class Start {

	// Method to read data from given url to string 
	public static String readUrl(String urlString) throws Exception {
	    BufferedReader reader = null;
	    try {
	        URL url = new URL(urlString);
	        reader = new BufferedReader(new InputStreamReader(url.openStream()));
	        StringBuffer buffer = new StringBuffer();
	        int read;
	        char[] chars = new char[1024];
	        while ((read = reader.read(chars)) != -1)
	            buffer.append(chars, 0, read); 

	        return buffer.toString();
	    } finally {
	        if (reader != null)
	            reader.close();
	    }
	    }

	// Method to determine if desired phrase is in next entry read from file 
	public static boolean isFound(UserSettings userChoice, String name, String dev_name) {
		// TODO Auto-generated method stub
		if((userChoice.find == Find.App && name.toLowerCase().indexOf(userChoice.search_phrase.toLowerCase()) >-1) || (userChoice.find == Find.Dev && dev_name.toLowerCase().indexOf(userChoice.search_phrase.toLowerCase()) >-1))
			return true;
		else
			return false;
	}
	
	public static void main(String[] args) throws Exception {
		
		JSONParser parser=new JSONParser();
		String StringFromUrl = readUrl("https://itunes.apple.com/us/rss/toppaidapplications/limit=400/json");
		
		int N =400; 	//maximum list size
	    
		// Variables with names, developer names, release dates and prices of applications
		
		String[] 	name 		= new String[N];
	    String[] 	dev_name 	= new String[N];
	    String[] 	release 	= new String[N];
	    String[] 	price 		= new String[N];
	    
	    // Variable, containing order after sort operation
	    int [] 		order = new int[N];
	    
	    int i=0, count =0;
	    
	    // Variable with parameters chosen by user 
	    UserSettings userChoice = new UserSettings(false);
	    
	    // Export parameters chosen by user
	    ExportToFile export = null;
	    
	    while(!userChoice.end){    	  
	    	
	    	count = 0;
	    	String result_string = "";
	      
	    try{
	         Object obj = parser.parse(StringFromUrl);
	         JSONObject RawData = (JSONObject) obj;
	         
	         // Initial decomposition of JSON object 
	         JSONObject Feed = (JSONObject) RawData.get("feed");
	         JSONArray Entries = (JSONArray) Feed.get("entry");

	         JSONObject Name ;
        	 JSONObject Artist ;
        	 JSONObject ReleaseDate ;
        	 JSONObject Price_object ;
        	 JSONObject Price ;
	         
	         for(i=0; i<N; i++){
	        	 
	        	 // Get next entry from JSON object
	        	 JSONObject Next_object = (JSONObject) Entries.get(i);
	        	 
	        	 Name = (JSONObject) Next_object.get("im:name");
	        	 Artist = (JSONObject) Next_object.get("im:artist");
	        	 ReleaseDate = (JSONObject) Next_object.get("im:releaseDate");
	        	 Price_object = (JSONObject) Next_object.get("im:price");
	        	 Price = (JSONObject) Price_object.get("attributes");
	        
	        	 
	        	 if( isFound(userChoice, (String) Name.get("label") , (String) Artist.get("label") ))
	         		{
	        	 	name[count] 	= (String) Name.get("label");
	        	 	dev_name[count] = (String) Artist.get("label");
	        	 	release[count] 	= (String) ReleaseDate.get("label");
	        	 	price[count] 	= (String) Price.get("amount");
	        	 	count++;
	         		}
	         	}
	         
	         // Create new temporary tables, to sort */
	         String[] tmp = new String[count];
	         String[] tmp2 = new String[count];
	         
	         switch (userChoice.sort) {
	            case AppName:
	            		tmp2 = name;
	                     	break;
	            case DevName:
            			tmp2 = dev_name;
            				break;
	            case Date:
	            		tmp2 = release;
	            			break;
	            case Price:
            			tmp2 = price;
            				break;
	            default:
	            		break;
	         };

	         // Add some string, dependent on natural (in json file from url) order
	         for (i=0; i < tmp.length ; i++){
	        	 if(i<10 && i>=0)
	        		 tmp[i] = tmp2[i] + "00" + i; 
	        	 if(i>= 10 && i<100)
	        		 tmp[i] = tmp2[i] + "0" + i; 
	        	 if(i>=100)
	        		 tmp[i] = tmp2[i] + i; 
	         }
	         
	         // Sort by element chosen by user
	         Arrays.sort( tmp );
	         
	         // Identify order of elements
	         for (i=0; i < count ; i++)
	        	 order[i] = Integer.parseInt(tmp[i].substring(tmp[i].length()-3));
	         

	         
	         // Show results
	         System.out.println("SEARCH RESULT:");
	         for (i=0; i < count ; i++){
	        	 String temporary_string ="APPLICATION : " + name[order[i]] +"\n" + "DEVELOPER : " + dev_name[order[i]] + " PRICE : " + Double.parseDouble(price[order[i]]) + " RELEASE DATE : " + release[order[i]].substring(0, 10); // + "  TIME : " + release[order[i]].substring(11,19);  
	        	 System.out.println(temporary_string);
	        	 result_string += temporary_string + "\n";
	        	 // Write to file
	        	 //export.Write(temporary_string);
	         }
	         // End of writing
	         //export.End();     
	        

	      }
	      catch(ParseException pe){
	         System.out.println("Parse exception, problem with input data");
	         System.out.println(pe);
	      	}
	      
	      // Ask user to specify export settings
	      export = new ExportToFile();
	      export.WriteAll(result_string);
	      
	      userChoice= new UserSettings(true);
	      }


	    
	}

}
