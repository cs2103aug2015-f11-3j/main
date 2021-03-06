package GUI;

import java.io.IOException;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 * This class handles the Search table that display search task 
 * on table
 * 
 * @author Cihang (A0126410)
 *
 */
//@@author A0126410X
public class SearchController extends BorderPane{
	private static final String SEARCH_TABLE_FXML = "/layout/Search.fxml";
	
	@FXML
	private TableView<Message> searchTable;
	
	@FXML
	private TableColumn<Message, Integer> indexColumn;
	
	@FXML
	private TableColumn<Message, String> eventColumn;
	
	@FXML
	private TableColumn<Message, String> dateColumn;
	
	public SearchController(ObservableList<Message> data){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(SEARCH_TABLE_FXML));
		loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        searchTable.setItems(data);
        indexColumn.setCellValueFactory(new PropertyValueFactory<Message, Integer>("Index"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Event"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Date"));
	}
}
