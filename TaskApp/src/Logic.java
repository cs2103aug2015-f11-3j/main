package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {
	
	private static CommandParser parser;
	private static Storage store;

	public ArrayList<Tasks> taskList;
	private ArrayList<Tasks> oldTaskList;
	public ArrayList<String> taskListString;
	private ArrayList<String> returnList;
	private ArrayList<String> consoleList;
	private ArrayList<Tasks> searchList;
	
	private Stack<Command> commandStack;
	private Stack<Tasks> deletedStack;
	private Stack<Command> searchStack;
	
	private File directoryFile;
	private File taskFile;
	
	private String ERROR_KEYWORD = "Keyword not recognized!";
	private String ERROR_UNDO = "You have no cammand to undo!";
	
	private final static Logger log = Logger.getLogger(Logic.class.getName());
	
	public Logic(){
		
		parser = new CommandParser();
		store = new Storage();
		
		directoryFile = store.prepareFile("directory.txt");
		taskFile = store.prepareFile("savefile.txt");	
		
		taskList = new ArrayList<>();
		oldTaskList = new ArrayList<>();
		taskListString = new ArrayList<>();
		
		consoleList = new ArrayList<>();
		searchList = new ArrayList<>();
		
		commandStack = new Stack<>();
		deletedStack = new Stack<>();
		searchStack = new Stack<>();
	}
	
	public void executeCommand(String userCommand){
		log.entering(getClass().getName(), "executeCommand with "+userCommand);
		
		prepareSystem();
		
		Command command;
		returnList = new ArrayList<>();
		
		command =  parser.parse(userCommand);
		commandStack.push(command);
		
		switch (command.getCommandType()) {
		case ADD:
			addTask(command);
			taskListToString();
			break;
			
		case DELETE:
			deleteTask(command);
			taskListToString();
			break;
			
		case DELETEI:
			deleteIndex(command);
			taskListToString();
			break;
			
		case SEARCH:
			searchThroughTasks(command);
			searchStack.push(command);
			break;
		
		case UPDATE:
			updateTask(command);
			taskListToString();
			break;
		
		case UPDATES:
			updateStatus(command);
			break;
			
		case READ:
			searchForDate(command);
			break;
			
		case UNDO:
			commandStack.pop();
			undoTask(commandStack.pop());
			taskListToString();
			break;
			
		case FILE:
			String originalLocation = taskFile.getAbsolutePath();
			taskFile = movedFile(taskFile, command.getTask());
			store.updateToFile(directoryFile, taskFile.getAbsolutePath());
			returnList.add("file is moved from "+ originalLocation +" to "+command.getTask());
			break;		

		default:
			log.log(Level.INFO, "Entered command: "+command.getTask());
			commandStack.pop();
			returnList.add(ERROR_KEYWORD);
			break;
		}
		
		store.updateToFile(taskFile, taskListString);
	}

	private void undoTask(Command pop) {
		
	}

	private void searchForDate(Command command) {
		searchList.clear();
		for (Tasks curTask : taskList){
			if(curTask.getDate().equals(command.getDates())){
				searchList.add(curTask);
			}
		}
	}

//	private void undoTask(Command command) {
//		
//		switch (command.getCommandType()) {
//		case ADD:
//			consoleList.add("Undo ADD command");
//			
//			deleteTask(command);
//			break;
//			
//		case DELETE:
//			consoleList.add("Undo DELETE command");
//			
//			command.setDates(deletedStack.peek().getDate());
//			command.setTask(deletedStack.pop().getEvent());
//			addTask(command);
//			break;
//			
//		case DELETEI:
//			consoleList.add("Undo DELETE command");
//			
//			command.setDates(deletedStack.peek().getDate());
//			command.setTask(deletedStack.pop().getEvent());
//			addTask(command);
//			break;
//			
//		case UPDATE:
//			consoleList.add("Undo UPDATE command");
//			
//			int i = searchFor(command.getTask());
//			taskList.get(i).setDate(deletedStack.pop().getDate());
//			break;
//		
//		case UPDATES:
//			consoleList.add("Undo DELETE STATUS command");
//			
//			updateStatus(command);
//			break;
//
//		default:
//			consoleList.add("Cannot undo this command");
//			break;
//		}
//	}

	private void deleteIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
		
		delete(i);
	}

	private void updateStatus(Command command) {
		int i = searchFor(command.getTask());
		
		if (i != taskList.size()) {
			taskList.get(i).setStatus(!taskList.get(i).getStatus());
			
			if (taskList.get(i).getStatus()) {
				consoleList.add("Task "+taskList.get(i).toString()+
						" completed!");
			}else{
				consoleList.add("Task "+taskList.get(i).toString()+
						" set to incomplete");
			}
		} else {
			consoleList.add("Task "+command.getTask()+
					" hasn't been found");
		}
	}

	private void updateTask(Command command) {
		int i = searchFor(command.getTask());
		
		if (i != taskList.size()) {
			deletedStack.push(taskList.get(i));
			taskList.get(i).setDate(command.getDates());
			
			consoleList.add("Task "+taskList.get(i).getEvent()+
					" has been updated from "+
					deletedStack.peek().getDate()+
					" to "+command.getDates());
		} else{
			consoleList.add("Task "+command.getTask()+
					" hasn't been found");
		}
	}

	private void searchThroughTasks(Command command) {
		searchList.clear();
		for(Tasks curTask : taskList){
			
			if(curTask.getEvent().contains(command.getTask())){
				searchList.add(curTask);
			}
		}
	}

	private void deleteTask(Command command) {
		int i = searchFor(command.getTask());
		
		delete(i);
	}

	private void delete(int i) {
		if(i != taskList.size()){
			Tasks removed = taskList.get(i);
			deletedStack.push(removed);
			
			oldTaskList = taskList;
			
			taskList.remove(i);
			consoleList.add("Task *"+removed.toString()+" has been deleted!");
		}else{
			consoleList.add("Task hasn't been found!");
		}
	}

	private void addTask(Command command) {
		Tasks task = new Tasks(command.getTask(), command.getDates());
		
		oldTaskList = taskList;
		
		taskList.add(task);
		consoleList.add("Added task "+command.getTask()+" on "+command.getDates());
	}
	
	private int searchFor(String string){
		int i = 0;
		for(Tasks curTask : taskList){
			if(curTask.getEvent().contains(string)){
				break;
			}
			i++;
		}
		return i;
	}
	
	private void taskListToString(){
		taskListString.clear();
		
		for(Tasks curTask : taskList){
			taskListString.add(curTask.toString());
		}
	}
	
	private void prepareSystem() {
		if(store.isEmptyFile(directoryFile)) {
			store.updateToFile(directoryFile,taskFile.getAbsolutePath());
		}
		taskFile = new File(store.readLastLineFromFile(directoryFile));	
		taskListString = store.accessToFile(taskFile);
		
		taskList = new ArrayList<>(parser.parseArrlist(taskListString));
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
	
	public ArrayList<String> getConsole(){
		return consoleList;
	}
	
	public ArrayList<Tasks> getToDoTask(){
		Date today;
		ArrayList<Tasks> toDoList = new ArrayList<>();
		
		
		for(Tasks curTask : taskList){
			if(curTask.getDate().)
		}
	}
}
