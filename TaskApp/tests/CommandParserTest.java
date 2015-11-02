package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;

import src.Command;
import src.CommandParser;

public class CommandParserTest {
	
	CommandParser cm = new CommandParser();
	//test on event with only one time
	@Test
	public void testParse1() {
		String str = "add go to school by 0700 oct 32 2015";
		Date date = new GregorianCalendar(2015, Calendar.OCTOBER, 32, 7,0).getTime();
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
//		assertEquals("go to school", cm.parse(str).getTask());
//		assertEquals(1, cm.parse(str).getDates().size());
//		assertEquals(date, cm.parse(str).getDates().get(0));
	}
	// test on not fully entered input
	@Test
	public void testParse2() {
		String str = "add go to school on";
		assertEquals(Command.TYPE.ADD, cm.parse(str).getCommandType());
		assertEquals("go to school", cm.parse(str).getTask());
		assertEquals(0, cm.parse(str).getDates().size());
		
	}
	// test on empty input
	@Test
	public void testParse3() {
		String str ="";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}
	// test on random input
	@Test
	public void testParse4() {
		String str ="dfsfsfsgs";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}
	// test on not following time format
	@Test
	public void testParse5() {
		String str ="add go to school from oct 31 to oct 10";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
		
	}
	// test on event which has two time
	@Test
	public void testParse6() {
		String str ="add go to school from oct 3 2015 to oct 10 2015";
		Date date1 = new GregorianCalendar(2015, Calendar.OCTOBER, 3).getTime();
		Date date2 = new GregorianCalendar(2015, Calendar.OCTOBER, 10).getTime();
		assertEquals(Command.TYPE.ADD, cm.parse(str).getCommandType());
		assertEquals("go to school", cm.parse(str).getTask());
		assertEquals(2, cm.parse(str).getDates().size());
		assertEquals(date1, cm.parse(str).getDates().get(0));
		assertEquals(date2, cm.parse(str).getDates().get(1));	
	}
	// test on normal search
	@Test
	public void testParse7() {
		String str ="search go to";
		assertEquals(Command.TYPE.SEARCH, cm.parse(str).getCommandType());
		assertEquals("go to", cm.parse(str).getTask());	
	}
	// test on search nothing
	@Test
	public void testParse8() {
		String str ="search";
		assertEquals(Command.TYPE.INVALID, cm.parse(str).getCommandType());
	}
	// test on update
		@Test
	public void testParse9() {
		String str ="update go to school on oct 10 2015";
		assertEquals(Command.TYPE.UPDATE, cm.parse(str).getCommandType());
	}
}
