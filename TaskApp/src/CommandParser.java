package src;
import src.Command;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Calendar;
import java.lang.Integer;

public class CommandParser {
	
	private static String[] KEYWORD = {"ADD", "UPDATE", "DELETE", "REDO", "READ","SEARCH"};
	private static String[] PREPOSITION_KEYWORD = {"AT", "BY", "FROM", "TO", "ON"};
	private static String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static String[] DAY_OF_THE_WEEK = {"MON", "TUE","WED","THU","FRI","SAT","SUN",
												"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
	
	public CommandParser() {
	}
	
	public boolean isValidCommand(String input) {
		String commandType = input.split(" ")[0];
		for(int n=0; n<KEYWORD.length; n++) {
			if(commandType.equalsIgnoreCase(KEYWORD[n])) {
				return true;
			}
		}
		return false;
	}
	
	public Command parse(String input) {
		assert input.length()>0;
		Command cmd = new Command();
		cmd.setKeyword(initKeyword(input));
		cmd.setEvent(initEvent(input));
		cmd.setTimeConstraint(initTimeConstraint(input));
		return cmd;
	}
	
	private String initKeyword(String str) {
		String commandTypeString = str.trim().split(" ")[0];
		return commandTypeString;
	}
	
	private String initEvent(String str) {
		StringBuilder sb = new StringBuilder();
		String remainStr = str.replaceFirst(initKeyword(str), "").trim();
		String[] pieces = remainStr.split(" ");
//		System.out.println(remainStr);
		
		for (int i = 0; i < pieces.length; i++) {
			if(isAppendable(pieces, i)) {
	//			System.out.println(isAppendable(pieces,i)+" +" + i);
				sb.append(" ");
				sb.append(pieces[i]);
			}
			else
				break;
		}
		return sb.toString().trim();
	}
	
	private String getRemainingString (String str) {
		String remainStr = str.replaceFirst(initKeyword(str), "").trim();
//	System.out.println(remainStr+" +" + "getRemain");
		remainStr = remainStr.replaceFirst(initEvent(str), "").trim();
//	System.out.println(remainStr+" +" + "getRemain");
		return remainStr;
	}
	
	// support only dd/mm format
	private String initTimeConstraint(String str) {
		String remainStr = getRemainingString(str);
//		System.out.println(remainStr.length());
		
		Calendar cal = Calendar.getInstance();
		 
		if(remainStr.length()==0) {
			Date date = new Date();
			cal.setTime(date);
			return new SimpleDateFormat("EEE, d MMM").format(cal.getTime());
		}else {
			String[] strArray = remainStr.split(" ");
	//		System.out.println(convertNumericMonth(strArray[2]));
			Date date = new GregorianCalendar(2015, convertNumericMonth(strArray[2]), Integer.valueOf(strArray[1])).getTime();
			return new SimpleDateFormat("EEE, d MMM").format(date);
		}
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
	
	private boolean isAppendable(String[] arr, int i) {
		assert i<arr.length;
		if(isPrepositionKeyword(arr[i])==false && isTimeFormat(arr[i])==false) {
			return true;
		}
		if(isPrepositionKeyword(arr[i])==true) {
			if(i==arr.length-1) {
				return true;
			}
				
			else {
				if(i<arr.length-1) {
					if(isTimeFormat(arr[i+1])==false) {
						return true;
					}
					else {
						return false;
					}
				}
			}
		}
		return false;
	}
	
	private int convertNumericMonth(String str) {
		int month;
		switch (str) {
        	case "Jan":  month = 0;
                 break;
        	case "Feb":  month = 1;
                 break;
        	case "Mar":  month = 2;
                 break;
        	case "Apr":  month = 3;
                 break;
        	case "May":  month = 4;
                 break;
        	case "Jun":  month = 5;
                 break;
        	case "Jul":  month = 6;
                 break;
        	case "Aug":  month = 7;
                 break;
        	case "Sep":  month = 8;
                 break;
        	case "Oct": month = 9;
                 break;
        	case "Nov": month = 10;
                 break;
        	case "Dec": month = 11;
                 break;
        	default: month = -1;
                 break;
        }
		return month;
	}

}
