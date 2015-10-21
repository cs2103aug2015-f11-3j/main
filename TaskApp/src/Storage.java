package src;

/**
 * The Storage saves all the data of the user,
 * and will be updated according to commands, i.e. add/delete/update.
 * @@author Fang Juping A0126415M
 */
import java.io.*;
import java.util.ArrayList;

public class Storage {
	
	public Storage () {
		//default
	}
	
	public static final String ERROR_MESSAGE = "Failed to create new file!";
	
	static File file;
	static ArrayList<String> fileContent = new ArrayList<String>();
	
	public ArrayList<String> accessToFile (File file) {
		//ArrayList<String> fileContent = new ArrayList<String>();
		if (!file.exists()) {
			try {
				file.createNewFile();
				fileContent = null;
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println("ERROR_MESSAGE");
			}} else {
				fileContent = FileManager.getContent(file);
			}
		return fileContent;
	}
	
	public void addToFile(String task) {
		FileManager.addTaskToFile(file, task);
	}
	
	public void deleteFromFile (int index) {
		FileManager.deleteTaskFromFile(file, index);
	}
	
	public void updateTask (int index, String newTask) {
		FileManager.updateTaskInFile(file, index,newTask);
	}

}