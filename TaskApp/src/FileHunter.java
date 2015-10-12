package src;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * In this class, it will do the file reading, i.e, get the content of file and search task by keyword.
 * @@author Fang Juping A0126415M
 */

public class FileHunter {
	
	public FileHunter () {
		//default
	}
	

	public static String getContent (File file) {
		StringBuilder result = new StringBuilder();
		InputStream inputStream = null;
		int length = 0;
		try {
			inputStream = new FileInputStream(file);
			length = inputStream.available();
			while (!(length == -1)) {
			    result.append(inputStream.read());
		}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result.toString();
	}


//	public static String searchInFile(File file, String keyword) {
		// TODO Auto-generated method stub
	//	return 
//	}

}
