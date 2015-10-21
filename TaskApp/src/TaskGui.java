package src;

//test
import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import src.TaskLogic;


public class TaskGui extends Application {
	
	TextField textfield;
	BorderPane rootlayout;
	Text actiontarget;
	
	ListView<String> console;
	
	TaskLogic tasklogic;

	@Override
	public void start(Stage primaryStage) throws Exception {

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
        
        //add a key listeners
        textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if(ke.getCode() == KeyCode.ENTER){

            		handleEnterPress();
            		textfield.clear();

            	}
            }

			private void handleEnterPress() {

				console.getItems().add("Key Pressed: " + tasklogic.executeCommand(textfield.getText()));
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
        launch(args);
    }

}






