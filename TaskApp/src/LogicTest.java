package src;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogicTest {

	@Test
	public void testAdd() {
		TaskLogic logic = new TaskLogic();
		
		logic.executeCommand("Add test1 on 12 12 2015");
		
		assertTrue(!logic.taskList.isEmpty());
		
		logic.executeCommand("delete test");
	}
	
	@Test
	public void testSearch(){
		TaskLogic logic = new TaskLogic();
		
		logic.executeCommand("Add test2 on 12 12 2015");
		logic.executeCommand("undo");
		
		assertTrue(logic.taskList.isEmpty());
	}

}
