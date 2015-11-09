package GUI;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

/**
 * This class handles the Console that users input commands in and the list view console
 * that shows feedback.
 * 
 * @author Cihang (A0126410)
 *
 */
public class ListViewController extends BorderPane{

	private static final String LISTVIEW_LAYOUT_FXML = "/layout/ListView.fxml";
	
	@FXML
	private ListView<String> listView = new ListView<String>(FXCollections.<String>observableArrayList());
		
	public ListViewController(){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LISTVIEW_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try{
			loader.load();
		} catch (IOException e) {
            e.printStackTrace();
        }
	}
	
	 public void addItemToListView(String items) {
		 listView.getItems().add(items);
	 }
	 public void addArrayList(ArrayList<String> list){
		 listView.getItems().clear();
		 for(int i=0; i<list.size(); i++){
			 listView.getItems().add(list.get(i));
		 }
	 }
}
