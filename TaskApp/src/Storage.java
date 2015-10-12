package src;

/**
 * The Storage saves all the data of the user,
 * and will be updated according to commands, i.e. add/delete/update.
 * @@author Fang Juping A0126415M
 */
import java.io.*;

public class Storage {
	
	public Storage(){
		
	}
	//constant
	static final Boolean OVERWRITE_TO_FILE = false;
	static final Boolean ADD_TO_FILE = true;
	
	static void saveToFile (File file, String text, Boolean isAppend) {
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			FileModifier.writeTextToFile(file, text, isAppend);
		}
		
	}
	
	static String displayAll (File file) {
		return FileHunter.getContent(file);
	}

	//static String search (File file, String keyword) {
		//return FileHunter.searchInFile(file, keyword);
	//}
	
	
}