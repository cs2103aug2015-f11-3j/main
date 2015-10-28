package src;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class CommandParser {
	
	private static final String USER_COMMAND_ADD = "add";
    private static final String USER_COMMAND_DELETE = "delete";
    private static final String USER_COMMAND_READ = "read";
    private static final String USER_COMMAND_UPDATE = "update";
    private static final String USER_COMMAND_SEARCH = "search";
    private static final String USER_COMMAND_UNDO = "undo";
    private static final String USER_COMMAND_EXIT = "exit";
	
	private static final String[] PREPOSITION_KEYWORD = {"AT", "BY", "FROM", "TO", "ON"};
	private static final String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static final String[] DAY_OF_THE_WEEK = {"MON", "TUE","WED","THU","FRI","SAT","SUN",
												"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
	
	public CommandParser() {
	}
	
	public Command parse(String input) {
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

        	case USER_COMMAND_UPDATE :
        		command = initUpdateCommand(remainStr);
        		break;
               
        	case USER_COMMAND_READ :
        		command = initReadCommand();
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
        
        	default :
        		command = initInvalidCommand();
		}				
		return command;
	}
	
	private Command initUpdateCommand(String remainStr) {
		Command cmd = new Command(Command.TYPE.UPDATE);
		createTask(remainStr, cmd);
		return cmd;
	}

	private Command initDeleteComamnd(String remainStr) {
		Command cmd = new Command(Command.TYPE.DELETE);
		cmd.setTask(initEvent(remainStr));
		return cmd;
	}

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
		createTimeConstraint(cmd, timeStr, dates);
		cmd.setDates(dates);
	}
	
	private void createTimeConstraint(Command cmd, String str, ArrayList<Date> dates) {
		str = str.replaceAll("\\b(from|to|at|on|by)\\b",  "-");
		ArrayList<String> tokens = new ArrayList<String>(Arrays.asList(str.split("-")));
		tokens.removeAll(Collections.singleton(""));
		convertToDate(cmd ,tokens, dates);
	}
	
	//supporting format example:
	//1800 oct 3 2015
	//3 10 2015
	//oct 3 2015
	//3 oct 2015
	private void convertToDate(Command cmd, ArrayList<String> tokens, ArrayList<Date> dateList) {
		ArrayList<SimpleDateFormat> knownPatterns = initTimeFormatBank();
		for(int i=0; i<tokens.size(); i++) {
			for (SimpleDateFormat pattern : knownPatterns) {
				try {
					if(pattern.toLocalizedPattern().length()==tokens.get(i).trim().length()){
						pattern.setLenient(false);
						Date date = pattern.parse(tokens.get(i).trim());
						System.out.println(date.toString());
						dateList.add(date);
						break;
					}
				} catch (ParseException pe) {
				}
			}
		}
		if(dateList.size()!=tokens.size()||dateList.size()>2) {
			cmd.setCommandType(Command.TYPE.INVALID);
		}
	}

	private ArrayList<SimpleDateFormat> initTimeFormatBank() {
		ArrayList<SimpleDateFormat> knownPatterns = new ArrayList<SimpleDateFormat>();
		knownPatterns.add(new SimpleDateFormat("HHmm MMM dd yyyy"));
		knownPatterns.add(new SimpleDateFormat("HHmm MMM d yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd/MM/yyyy"));
		knownPatterns.add(new SimpleDateFormat("d/MM/yyyy"));
		knownPatterns.add(new SimpleDateFormat("d/M/yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd/M/yyyy"));
		knownPatterns.add(new SimpleDateFormat("MMM d yyyy"));
		knownPatterns.add(new SimpleDateFormat("MMM dd yyyy"));
		knownPatterns.add(new SimpleDateFormat("d MMM yyyy"));
		knownPatterns.add(new SimpleDateFormat("dd MMM yyyy"));
//		knownPatterns.add(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX"));
		return knownPatterns;
	}

	private Command initExitCommand() {
        return new Command(Command.TYPE.EXIT);
    }

	private Command initInvalidCommand() {
        return new Command(Command.TYPE.INVALID);
    }
	
	private Command initUndoCommand() {
        return new Command(Command.TYPE.UNDO);
    }
	
	private Command initReadCommand() {
        return new Command(Command.TYPE.READ);
    }
	
	private Command initSearchCommand(String remainStr) {
        Command cmd = new Command(Command.TYPE.SEARCH);
        cmd.setTask(remainStr);
        if(remainStr.length()==0) {
        	cmd.setCommandType(Command.TYPE.INVALID);
        }
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
			if(toAppend(pieces, i)) {
				sb.append(" ");
				sb.append(pieces[i]);
			}
			else
				break;
		}
		return sb.toString().trim();
	}

	
	
	private boolean isTimeFormat(String str) { 
		if (str.contains("/") || str.contains(":")||str.matches(".*\\d+.*")||isDay(str)||isMonth(str)) { 
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
	
	private boolean toAppend(String[] arr, int i) {
//		assert i<arr.length;
		if(isPrepositionKeyword(arr[i])==false && isTimeFormat(arr[i])==false) {
			return true;
		}
		if(isPrepositionKeyword(arr[i])==true && i <arr.length-1 && !isTimeFormat(arr[i+1])) {
			return true;
		}
		return false;
	}
}
	
