package src;

import java.util.ArrayList;
import java.util.Stack;

public class TaskLogic {
	
	private static CommandParser parser;
	private static Storage store;
	private ArrayList<String> taskList;
	private Command LastCommand;
	private Stack<Command> commandStack;
	
	private String ERROR_KEYWORD = "Keyword not recognized!";
	private String ERROR_UNDO = "You have no cammand to undo!";
	
	public TaskLogic(){
		
		parser = new CommandParser();
		store = new Storage();
		
		taskList = new ArrayList<>();
		//taskList = store.start(filename);
		
		commandStack = new Stack<>();
	}
	
	//According to the keyword, execute the appropiate command
	public String executeCommand(String userCommand){
		
		Command command = new Command();
		
		//send command to parser
		command = parser.parse(userCommand);
		commandStack.push(command);

		
		switch (command.getKeyword()) {
		case "add":
			return addTask(command);

		case "delete":
			return deleteTask(command.getEvent());
			
		case "update":
			return updateTask(command);

		case "undo":{
			if(commandStack.size()==1){
				commandStack.pop();
				return ERROR_UNDO;
			}else{
				commandStack.pop();
				return undoTask(commandStack.pop());
			}
		}
			
		default:
			return ERROR_KEYWORD;
		}
	}

	private String undoTask(Command pop) {
		// TODO Auto-generated method stub
		return null;
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
