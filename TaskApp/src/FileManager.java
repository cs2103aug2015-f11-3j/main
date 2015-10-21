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
	
	static ArrayList<String> fileContent = Storage.fileContent;
	
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

	static void addTaskToFile(File file, String task) {
		fileContent.add(task);
		updateFileContent(file, fileContent);
	}

	static void deleteTaskFromFile(File file, int index) {
		fileContent.remove(index);
		updateFileContent(file, fileContent);
	}


	static void updateTaskInFile(File file, int index, String newTask) {
		fileContent.set(index,newTask);
		updateFileContent(file, fileContent);		
	}
	
	
	private static void updateFileContent(File file, ArrayList<String> fileContent) {
		try {
			FileWriter fw = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			for (int i = 0; i < fileContent.size(); i++) {
				bw.write(fileContent.get(i));
				bw.newLine();
				}
			bw.close();
			fw.close();
			} catch (Exception e) {
				e.printStackTrace();
				}
		}
}
