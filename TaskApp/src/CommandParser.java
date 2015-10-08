package src;
public class CommandParser {
	
//	private static String[] KEYWORD = {"ADD", "UPDATE", "DELETE", "REDO"};
	private static String[] PREPOSITION_KEYWORD = {"AT", "BY", "FROM", "TO", "ON"};
	private static String[] MONTHS = {"JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"};
	private static String[] DAY_OF_THE_WEEK = {"MON", "TUE","WED","THU","FRI","SAT","SUN",
												"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY","SATURDAY","SUNDAY"};
	
	public CommandParser() {
	}
	
	public Command parse(String input) {
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
	
	private String initTimeConstraint(String str) {
		String remainStr = getRemainingString(str);
		return remainStr;
	
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
		if(isPrepositionKeyword(arr[i])==false && isTimeFormat(arr[i])==false) {
			return true;
		}
		if(isPrepositionKeyword(arr[i])==true) {
			if((i+1)<arr.length) {
				if(isTimeFormat(arr[i+1])==false) {
					return true;
				}
			}
			else {
				return false;
			}
		}
		return false;
	}
}

