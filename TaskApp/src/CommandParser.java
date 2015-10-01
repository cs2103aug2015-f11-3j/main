public class CommandParser {
	
	private static String[] ACTION_KEYWORD = {"add", "update", "delete", "redo", "at", "by", "from", "to"};
	private static String[] TIME_RELATED_KEYWORD = {"Jan", "Feb"};
	
	public CommandParser() {
	}
	
	public Command parse(String input) {
		Command command = new Command();
		String[] commandPieces = input.split(" ");
		command.setKeyword(commandPieces[0]);
		command.setEvent(initEvent(commandPieces));
		command.setTimeConstraint(initTimeConstraint(commandPieces));
		return command;
	}
	
	private String initEvent(String[] array) {
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i < array.length; i++) {
			if(isAppendable(array, i)) {
				sb.append(" ");
				sb.append(array[i]);
			}
		}
 		return sb.toString().trim();
	}
	
	private String initTimeConstraint(String[] array) {
		String timeConstraint = new String();
		return timeConstraint;
	}
	
	private boolean isTimeFormat(String str) { 
		if (str.contains("/") || str.contains(":")) { 
			return true;
		}
		else 
			return false;	
	}
	
	private boolean isKeyword(String str) {
		for(int i=0; i<KEYWORD.length; i++) {
			if(str.equals(KEYWORD[i])) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isAppendable(String[] arr, int i) {
		if(isKeyword(arr[i])==false && isTimeFormat(arr[i])==false) {
			return true;
		}
		if(isKeyword(arr[i])==true) {
			if(isTimeFormat(arr[i+1]) && (i+1)<arr.length) {
				return true;
			}
			else {
				return false;
			}
		}
		return false;
	}
}
