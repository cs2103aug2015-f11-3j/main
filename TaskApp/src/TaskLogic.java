package src;


public class TaskLogic {
	
	CommandParser parser;
	Command com;
	Storage store;
	
	public TaskLogic(){
		parser = new CommandParser();
		store = new Storage();
	}
	
	public String executeCommand(String userCommand){
		
		com = new Command();
		
		//send command to parser
		com = parser.parse(userCommand);
		
		//case(com.getKeyword())
		
		//System.out.println(userCommand);
		
		return "Welcome!";
	}
	
}
