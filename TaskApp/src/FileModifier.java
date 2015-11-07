package src;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * In this class, it will do the file modification, i.e, add to file, delete from file and update the file.
 * @@author Fang Juping A0126415M
 */
public class FileModifier {
	
	FileModifier () {
		//default
	}

	public static void writeTextToFile (File file, String task, Boolean isAppend) {
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

}
