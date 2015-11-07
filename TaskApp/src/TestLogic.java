package src;

import static org.junit.Assert.*;

import org.junit.Test;

public class TestLogic {

	public void testAdd() {
		Logic logic = new Logic();
		
		logic.executeCommand("add dinner with boss on 12 12 2015");
		
		assertTrue(logic.getToDoTask().size()==1);
	}

}
