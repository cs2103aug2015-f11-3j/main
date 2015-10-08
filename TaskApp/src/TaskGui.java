package src;
import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.application.Application;
<<<<<<< Updated upstream
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
=======
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ListChangeListener.Change;
import javafx.event.EventHandler;
>>>>>>> Stashed changes
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
//import main.java.gui.CommandBarController;


public class TaskGui extends Application {
	
	TextField textfield;
	BorderPane rootlayout;
	TaskLogic tasklogic;
	Text actiontarget;
	ListView<String> console;
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		String str = tasklogic.executeCommand("send");
		System.out.println(str);
		
		primaryStage.setTitle("TaskManager");
		
		Text scenetitle = new Text("Welcome");
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
        
        //add a key listeners
        textfield.setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
            	if(ke.getCode() == KeyCode.ENTER){
            		//String str = tasklogic.executeCommand("send");
            		//System.out.println(str);
            		handleEnterPress();
	                //console.getItems().add("Key Pressed: " + textfield.getText());
	                //actiontarget.setFill(Color.FIREBRICK);
	                //actiontarget.setText("thanks for type in command.");
            	}
            }

			private void handleEnterPress() {
				// TODO Auto-generated method stub
				console.getItems().add("Key Pressed: " + textfield.getText());
				//console.getItems().add("Key Pressed: " + tasklogic.executeCommand("test"));
				actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("thanks for type in command."+ textfield.getText());
			}
        });
		
		rootlayout = new BorderPane();
		rootlayout.setRight(actiontarget);
		rootlayout.setBottom(textfield);
		rootlayout.setTop(scenetitle);
		rootlayout.setCenter(console);
		
<<<<<<< Updated upstream
//		textField.setOnAction(new EventHandler<KeyEvent>()
//	    {
//	        @Override
//	        public void handle(KeyEvent ke)
//	        {
//	            if (ke.getCode().equals(KeyCode.ENTER))
//	            {
//	                doSomething();
//	            }
//	        }
//	    });
		
		VBox root = new VBox();
		root.getChildren().add(lb);
		root.getChildren().add(textField);
		Scene scene = new Scene(root, 500, 500);
=======
		Scene scene = new Scene(rootlayout, 500, 450);
>>>>>>> Stashed changes
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}

}






