package src;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Message {
	private final IntegerProperty indexColumn;
	private final StringProperty eventColumn;
	private final StringProperty dateColumn;
	private final BooleanProperty status;
	
	public Message(int indexColumn, String eventColumn, String dateColumn, boolean status){
		this.indexColumn = new SimpleIntegerProperty(indexColumn);
		this.eventColumn = new SimpleStringProperty(eventColumn);
		this.dateColumn = new SimpleStringProperty(dateColumn);
		this.status = new SimpleBooleanProperty(status);
	}
	
	public Boolean getStatus(){
		return status.get();
	}
	public Integer getIndex(){
		return indexColumn.get();
	}
	
	public String getEvent(){
		return eventColumn.get();
	}
	
	public String getDate(){
		return dateColumn.get();
	}
}
