import java.util.HashMap;

import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private BorderPane borderPane;
	EventHandler<ActionEvent> startButton, backBotton;
	private Button b1;
	HashMap<String, Scene> sceneMap;
	private Button gamePlay;
	private Button theme;
	private Button options;
	private VBox root;
	private HBox menuBar;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		sceneMap = new HashMap<String,Scene>();
		primaryStage.setTitle("Welcome to Connect 4");
		
		// ------------------------------------------------------------------------------------------------------------------
		b1 = new Button("Start");
		b1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		Label label = new Label("-------------- !! WELCOME TO CONNECT 4 !! --------------");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		b1.setPrefWidth(100);
		root = new VBox(10,label, b1);
		/*
		 add an image to the background
		 */
		borderPane = new BorderPane();
		borderPane.setCenter(label);
		borderPane.setCenter(b1);
		b1.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
		Scene welcome = new Scene(borderPane, 500,500);
		// ------------------------------------------------------------------------------------------------------------------
		
		sceneMap.put("welcome", welcome);
		sceneMap.put("game", gameScene());
		
		primaryStage.setScene(sceneMap.get("welcome"));
		primaryStage.show();
	}

	public Scene gameScene() {
		Label label = new Label("-------------- !! Game Started !! --------------");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		gamePlay = new Button("Game Play");
		//gamePlay.setPrefWidth(50);
		gamePlay.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
		theme = new Button("Theme");
		theme.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		//theme.setPrefWidth(50);
		
		options = new Button("Options");
		options.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		//options.setPrefWidth(50);
		menuBar = new HBox(150, gamePlay, theme, options);
		
		BorderPane pane = new BorderPane();
		pane.setTop(menuBar);
		pane.setCenter(label);
		pane.setStyle("-fx-background-color: lightBlue;");
		return new Scene(pane, 600, 600);
	}
	

}
