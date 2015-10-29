package src;
<<<<<<< Updated upstream

=======
>>>>>>> Stashed changes
/**
 * The Storage saves all the data of the user,
 * and will be updated after every action.
 * This part only aims to connect with the TaskLogic Part.
 * @@author Fang Juping A0126415M
 */
import java.io.*;
import java.util.ArrayList;

public class Storage {
	
	public static final String ERROR_MESSAGE = "Failed to create new file!";
	public static final String FILE_EXISTED_MESSAGE = "This file is existed!";
		
	public Storage () {
		//default
	}
	
	/**
	 * This method will create a file based on the fileName if the file does not exist
	 * @param fileName
	 */
	static void prepareFile (String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("ERROR_MESSAGE");
			}
		} else {
				System.out.println(FILE_EXISTED_MESSAGE);
			}
	}
	
	/**
	 * This method allows connection between file and Logic to get content
	 */
	public ArrayList<String> accessToFile (File file) {
		return FileManager.getContent(file);
	}
	
	/**
	 * This method allows connection between file and Logic to update file
	 */		
	public void updateToFile (File file, ArrayList<String> fileContent) {
		FileManager.updateFileContent(file,fileContent);
	}

}