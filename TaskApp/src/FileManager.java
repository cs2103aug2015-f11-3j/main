package src;

import java.io.*;
import java.util.ArrayList;
/**
 * In this class, it will do the file content updating and allows to be accessed to get the content of the file.
 * @@author A0126415M
 */
public class FileManager {
	
	public FileManager () {
		//default
	}
	
    
	/**
	 * This method will access to file content
	 * @param file
	 * @return content
	 */
	static ArrayList<String> getContent(File file) {	
		ArrayList<String> content = new ArrayList<String>();	
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			String text = null;
			while ((text = br.readLine()) != null) {
				content.add(text);
			}
			br.close();
			fr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		return content;
		}	
	
	/**
	 * This method will fresh the file
	 * @param file
	 * @param fileContent
	 */
    static void updateFileContent(File file, ArrayList<String> fileContent) {
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
    //Overloading method updateFileContent
    static void updateFileContent(File file, String str) {
    	try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));
			bw.write(str+'\n');
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} 
}