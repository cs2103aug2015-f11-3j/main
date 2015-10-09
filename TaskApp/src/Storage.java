
/**
 * The Storage saves all the data of the user,
 * and will be updated according to commands, i.e. add/delete/update.
 * @author fang
 */
import java.io.*;

public class Storage {
	
	public Storage(){
		
	}
	
	static final Boolean OVERWRITE_TO_FILE = false;
	
	static void createSaveFile (String fileName) {
		File file = new File(fileName);
		if (!file.exists()) {
		   writeTextToFile(file, "", Storage.OVERWRITE_TO_FILE );
		}
		//Write to existing file.
	}
	
	static void writeTextToFile (File file, String task, Boolean isAppend) {
		OutputStream output = null;
		try {
			output = new FileOutputStream(file, isAppend);
			output.write(task.getBytes());
			if (isAppend == true) {
			    output.write('\n');
			    }
			} catch (IOException e) {
				e.printStackTrace();
				}
		}
	
	static String getContent (File file){
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
	}
}