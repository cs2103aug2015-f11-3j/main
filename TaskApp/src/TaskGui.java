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
	private BorderPane root;
	private GridPane container;
	private Stage primaryStage;
	private ListViewController listViewController;
	private UpdateTableController updateTableController;
	private TodoTableController todoTableController;
	private OverdueTableController overdueTableController;
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
		addTableView();
		addTableView1();
		addTableView2();
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
	
	/*private ObservableList<Message> getInitialTableData3(ArrayList<Task> listTask) {
		// TODO Auto-generated method stub
		List<Message> list = new ArrayList<Message>();
		listTask = new ArrayList<Task>();
		Tasks task = new Tasks(null, null);
		//list.add(new Message(1, "homework", "Sat"));
		//list.add(new Message(1, "homework", "Sat"));
		
		for(int i=0; i<listTask.size(); i++){
			list.add(new Message());
		}
		//ObservableList<Message> data = FXCollections.observableArrayList(list);
		data = FXCollections.observableArrayList(list);
		
		return data;
	}*/
	
	private void addTableView() {
		// TODO Auto-generated method stub
		
		data = getInitialTableData();
		updateTableController = new UpdateTableController(data); 
		//listViewController.getChildren().add(updateTableController);
		//root.setCenter(updateTableController);
		container.add(updateTableController, 1, 0);
	}

	private void initialLogic() {
		// TODO Auto-generated method stub
		//taskLogic = new TaskLogic();
		logic = new Logic();
	}

	private void addListView() {
		// TODO Auto-generated method stub
		//root.setLeft(new ListViewController(userInput));
		listViewController = new ListViewController();
		container.add(listViewController, 0, 0);
		//root.setTop(listViewController);
	}

	private void addCommandBar(TaskGui taskGui) {
		// TODO Auto-generated method stub
		root.setBottom(new CommandBarController(taskGui));
		
		FXMLLoader contain = new FXMLLoader(getClass().getResource(CONTAINER_LAYOUT_FXML));
		try {
			container = contain.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        root.setCenter(container);
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
		//System.out.println("$$$$$$$$$$"+ logic.getConsole().get(0));
		//listViewController.addItemToListView(logic.getConsole().get(0));
		commandBarController.clear();
	}

	public static void main(String[] args) {
		try{
			launch(args);
		}catch(Throwable e){
			System.out.println(e.getMessage());
		}
	}
}









/*
public class TaskGui extends Application {
	
	TextField textfield;
	BorderPane rootlayout;
	Text actiontarget;
	
	ListView<String> console;
	
	TaskLogic tasklogic;
	
	private static Logger logger = Logger.getLogger("Foo");

	@Override
	public void start(Stage primaryStage) throws Exception {
<<<<<<< Updated upstream

		tasklogic = new TaskLogic();
=======
		// TODO Auto-generated method stub
>>>>>>> Stashed changes
		
		logger.log(Level.INFO, "going to start processing");
		
		tasklogic = new TaskLogic();
		primaryStage.setTitle("TaskManager");
		
		Text scenetitle = new Text("Welcome to TaskManager!");
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		//---------test-------
		final ListView<String> console = new ListView<String>(FXCollections.<String>observableArrayList());
        // listen on the console items and remove old ones when we get over 10 items
        console.getItems().addListener(new ListChangeListener<String>() {
            @Override 
            public void onChanged(Change<? extends String> change) {
                while (change.next()) {
                    if (change.getList().size() > 20) 
                    	change.getList().remove(0);
                }
            }
        });
        
        // create text box for typing in
        actiontarget = new Text();
        textfield = new TextField();
        textfield.setPromptText("Write you commands here.");
        textfield.setStyle("-fx-font-size: 25;");
        
        tasklogic = new TaskLogic();
        
        //add a key listenersa
        textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	//assert ke.getCode() == KeyCode.ENTER: "correct";
            	if(ke.getCode() == KeyCode.ENTER){

            		handleEnterPress();
            		textfield.clear();

            	}
            }

			private void handleEnterPress() {

<<<<<<< Updated upstream
=======
				// TODO Auto-generated method stub
				logger.log(Level.INFO, "end of processing");
				assert tasklogic.executeCommand(textfield.getText()).equals("Added task do homework for Fri, 16 Oct"): "message correct";
				
>>>>>>> Stashed changes
				console.getItems().add("Key Pressed: " + tasklogic.executeCommand(textfield.getText()));
				//assertEquals("add do homework", tasklogic.executeCommand(textfield.getText()));
				
				
				actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("thanks for type in command.\n"+textfield.getText());
			}
        });
		
		rootlayout = new BorderPane();
		//rootlayout.setRight(actiontarget);
		rootlayout.setBottom(textfield);
		rootlayout.setTop(scenetitle);
		rootlayout.setCenter(console);
		

		Scene scene = new Scene(rootlayout, 700, 450);

		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void main(String[] args) {
		try{
        launch(args);
		}catch(Throwable e){
			System.out.println(e.getMessage());
		}
	}
}*/








