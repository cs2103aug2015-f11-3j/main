package src;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Logic {
	
	private static CommandParser parser;
	private static Storage store;

	public ArrayList<Tasks> taskList;
	private ArrayList<Tasks> oldTaskList;
	private ArrayList<Tasks> searchList;
	
	public ArrayList<String> taskListString;
	private ArrayList<String> consoleList;
	
	
	private File directoryFile;
	private File taskFile;
	
	private static final String ERROR_UNDO = "Cannot UNDO";
	private String ERROR_KEYWORD = "Keyword not recognized!";
	private String ERROR_INDEX = "Index not found";
	
	private int index = 0;
	
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
		
	}
	
	public void executeCommand(String userCommand){
		log.entering(getClass().getName(), "executeCommand with "+userCommand);
		prepareSystem();
		
		Command command;
		command =  parser.parse(userCommand);
		
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
			
		case UPDATE:
			updateTask(command);
			taskListToString();
			break;
		
		case UPDATES:
			updateStatus(command);
			break;
			
		case UPDATEI:
			updateIndex(command);
			taskListToString();
			break;
			
		case STATUSI:
			updateStatusFromIndex(command);
			taskListToString();
			break;
			
		case SEARCH:
			searchThroughTasks(command);
			break;	
			
		case READ:
			searchForDate(command);
			break;
			
		case UNDO:
			undoTask();
			taskListToString();
			break;
			
		case FILE:
			String originalLocation = taskFile.getAbsolutePath();
			taskFile = movedFile(taskFile, command.getTask());
			store.updateToFile(directoryFile, taskFile.getAbsolutePath());
			consoleList.add("file is moved from "+ originalLocation +" to "+command.getTask());
			break;		

		default:
			log.log(Level.INFO, "Entered command: "+command.getTask());
			consoleList.add(ERROR_KEYWORD);
			break;
		}
		
		store.updateToFile(taskFile, taskListString);
	}

	private void updateStatusFromIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
		boolean updated = false;

		for(int j=0;i<taskList.size();j++){
			if(taskList.get(j).getIndex()==i){
				taskList.get(j).setStatus(!taskList.get(j).getStatus());
				consoleList.add("Task with index:"+i+" has had it's status updated");
				
				updated = true;
			}
		}
		
		if (!updated) {
			consoleList.add(ERROR_INDEX);
		}
	}

	private void updateIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
		boolean updated = false;
		
		for(int j=0;i<taskList.size();j++){
			if(taskList.get(j).getIndex()==i){
				taskList.get(j).setDate(command.getDates());
				consoleList.add("Task with index:"+i+" has had it's date updated");
				
				updated = true;
			}
		}
		if (!updated) {
			consoleList.add(ERROR_INDEX);
		}
	}

	private void undoTask() {
		if (!oldTaskList.isEmpty()) {
			taskList = oldTaskList;
			oldTaskList.clear();
			
			consoleList.add("UNDO command performed");	
		} else {
			consoleList.add(ERROR_UNDO);
		}
		
	}

	private void searchForDate(Command command) {
		searchList.clear();
		for (Tasks curTask : taskList){
			if(curTask.getDate().equals(command.getDates())){
				searchList.add(curTask);
			}
		}
	}


	private void deleteIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
		
		for(Tasks curTask : taskList){
			if(curTask.getIndex()==i){
				taskList.remove(curTask);
				consoleList.add("Deleted task with index:"+i);
			}
		}
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
			ArrayList<Date> removed = taskList.get(i).getDate();
			taskList.get(i).setDate(command.getDates());
			
			consoleList.add("Task "+taskList.get(i).getEvent() +
					" has been updated from " +
					removed + " to "+command.getDates());
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
			
			oldTaskList = taskList;
			
			taskList.remove(i);
			consoleList.add("Task *"+removed.toString()+" has been deleted!");
		}else{
			consoleList.add("Task hasn't been found!");
		}
	}

	private void addTask(Command command) {
		int n = command.getKey();
		
		if(n>0){
			for(int i=0; i<command.getDates().size();i+=n){
				ArrayList<Date> limit = new ArrayList<>();
				
				if (n==2) {
					limit.add(command.getDates().get(i));
					limit.add(command.getDates().get(i+1));
				}else{
					limit.add(command.getDates().get(i));
				}
				
				Tasks task = new Tasks(command.getTask(), limit);
				task.setIndex(index++);
				
				oldTaskList = taskList;
				
				taskList.add(task);
			}
		}else{
			Tasks task = new Tasks(command.getTask(), command.getDates());
			task.setIndex(index++);
			
			oldTaskList = taskList;
			
			taskList.add(task);
		}
//		Tasks task = new Tasks(command.getTask(), command.getDates());
//		task.setIndex(index++);
//		
//		oldTaskList = taskList;
//		
//		taskList.add(task);
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
		
		taskList = new ArrayList<>(parser.parseArrList(taskListString));
		setIndex();
	}
	
	private void setIndex() {
		for(index = 1; index <= taskList.size();index++){
			taskList.get(index-1).setIndex(index);
		}
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
		Date today = new Date();
	
		ArrayList<Tasks> toDoList = new ArrayList<>();
		
		for(Tasks curTask : taskList){
			if(!curTask.getStatus()){
				if(!curTask.getDate().isEmpty()){
					if(curTask.getDate().get(curTask.getDate().size()-1).after(today)){
						toDoList.add(curTask);
					}
				}
			}
		}
		return toDoList;
		
	}
	
	public ArrayList<Tasks> getDiscard() {
		Date today = new Date();
		
		ArrayList<Tasks> discardList = new ArrayList<>();
		
		for(Tasks curTask : taskList){
			if(curTask.getStatus()){
				discardList.add(curTask);
			}else {
				if(curTask.getDate().isEmpty()){
					discardList.add(curTask);
				}else {
					if(curTask.getDate().get(curTask.getDate().size()-1).before(today)){
						discardList.add(curTask);
					}
				}
				
			}
		}
		return discardList;
	}
}

//private void undoTask(Command command) {
//
//switch (command.getCommandType()) {
//case ADD:
//	consoleList.add("Undo ADD command");
//	
//	deleteTask(command);
//	break;
//	
//case DELETE:
//	consoleList.add("Undo DELETE command");
//	
//	command.setDates(deletedStack.peek().getDate());
//	command.setTask(deletedStack.pop().getEvent());
//	addTask(command);
//	break;
//	
//case DELETEI:
//	consoleList.add("Undo DELETE command");
//	
//	command.setDates(deletedStack.peek().getDate());
//	command.setTask(deletedStack.pop().getEvent());
//	addTask(command);
//	break;
//	
//case UPDATE:
//	consoleList.add("Undo UPDATE command");
//	
//	int i = searchFor(command.getTask());
//	taskList.get(i).setDate(deletedStack.pop().getDate());
//	break;
//
//case UPDATES:
//	consoleList.add("Undo DELETE STATUS command");
//	
//	updateStatus(command);
//	break;
//
//default:
//	consoleList.add("Cannot undo this command");
//	break;
//}
//}

