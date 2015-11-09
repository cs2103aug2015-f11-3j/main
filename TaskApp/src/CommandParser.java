package src;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import logic.Tasks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;

/**CommandParser parses the user's input to a command object that appropriate 
 * fields initialized. 
 * 
 * @author CYC
 *
 */
public class CommandParser {
    private static CommandParser cp;
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
    
	private static final String[] PREPOSITION_KEYWORD = {"AT", "BY", "FROM", "TO", "ON"};
	private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static final String[] DAY_OF_THE_WEEK = {"MON", "TUE","WED","THU","FRI","SAT","SUN"};
	
	public CommandParser() {
		KNOWPATTERNS = initTimeFormatBank();
	}
	
	public static CommandParser getInstance() {

        if (cp == null) {
            cp = new CommandParser();
        }
        return cp;
    }
	
	/**This method is to parse file content in the form of ArrayList<String>
	 *     to ArrayList<Task> when the application launches.
	 * 
	 * @param ArrayList<String>
	 * @return ArrayList<Tasks>
	 */
	public ArrayList<Tasks> parseArrList(ArrayList<String> strTaskList) {
	    return TaskParser.parseArrList(strTaskList);
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
        		break;
		}
		return command;
	}
	
    // ================================================================
    // Create update read event on specific date command method
    // ================================================================
	private Command initReadCommand(String str) {
        Command cmd = new Command(Command.TYPE.READ);
        ArrayList<Date> dates = new ArrayList<Date>();
        Date date = new Date();
        
        if(str.equalsIgnoreCase("today")) {
        	date = endOfDay(date);
        }

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
	
    // ================================================================
    // Create update date command method
    // ================================================================
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
	
	// ================================================================
    // Create update status command method
    // ================================================================
	private Command initUpdateStatus(String remainStr) {
		if(!isNumeric(remainStr)) {
			return initInvalidCommand();
		}
		Command cmd = new Command(Command.TYPE.STATUS);
		cmd.setTask(remainStr);
		return cmd;
	}
	
	// ================================================================
    // Create delete by index command method 
    // ================================================================
	private Command initDeleteIndex(String remainStr) {
		if(!isNumeric(remainStr)) {
			return initInvalidCommand();
		}
		Command cmd = new Command(Command.TYPE.DELETEI);
		cmd.setTask(remainStr);
		return cmd;
	}
	
	// ================================================================
    // Create delete by a given string command method 
    // ================================================================
	private Command initDeleteComamnd(String remainStr) {
		Command cmd = new Command(Command.TYPE.DELETE);
		cmd.setTask(remainStr);
		return cmd;
	}

	// ================================================================
    // Create file moving command method 
    // ================================================================
	private Command initFileLocation(String directory) {
		if(directory.length()==0) {
			return initInvalidCommand();
		}
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
	
	// ================================================================
    // Create exit command method 
    // ================================================================
    private Command initExitCommand() {
        return new Command(Command.TYPE.EXIT);
    }
    // ================================================================
    // Create invalid command method 
    // ================================================================
    private Command initInvalidCommand() {
        return new Command(Command.TYPE.INVALID);
    }
    // ================================================================
    // Create undo string command method 
    // ================================================================
    private Command initUndoCommand() {
        return new Command(Command.TYPE.UNDO);
    }

    // ================================================================
    // Create search by a given string command method 
    // ================================================================
    private Command initSearchCommand(String remainStr) {
        if(remainStr.length()==0) {
            return initInvalidCommand();
        }
        Command cmd = new Command(Command.TYPE.SEARCH);
        cmd.setTask(remainStr);
        return cmd;
    }
    
    // ================================================================
    // Create add command method 
    // ================================================================
	private Command initAddCommand(String remainStr) {
		Command cmd = new Command(Command.TYPE.ADD);
		createTask(remainStr, cmd);
		return cmd;
	}

	public void createTask(String remainStr, Command cmd) {
		ArrayList<Date> dates = new ArrayList<Date>();
		String event = initEvent(remainStr);
		cmd.setTask(event);
		String timeStr = removeString(remainStr, event).toLowerCase();
		if (timeStr.contains("every") && timeStr.contains("until")) {
			createReoccurringTimeConstraint(cmd, timeStr, dates);			
		}
		else if(timeStr.contains("every") && timeStr.contains("from") && timeStr.contains("to")) {
		    createDurationReoccurringTimeConstraint(cmd, timeStr, dates);
		}
		else {
			createTimeConstraint(cmd, timeStr, dates);
		}
		cmd.setDates(dates);
	}
	
	private void createDurationReoccurringTimeConstraint(Command cmd, String timeStr, ArrayList<Date> dates) {
	    System.out.println("entre duration reoccur");
	    ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(timeStr.split("\\b(to)\\b")));
	    Date endDate = convertToDate(arrList.get(arrList.size()-1).trim());
	    arrList = new ArrayList<String>(Arrays.asList(arrList.get(0).split("\\b(from)\\b")));
	    Date startDate = convertToDate(arrList.get(arrList.size()-1).trim());
	    if(endDate==null || startDate==null || endDate.getTime()<startDate.getTime()) {
            cmd.setCommandType(Command.TYPE.INVALID);
            return;
        }
	    endDate = endOfDay(endDate);
	    startDate = startOfDay(startDate);
	    String str = arrList.get(0).replaceAll("\\b(from|to|at|on|by|every|until)\\b",  "").trim();
	    int dayOfWeek = convertDayToInt(str);
	    if (dayOfWeek==-1) {
            cmd.setCommandType(Command.TYPE.INVALID);
            return;
        }
        cmd.setKey(1);
        if(isSameDay(startDate, dayOfWeek)) {
            dates.add(startDate);
        }
        Date baseDate = getNextOccurenceOfDay(startDate, dayOfWeek);
        while(baseDate.getTime()<endDate.getTime()) {
            dates.add(baseDate);
            baseDate = startOfDay(getNextOccurenceOfDay(baseDate, dayOfWeek));
        }
        cmd.setDates(dates); 
	}

    private void createReoccurringTimeConstraint(Command cmd, String timeStr, ArrayList<Date> dates) {
		System.out.println("entre reoccuring");		//for debug
		ArrayList<String> arrList = new ArrayList<String>(Arrays.asList(timeStr.split("\\b(until)\\b")));
		for(int i=0; i<arrList.size(); i++) {
			System.out.println(i +" "+arrList.get(i));
		}
		Date endDate = convertToDate(arrList.get(arrList.size()-1).trim());
		if(endDate==null) {
			cmd.setCommandType(Command.TYPE.INVALID);
			return;
		}
		endDate = endOfDay(endDate);
		String str = arrList.get(0).replaceAll("\\b(from|to|at|on|by|every|until)\\b",  "").trim();
		int dayOfWeek = convertDayToInt(str);
		if (dayOfWeek==-1) {
			cmd.setCommandType(Command.TYPE.INVALID);
			return;
		}
		cmd.setKey(1);
		if(isSameDay(new Date(), dayOfWeek)) {
			dates.add(startOfDay(new Date()));
		}
		Date baseDate = getNextOccurenceOfDay(new Date(), dayOfWeek);
		while(baseDate.getTime()<endDate.getTime()) {
			dates.add(baseDate);
			baseDate = startOfDay(getNextOccurenceOfDay(baseDate, dayOfWeek));
		}
		cmd.setDates(dates);
			
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
		}else if(dateList.size()==2 && dateList.get(0).getTime() > dateList.get(1).getTime()) {
			cmd.setCommandType(Command.TYPE.INVALID);
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
	
	private boolean isSameDay(Date date, int dayOfWeek) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);   
	    return dayOfWeek == cal.get(Calendar.DAY_OF_WEEK);
	}
	
	private boolean toAppend(String[] arr, int i) {
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
	
	private boolean isNumeric(String str) {
		String regex = "\\d+";
		return str.matches(regex);
	}
	
	private int convertDayToInt(String str) {
		int i;
		switch (str.toLowerCase()) {
    		case "mon" :
    		case "monday" :
    			i = 2;
    			break;
    		case "tue" :
    		case "tuesday" :
    			i = 3;
    			break;
    		case "wed" :
    		case "wednesday" :
    			i = 4;
    		case "thu" :
    		case "thursday" :
    			i = 5;
    			break;
    		case "fri" :
    		case "friday" :
    			i = 6;
    			break;
    		case "sat" :
    		case "saturday" :
    			i = 7;
    			break;
    		case "sun" :
    		case "sunday" :
    			i = 1;
    			break;
    		default :
    			i = -1;
    			break;
		}
    	return i;
	}
	
	/**This method is to calculate the next nearest occurrence of specific day of the week after a specific date
     * 
     * @param Date: the reference date 
     * @param int: integer representation of day of the week
     * @return Date: next occurrence of the day
     */
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
        return cal.getTime();
    }
    
    /**This method is to add known patterns of date to the pattern bank
     * 
     * @return knowPatterns: the known pattern bank that will be used in parser.
     */
    private  ArrayList<SimpleDateFormat> initTimeFormatBank() {    
        ArrayList<SimpleDateFormat> knowPatterns = new ArrayList<SimpleDateFormat>();
        knowPatterns.add(new SimpleDateFormat("HHmm MMM dd yyyy"));
        knowPatterns.add(new SimpleDateFormat("HHmm MMM d yyyy"));
        knowPatterns.add(new SimpleDateFormat("HHmm dd/MM/yyyy"));
        knowPatterns.add(new SimpleDateFormat("HHmm d/MM/yyyy"));
        knowPatterns.add(new SimpleDateFormat("HHmm dd/M/yyyy"));
        knowPatterns.add(new SimpleDateFormat("HHmm d/M/yyyy"));
        knowPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));
        knowPatterns.add(new SimpleDateFormat("d/MM/yyyy"));
        knowPatterns.add(new SimpleDateFormat("d/M/yyyy"));
        knowPatterns.add(new SimpleDateFormat("dd/M/yyyy"));
        knowPatterns.add(new SimpleDateFormat("MMM d yyyy"));
        knowPatterns.add(new SimpleDateFormat("MMM dd yyyy"));
        knowPatterns.add(new SimpleDateFormat("d MMM yyyy"));
        knowPatterns.add(new SimpleDateFormat("dd MMM yyyy"));
        return knowPatterns;
    }
}
	
