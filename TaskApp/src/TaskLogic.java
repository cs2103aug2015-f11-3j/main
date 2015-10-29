package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskLogic {
	
	private static CommandParser parser;
	private static Storage store;
	
	public ArrayList<String> taskList;
	
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
		deletedStack = new Stack<>();
	}
	
	
	public String executeCommand(String userCommand){		
		log.entering(getClass().getName(), "executeCommand with"+userCommand);
		
		Command command;
		returnList = new ArrayList<>();
		
		//send command to parser
		command = parser.parse(userCommand);
		commandStack.push(command);
		
		store.updateToFile(file, taskList);//add by ZHOU
		
		switch(command.getCommandType()){
			case ADD:{
				returnList.add(addTask(command.getTask()+" "+command.getDates()));
				return addTask(command.getTask()+" "+command.getDates());//add by ZHOU
				//break;
			}
			
			case DELETE:{
				returnList.add(deleteTask(command.getTask()));
				return deleteTask(command.getTask());//add by ZHOU
				//break;
			}
			
			case UPDATE:{
				returnList.add(updateTask(command));
				return updateTask(command);//add by ZHOU
				//break;
			}
			
			case SEARCH:{
				returnList = new ArrayList<>(searchForTask(command));
				
				if (returnList.size()>0){
					returnList.set(0, "There are "+returnList.size()+" options found!");
				}else {
					returnList.add("There are no options found!");
				}
				return "NULL";//add by ZHOU
				//break;
			}
			
			case UNDO:{
				if(commandStack.size()==1){
					commandStack.pop();
					returnList.add(ERROR_UNDO);
					return ERROR_UNDO;// add by ZHOU
					//break;
				}else{
					commandStack.pop();
					returnList.add(undoTask(commandStack.pop()));
					return undoTask(commandStack.pop());// add by ZHOU
					//break;

				}
			}
			
			case EXIT:{
				returnList.add("Still not implemented");
				return "till not implemented";//add by ZHOU
				//break;
			}
			
			default:{
				log.log(Level.INFO, "Entered command: "+command.getTask());
				commandStack.pop();
				returnList.add(ERROR_KEYWORD);
				return ERROR_KEYWORD;
			}
		}
				
		//store.updateToFile(file, taskList);
		//return returnList.get(0);
	}
	


	private ArrayList<String> searchForTask(Command command) {
		
		ArrayList<String> pass = new ArrayList<>();
		
		for (String curTask : taskList){
			
			if (curTask.contains(command.getTask())){
				pass.add(curTask);
			}
		}
			
		return pass;
	}

	
	
	private String undoTask(Command pop) {
		
		String returnText;
		
		switch(pop.getCommandType()){
		
		case ADD:{
			returnText = "Undo ADD command";
			deleteTask(pop.getTask());
			break;
		}
		
		case DELETE:{
			returnText = "Undo DELETE command";
			addTask(deleteTask(deletedStack.pop()));
			break;
		}
		
		case UPDATE:{
			returnText = "Undo UPDATE command";
			updateTask(pop);
			deletedStack.pop();
			break;
		}
		default:{
			returnText = "Cannot undo this command";
			break;
		}
		
		}
		
		return returnText;
	}
	

	//Updates a task that contains a similar task event with the commands event and date
	private String updateTask(Command command) {
	
		int i = searchFor(command.getTask());
		
		if (i != taskList.size()) {
			deletedStack.push(taskList.get(i));
			taskList.set(i, command.getTask()+" "+command.getDates());
			
			return "Task "+command.getTask()+" has been modified!";
		}else{
			return "Task "+command.getTask()+" hasn't been found in your Task List!";
		}
	}

	//Adds a task in the task list
	private String addTask(String command) {
		taskList.add(command);
		System.out.println(command);
		
		return "Added task "+command;
	}

	//Deletes the first task that contains a specific string
	private String deleteTask(String string) {
				
		int i = searchFor(string);
		
		if(i != taskList.size()){
			String removed = taskList.get(i);
			deletedStack.push(removed);
			
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
