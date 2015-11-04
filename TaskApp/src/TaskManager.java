package src;
//kjaskja
public class TaskManager {
//test
	public static void main(String[] arg) {
		
		CommandParser cp = new CommandParser();
		System.out.println(cp.isNumeric("700"));
		String str ="add dojhshdhj every mon until 12 dec 2015";
		Command cmd = cp.parse(str);
		System.out.println(cmd.getCommandType());
		System.out.println(cmd.getTask());
		System.out.println(cmd.getDates().toString());
		System.out.println(cmd.getKey());
		
	}
}
