
public class TaskLogic {
	
	CommandParser parse;
	Storage store;
	
	public TaskLogic(){
		parse = new CommandParser();
		store = new Storage();
	}
	
	public String executeCommand(String userCommand){
		//send command to parser
		
		return "Welcome!";
	}
	
}
