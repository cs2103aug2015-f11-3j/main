package src;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;

public class CommandBarController extends BorderPane{

	private static final String COMMANDBAR_LAYOUT_FXML = "/layout/CommandBar.fxml";
	private TaskGui taskGui;
	
	@FXML
	private Label messageLabel;
	
	@FXML
	private TextField commandTextfield;
	
	public CommandBarController(TaskGui taskGui){
		FXMLLoader loader = new FXMLLoader(getClass().getResource(COMMANDBAR_LAYOUT_FXML));
		loader.setController(this);
		loader.setRoot(this);
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.taskGui = taskGui;
	}
	
	@FXML
	public void onKeyPress(KeyEvent event) {
		taskGui.handleKeyPress(this, event.getCode(), commandTextfield.getText());
	}
	
	@FXML
	public void setFeedback(String feedback){
		messageLabel.setText(feedback);
	}
	
	@FXML
	public void clear() {
        commandTextfield.clear();
    }
}

