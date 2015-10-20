package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskLogic {
	
	private static CommandParser parser;
	private static Storage store;
	
	private ArrayList<String> taskList;
	private ArrayList<String> returnList;
	private Command LastCommand;
	private Stack<Command> commandStack;
	private Stack<String> deletedStack;
	private File file;
	
	private String ERROR_KEYWORD = "Keyword not recognized!";
	private String ERROR_UNDO = "You have no cammand to undo!";
	private final static Logger log = Logger.getLogger(TaskLogic.class.getName());
	
	public TaskLogic(){
		
		parser = new CommandParser();
		store = new Storage();
		
		file = new File("saveFile");
		
		taskList = new ArrayList<>();
		taskList = store.accessToFile(file);
		
		commandStack = new Stack<>();	
	}
	
	//According to the keyword, execute the appropiate command
	public String executeCommand(String userCommand){
		
		log.entering(getClass().getName(), "executeCommand with"+userCommand);
		
		Command command;
		returnList = new ArrayList<>();
		
		//send command to parser
		command = parser.parse(userCommand);
		commandStack.push(command);
		
		switch (command.getCommandType()) {
		case ADD:
			return addTask(command);

		case DELETE:
			return deleteTask(command.getTask());
			
		case UPDATE:
			return updateTask(command);
		
		case SEARCH:
			return searchForTask(command);

		case UNDO:{
			if(commandStack.size()==1){
				commandStack.pop();
				return ERROR_UNDO;
			}else{
				commandStack.pop();
				return undoTask(commandStack.pop());
			}
		}
			
		default:
			log.log(Level.INFO, "Entered command: "+command.getTask());
			return ERROR_KEYWORD;
		}
	}

	private String searchForTask(Command command) {
		// TODO Auto-generated method stub
		return null;
	}

	private String undoTask(Command pop) {
		
		String returnText;
		
		switch(pop.getCommandType()){
		
		case ADD:{
			returnText = "Undo ADD command";
			deleteTask(pop.getTask());
		}
		
		case DELETE:{
			returnText = "Undo DELETE command";
			
		}
			
		}

		return null;
	}
	

	//Updates a task that contains a similar task event with the commands event and date
	private String updateTask(Command command) {
	
		int i = searchFor(command.getTask());
		
		if (i != taskList.size()) {
			taskList.set(i, command.getTask()+" "+command.getDates());
			store.updateTask(i, taskList.get(i));
			return "Task "+command.getTask()+" has been modified!";
		}else{
			return "Task "+command.getTask()+" hasn't been found in your Task List!";
		}
	}

	//Adds a task in the task list
	private String addTask(Command command) {
		taskList.add(command.getTask()+" "+command.getDates());
		store.addToFile(taskList.get(taskList.size()-1));
		System.out.println(command.getDates());
		
		return "Added task "+command.getTask()+" for "+command.getDates().toString();
	}

	//Deletes the first task that contains a specific string
	private String deleteTask(String string) {
				
		int i = searchFor(string);
		
		if(i != taskList.size()){
			String removed = taskList.get(i);
			store.deleteFromFile(i);
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
