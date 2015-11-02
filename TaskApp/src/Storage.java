package src;

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
	public File prepareFile (String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				System.err.println(ERROR_MESSAGE);
			}
		} else {
				System.out.println(FILE_EXISTED_MESSAGE);
			}
		return file;
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
	
	//OVERLOAD
	public void updateToFile (File file, String str) {
		FileManager.updateFileContent(file,str);
	}
	
	
	public String readLastLineFromFile(File file) {
		String str = new String();
		String lastLine = new String();
		try{		
			BufferedReader br = new BufferedReader(new FileReader(file.getName()));
			while((str=br.readLine())!=null) {					
				lastLine = str;
			}
			br.close();
		}catch(IOException e){
			e.printStackTrace();
		}
		return lastLine;
	}
	
	public File prepareFileDirectoryFile(File file) {
		String directory = readLastLineFromFile(file);		
		return new File(directory);
	}
	
	public boolean isEmptyFile(File file) {
		boolean fileIsEmpty = false;
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			if (br.readLine() == null) {  
				fileIsEmpty = true;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}   
		return fileIsEmpty;
	}

}