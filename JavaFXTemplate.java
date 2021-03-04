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
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {
	private BorderPane borderPane;
	EventHandler<ActionEvent> moveButton, moveBottom;
	private Button b1;
	HashMap<String, Scene> sceneMap;
	private Button gamePlay;
	private Button theme;
	private Button options;
	private VBox root;
	private HBox menuBar;
	private HBox moveBar;
	private GridPane board;
	private int playerCounter;
	GameButton array[][];
	
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
//		borderPane.setCenter(label);
//		borderPane.setCenter(b1);
		borderPane.setCenter(root);
		b1.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
		Scene welcome = new Scene(borderPane, 500,500);
		// ------------------------------------------------------------------------------------------------------------------
		
		sceneMap.put("welcome", welcome);
		sceneMap.put("game", gameScene());
		
		primaryStage.setScene(sceneMap.get("welcome"));
		primaryStage.show();
	}
	public void setCounter(int i) {
		playerCounter = i;
	}
	public int getCounter() {
		return playerCounter;
	}

	public Scene gameScene() {
		Label label = new Label("MOVE : ");
		Label validity = new Label("No - Move");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		//gamePlay.setPrefWidth(50);
		array = new GameButton[7][6];
		board = new GridPane();
		board.setVgap(10);
		board.setHgap(10);
		setCounter(2);
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				GameButton box = new GameButton();
				board.add(box, i, j);
				array[i][j] = box;
			}
		}
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
			GameButton box = array[i][j];
			final int boxcol = j;
			final int boxrow = i;
			final int playCount = getCounter();
			box.setOnAction(e -> {
				if(array[boxrow][boxcol + 1].isDisabled())
				{
					box.setDisable(true);
					if (playCount % 2 == 0) {
						box.setStyle("-fx-background-color: darkRed;");
					} else {
						box.setStyle("-fx-background-color: darkYellow;");
					}
					validity.setText("On [" + boxcol + "] [" + boxrow + "]");
					setCounter(playCount + 1);
				} else {
					validity.setText("Invalid Move");
				}
				});
		}
		}
		for (int i = 0; i < 7; i++) {
			GameButton box = array[i][5];
			final int boxcol = i;
			final int boxrow = 5;
			final int playCount = getCounter();
			box.setOnAction(e -> {
				if (playCount % 2 == 0) {
					box.setStyle("-fx-color: darkRed;");
				} else {
					box.setStyle("-fx-color: darkYellow;");
				}
				box.setDisable(true);
				validity.setText("On [" + boxcol + "] [" + boxrow + "]");
				setCounter(playCount + 1);
				});
		}
		moveBar = new HBox(50, label, validity);
		
		
//		_________________________________________________________________________________________________________________________
		gamePlay = new Button("Game Play");
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
		pane.setCenter(board);
		pane.setStyle("-fx-background-color: lightBlue;");
		return new Scene(new VBox(20, menuBar, pane, moveBar), 600, 600);
	}
	

}
