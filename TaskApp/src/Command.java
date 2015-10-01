import java.lang.String;

public class Command {
	
	private String commandKeyword;
	private String event;
	private String timeConstraint;
	
	public Command() {
		commandKeyword = new String();
		event = new String();
		timeConstraint = new String();
	}
	
	public void setKeyword (String str) {
		commandKeyword = str;
	}
	
	public void setEvent (String str) {
		event = str;
	}
	
	public void setTimeConstraint (String time) {
		timeConstraint = time;
	}
	
	public String getKeyword () {
		return commandKeyword;
	}
	
	public String getEvent() {
		return event;
	}
	
	public String getTimeConstraint() {
		return timeConstraint;
	}
}
