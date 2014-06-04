
package sortApp;
import java.util.Scanner;


public class UserSettings {
	Find find;
	Sort sort;
	String search_phrase;
	boolean end = false;
	
	public UserSettings(boolean arg){
		
		// Default settings
		find = Find.App;
		sort = Sort.AppName;
		search_phrase = "";

		Scanner scanIn = new Scanner(System.in);
		if(arg){
		System.out.println("Quit(Q) or try again (any other key)?");
		String quit = scanIn.nextLine();
		if(quit.equals("Q") || quit.equals("q")){
			end = true;
			scanIn.close();	
			}
		System.out.println("SortApp succesfully closed! Thanks for using our app.");
		}
		if(!end){
		// Application name or developer name	
		System.out.println("What do you want to search for? \n[App name(A) / Developer name (D)] :");
		String choice_find;
		choice_find = scanIn.nextLine();
		
		if(choice_find.equals("D")){
			find = Find.Dev;  
			System.out.println("Your choice is: Developer name");
		}
		else{
			if(choice_find.equals("A"))
				System.out.println("Your choice is: App name");
			else
				System.out.println("Unspecfied choice. Using default choice: App name");
		}
		
		// Phrase to search for
		System.out.println("Search for:");
		search_phrase = scanIn.nextLine();
		
		
		// Sort order
		System.out.println("What do you want to sort results by ?\n"+ "Application Name (A), Developer Name (D), Realease date (R), Price (P)");
		String choice_sort = " ";
		choice_sort = scanIn.nextLine();          
		if(choice_sort.equals("D")){
			sort = Sort.DevName;  
			System.out.println("Your choice is: Developer name");
		}
		else{
			if(choice_sort.equals("A")){
				sort = Sort.AppName; 
				System.out.println("Your choice is: App name");
				}
			else{
				if(choice_sort.equals("R")){
					sort = Sort.Date; 
					System.out.println("Your choice is: Release date");
					}
				else{
					if(choice_sort.equals("P")){
						sort = Sort.Price; 
						System.out.println("Your choice is: Price");
						}
					else	
						System.out.println("Unspecfied choice. Using default choice: App name");
					
					}
				}
			}
		}
		

	}
	
	public UserSettings(Find _find, Sort _sort ,  String _search_phrase, boolean _end){
		find = _find;
		sort = _sort;
		search_phrase = _search_phrase;
		end = _end;
	}
}
