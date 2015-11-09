package GUI;

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
import logic.Logic;
import logic.Tasks;

/**
 * TaskGui is the entry point for JavaFX applications.
 * 
 * @author Cihang (A0126410)
 *
 */
//@@author A0126410X
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
		initialRootLayout();
		initialPrimaryStage(primaryStage);
		initialLogic();
		addCommandBar(this);
		addListView();
		addTodoTableView(logic.getToDoTask());
		addOverdueTableView(logic.getDiscard());
		addSearchTableView(logic.getSearch());
	}

	/**
	 * Initializes the Logic class
	 */
	private void initialLogic() {
		logic = Logic.getInstance();
	}

	/**
	 * Add ListView to root and set it position
	 */
	private void addListView() {
		listViewController = new ListViewController();
		root.getChildren().add(listViewController);
		AnchorPane.setTopAnchor(listViewController, 10.0);
		AnchorPane.setLeftAnchor(listViewController, 25.0);
		AnchorPane.setBottomAnchor(listViewController, 300.0);
	}

	/**
	 * Add CommandBar to root and set it position
	 * 
	 * @param taskGui
	 */
	private void addCommandBar(TaskGui taskGui) {
		CommandBarController commandBarController = new CommandBarController(taskGui);
		root.getChildren().add(commandBarController);
		AnchorPane.setBottomAnchor(commandBarController, 10.0);
		AnchorPane.setLeftAnchor(commandBarController, 25.0);
		AnchorPane.setRightAnchor(commandBarController, 25.0);
	}

	/**
	 * Initializes the main JavaFX Stage with RootLayout being the main Scene.
	 * 
	 * @param primaryStage
	 */
	private void initialPrimaryStage(Stage primaryStage) {
		this.primaryStage = primaryStage;
        this.primaryStage.setTitle(WINDOW_TITLE);
        this.primaryStage.setScene(new Scene(root));
        this.primaryStage.setResizable(false);
        this.primaryStage.show();
	}

	/**
	 * Initializes the RootLayout that will contain all other JavaFX components.
	 */
	private void initialRootLayout() {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(ROOT_LAYOUT_FXML));
		try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	/**
	 * Methods which refer to Logic class directly
	 * 
	 * @param commandBarController
	 * @param key
	 * @param userInput
	 */
	public void handleKeyPress(CommandBarController commandBarController, KeyCode key, String userInput) {
		if (key == KeyCode.ENTER) {
			handleEnterPress(commandBarController, userInput);
		}
	}

	/**
	 * Methods which use to add content to GUI
	 * 
	 * @param commandBarController
	 * @param userInput
	 */
	private void handleEnterPress(CommandBarController commandBarController, String userInput) {
		logic.executeCommand(userInput);
		listViewController.addArrayList(logic.getConsole());
		addTodoTableView(logic.getToDoTask());
		addOverdueTableView(logic.getDiscard());
		addSearchTableView(logic.getSearch());
		commandBarController.clear();
	}
	
	/**
	 * Add Search Table to root and set it position
	 * 
	 * @param search
	 */
	private void addSearchTableView(ArrayList<Tasks> search) {
		data2 = getInitialTableData3(search);
		searchController = new SearchController(data2);
		root.getChildren().add(searchController);
		AnchorPane.setTopAnchor(searchController, 350.0);
		AnchorPane.setRightAnchor(searchController, 25.0);
		AnchorPane.setBottomAnchor(searchController, 90.0);
	}
	
	/**
	 * Add Overdue Table to root and set it position
	 * 
	 * @param discard
	 */
	private void addOverdueTableView(ArrayList<Tasks> discard) {
		data1 = getInitialTableData3(discard);
		overDueController = new OverDueController(data1);
		root.getChildren().add(overDueController);
		AnchorPane.setTopAnchor(overDueController, 15.0);
		AnchorPane.setRightAnchor(overDueController, 25.0);
		AnchorPane.setBottomAnchor(overDueController, 400.0);
	}
	
	/**
	 * Add Todo Table to root and set it position
	 * 
	 * @param toDoTask
	 */
	private void addTodoTableView(ArrayList<Tasks> toDoTask) {
		data = getInitialTableData3(toDoTask);
		todoController = new TodoController(data);
		root.getChildren().add(todoController);
		AnchorPane.setTopAnchor(todoController, 10.0);
		AnchorPane.setLeftAnchor(todoController, 410.0);
		AnchorPane.setBottomAnchor(todoController, 90.0);
	}
	
	/**
	 * Add content to TableView list
	 * 
	 * @param listTask
	 * @return
	 */
	private ObservableList<Message> getInitialTableData3(ArrayList<Tasks> listTask) {
		List<Message> list = new ArrayList<Message>();
		for(int i=0; i<listTask.size(); i++){
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

	/**
	 * Reformat the date format and display to GUI
	 * 
	 * @param string
	 * @return
	 */
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
