package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import src.Logic;

public class LogicTest {

	@Test
	public void testAdd() {
		Logic logic = Logic.getInstance();
		
		logic.executeCommand("add dinner with boss on 12 12 2015");
		
		System.out.println(logic.getConsole().size());
		
		assertTrue(logic.getToDoTask().size()==1);
		
	}
	
//	@Test
//	public void testUndo(){
//		TaskLogic logic = new TaskLogic();
//		
//		logic.executeCommand("add test 13/12/2015");
//		logic.executeCommand("undo");
//		
//		assertTrue(logic.taskList.isEmpty());
//	}

}
