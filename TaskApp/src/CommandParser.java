package src;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**
 * 
 * @author CYC
 *
 */
public class CommandParser {
	private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_DELETE_INDEX = "deletei";
    private static final String USER_COMMAND_READ = "read";
    private static final String USER_COMMAND_UPDATE_BY_INDEX = "update";
    private static final String USER_COMMAND_UPDATE_STATUS_BY_INDEX = "status";
    private static final String USER_COMMAND_SEARCH = "search";
    private static final String USER_COMMAND_UNDO = "undo";
    private static final String USER_COMMAND_EXIT = "exit";
    private static final String USER_COMMAND_MOVE_FILE ="file";
    
    private static ArrayList<SimpleDateFormat> KNOWPATTERNS;
    
    
    private static final String PARSE_PATTERN = "EEE MMM dd HH:mm:ss Z yyyy";
    private static final String END_OF_DAY_PATTERN = "EEE MMM dd 23:59:59 Z yyyy";
	
	private static final String[] PREPOSITION_KEYWORD = {"AT", "BY", "FROM", "TO", "ON"};
	
	private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static final String[] DAY_OF_THE_WEEK = {"MON", "TUE","WED","THU","FRI","SAT","SUN"};
	
	public CommandParser() {
		KNOWPATTERNS = new ArrayList<SimpleDateFormat>();
	}
	
	public ArrayList<Tasks> parseArrList(ArrayList<String> arrList) {
		ArrayList<Tasks> tasks = new ArrayList<Tasks>();
		
			for(int i=0; i<arrList.size(); i++) {
				ArrayList<Date> dates = new ArrayList<Date>();
				String event = createEvent(arrList.get(i));
				toDateList(dates ,arrList.get(i));
				tasks.add(new Tasks(event, dates));
			}
		return tasks;
	}
	
	public String createEvent(String string) {
		String[] tokens = string.split("\\[");
		return tokens[0].trim();
	}

	public void toDateList(ArrayList<Date> dates, String string) {
		String[] tokens = string.split("\\[");
		String str =tokens[1].substring(0, tokens[1].length()-1);
		String[] rawDate = str.split(",");
		SimpleDateFormat sdf = new SimpleDateFormat(PARSE_PATTERN);
		
		for(int i=0; i<rawDate.length; i++) {
			try {
				Date date = sdf.parse(rawDate[i].trim());
				dates.add(date);
			} catch (ParseException e) {
				System.err.println("cannot be parsed as date");
			}	
		}
	}

	public Command parse(String input) {
		initTimeFormatBank();
		Command command;
		String commandTypeString = getFirstWord(input);
		String remainStr = removeString(input, commandTypeString);
		
		switch (commandTypeString.toLowerCase()) {
        	case USER_COMMAND_ADD :
        		command = initAddCommand(remainStr);
        		break;

        	case USER_COMMAND_DELETE :
        		command = initDeleteComamnd(remainStr);
        		break;
        	
        	case USER_COMMAND_DELETE_INDEX :
        		command = initDeleteIndex(remainStr);
        		break;
        		
        	case USER_COMMAND_UPDATE_BY_INDEX :
        		command = initUpdateDate(remainStr);
        		break;
        	
        	case USER_COMMAND_UPDATE_STATUS_BY_INDEX :
        		command = initUpdateStatus(remainStr);
        		break;
        		
        	case USER_COMMAND_READ :
        		command = initReadCommand(remainStr);
        		break;
        
        	case USER_COMMAND_SEARCH :
        		command = initSearchCommand(remainStr);
        		break;
        
        	case USER_COMMAND_EXIT :
        		command = initExitCommand();
        		break;
        
        	case USER_COMMAND_UNDO :
        		command = initUndoCommand();
        		break;
        	
        	case USER_COMMAND_MOVE_FILE :
        		command = initFileLocation(remainStr);
        		break;
        	
        	default :
        		command = initInvalidCommand();
		}
		return command;
	}
	
	//READ a date
	private Command initReadCommand(String str) {
        Command cmd = new Command(Command.TYPE.READ);
        ArrayList<Date> dates = new ArrayList<Date>();
        Date date = new Date();
        
        if(str.equalsIgnoreCase("today")) {
        	date = endOfDay(date);
        }
//        else if(str.equalsIgnoreCase("tmr")){
//        	Calendar c = Calendar.getInstance(); 
//        	c.setTime(date); 
//        	c.add(Calendar.DATE, 1);
//        	date = endOfDay(c.getTime());
//        }
//        else if(str.equalsIgnoreCase("this week")){
//        	date = getNextOccurenceOfDay(date, Calendar.SUNDAY);
//        	date = endOfDay(date);
//      }
        else {
        	date = convertToDate(str);
        	if(date==null) {
        		return initInvalidCommand();
        	}
        	date = endOfDay(date);
        }
        dates.add(date);
        cmd.setDates(dates);
        return cmd;
    }
	
	private Date getNextOccurenceOfDay(Date today, int dayOfWeek) {  
		  Calendar cal = Calendar.getInstance();  
		  cal.setTime(today);  
		  int dow = cal.get(Calendar.DAY_OF_WEEK);  
		  int numDays = 7 - ((dow - dayOfWeek) % 7 + 7) % 7;  
		  cal.add(Calendar.DAY_OF_YEAR, numDays); 
		  return cal.getTime();  
	} 
	
	private Date startOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		return cal.getTime();
	}
	
	private Date endOfDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		
//		SimpleDateFormat standardSdf = new SimpleDateFormat(PARSE_PATTERN);
//        SimpleDateFormat endOfDay = new SimpleDateFormat(END_OF_DAY_PATTERN);
//        String str = endOfDay.format(date);
//		try {
//			date = standardSdf.parse(str);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
		return cal.getTime();
	}
	
	// update date to task represented by unique index
	private Command initUpdateDate(String remainStr) {
		Command cmd = new Command(Command.TYPE.UPDATE);
		ArrayList<Date> dates = new ArrayList<Date>();
		String str = getFirstWord(remainStr);
		if(!isNumeric(str)) {
			return initInvalidCommand();
		}
		cmd.setTask(str);
		String rawDate = removeString(remainStr, str);
		createTimeConstraint(cmd, rawDate, dates);	
		cmd.setDates(dates);
		return cmd;
	}
	//update status with task index
	private Command initUpdateStatus(String remainStr) {
		if(!isNumeric(remainStr)) {
			return initInvalidCommand();
		}
		Command cmd = new Command(Command.TYPE.STATUS);
		cmd.setTask(remainStr);
		return cmd;
	}
	
	//delete by task index
	private Command initDeleteIndex(String remainStr) {
		if(!isNumeric(remainStr)) {
			return initInvalidCommand();
		}
		Command cmd = new Command(Command.TYPE.DELETEI);
		cmd.setTask(remainStr);
		return cmd;
	}
	
	//delete by string	
	private Command initDeleteComamnd(String remainStr) {
		Command cmd = new Command(Command.TYPE.DELETE);
		cmd.setTask(remainStr);
		return cmd;
	}

	private Command initFileLocation(String directory) {
		Command cmd = new Command(Command.TYPE.FILE);
		File file = new File(directory);
		System.out.println(file.getName());
		try {
			if((!directory.isEmpty()) && file.createNewFile()) {
				cmd.setTask(directory);				
			}
		} catch (IOException e) {
			return initInvalidCommand();
		}	
		return cmd;
	}
	//add command
	private Command initAddCommand(String remainStr) {
		Command cmd = new Command(Command.TYPE.ADD);
		createTask(remainStr, cmd);
		return cmd;
	}

	public void createTask(String remainStr, Command cmd) {
		ArrayList<Date> dates = new ArrayList<Date>();
		String event = initEvent(remainStr);
		cmd.setTask(event);
		String timeStr = removeString(remainStr, event);
		timeStr=timeStr.toLowerCase();
		System.out.println("timeStr is " +timeStr);
		if (timeStr.contains("every") && timeStr.contains("until")) {
			System.out.println("entre reoccurring task");
			System.out.println(timeStr);
			createReoccurringTimeConstraint(cmd, timeStr, dates);			
		}else {
			System.out.println("entre noraml task");
			createTimeConstraint(cmd, timeStr, dates);
		}
		cmd.setDates(dates);
	}
	
	private void createReoccurringTimeConstraint(Command cmd, String timeStr, ArrayList<Date> dates) {
		System.out.println("entre reoccuring");		//for debug
		ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(timeStr.split("\\b(until)\\b")));
		for(int i=0; i<arrList.size(); i++) {
			System.out.println(i +" "+arrList.get(i));
		}
		Date endDate = endOfDay(convertToDate(arrList.get(arrList.size()-1).trim()));
		if(endDate==null) {
			cmd.setCommandType(Command.TYPE.INVALID);
			return;
		}
		System.out.println("end Date "+endDate.toString());		//for debug
		String str = arrList.get(0).replaceAll("\\b(from|to|at|on|by|every|until)\\b",  "").trim();
		System.out.println(str);
		int dayOfWeek = convertDayToInt(str);
		if (dayOfWeek==-1) {
			System.out.println("entre escape 1");
			cmd.setCommandType(Command.TYPE.INVALID);
			return;
		}
		cmd.setKey(1);
		if(isSameDay(new Date(), dayOfWeek)) {
			dates.add(startOfDay(new Date()));
		}
		Date baseDate = getNextOccurenceOfDay(new Date(), dayOfWeek);
		System.out.println("base date "+baseDate.toString());	//for debug
		while(baseDate.getTime()<endDate.getTime()) {
			dates.add(baseDate);
			baseDate = startOfDay(getNextOccurenceOfDay(baseDate, dayOfWeek));
			System.out.println("base date in loop "+baseDate.toString());
		}
		cmd.setDates(dates);
			
	}

	private boolean isSameDay(Date date, int dayOfWeek) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);	 
		return dayOfWeek == cal.get(Calendar.DAY_OF_WEEK);
	}

	private void createTimeConstraint(Command cmd, String str, ArrayList<Date> dates) {
		str = str.replaceAll("\\b(from|to|at|on|by|every|until)\\b",  "-");
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(str.split("-")));
		tokens.removeAll(Collections.singleton(""));
		convertToDate(cmd ,tokens, dates);
	}
	
	//supporting format example:
	//1800 oct 3 2015
	// 3/10/2015
	//oct 3 2015
	//3 oct 2015
	private void convertToDate(Command cmd, ArrayList<String> tokens, ArrayList<Date> dateList) {
		for(int i=0; i<tokens.size(); i++) {
			for (SimpleDateFormat pattern : KNOWPATTERNS) {
				try {
					if(pattern.toLocalizedPattern().length()==tokens.get(i).trim().length()){
						pattern.setLenient(false);
						Date date = pattern.parse(tokens.get(i).trim());
						System.out.println(date.toString()+"    "+1);
						dateList.add(date);
						break;
					}
				} catch (ParseException pe) {
				}
			}
		}
		if(dateList.size()!=tokens.size()) {
			cmd.setCommandType(Command.TYPE.INVALID);
			cmd.setKey(dateList.size());
		}
	}
	
	public Date convertToDate(String str) {
		for (SimpleDateFormat pattern : KNOWPATTERNS) {
			try {
				if(pattern.toLocalizedPattern().length()==str.trim().length()){
					pattern.setLenient(false);
					Date date = pattern.parse(str.trim());	
					System.out.println(date.toString()+"    "+pattern.toLocalizedPattern());
					return date;
				}
			} catch (ParseException pe) {
			}
		}
		return null;
	}

	private void initTimeFormatBank() {	
		KNOWPATTERNS.add(new SimpleDateFormat("HHmm MMM dd yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("HHmm MMM d yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("dd/MM/yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("d/MM/yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("d/M/yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("dd/M/yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("MMM d yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("MMM dd yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("d MMM yyyy"));
		KNOWPATTERNS.add(new SimpleDateFormat("dd MMM yyyy"));
	}
	
	//EXIT
	private Command initExitCommand() {
        return new Command(Command.TYPE.EXIT);
    }
	//INVALID
	private Command initInvalidCommand() {
        return new Command(Command.TYPE.INVALID);
    }
	//UNDO
	private Command initUndoCommand() {
        return new Command(Command.TYPE.UNDO);
    }

	//SEARCH a string
	private Command initSearchCommand(String remainStr) {
		if(remainStr.length()==0) {
        	return initInvalidCommand();
        }
		Command cmd = new Command(Command.TYPE.SEARCH);
        cmd.setTask(remainStr);
        return cmd;
    }
	
	private String getFirstWord(String str) {
		return str.trim().split(" ")[0];
	}
	
	private String removeString(String str, String strToRemove) {
		return str.replaceFirst(strToRemove, "").trim();
	}
	
	private String initEvent(String str) {
		StringBuilder sb = new StringBuilder();
		String[] pieces = str.split(" ");
		for (int i = 0; i < pieces.length; i++) {
			System.out.println(i+ " "+pieces[i]);
			System.out.println(toAppend(pieces,i));
			if(toAppend(pieces, i)) {
				sb.append(" ");
				sb.append(pieces[i].trim());
			}
			else
				break;
		}
		return sb.toString().trim();
	}
	
	private boolean isTimeFormat(String str) { 
		if (str.contains("/") || isNumeric(str) ||isDay(str)||isMonth(str)) { 
			return true;
		}
		else 
			return false;	
	}
	
	private boolean isMonth(String str) {
		for(int i=0; i< MONTHS.length; i++) {
			if(str.equalsIgnoreCase(MONTHS[i]))
				return true;
		}
		return false;
	}
	
	private boolean isDay(String str) {
		for(int i=0; i< DAY_OF_THE_WEEK.length; i++) {
			if(str.equalsIgnoreCase(DAY_OF_THE_WEEK[i]))
				return true;
		}
		return false;
	}
	
	private boolean isPrepositionKeyword(String str) {
		for(int i=0; i<PREPOSITION_KEYWORD.length; i++) {
			if(str.equalsIgnoreCase(PREPOSITION_KEYWORD[i])) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isReoccurringKeyword(String str) {
		return str.equalsIgnoreCase("EVERY") || str.equalsIgnoreCase("UNTIL") || str.equalsIgnoreCase("EVERYDAY");
	}
	
	private boolean toAppend(String[] arr, int i) {
//		assert i<arr.length;
		if(!isPrepositionKeyword(arr[i]) && !isTimeFormat(arr[i]) && !isReoccurringKeyword(arr[i])) {
			return true;
		}
		if(isPrepositionKeyword(arr[i])==true && i <arr.length-1 && !isTimeFormat(arr[i+1])) {
			return true;
		}
		if(isNumeric(arr[i]) && i>=1  && !isPrepositionKeyword(arr[i-1])) {
			return true;
		}
		return false;
	}
	
	public boolean isNumeric(String str) {
		String regex = "\\d+";
		return str.matches(regex);
	}
	
	private int convertDayToInt(String str) {
		int i;
		switch (str.toLowerCase()) {
    		case "mon" :
    			i = 2;
    			break;
    		case "tue" :
    			i = 3;
    			break;
    		case "wed" :
    			i = 4;
    		case "thu" :
    			i = 5;
    			break;
    		case "fri" :
    			i = 6;
    			break;
    		case "sat" :
    			i = 7;
    			break;
    		case "sun" :
    			i = 1;
    			break;
    		default :
    			i = -1;
		}
    	return i;
	}
}
	
