package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TaskLogic {
	
	private static CommandParser parser;
	private static Storage store;
	
	public ArrayList<Tasks> taskList;
	
	private ArrayList<String> returnList;
	private Stack<Command> commandStack;
	private Stack<String> deletedStack;
	private File directoryFile;
	private File taskFile;
	
	private String ERROR_KEYWORD = "Keyword not recognized!";
	private String ERROR_UNDO = "You have no cammand to undo!";
	
	private final static Logger log = Logger.getLogger(TaskLogic.class.getName());
	
	public TaskLogic(){
		
		parser = new CommandParser();
		store = new Storage();
		
		directoryFile = store.prepareFile("directory.txt");
		taskFile = store.prepareFile("savefile.txt");	
		taskList = new ArrayList<>();
		
		commandStack = new Stack<>();	
		deletedStack = new Stack<>();
	}
	
	
	public String executeCommand(String userCommand){	
		
		prepareSystem();
		log.entering(getClass().getName(), "executeCommand with"+userCommand);
		Command command;
		returnList = new ArrayList<>();
		
		//send command to parser
		command = parser.parse(userCommand);
		commandStack.push(command);
		
		switch(command.getCommandType()){
			case ADD:{
				returnList.add(addTask(command.getTask()+" "+command.getDates()));
				break;
			}
			
			case DELETE:{
				returnList.add(deleteTask(command.getTask()));
				break;
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
				//return "NULL";//add by ZHOU
				break;
			}
			
			case UNDO:{
				if(commandStack.size()==1){
					commandStack.pop();
					returnList.add(ERROR_UNDO);
					break;
				}else{
					commandStack.pop();
					returnList.add(undoTask(commandStack.pop()));
					break;

				}
			}
			
			case FILE:{
				String originalLocation = taskFile.getAbsolutePath();
				taskFile = movedFile(taskFile, command.getTask());
				store.updateToFile(directoryFile, taskFile.getAbsolutePath());
				returnList.add("file is moved from "+ originalLocation +" to "+command.getTask());
				break;
		
			}
			
			case EXIT:{
				System.exit(0);
				break;
			}
			
			default:{
				log.log(Level.INFO, "Entered command: "+command.getTask());
				commandStack.pop();
				returnList.add(ERROR_KEYWORD);
			}
		}
				
//		store.updateToFile(taskFile, taskList);
		return returnList.get(0);
	}
	
	private void prepareSystem() {
		if(store.isEmptyFile(directoryFile)) {
			store.updateToFile(directoryFile,taskFile.getAbsolutePath());
		}
		taskFile = new File(store.readLastLineFromFile(directoryFile));	
		taskList = store.accessToFile(taskFile);
	}


	private File movedFile(File oldFile, String directory) {
		Path movefrom = FileSystems.getDefault().getPath(oldFile.getAbsolutePath());
        Path target = FileSystems.getDefault().getPath(directory);
        try {
            Files.move(movefrom, target, StandardCopyOption.REPLACE_EXISTING);
            File file = new File(target.toString());
            return file;
        } catch (IOException e) {
            System.err.println(e);
        }
        return null;
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
			//updateTask(pop);
			
			int i = searchFor(pop.getTask());
			taskList.set(i, deletedStack.pop());
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
