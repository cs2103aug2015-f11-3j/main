package tests;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Test;

import src.Storage;
import src.TaskLogic;

public class Testing {

	@Test
	public void testAdd() throws IOException {
		TaskLogic logic = new TaskLogic();
		Storage store = new Storage();
		File file = new File("textShownOnGUI");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		ArrayList<String> content = new ArrayList<String>();
		//logic.executeCommand("add test1 30/10/2015");
		bw.write(logic.executeCommand("add test 30/10/2015"));
		//bw.newLine();
		content.add("Added task test [Fri Oct 30 00:00:00 CST 2015]");
		//logic.executeCommand("add test2 30/10/2015");
		//bw.write("test2 30/10/2015");
		//bw.newLine();
		//content.add("test2 30/10/2015");
		bw.close();		
		
		assertEquals(content, store.accessToFile(file));
		
	}

}
