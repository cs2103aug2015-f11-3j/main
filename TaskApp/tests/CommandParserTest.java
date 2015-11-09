package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import CommandParser.CommandParser;
import src.Command;

public class CommandParserTest {
	
	CommandParser cm = CommandParser.getInstance();
	
	@Test
	public void testParseAddInvertedDates() {
		String str = "add go to school from 15/10/2015 to 13/10/2015";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
	}

	@Test
	public void testParseInvalid_EmptyString() {
		String str ="";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}

	@Test
	public void testParseInvalid_RandomString() {
		String str ="dfsfsfsgs";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}

	@Test
	public void testParseAdd_Not_Recognized_Date() {
		String str ="add go to school from oct 31 to oct 10";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}

	@Test
	public void testParseAdd_CorrectFormat() {
		String str ="add go to school from oct 3 2015 to oct 10 2015";
		Date date1 = new GregorianCalendar(2015, Calendar.OCTOBER, 3).getTime();
		Date date2 = new GregorianCalendar(2015, Calendar.OCTOBER, 10).getTime();
		assertEquals(Command.TYPE.ADD, cm.parse(str).getCommandType());
		assertEquals("go to school", cm.parse(str).getTask());
		assertEquals(2, cm.parse(str).getDates().size());
		assertEquals(date1, cm.parse(str).getDates().get(0));
		assertEquals(date2, cm.parse(str).getDates().get(1));	
	}
	
	@Test
	public void testParseSearch_Correct() {
		String str ="search go to";
		assertEquals(Command.TYPE.SEARCH, cm.parse(str).getCommandType());
		assertEquals("go to", cm.parse(str).getTask());	
	}
	
	@Test
	public void testParseSearch_EmptyString() {
		String str ="search";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
	}
	// test on update
	@Test
	public void testParseUpdate_Normal1() {
		String str ="update 3 on oct 10 2015";
		Date date1 = new GregorianCalendar(2015, Calendar.OCTOBER, 10).getTime();
		assertEquals(Command.TYPE.UPDATE, cm.parse(str).getCommandType());
		assertEquals("3", cm.parse(str).getTask());
		assertEquals(cm.parse(str).getDates().size(), 1);
		assertEquals(cm.parse(str).getDates().get(0),date1);	
	}
	@Test
	public void testParseUpdate_Normal2() {
		String str ="update 3 from oct 10 2015 to oct 20 2015";
		Date date0 = new GregorianCalendar(2015, Calendar.OCTOBER, 10).getTime();
		Date date1 = new GregorianCalendar(2015, Calendar.OCTOBER, 20).getTime();
		assertEquals(Command.TYPE.UPDATE, cm.parse(str).getCommandType());
		assertEquals("3", cm.parse(str).getTask());
		assertEquals(cm.parse(str).getDates().size(), 2);
		assertEquals(cm.parse(str).getDates().get(0),date0);
		assertEquals(cm.parse(str).getDates().get(1),date1);
	}
	@Test	
	public void testParseUpdate_IlligalIndex1() {
		String str ="update hqgwhqgw on oct 10 2015";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());	
	}
	@Test
	public void testParseUpdate_IlligalIndex2() {
		String str ="update 31873817 yuqwyuq on oct 10 2015";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());	
	}
	@Test
	public void testParseFile_IlligalLocation() {
		String str ="file /Users/C/1.txt";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());	
	}
	@Test
    public void testParseRead_today() {
        Date today = new Date();
        today= CommandParser.endOfDay(today);
	    String str ="read today";
        assertEquals(Command.TYPE.READ, cm.parse(str).getCommandType()); 
        assertEquals(1, cm.parse(str).getDates().size());
        assertEquals(today.toString(), cm.parse(str).getDates().get(0).toString());
    }
	@Test
    public void testParseAdd_reoccurring_duration() {
        String str ="add task1 every thursday from 11/11/2015 to 21/11/2015";
        Date date0 = new GregorianCalendar(2015, Calendar.NOVEMBER, 12).getTime();
        Date date1 = new GregorianCalendar(2015, Calendar.NOVEMBER, 19).getTime();
        assertEquals(Command.TYPE.ADD, cm.parse(str).getCommandType()); 
        assertEquals(2, cm.parse(str).getDates().size());
        assertEquals(CommandParser.startOfDay(date0).toString(), cm.parse(str).getDates().get(0).toString());
        assertEquals(CommandParser.startOfDay(date1).toString(), cm.parse(str).getDates().get(1).toString());
    }
	@Test
    public void testParseAdd_reoccurring_until() {
        String str ="add task1 every wed until 21/11/2015";
        Date date0 = new GregorianCalendar(2015, Calendar.NOVEMBER, 11).getTime();
        Date date1 = new GregorianCalendar(2015, Calendar.NOVEMBER, 18).getTime();
        assertEquals(Command.TYPE.ADD, cm.parse(str).getCommandType()); 
        assertEquals(2, cm.parse(str).getDates().size());
        assertEquals(CommandParser.startOfDay(date0).toString(), cm.parse(str).getDates().get(0).toString());
        assertEquals(CommandParser.startOfDay(date1).toString(), cm.parse(str).getDates().get(1).toString());
    }
	@Test
	public void testParseExit() {
	    String str ="exit";
	    assertEquals(Command.TYPE.EXIT, cm.parse(str).getCommandType()); 
	}
    @Test
    public void testParseDeletei() {
        String str ="deletei 3";
        assertEquals(Command.TYPE.DELETEI, cm.parse(str).getCommandType()); 
        assertEquals("3", cm.parse(str).getTask());
    }
    @Test
    public void testParseRead_aday() {
        String str ="read 11/12/2015";
        Date date = CommandParser.endOfDay(new GregorianCalendar(2015, Calendar.DECEMBER, 11).getTime());
        assertEquals(Command.TYPE.READ, cm.parse(str).getCommandType()); 
        assertEquals(1, cm.parse(str).getDates().size());
        assertEquals(date.toString(), cm.parse(str).getDates().get(0).toString());
    }
    @Test
    public void testParseUndo() {
        String str ="undo";
        assertEquals(Command.TYPE.UNDO, cm.parse(str).getCommandType()); 
    }
    @Test
    public void testParseStatus() {
        String str ="status 2";
        assertEquals(Command.TYPE.STATUS, cm.parse(str).getCommandType());
        assertEquals("2", cm.parse(str).getTask());
    }
    @Test
    public void testParseDeleteString() {
        String str ="dELETE task1";
        assertEquals(Command.TYPE.DELETE, cm.parse(str).getCommandType());
        assertEquals("task1", cm.parse(str).getTask());
    }
    @Test
    public void testParseRead_illigalday() {
        String str ="read 11/13/2015";
        assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType()); 
    }
    @Test
    public void testParseDeletei_Illigal() {
        String str ="dELETEI xxx";
        assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
    }
    @Test
    public void testParseStatus_Illigal() {
        String str ="statUS xxx";
        assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
    }
    @Test
    public void testParseFile_EmptyString() {
        String str ="file";
        assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
    }
    @Test
    public void testParseFile_CorrectUse() {
        String str ="file /Users/CYC/Desktop/123.txt";
        Command cmd = cm.parse(str);
        assertEquals(Command.TYPE.FILE, cmd.getCommandType());
        assertEquals("/Users/CYC/Desktop/123.txt", cmd.getTask());
    }
	
}
