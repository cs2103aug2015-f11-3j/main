package src;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ListViewController extends BorderPane{

	private static final String LISTVIEW_LAYOUT_FXML = "/layout/ListView.fxml";
	private TaskGui taskGui;
	//private ArrayList<String> items = new ArrayList<String>();;
	
	@FXML
	private ListView<String> listView = new ListView<String>(FXCollections.<String>observableArrayList());
	//private ListView<String> listView;
	
	public ListViewController(){
	//public ListViewController(String userInput){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(LISTVIEW_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
		try{
			loader.load();
		} catch (IOException e) {
            e.printStackTrace();
        }
		 
		//initialListView(userInput);
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
	/*private void initialListView(String userInput) {
		// TODO Auto-generated method stub
		
		listView.getItems().add(userInput);
		//items.add(userInput);
		//listView.setItems(FXCollections.observableArrayList(userInput));
		//listView.setItems(FXCollections.observableList(items));
	}*/
}
