package src;

//test
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TaskGui extends Application{

	private static final String ROOT_LAYOUT_FXML = "/layout/RootLayout.fxml";
	private static final String WINDOW_TITLE = "Task Manager";
	private AnchorPane root;
	private Stage primaryStage;
	private ListViewController listViewController;
	private TodoController todoController;
	private OverDueController overDueController;
	private SearchController searchController;
	private Logic logic;
	
	ObservableList<Message> data, data1, data2, data3;
	ArrayList<Boolean> status = new ArrayList<Boolean>();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initialRootLayout();
		initialPrimaryStage(primaryStage);
		initialLogic();
		addCommandBar(this);
		addListView();
		//addTodo();
		//addOverdue();
		//addSearch();
		addTodoTableView(logic.getToDoTask());
		addOverdueTableView(logic.getDiscard());
		addSearchTableView(logic.getSearch());
	}

	private void initialLogic() {
		// TODO Auto-generated method stub
		//taskLogic = new TaskLogic();
		logic = Logic.getInstance();
	}

	private void addListView() {
		// TODO Auto-generated method stub
		//root.setLeft(new ListViewController(userInput));
		listViewController = new ListViewController();
		root.getChildren().add(listViewController);
		AnchorPane.setTopAnchor(listViewController, 10.0);
		AnchorPane.setLeftAnchor(listViewController, 15.0);
		AnchorPane.setBottomAnchor(listViewController, 300.0);
	}

	private void addCommandBar(TaskGui taskGui) {
		
		CommandBarController commandBarController = new CommandBarController(taskGui);
		root.getChildren().add(commandBarController);
		AnchorPane.setBottomAnchor(commandBarController, 10.0);
		AnchorPane.setLeftAnchor(commandBarController, 15.0);
		AnchorPane.setRightAnchor(commandBarController, 15.0);
		
	}

	private void initialPrimaryStage(Stage primaryStage) {
		// TODO Auto-generated method stub
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle(WINDOW_TITLE);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.show();
	}

	private void initialRootLayout() {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader(getClass().getResource(ROOT_LAYOUT_FXML));
		try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput) {
		
		if (key == KeyCode.ENTER) {
			
			handleEnterPress(commandBarController, userInput);
		}
	}

	private void handleEnterPress(CommandBarController commandBarController, String userInput) {
		
		logic.executeCommand(userInput);
		listViewController.addArrayList(logic.getConsole());
		//System.out.println("SIZE: "+logic.getToDoTask().size());
		addTodoTableView(logic.getToDoTask());
		addOverdueTableView(logic.getDiscard());
		addSearchTableView(logic.getSearch());
		commandBarController.clear();
	}
	

	private void addSearchTableView(ArrayList<Tasks> search) {
		// TODO Auto-generated method stub
		System.out.println("SEARCH:  ");
		data2 = getInitialTableData3(search);
		searchController = new SearchController(data2);
		root.getChildren().add(searchController);
		AnchorPane.setTopAnchor(searchController, 10.0);
		AnchorPane.setRightAnchor(searchController, 15.0);
		AnchorPane.setBottomAnchor(searchController, 90.0);
		
	}
	
	private void addOverdueTableView(ArrayList<Tasks> discard) {
		// TODO Auto-generated method stub
		System.out.println("OVERDUE:  ");
		data1 = getInitialTableData3(discard);
		overDueController = new OverDueController(data1);
		root.getChildren().add(overDueController);
		AnchorPane.setTopAnchor(overDueController, 10.0);
		AnchorPane.setLeftAnchor(overDueController, 680.0);
		AnchorPane.setBottomAnchor(overDueController, 90.0);
	}
	
	private void addTodoTableView(ArrayList<Tasks> toDoTask) {
		// TODO Auto-generated method stub
		System.out.println("TODO:  ");
		data = getInitialTableData3(toDoTask);
		//todoTableController = new TodoTableController(data);
		todoController = new TodoController(data);
		
		/*root.getChildren().add(todoTableController);
		AnchorPane.setTopAnchor(todoTableController, 25.0);
		AnchorPane.setLeftAnchor(todoTableController, 410.0);
		AnchorPane.setBottomAnchor(todoTableController, 80.0);*/
		root.getChildren().add(todoController);
		AnchorPane.setTopAnchor(todoController, 10.0);
		AnchorPane.setLeftAnchor(todoController, 390.0);
		AnchorPane.setBottomAnchor(todoController, 90.0);
	}
	
	private ObservableList<Message> getInitialTableData3(ArrayList<Tasks> listTask) {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		
		for(int i=0; i<listTask.size(); i++){
			/*list.add(new Message(listTask.get(i).getIndex(), listTask.get(i).getEvent(),
					listTask.get(i).getDate().toString(), listTask.get(i).getStatus()));*/
			System.out.println(listTask.get(i).getDate().size()+ "####");
			if(listTask.get(i).getDate().size() == 0){
				list.add(new Message(listTask.get(i).getIndex(), listTask.get(i).getEvent(),
						"Not set yet", listTask.get(i).getStatus()));
			}
			if(listTask.get(i).getDate().size() == 1){
				list.add(new Message(listTask.get(i).getIndex(), listTask.get(i).getEvent(),
						ConvertDateFormat(listTask.get(i).getDate().get(0).toString()), listTask.get(i).getStatus()));
			}
			if(listTask.get(i).getDate().size() == 2)
			{
				String dateDisplay = ConvertDateFormat(listTask.get(i).getDate().get(0).toString()) + " - "
						+ConvertDateFormat(listTask.get(i).getDate().get(1).toString());

				list.add(new Message(listTask.get(i).getIndex(), listTask.get(i).getEvent(),
						dateDisplay, listTask.get(i).getStatus()));
			}
		}
		data = FXCollections.observableArrayList(list);
		return data;
	}

	private String ConvertDateFormat(String string) {
		
		DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		Date date;

		try {
			date = (Date)formatter.parse(string);				
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			String formatedDate = cal.get(Calendar.DATE) + "/" + (cal.get(Calendar.MONTH) + 1) + 
					"/" +         cal.get(Calendar.YEAR);
			return formatedDate;
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}

	public static void main(String[] args) {
		try{
			launch(args);
		}catch(Throwable e){
			System.out.println(e.getMessage());
		}
	}
}
