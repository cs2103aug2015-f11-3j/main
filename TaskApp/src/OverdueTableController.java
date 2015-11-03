package src;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;



public class OverdueTableController extends StackPane{
	
	private static final String UPDATE_TABLE_FXML = "/layout/OverdueTable.fxml";
	private int count = 1;
	@FXML
	private TableView<Message> overdueTable;
	
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
	
	public OverdueTableController(ObservableList<Message> data){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(UPDATE_TABLE_FXML));
		loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        //ObservableList<Message> data = FXCollections.observableArrayList(msg);
        
        overdueTable.setItems(data);
        indexColumn.setCellValueFactory(new PropertyValueFactory<Message, Integer>("Index"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Event"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Date"));
        
	}
}
