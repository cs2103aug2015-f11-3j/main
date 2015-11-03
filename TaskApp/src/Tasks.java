package src;

import java.util.ArrayList;
import java.util.Date;

public class Tasks {
	
	private String event;
	private ArrayList<Date> date;
	private boolean status;
	private int index;
	
	public Tasks(String task, ArrayList<Date> dates) {
		event = task;
		date = dates;
		status = false;
	}
	
	public void setIndex(int newIndex){
		index = newIndex;
	}

	public void setEvent(String newEvent){
		event = newEvent;
	}
	
	public void setDate(ArrayList<Date> newDate){
		date = newDate;
	}
	
	public void setStatus(boolean newStatus){
		status = newStatus;
	}
	
	public int getIndex() {
		return index;
	}
	
	public ArrayList<Date> getDate(){
		return date;
	}
	
	public String getEvent(){
		return event;
	}
	
	public boolean getStatus(){
		return status;
	}
	
	public String toString(){
		return event + date.toString();
	}

}
