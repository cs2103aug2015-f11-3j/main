package src;

import java.util.ArrayList;

public class TaskLogic {
	
	private CommandParser parser;
	private Storage store;
	private ArrayList<String> taskList;
	private Command LastCommand;
	
	private String ERROR_KEYWORD = "Keyword not recognized!";
	
	public TaskLogic(){
		
		parser = new CommandParser();
		store = new Storage();
		
		taskList = new ArrayList<>();
		LastCommand = new Command();
	}
	
	public String executeCommand(String userCommand){
		
		Command command = new Command();
		
		//send command to parser
		command = parser.parse(userCommand);
		
		//According to the keyword, execute the appropiate command
		switch (command.getKeyword()) {
		case "add":
			return addTask(command);

		case "delete":
			return deleteTask(command.getEvent());
			
		case "update":
			return updateTask(command);

//		case "undo":
//			return undoTask()
			
		default:
			return ERROR_KEYWORD;
		}
	}

	private String updateTask(Command command) {
		
		int i = searchFor(command.getEvent());
		
		if (i != taskList.size()) {
			taskList.set(i, command.getEvent()+" "+command.getTimeConstraint());
			return "Task "+command.getEvent()+" has been modified!";
		}else{
			return "Task "+command.getEvent()+" hasn't been found in your Task List!";
		}
	}

	private String addTask(Command command) {
		taskList.add(command.getEvent()+" "+command.getTimeConstraint());
		
		return "Added task "+command.getEvent()+" for "+command.getTimeConstraint();
	}

	private String deleteTask(String string) {
				
		int i = searchFor(string);
		
		if(i != taskList.size()){
			taskList.remove(i);
			return string+" has been deleted from your task list";
		}else{
			return string+" hasn't been found in your task list!";
		}
		
	}

	private int searchFor(String string) {
		int i = 0;
		
		for (String curTask : taskList){
			
			if (curTask.contains(string)){
				break;
			}
			i++;
		}
		return i;
	}
	
}
