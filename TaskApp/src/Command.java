package src;
import java.util.Date;
import java.util.ArrayList;

/**Command is a shared class between logic and commandparser
 * 
 * @@A0126331U
 *
 */
public class Command {
		public enum TYPE {ADD, DELETE, DELETEI, UPDATE, STATUS, READ, SEARCH, FILE, UNDO, EXIT, INVALID 
	};
	
	private TYPE commandType;
	private String task;
	private ArrayList<Date> dates;
	private int reoccuringKey;
	
	public Command(TYPE commandType) {
		this.commandType = commandType;
		task = new String();
		dates = new ArrayList<Date>();
		reoccuringKey = 0;
	}
	
	public void setCommandType(TYPE type) {
		this.commandType = type;
	}
	
	public TYPE getCommandType() {
		return commandType;
	}
	
	public String getTask() {
		return task;
	}
	
	public void setTask(String task) {
		this.task = task;
	}
	
	public ArrayList<Date> getDates() {
		return dates;
	}

	public void setDates(ArrayList<Date> datesArr) {
		dates = datesArr;
	}
	
	public void setKey(int i) {
		reoccuringKey = i;
	}
	public int getKey() {
		return reoccuringKey;
	}
}