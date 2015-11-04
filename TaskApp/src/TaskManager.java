package src;
//kjaskja
public class TaskManager {
//test
	public static void main(String[] arg) {
		CommandParser cp = new CommandParser();
		String str ="add dojhshdhj 11/12/2015";
		Command cmd = cp.parse(str);
		System.out.println(cmd.getCommandType());
		System.out.println(cmd.getTask());
		System.out.println(cmd.getDates().toString());
		
	}
}
