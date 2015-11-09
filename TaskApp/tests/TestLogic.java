package tests;

import static org.junit.Assert.*;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import logic.Logic;

//@@author A0126410X
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestLogic {
	Logic logic = Logic.getInstance();
	private String consoleTest1 = "Added task dinner on []";
    private String consoleTest2 = "Added task meeting on [Thu Nov 05 00:00:00 SGT 2015]";
    private String consoleTest3 = "meeting";
    private String consoleTest4 = "[Thu Nov 05 00:00:00 SGT 2015]";
    private String consoleTest5 = "lecture";
    private String consoleTest6 = "[Sun Jan 03 00:00:00 SGT 2016]";
    private String consoleTest7 = "housekeeping";
    private String consoleTest8 = "[Mon Jan 04 00:00:00 SGT 2016, Tue Jan 05 00:00:00 SGT 2016]";
    private String consoleTest9 = "[Sat Dec 12 00:00:00 SGT 2015]";
    
    private boolean test = true;
    private boolean test1 = false;
    
    private int countTodo=0, countOverdue =0, countSearch = 0;
    
    private void isTextFileContain(String string, int index) {
    	if(index == 1){
    		for(int i=0; i<logic.getToDoTask().size(); i++){
    			if(logic.getToDoTask().get(i).getEvent().equals(string)){
    				countTodo++;
    			}
    		}
    	}
    	if(index == 2){
    		for(int i=0; i<logic.getDiscard().size(); i++){
    			if(logic.getDiscard().get(i).getEvent().equals(string))
    				countOverdue++;
    		}
    	}
    	if(index == 3){
    		for(int i=0; i<logic.getSearch().size(); i++){
    			if(logic.getSearch().get(i).getEvent().equals(string)){
    				countSearch++;
    			}
    		}
    	}
		//return false;
	}
    
    @Test
    public void testAaddCommandFunction(){
    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("add task1");
    	logic.executeCommand("add task2 nov 5 2015");
    	logic.executeCommand("add task3 Jan 4 2016 to Jan 5 2016");
    	logic.executeCommand("add task4 Jan 6 2016 - Jan 7 2016");
    	isTextFileContain("task1", 1);
    	assertEquals(1, countTodo);
    	isTextFileContain("task2", 2);
    	assertEquals(1, countOverdue);
    	isTextFileContain("task3", 1);
    	assertEquals(2, countTodo);
    	isTextFileContain("task4", 1);
    	assertEquals(3, countTodo);
    }
    
    @Test
    public void testBdeleteCommandFunction(){
    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("delete task3");
    	isTextFileContain("task3", 1);
    	assertEquals(0, countTodo);
    	
    }
    
    @Test
    public void testCupdateCommandFunction(){

    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("update 1 dec 12 2015");
    	isTextFileContain("task1", 1);
    	assertEquals(1, countTodo);
    }
    
    @Test
    public void testDdeleteiCommandFunction(){

    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("deletei 1");
    	isTextFileContain("task1", 1);
    	assertEquals(1, countTodo);
    }
    
    @Test
    public void testEstatusCommandFunction(){
    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("status 4");
    	isTextFileContain("task4", 1);
    	assertEquals(1, countTodo);
    	isTextFileContain("task4", 2);
    	assertEquals(0, countOverdue);
    	assertEquals(false, logic.getDiscard().get(1).getStatus());
    	
    }
    
    @Test
    public void TestFsearchCommandFunction(){
    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("add task5 Jan 12 2016");
    	logic.executeCommand("add task5 Nov 4 2015");
    	logic.executeCommand("add task6 Dec 12 2015");
    	logic.executeCommand("search task5");
    	isTextFileContain("task5", 3);
    	assertEquals(2, countSearch);
    }
    
    @Test
    public void TestGreoccuringCommandFunction(){
    	countTodo=0; countOverdue =0; countSearch = 0;
    	logic.executeCommand("add task7 every Thu from nov 17 2015 to dec 17 2015");
    	isTextFileContain("task7", 1);
    	assertEquals(5, countTodo);
    }
}
