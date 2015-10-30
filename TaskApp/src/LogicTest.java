package src;

import static org.junit.Assert.*;

import org.junit.Test;

public class LogicTest {

	@Test
	public void testAdd() {
		TaskLogic logic = new TaskLogic();
		
		logic.executeCommand("add test 12/12/2015");
		
		assertTrue(!logic.taskList.isEmpty());
		
		logic.executeCommand("delete test");
	}
	
	@Test
	public void testUndo(){
		TaskLogic logic = new TaskLogic();
		
		logic.executeCommand("add test 13/12/2015");
		logic.executeCommand("undo");
		
		assertTrue(logic.taskList.isEmpty());
	}

}
