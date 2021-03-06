import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.image.Image;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Dialog;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.stage.Stage;

public class JavaFXTemplate extends Application {	
	private BorderPane borderPane;
	EventHandler<ActionEvent> moveButton, moveBottom;
	private Button b1;
	HashMap<String, Scene> sceneMap;
	private MenuBar menu;
	private Menu gamePlay;
	private MenuItem reverse;
	private Menu theme;
	private MenuItem theme1;
	private MenuItem theme2;
	private Menu options;
	private MenuItem exit;
	private MenuItem howToPlay;
	private MenuItem newGame;
	private Stack<GameButton> stack_Buttons;
	private HBox moveBar;
	private VBox root;
	private GridPane board;
	private int player;
	GameButton array[][];
	static final int picHeight = 100;
	static final int picWidth = 100;
	int flag = 0;
	
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
	public Scene gameScene() {
		
		stack_Buttons = new Stack<GameButton> ();
		Label label = new Label("MOVE : ");
		Label validity = new Label("No - Move");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		//gamePlay.setPrefWidth(50);
		array = new GameButton[7][6];
		board = new GridPane();
		board.setVgap(10);
		board.setHgap(10);
		player = 1;
		// for building the grid
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 6; j++) {
				GameButton box = new GameButton();
				board.add(box, i, j);
				array[i][j] = box;
			}
		}
		
		// for any moves above the last row
		for (int i = 0; i < 7; i++) {
			for (int j = 0; j < 5; j++) {
				GameButton box = array[i][j];
				final int boxcol = j;
				final int boxrow = i;
				box.setOnAction(e -> {
					if(array[boxrow][boxcol + 1].isDisabled()) {	
						box.setDisable(true);
						if (player == 1) {
							// stack_Buttons.push(box);
							box.setStyle("-fx-background-color: Red;");
							player = 2;
						} else {
							box.setStyle("-fx-background-color: Yellow;");
							player = 1;
						}
						stack_Buttons.push(box);
						validity.setText("For Player : " + player +" Last Move On [" + boxcol + "] [" + boxrow + "]");
					} else {
						validity.setText("For Player : " + player +" Invalid Move");
					}
				});
			}
		}
//		 Only valid Moves
		for (int i = 0; i < 7; i++) {
			GameButton box = array[i][5];
			final int boxcol = i;
			final int boxrow = 5;
			box.setOnAction(e -> {
				if (player == 1) {
//					stack_Buttons.push(box);
					box.setStyle("-fx-background-color: Red;");
					player = 2;
				} else {
					box.setStyle("-fx-background-color: Yellow;");
					player = 1;
				}
				stack_Buttons.push(box);
				box.setDisable(true);
				validity.setText("For Player : " + player +" Last Move On [" + boxcol + "] [" + boxrow + "]");
			});
		}
		moveBar = new HBox(10, label, validity);	

		// Menu Bar
		menu = new MenuBar();
		gamePlay = new Menu("gamePlay");
		theme = new Menu("theme");
		options = new Menu("options");
		reverse = new MenuItem("reverse");
		theme1 = new MenuItem("theme1");
		theme2 = new MenuItem("theme2");
		howToPlay = new MenuItem("howToPlay");
		exit = new MenuItem("exit");
		newGame = new MenuItem("newGame");
		gamePlay.getItems().add(reverse);
		theme.getItems().addAll(theme1, theme2);
		options.getItems().addAll(howToPlay, newGame, exit);
		menu.getMenus().addAll(gamePlay, theme, options);
		
		// action event for reverse
		reverse.setOnAction(e -> {
			int count = 0;
			int storeRow = 0;
			int storeColumn = 0;
			GameButton remove = new GameButton();
			GameButton temp = new GameButton();
			count = stack_Buttons.size();
			if (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();
				count --;
			}
			if (count == 0) {
				validity.setText("No - Move!");
			}
			remove.setStyle("-fx-background-color: darkGrey");
			remove.setDisable(false);
			if (player == 1) {
//				stack_Buttons.push(box);
				player = 2;
			} else {
				player = 1;
			}
			temp = stack_Buttons.peek();
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					if (array[i][j] == temp) {
						storeRow = i;
						storeColumn = j;
					}
				}
			}
			validity.setText("For Player : " + player +" Last Move On [" + storeColumn + "] [" + storeRow + "]");
			
		});
		
		// called when newGame is clicked
		howToPlay.setOnAction(e -> {
		      Dialog<String> dialog = new Dialog<String>();
		      dialog.setTitle("How To Play");
		      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
		      dialog.setContentText("This game is called Connect 4\n"
		      		+ "It involves connecting four tiles\n"
		    		+ "These tiles can be connected either Horizontally, Vertically and Diagnolly\n");
		      dialog.getDialogPane().getButtonTypes().add(type);
		      dialog.showAndWait();
		});
		
		newGame.setOnAction(e -> {
			int storeRow = 0;
			int storeColumn = 0;
			GameButton remove = new GameButton();
			GameButton temp = new GameButton();
			while (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();
				remove.setStyle("-fx-background-color: darkGrey");
				remove.setDisable(false);
			}
			validity.setText("No - Move!");
			validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		});
		
		exit.setOnAction(e-> {
		    	System.exit(0);
		});
		
		// Border Pane execution
		BorderPane pane = new BorderPane();
		theme1.setOnAction(e -> {
			pane.setStyle("-fx-background-color: DeepSkyBlue;");
		});
		
		theme2.setOnAction(e -> {
			pane.setStyle("-fx-background-color: #228B22;");
		});
		pane.setCenter(board);
		pane.setStyle("-fx-background-color: white;");
		return new Scene(new VBox(20, menu, pane, moveBar), 600, 600);
	}
}
