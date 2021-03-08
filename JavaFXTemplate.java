// CS 342
// Project 2 : Connect4 
// Pranav Rathod and Parth Tawde
// pratho2 and ptawde2
// This program is GUI based and generates a game called Connect4
// This game is particularly based on connecting the four tiles
// either diagonally, horizonatally or vertically.
import java.util.HashMap;
import java.util.Stack;
import java.util.Vector;
import javafx.scene.paint.Color;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.geometry.Pos;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
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
import javafx.util.Duration;

public class JavaFXTemplate extends Application {	
	private BorderPane borderPane;
	EventHandler<ActionEvent> moveButton, moveBottom;
	private Button b1;
	HashMap<String, Scene> sceneMap;
	Grid object;
	private VBox root;
	public Stage dummyStage;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	//feel free to remove the starter code from this method
	@Override
	public void start(Stage primaryStage) throws Exception {
		HBox root2 = new HBox();
		// TODO Auto-generated method stub
		dummyStage = primaryStage;  // set primary to dummy stage.
		
		sceneMap = new HashMap<String,Scene>();
		dummyStage.setTitle("Welcome to Connect 4");
		
		// ------------------------------------------------------------------------------------------------------------------
		Image myImage = new Image("Start.gif");
		BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
		b1 = new Button("Start");
		root2.getChildren().add(b1);  // adding button to HBox
		root2.setAlignment(Pos.CENTER);  // setting position to center
		b1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));  // setting font
		Label label = new Label("-------------- !! WELCOME TO CONNECT 4 !! -------------------");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		label.setBackground(new Background(new BackgroundFill(Color.ORANGE,null, null)));
		b1.setPrefWidth(100);
		root = new VBox(300,label,root2);  // setting the vBox
		/*
		 add an image to the background
		 */
		borderPane = new BorderPane();
		borderPane.setCenter(root);
		borderPane.setBackground(new Background(new BackgroundImage(myImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, bSize)));
		// when b1 button gets clicked.
		b1.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
		Scene welcome = new Scene(borderPane, 500,500);
		// ------------------------------------------------------------------------------------------------------------------
		// adding the scenes to the map.
		sceneMap.put("welcome", welcome);
		sceneMap.put("game", gameScene());
		// getting the scene from map and setting in a dummy stage.
		dummyStage.setScene(sceneMap.get("welcome"));
		dummyStage.show();
	}
	
	public Scene gameScene() {
		object = new Grid();  // allocating memory to the object.
		object.setButtonConfigurations();  // setting up the grid
		return object.getScene();  // returning the scene
	}
	private class Grid{
		private Scene gameScene;
		private Stack<GameButton> stack_Buttons;
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
		private MenuItem original_theme;
//		private Stack<GameButton> stack_Buttons;
		private HBox moveBar;
		Label validity;
		Label label;
		private GridPane board;
		private int player;
		private int countTile;
		private String buttonColor;
		private GameButton array[][];
		private boolean isWin;
		private String defaultStyle;
		private Vector<GameButton> winBoxes;
		//private Stage dummyStage;
		Grid(){
			super();
			stack_Buttons = new Stack<GameButton> ();
			winBoxes = new Vector<GameButton>();
			label = new Label("MOVE : ");
			label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			validity = new Label("No - Move");
			validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			array = new GameButton[7][6];
			board = new GridPane();
			board.setVgap(10);
			board.setHgap(10);
			player = 1;
			// for building the grid
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					String s = i+","+j;
					GameButton box = new GameButton();
					board.add(box, i, j);
					array[i][j] = box;
				}
			}
			board.setStyle("-fx-background-color: White");  // set background of the board
			player = 1;
			countTile = 0;
			isWin = false;
			buttonColor = "-fx-background-color: Black";  // set button color
			defaultStyle = "-fx-background-color: Black";
			
			// setting up the entire menu bar
			menu = new MenuBar();
			gamePlay = new Menu("gamePlay");
			theme = new Menu("theme");
			options = new Menu("options");
			reverse = new MenuItem("reverse");
			theme1 = new MenuItem("theme1");
			theme2 = new MenuItem("theme2");
			original_theme = new MenuItem("original");
			howToPlay = new MenuItem("howToPlay");
			exit = new MenuItem("exit");
			newGame = new MenuItem("newGame");
			
			// setting up menu items
			gamePlay.getItems().add(reverse);
			theme.getItems().addAll(theme1, theme2, original_theme);
			options.getItems().addAll(howToPlay, newGame, exit);
			
			// setting menu bar
			menu.getMenus().addAll(gamePlay, theme, options);
		}
		public void setButtonConfigurations() {
			
			// for any moves above the last row
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 5; j++) {
					GameButton box = array[i][j];
					final int boxcol = j;
					final int boxrow = i;
					box.setOnAction(e -> {
						if(array[boxrow][boxcol + 1].isDisabled()) {	
							Configure(box,boxrow,boxcol);
						} else {
							validity.setText("Player : " + player +" Invalid Move");
						}
					});
				}
			}

			//	Only valid Moves
			for (int i = 0; i < 7; i++) {
				GameButton box = array[i][5];
				final int boxcol = 5;
				final int boxrow = i;
				box.setOnAction(e -> {
					Configure(box,boxrow,boxcol);
				});
			}
			
			reverse.setOnAction(e -> this.reverse());  // when reverse is pressed
			moveBar = new HBox(10, label, this.getValidity());  // set the hBox
			
			// when howToPlay is pressed
			howToPlay.setOnAction(e -> {
				// dialog box configurations
			      Dialog<String> dialog = new Dialog<String>();
			      dialog.setTitle("How To Play");
			      ButtonType type = new ButtonType("Ok", ButtonData.OK_DONE);
			      dialog.setContentText("This game is called Connect 4\n"
			      		+ "It involves connecting four tiles\n"
			    		+ "These tiles can be connected either Horizontally, Vertically and Diagnolly\n");
			      dialog.getDialogPane().getButtonTypes().add(type);
			      dialog.showAndWait();
			});
			
			// if new is pressed
			newGame.setOnAction(e -> this.newGame());
			exit.setOnAction(e-> {
		    	System.exit(0);
			});
			
			// if theme1 is pressed
			theme1.setOnAction(e -> {
				board.setStyle("-fx-background-color: DeepSkyBlue;");
			});
			
			// if theme2 is pressed
			theme2.setOnAction(e -> {
				board.setStyle("-fx-background-color: #228B22;");
			});
			
			// if original theme is pressed
			original_theme.setOnAction(e -> {
				board.setStyle("-fx-background-color: White;");
			});
			
			// setting the gameScene
			gameScene = new Scene (new VBox(20, menu, board, moveBar), 600, 600);
		}
		
		// switching the players and checking the win condition.
		private void Configure(GameButton box, int boxrow, int boxcol) {
			countTile++;  // increment the counter when buttons are clicked.
			if (player == 1) {
				buttonColor = "-fx-background-color: Red;";  // changing the style
				box.setStyle(buttonColor);
				
				// check only after 7 clicks.
				if (countTile >= 7) {
					checkWin(boxrow, boxcol);
				}
				player = 2;
			} else {
				buttonColor = "-fx-background-color: Yellow;";
				box.setStyle(buttonColor);
				
				// check only after 7 clicks
				if (countTile >= 7) {
					checkWin(boxrow, boxcol);
				}
				player = 1;
			}
			
			if(!isWin) {
				validity.setText(" Last Move On [" + boxrow + "] [" + boxcol + "] Next Move For Player : " + player);
			}
			
			stack_Buttons.push(box);  // pushing all the buttons in a stack
			box.setDisable(true);  // disabling the box.
		}
		
		// function definition for undo moves.
		public void reverse() {
			int count = 0;
			int storeRow = 0;
			int storeColumn = 0;
			GameButton remove;
			GameButton temp = null;
			count = stack_Buttons.size();
			
			// pop only if not empty
			if (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();  // remove acts like a temporary
				remove.setStyle("-fx-background-color: Black");
				remove.setDisable(false);
				if (player == 1) {
					player = 2;  // switching players
				} else {
					player = 1;
				}
				
				// check for the top position of the stack
				if (!stack_Buttons.empty()) {
					temp = stack_Buttons.peek();
				}
				
				// get the value of i, j for the message.
				for (int i = 0; i < 7; i++) {
					for (int j = 0; j < 6; j++) {
						if (array[i][j] == temp) {
							storeRow = i;
							storeColumn = j;
						}
					}
				}
				validity.setText("For Player : " + player +" Last Move On [" + storeColumn + "] [" + storeRow + "]");
				count --;
			}
			if (count == 0) {
				validity.setText("No - Move!");
			}
			
		}
		
		// checkWin function definition
		private void checkWin(int row, int col) {
			isWin = checkRow(col);  // first checkRow
			if (!isWin) {
				isWin = checkCol(row);  // checkCol if checkRow is false
				if(!isWin) {
					isWin = checkDiagonal();  // checkDiagnol if checkRow is false
				}
			}
			// if isWin is true
			if (isWin || countTile == 42) {
				displayWin();
				winScene();
			}
		}
		private void winScene() {
			reverse.setDisable(true); // cannot do reverse after winning the game.
			
			// set the gif
			Image newImage = new Image("tenor.gif");
			BackgroundSize Size = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
			
			// setting buttons
			Button exit = new Button("Exit");
			Button restart = new Button("Play Again");
			exit.setPrefWidth(150);
			restart.setPrefWidth(150);
			exit.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			restart.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			
			// set the hBox for the two buttons 
			HBox var3 = new HBox(50, restart, exit);
			var3.setAlignment(Pos.TOP_CENTER);
			
			// setting messages
			Label message = new Label("---------------- !! GAME OVER !! -------------------");
			message.setBackground(new Background(new BackgroundFill(Color.LAWNGREEN,null, null)));
			Label message2 = new Label("WINNER IS PLAYER:" + player +"");
			
			// if all buttons are clicked and there is no win
			if(countTile == 42) {
				message2.setText("IT IS A TIE");
			}
			
			// setting VBox for two messages
			VBox vbox = new VBox(50,message, message2);
			vbox.setAlignment(Pos.CENTER);
			message2.setBackground(new Background(new BackgroundFill(Color.WHITE,null, null)));
			message.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			message2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 25));
			
			// VBox for two messages and buttons.
			VBox var = new VBox(50, vbox, var3);
			
			BorderPane exitPane = new BorderPane();
			exitPane.setCenter(var);
			exitPane.setBackground(new Background(new BackgroundImage(newImage, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, Size)));
			exit.setOnAction(e -> {
				System.exit(0);
			});
			
			// when restart is pressed
			restart.setOnAction(e -> {
				dummyStage.setScene(gameScene());
				newGame();
			});
			
			// This will be use to delay for 3 seconds before the scene changes to the win scene
			PauseTransition halt = new PauseTransition(Duration.seconds(3));
			halt.setOnFinished(e -> {
				dummyStage.setScene(new Scene(exitPane, 400, 400));
				dummyStage.show();
			});
			halt.play();
		}
		
		// highlights the win sequence
		private void displayWin() {
			// looping through the board disabling all the buttons
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					array[i][j].setDisable(true);
					array[i][j].setStyle("-fx-background-color: #FFFFE0");  // make them white
				}
			}
			for (int i =0; i < winBoxes.size(); i++) {
				// only win sequence becomes green.
				winBoxes.elementAt(i).setStyle("-fx-background-color: Green");;
			}
			winBoxes.clear();  // clearing the vector.
		}
		
		// only checking the row
		private boolean checkRow(int row) {
			int counter = 0;
			// loop through a particular row
			for (int i = 0; i < 4; i++) {
				String style = array[i][row].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[i][row]);  // adding buttons to the vector
					// loop through the row
					for (int j = i + 1; j<= i+3; j++) {
						String style2 = array[j][row].getStyle();
						if(style.equals(style2)){  // check for equal colors
							winBoxes.add(array[j][row]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON BY MATCHING 4 in A ROW!!!!");
					return true;
				}
			}
			winBoxes.clear();
			return false;
		}
		private boolean checkCol(int col) {
			int counter = 0;
			for (int i = 0; i < 3; i++) {
				String style = array[col][i].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[col][i]);
					// loop through the column
					for (int j = i+1; j<= i+3; j++) {
						if(style.equals(array[col][j].getStyle())){  // check for equal colors
							winBoxes.add(array[col][j]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON BY MATCHING 4 in A COLUMN!!!!");
					return true;
				}
			}
			winBoxes.clear();
			return false;
		}
		
		// check for diagnol
		private boolean checkDiagonal() {
			// as there are 4:3 possibilities for left diagnol
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					if (checkLeft(i,j)) {
						return true;
					}
				}
			}
			
			// as there are 4:3 possibilities for right diagnol.
			for (int i = 6; i > 2; i--) {
				for (int j = 0; j < 3; j++) {
					if (checkRight(i,j)) {
						return true;
					}
				}
			}
			return false;
		}
		
		// checkLeft diagonal
		private boolean checkLeft(int i, int j) {
			int counter = 0;
			// check for extreme ends and check only left diagonals
			while ((i+3) < 7 && (j+3) < 6) {
				String style = array[i][j].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[i][j]);  // add the buttons in a vector
					for (int k = i+1, l = j+1; k <= i+3 && l <= j+3; k++, l++) {
						if(style.equals(array[k][l].getStyle())){
							winBoxes.add(array[k][l]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON LEFT DIAGONAL!!!!");
					return true;
				}
				i++;
				j++;
			}
			winBoxes.clear();
			return false;
		}
		private boolean checkRight(int i, int j) {
			int counter = 0;
			// check for combinations of four from right to left.
			while ((i-3) > -1 && (j+3) < 6) {
				String style = array[i][j].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[i][j]);
					for (int k = i-1, l = j+1; k >= i-3 && l <= j+3; k--, l++) {
						if(style.equals(array[k][l].getStyle())){
							winBoxes.add(array[k][l]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON RIGHT DIAGONAL!!!!");
					return true;
				}
				i--;
				j++;
			}
			winBoxes.clear();
			return false;
		}
		
		// restart function
		public void newGame() {
			GameButton remove;
			// clear all the buttons that were clicked
			while (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();
				remove.setStyle("-fx-background-color: Black");
				remove.setDisable(false);  // buttons could be pressed again
			}
			// set this text
			validity.setText("No - Move!");
			validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			player = 1;  // back to player one
			isWin = false;
		}
		
		public Label getValidity() {
			return validity;
		}
		public Scene getScene() {
			return gameScene;
		}
	}
}
