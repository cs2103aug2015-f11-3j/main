package src;

import java.io.IOException;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

public class OverDueController extends BorderPane{
	private static final String UPDATE_TABLE_FXML = "/layout/Overdue.fxml";
	@FXML
	private TableView<Message> overDueTable;
	
	@FXML
	private TableColumn<Message, Integer> indexColumn;
	
	@FXML
	private TableColumn<Message, String> eventColumn;
	
	@FXML
	private TableColumn<Message, String> dateColumn;

	@FXML
	private TableColumn<Message, Boolean> statusColumn;
	
	public OverDueController(ObservableList<Message> data){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(UPDATE_TABLE_FXML));
		loader.setController(this);
        loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        overDueTable.setItems(data);
        indexColumn.setCellValueFactory(new PropertyValueFactory<Message, Integer>("Index"));
        eventColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Event"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<Message, String>("Date"));   
        statusColumn.setCellValueFactory(new PropertyValueFactory<Message, Boolean>("Status"));
        
        statusColumn.setCellFactory(column ->{
        	return new TableCell<Message, Boolean>(){
        		@Override
				protected void updateItem(Boolean item, boolean empty) {
					super.updateItem(item, empty);
					if (!empty) {
						if (item) {
							setStyle("-fx-background-color: green");
						}
						else{
							setStyle("-fx-background-color: yellow");
						}
					}
				}
        	};
        });
       
	}
}
