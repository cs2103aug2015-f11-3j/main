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
	
	//According to the keyword, execute the appropiate command
	public String executeCommand(String userCommand){
		
		Command command = new Command();
		
		//send command to parser
		command = parser.parse(userCommand);
		
		
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

	//Updates a task that contains a similar task event with the commands event and date
	private String updateTask(Command command) {
	
		int i = searchFor(command.getEvent());
		
		if (i != taskList.size()) {
			taskList.set(i, command.getEvent()+" "+command.getTimeConstraint());
			return "Task "+command.getEvent()+" has been modified!";
		}else{
			return "Task "+command.getEvent()+" hasn't been found in your Task List!";
		}
	}

	//Adds a task in the task list
	private String addTask(Command command) {
		taskList.add(command.getEvent()+" "+command.getTimeConstraint());
		
		return "Added task "+command.getEvent()+" for "+command.getTimeConstraint();
	}

	//Deletes the first task that contains a specific string
	private String deleteTask(String string) {
				
		int i = searchFor(string);
		
		if(i != taskList.size()){
			String removed = taskList.get(i);
			taskList.remove(i);
			return "Task *"+removed+"* has been deleted from your task list";
		}else{
			return string+" hasn't been found in your task list!";
		}
		
	}

	//Search in each task for a substring
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
