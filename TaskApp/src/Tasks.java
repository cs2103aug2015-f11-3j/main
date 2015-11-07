package src;

import java.util.ArrayList;
import java.util.Date;

/**
 * This object is containing a task with it's dates, event, status and index.
 * @author A0145617A
 *
 */
public class Tasks {
	
	private String event;
	private ArrayList<Date> date;
	private boolean status;
	private int index;
	
	/**
	 * This is the constructor for the Tasks class
	 * @param task It's the event given by the user to be stored
	 * @param dates An array list that contains the dates for that specific event
	 */
	public Tasks(String task, ArrayList<Date> dates) {
		event = task;
		date = dates;
		status = false;
	}
	
	/**
	 * Sets the index to the one that is sent as a parameter
	 * @param newIndex the new index of the class
	 */
	public void setIndex(int newIndex){
		index = newIndex;
	}

	/**
	 * Sets the Event to a new string
	 * @param newEvent The new event of the task
	 */
	public void setEvent(String newEvent){
		event = newEvent;
	}
	
	/**
	 * Sets the date of the task to a new one
	 * @param newDate The new dates of the task.
	 */
	public void setDate(ArrayList<Date> newDate){
		date = newDate;
	}
	
	/**
	 * Sets the status to either true = completed or false = incomplete 
	 * @param newStatus The new status of the event
	 */
	public void setStatus(boolean newStatus){
		status = newStatus;
	}
	
	/**
	 * 
	 * @return index of the task
	 */
	public int getIndex() {
		return index;
	}
	
	/**
	 * 
	 * @return the ArrayList of Date contained by the task
	 */
	public ArrayList<Date> getDate(){
		return date;
	}
	
	/**
	 * 
	 * @return the event of the task
	 */
	public String getEvent(){
		return event;
	}
	
	/**
	 * 
	 * @return the status of the event
	 */
	public boolean getStatus(){
		return status;
	}
	
	/**
	 * @return the event and dates of the event as a String.
	 */
	public String toString(){
		return event + date.toString();
	}

}
