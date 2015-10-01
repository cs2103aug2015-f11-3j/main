import java.io.IOException;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


//import main.java.backend.Logic;

public class TaskGui extends Application {
	 
	private Stage primaryStage;
	private BorderPane rootLayout;
	private TaskLogic logic;
	Label lb;
	TextField textField;
	
	public static void main(String[] args) {
        launch(args);
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		lb = new Label("Welcome to task manager");
		textField = new TextField ();
		
		VBox root = new VBox();
		root.getChildren().add(lb);
		root.getChildren().add(textField);
		Scene scene = new Scene(root, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.show();
		//initRootLayout();
        //initPrimaryStage(primaryStage);
        //initLogic();

        // Add components to RootLayout
        //addCommandBar(this);
	}

	private void addCommandBar(TaskGui taskGui) {
		// TODO Auto-generated method stub
		
	}

	private void initLogic() {
		// TODO Auto-generated method stub
		
	}

	private void initPrimaryStage(Stage primaryStage2) {
		// TODO Auto-generated method stub
		
	}

	private void initRootLayout() {
		// TODO Auto-generated method stub
		
	}
}
