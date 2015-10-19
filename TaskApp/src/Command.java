package src;
import java.util.Date;
import java.util.ArrayList;

//package src;
//import java.lang.String;

public class Command {
	
	public enum TYPE {
		ADD, DELETE, READ, UNDO, UPDATE, EXIT, INVALID, SEARCH
	};
	private TYPE commandType;
	private String task;
	private ArrayList<Date> dates;
	
	public Command(TYPE commandType) {
		this.commandType = commandType;
		task = new String();
		dates = new ArrayList<Date>();
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
		// TODO Auto-generated method stub
		dates = datesArr;
	}

}