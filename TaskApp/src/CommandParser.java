
public class CommandParser {
		
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
		String event = new String();
		return event;
	}
	
	private String initTimeConstraint(String[] array) {
		String timeConstraint = new String();
		return timeConstraint;
	}
}
