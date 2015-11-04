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
	//Test add - valid input.
	public void testAdd() throws IOException {
		TaskLogic logic = new TaskLogic();
		Storage store = new Storage();
		File file = new File("testFile");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file));
		ArrayList<String> content = new ArrayList<String>();
		bw.write(logic.executeCommand("add CS3204 group meeting at 18/11/2015"));
		bw.newLine();
		content.add("task CS3204 group meeting [Wed Nov 18 00:00:00 CST 2015]");
		//logic.executeCommand("add test2 30/10/2015");
		//bw.write("test2 30/10/2015");
		//bw.newLine();
		//content.add("test2 30/10/2015");
		bw.close();		
		
		assertEquals(content, store.accessToFile(file));
		
	}

}
