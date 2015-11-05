package src;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class TodoController extends BorderPane {
	private static final String UPDATE_TABLE_FXML = "/layout/Todo.fxml";
	private int count = 1;
	@FXML
	private TableView<Message> todoTable;
	
	@FXML
	private TableColumn<Message, Integer> indexColumn;
	
	@FXML
	private TableColumn<Message, String> eventColumn;
	
	@FXML
	private TableColumn<Message, String> dateColumn;
	
	/*ObservableList<Message> data = FXCollections.observableArrayList(
			new Message(count++, "do homework", "today"),
			new Message(count++, "do homework", "today"),
			new Message(count++, "do homework", "today"),
			new Message(count++, "do homework", "today"),
			new Message(count++, "do homework", "today")
			
	);*/
	
	public TodoController(ObservableList<Message> data){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(UPDATE_TABLE_FXML));
		loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //ObservableList<Message> data = FXCollections.observableArrayList(msg);
        
        todoTable.setItems(data);
        indexColumn.setCellValueFactory(new PropertyValueFactory<Message, Integer>("Index"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Event"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Date"));
        
	}
}
