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


/**
 * This class is the main logic class of the Software Engineering class CS2104 taken at NUS.
 * My team name is F11-3J.
 * @author A0145617A
 * @version 0.4
 *
 */
public class Logic {
	
	private static final String INDEX_OUT_OF_BOUNDS = "Index you searched for is out of bounds";
	private static final String UNDO_COMMAND_PERFORMED = "UNDO command performed";
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
	private int index = 0;
	
	private final static Logger log = Logger.getLogger(Logic.class.getName());
	
	/**
	 * This method is the constructor of the Logic class.
	 */
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
		
		prepareSystem();
	}
	
	/**
	 * Execute command method is used to modify the content of the application by the command of the user.
	 * @param userCommand The String input of the user
	 */
	public void executeCommand(String userCommand){
		log.entering(getClass().getName(), "executeCommand with "+userCommand);
		
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
			updateIndex(command);
			taskListToString();
			break;

		case STATUS:
			updateStatus(command);
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

		case EXIT:
			System.exit(1);
			break;
			
		default:
			log.log(Level.INFO, "Entered command: "+command.getTask());
			consoleList.add(ERROR_KEYWORD);
			break;
		}
		
		store.updateToFile(taskFile, taskListString);
	}

	//Undo the latest change to the task manager
	private void undoTask() {
		if (!oldTaskList.isEmpty()) {
			
			taskList = new ArrayList<>(oldTaskList);
			oldTaskList.clear();
			
			consoleList.add(UNDO_COMMAND_PERFORMED);	
		} else {
			consoleList.add(ERROR_UNDO);
		}
		
	}

	//Searches for all the task in a specific dare and stores them in an ArrayList
	@SuppressWarnings("deprecation")
	private void searchForDate(Command command) {
		searchList.clear();
		
		consoleList.add("Search for date: " + command.getDates());
		for (Tasks curTask : taskList){
			if(curTask.getDate().get(curTask.getDate().size()-1).getDate()
					==(command.getDates().get(command.getDates().size()-1).getDate())&&
					curTask.getDate().get(curTask.getDate().size()-1).getMonth()
					==(command.getDates().get(command.getDates().size()-1).getMonth())&&
					curTask.getDate().get(curTask.getDate().size()-1).getYear()
					==(command.getDates().get(command.getDates().size()-1).getYear())){
				
				searchList.add(curTask);
			}
		}
	}

	//Delete a task by using it's index
	private void deleteIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
		
		if(i<=index&&i>0){
			for(int j = 0; j<taskList.size();j++){
				if(taskList.get(j).getIndex()==i){
					oldTaskList = new ArrayList<>(taskList);
					System.out.println(oldTaskList);

					taskList.remove(j);
					System.out.println(oldTaskList);

					consoleList.add("Deleted task with index:"+i);					
				}
			}
		}else{
			consoleList.add(INDEX_OUT_OF_BOUNDS);
		}
	}
	
	//Updates the date after searching for it by its index
	private void updateIndex(Command command) {
		int i = Integer.parseInt(command.getTask());
					
		if (i<=index&&i>0) {
			for(int j = 0; j<taskList.size(); j++){
				if(taskList.get(j).getIndex()==i){
					oldTaskList = new ArrayList<>(taskList);
//					System.out.println(oldTaskList);
						
					taskList.get(j).setDate(command.getDates());
					
//					System.out.println(oldTaskList);
//					System.out.println(taskList);
					consoleList.add("Task with index:"+i+" has had it's date updated");
				}
			}
		} else {
			consoleList.add(INDEX_OUT_OF_BOUNDS);
		}
	}
	
	//Updates the status after searching for it by the index
	private void updateStatus(Command command) {
		int i = Integer.parseInt(command.getTask());
		
		if(i<=index&&i>0){
			
			for(int j=0;j<taskList.size();j++){
				if(taskList.get(j).getIndex()==i){
					
					boolean status = taskList.get(j).getStatus();
					
					taskList.get(j).setStatus(!status);
					consoleList.add("Task with index:"+i+" has had it's status updated");
				}
			}
		} else {
			consoleList.add(INDEX_OUT_OF_BOUNDS);
		}
	}
	
	//Searches through the task for a certain substring and adds them to an ArrayList
	private void searchThroughTasks(Command command) {
		searchList.clear();
		
		consoleList.add("Searched for: " + command.getTask());
		for(Tasks curTask : taskList){
			
			if(curTask.getEvent().contains(command.getTask())){
				searchList.add(curTask);
			}
		}
	}

	//Delete a certain task after it's event.
	private void deleteTask(Command command) {		
		ArrayList<Tasks> removeList = new ArrayList<>();
		
		for(int j=0;j<taskList.size();j++){
			if(taskList.get(j).getEvent().contains(command.getTask())){
				removeList.add(taskList.get(j));
			}
		}
		
		if (removeList.isEmpty()) {
			consoleList.add("No task has been found with the event: "+command.getTask());
		} else {
			oldTaskList = new ArrayList<>(taskList);
			taskList.removeAll(removeList);

			consoleList.add(removeList.size()+ " tasks have been deleted from your file");
		}
		
	}

	//Add a task to the task ArrayList
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
				
				oldTaskList = new ArrayList<>(taskList);
				
				taskList.add(task);
			}
		}else{
			Tasks task = new Tasks(command.getTask(), command.getDates());
			task.setIndex(index++);
			
			oldTaskList = new ArrayList<>(taskList);
			
			taskList.add(task);
		}
		
		consoleList.add("Added task "+command.getTask()+" on "+command.getDates());
	}
	
	//Transforms the Tasks into Strings
	private void taskListToString(){
		taskListString.clear();
		
		for(Tasks curTask : taskList){
			if(!curTask.getStatus()){
				taskListString.add(curTask.toString());
			}
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
	
	//Sets the index at the beginning of the application
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
	
	/**
	 * This method returns the content of the console.
	 * @return ArrayList of the console results.
	 */
	public ArrayList<String> getConsole(){
		return consoleList;
	}
	
	/**
	 * This method returns the tasks that are still in progres and at a later dare
	 * @return ArrayList containing the tasks that are not completed and still on time.
	 */
	public ArrayList<Tasks> getToDoTask(){
		Date today = new Date();
	
		ArrayList<Tasks> toDoList = new ArrayList<>();
		
		for(Tasks curTask : taskList){
			if(!curTask.getStatus()){
				if(!curTask.getDate().isEmpty()){
					if(curTask.getDate().get(curTask.getDate().size()-1).after(today)){
						toDoList.add(curTask);
					}else{
						if((curTask.getDate().get(curTask.getDate().size()-1).equals(today))){
								toDoList.add(curTask);
						}
					}
				}else {
					toDoList.add(curTask);
				}
			}
		}
		
		return toDoList;	
	}
	
	/**
	 * This method returns the tasks that are completed or overdue
	 * @return ArrayList that contains the tasks that are either completed or overdue
	 */
	public ArrayList<Tasks> getDiscard() {
		Date today = new Date();
		
		ArrayList<Tasks> discardList = new ArrayList<>();
		
		for(Tasks curTask : taskList){
			if(curTask.getStatus()){
				discardList.add(curTask);
			}else 
			{
				if(!curTask.getDate().isEmpty()){ 
					if(curTask.getDate().get(curTask.getDate().size()-1).before(today)){
						discardList.add(curTask);	
					}	
				}
			}
		}
		
		return discardList;
	}
	
	/**
	 * This method returns the ArrayList of the items found in the file that correspond to the search
	 * @return Array List that contains the search results tasks
	 */
	public ArrayList<Tasks> getSearch() {
		return searchList;
	}
}
