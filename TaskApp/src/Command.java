package src;
import java.util.Date;
import java.util.ArrayList;

public class Command {
		public enum TYPE {ADD, DELETE, READ, UNDO, UPDATE, EXIT, INVALID, SEARCH, UPDATES, DELETEI, FILE, UPDATEI, STATUSI
	};
	
	private TYPE commandType;
	private String task;
	private ArrayList<Date> dates;
//	private int indexOfUpdateDeleteItem;
	private int reoccuringKey;
	
	public Command(TYPE commandType) {
		this.commandType = commandType;
		task = new String();
		dates = new ArrayList<Date>();
//		indexOfUpdateDeleteItem = -1;
		reoccuringKey = 0;
	}
	
	public void setCommandType(TYPE type) {
		this.commandType = type;
	}
	
	public TYPE getCommandType() {
		return commandType;
	}
	
	// add/delete/update methods
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
	
//	public void setIndex(int i) {
//		indexOfUpdateDeleteItem = i;
//	}
//	public int getIndex() {
//		return indexOfUpdateDeleteItem;
//	}
	public void setKey(int i) {
		reoccuringKey = i;
	}
	public int getKey() {
		return reoccuringKey;
	}
}