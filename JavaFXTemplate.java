import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private BorderPane borderPane;
	private Button b1;
	private VBox root;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		primaryStage.setTitle("Welcome to JavaFX");
		b1 = new Button("Start");
		b1.setPrefWidth(100);
		root = new VBox(10, b1);
		/*
		 add an image to the background
		 */
		borderPane = new BorderPane();
		borderPane.setCenter(b1);
		EventHandler<ActionEvent> handler = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				// calling the gameButton
			}
		};
		// sets the action
		b1.setOnAction(handler);
		Scene scene = new Scene(borderPane, 500,500);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
