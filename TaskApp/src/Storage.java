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
	
	public static ArrayList<String> accessToFile (File file) {
		ArrayList<String> fileContent = new ArrayList<String>();
		if (!file.exists()) {
			try {
				file.createNewFile();
				fileContent = null;
			} catch (IOException e) {
				e.printStackTrace();
			}} else {
				fileContent = FileManager.getContent(file);
			}
		return fileContent;
	}
	
	public static void addToFile(String task) {
		FileManager.addTaskToFile(task);
	}
	
	public static void deleteFromFile (int index) {
		FileManager.deleteTaskFromFile(index);
	}
	
	public static void updateTask (int index, String newTask) {
		FileManager.updateTaskInFile(index,newTask);
	}
}