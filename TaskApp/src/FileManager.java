package src;

import java.io.*;
import java.util.ArrayList;
/**
 * In this class, it will do the file manage, i.e, add to file, delete from file, update the file and get content of the file.
 * @@author Fang Juping A0126415M
 */
public class FileManager {
	public FileManager () {
		//default
	}
	static ArrayList<String> fileContent = new ArrayList<String>();
	
	static ArrayList<String> getContent(File file) {	
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String text = null;
			while ((text = br.readLine()) != null) {
				fileContent.add(text);
			}
			br.close();
			fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return fileContent;
}

	static void addTaskToFile(String task) {
		fileContent.add(task);
	}

	static void deleteTaskFromFile(int index) {
		fileContent.remove(index);
	}

	static void updateTaskInFile(int index, String newTask) {
		fileContent.set(index,newTask);
		
	}
	
	

	
}
