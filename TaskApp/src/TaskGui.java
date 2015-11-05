package src;

//test
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.*;

import com.sun.jmx.snmp.tasks.Task;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.TaskLogic;

public class TaskGui extends Application{

	private static final String ROOT_LAYOUT_FXML = "/layout/RootLayout.fxml";
	private static final String WINDOW_TITLE = "Task Manager";
	private static final String CONTAINER_LAYOUT_FXML = "/layout/Container.fxml";
	private AnchorPane root;
	private GridPane container;
	private Stage primaryStage;
	private ListViewController listViewController;
	private UpdateTableController updateTableController;
	private TodoTableController todoTableController;
	private TodoController todoController;
	private OverDueController overDueController;
	private SearchController searchController;
	private OverdueTableController overdueTableController;
	//private CommandBarController commandBarController;
	
	//private TaskLogic taskLogic;
	private Logic logic;
	
	//private ArrayList<Message> list = new ArrayList<Message>();

	//private Message msg = new Message(12, "hello", "Sat");
	ObservableList<Message> data, data1, data2;
	
	
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		initialRootLayout();
		initialPrimaryStage(primaryStage);
		initialLogic();
		addCommandBar(this);
		addListView();
		//addTableView();
		//addTableView1();
		//addTableView2();
	}
	
	private void addTableView2() {
		// TODO Auto-generated method stub
		data2 = getInitialTableData2();
		overdueTableController = new OverdueTableController(data2);
		container.add(overdueTableController, 1, 1);
	}

	private ObservableList<Message> getInitialTableData2() {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		
		list.add(new Message(1, "drink", "Sat"));
		list.add(new Message(1, "drink", "Sat"));
		
		//ObservableList<Message> data = FXCollections.observableArrayList(list);
		data = FXCollections.observableArrayList(list);
		
		return data;
	}

	private void addTableView1() {
		// TODO Auto-generated method stub
		data1 = getInitialTableData1();
		
		todoTableController = new TodoTableController(data1);
		container.add(todoTableController, 0, 1);
	}

	private ObservableList<Message> getInitialTableData1() {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		
		list.add(new Message(1, "sleep", "Sat"));
		list.add(new Message(1, "sleep", "Sat"));
		
		//ObservableList<Message> data = FXCollections.observableArrayList(list);
		data = FXCollections.observableArrayList(list);
		
		return data;
	}

	private ObservableList<Message> getInitialTableData() {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		
		list.add(new Message(1, "homework", "Sat"));
		list.add(new Message(1, "homework", "Sat"));
		
		//ObservableList<Message> data = FXCollections.observableArrayList(list);
		data = FXCollections.observableArrayList(list);
		
		return data;
	}
	
	/*private void addTableView() {
		// TODO Auto-generated method stub
		
		data = getInitialTableData();
		updateTableController = new UpdateTableController(data); 
		//listViewController.getChildren().add(updateTableController);
		//root.setCenter(updateTableController);
		container.add(updateTableController, 1, 0);
	}*/

	private void initialLogic() {
		// TODO Auto-generated method stub
		//taskLogic = new TaskLogic();
		logic = new Logic();
	}

	private void addListView() {
		// TODO Auto-generated method stub
		//root.setLeft(new ListViewController(userInput));
		listViewController = new ListViewController();
		root.getChildren().add(listViewController);
		AnchorPane.setTopAnchor(listViewController, 10.0);
		AnchorPane.setLeftAnchor(listViewController, 15.0);
		AnchorPane.setBottomAnchor(listViewController, 80.0);
		
		//container.add(listViewController, 0, 0);
		//root.setTop(listViewController);
	}

	private void addCommandBar(TaskGui taskGui) {
		// TODO Auto-generated method stub
		/*root.setBottom(new CommandBarController(taskGui));
		
		FXMLLoader contain = new FXMLLoader(getClass().getResource(CONTAINER_LAYOUT_FXML));
		try {
			container = contain.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        root.setCenter(container);*/
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
		//FXMLLoader contain = new FXMLLoader(getClass().getResource(CONTAINER_LAYOUT_FXML));
		try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	public void handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput) {
		
		if (key == KeyCode.ENTER) {
			System.out.println(userInput);			
			handleEnterPress(commandBarController, userInput);
		}
	}

	private void handleEnterPress(CommandBarController commandBarController, String userInput) {
		// TODO Auto-generated method stub
		//addListView(userInput);
		//listViewController.addItemToListView(taskLogic.executeCommand(userInput));
		logic.executeCommand(userInput);
		listViewController.addArrayList(logic.getConsole());
		System.out.println("SIZE: "+logic.getToDoTask().size());
		addTodoTableView(logic.getToDoTask());
		addOverdueTableView(logic.getDiscard());
		addSearchTableView(logic.getSearch());
		commandBarController.clear();
	}

	private void addSearchTableView(ArrayList<Tasks> search) {
		// TODO Auto-generated method stub
		data2 = getInitialTableData3(search);
		searchController = new SearchController(data2);
		root.getChildren().add(searchController);
		AnchorPane.setTopAnchor(searchController, 10.0);
		AnchorPane.setLeftAnchor(searchController, 950.0);
		AnchorPane.setBottomAnchor(searchController, 80.0);
		
	}

	private void addOverdueTableView(ArrayList<Tasks> discard) {
		// TODO Auto-generated method stub
		data1 = getInitialTableData3(discard);
		overDueController = new OverDueController(data1);
		root.getChildren().add(overDueController);
		AnchorPane.setTopAnchor(overDueController, 10.0);
		AnchorPane.setLeftAnchor(overDueController, 680.0);
		AnchorPane.setBottomAnchor(overDueController, 80.0);
		
	}

	private void addTodoTableView(ArrayList<Tasks> toDoTask) {
		// TODO Auto-generated method stub
		data = getInitialTableData3(toDoTask);
		//todoTableController = new TodoTableController(data);
		todoController = new TodoController(data);
		
		/*root.getChildren().add(todoTableController);
		AnchorPane.setTopAnchor(todoTableController, 25.0);
		AnchorPane.setLeftAnchor(todoTableController, 410.0);
		AnchorPane.setBottomAnchor(todoTableController, 80.0);*/
		root.getChildren().add(todoController);
		AnchorPane.setTopAnchor(todoController, 10.0);
		AnchorPane.setLeftAnchor(todoController, 410.0);
		AnchorPane.setBottomAnchor(todoController, 80.0);
	}
	
	private ObservableList<Message> getInitialTableData3(ArrayList<Tasks> listTask) {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		System.out.println("!@#$%^& "+ listTask.size());
		for(int i=0; i<listTask.size(); i++){
			System.out.println(listTask.get(i).getDate().toString() + "	@@@@@");
			list.add(new Message(listTask.get(i).getIndex(), listTask.get(i).getEvent(),
					listTask.get(i).getDate().toString()));
		}
		data = FXCollections.observableArrayList(list);
		return data;
	}

	public static void main(String[] args) {
		try{
			launch(args);
		}catch(Throwable e){
			System.out.println(e.getMessage());
		}
	}
}
