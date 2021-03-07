import java.util.HashMap;
import java.util.Optional;
import java.util.Stack;
import java.util.Vector;

import javafx.animation.PauseTransition;
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
		// TODO Auto-generated method stub
		//dummyStage = new Stage();
		dummyStage = primaryStage;
		
		sceneMap = new HashMap<String,Scene>();
		dummyStage.setTitle("Welcome to Connect 4");
		
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
		borderPane.setCenter(root);
		b1.setOnAction(e -> primaryStage.setScene(sceneMap.get("game")));
		Scene welcome = new Scene(borderPane, 500,500);
		// ------------------------------------------------------------------------------------------------------------------
		
		sceneMap.put("welcome", welcome);
		sceneMap.put("game", gameScene());
		
		dummyStage.setScene(sceneMap.get("welcome"));
		dummyStage.show();
	}
	
	public Scene gameScene() {
		object = new Grid();
		object.setButtonConfigurations();
		return object.getScene();
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
			//label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
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
					String s = i+","+j;
					GameButton box = new GameButton(s);
					board.add(box, i, j);
					array[i][j] = box;
				}
			}
			board.setStyle("-fx-background-color: White");
			player = 1;
			countTile = 0;
			isWin = false;
			buttonColor = "-fx-background-color: Black";
			defaultStyle = "-fx-background-color: Black";
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
			gamePlay.getItems().add(reverse);
			theme.getItems().addAll(theme1, theme2, original_theme);
			options.getItems().addAll(howToPlay, newGame, exit);
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
							// validity.setText("For Player : " + player +" Last Move On [" + boxrow + "] [" + boxcol + "]");
						} else {
							validity.setText("Player : " + player +" Invalid Move");
						}
					});
				}
			}
//					 Only valid Moves
			for (int i = 0; i < 7; i++) {
				GameButton box = array[i][5];
				final int boxcol = 5;
				final int boxrow = i;
				box.setOnAction(e -> {
					Configure(box,boxrow,boxcol);
					// validity.setText("For Player : " + player +" Last Move On [" + boxrow + "] [" + boxcol + "]");
				});
			}
			reverse.setOnAction(e -> this.reverse());
			moveBar = new HBox(10, label, this.getValidity());	
			// Border Pane execution
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
			newGame.setOnAction(e -> this.newGame());
			exit.setOnAction(e-> {
		    	System.exit(0);
			});
			theme1.setOnAction(e -> {
				board.setStyle("-fx-background-color: DeepSkyBlue;");
			});
			
			theme2.setOnAction(e -> {
				board.setStyle("-fx-background-color: #228B22;");
			});
			original_theme.setOnAction(e -> {
				board.setStyle("-fx-background-color: Black;");
			});
			gameScene = new Scene (new VBox(20, menu, board, moveBar), 650, 650);
		}
		private void Configure(GameButton box, int boxrow, int boxcol) {
			countTile++;
			if (player == 1) {
				// stack_Buttons.push(box);
				buttonColor = "-fx-background-color: Red;";
				box.setStyle(buttonColor);
				if (countTile >= 7) {
					checkWin(boxrow, boxcol);
				}
				player = 2;
			} else {
				buttonColor = "-fx-background-color: Yellow;";
				box.setStyle(buttonColor);
				if (countTile >= 7) {
					checkWin(boxrow, boxcol);
				}
//				box.setStyle("-fx-background-color: Yellow;");
				player = 1;
			}
			if(!isWin) {
				validity.setText(" Last Move On [" + boxrow + "] [" + boxcol + "] Next Move For Player : " + player);
			}
			stack_Buttons.push(box);
			box.setDisable(true);
		}
		public void reverse() {
			int count = 0;
			int storeRow = 0;
			int storeColumn = 0;
			GameButton remove;
			GameButton temp;
			count = stack_Buttons.size();
			if (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();
				remove.setStyle("-fx-background-color: Black");
				remove.setDisable(false);
				if (player == 1) {
//					stack_Buttons.push(box);
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
				count --;
			}
			if (count == 0) {
				validity.setText("No - Move!");
			}
			
		}
		private void checkWin(int row, int col) {
			isWin = checkRow(col);
			if (!isWin) {
				isWin = checkCol(row);
				if(!isWin) {
					isWin = checkDiagonal();
				}
			}
			if (isWin) {
				displayWin();
				Label message = new Label("CONGRATULATIONS GAME OVER");
				message.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
				BorderPane exitPane = new BorderPane();
				exitPane.setCenter(message);
				PauseTransition halt = new PauseTransition(Duration.seconds(2));
				halt.setOnFinished(e -> {
					dummyStage.setScene(new Scene(exitPane, 100, 100));
					dummyStage.show();
				});
				halt.play();
			}
		}
		private void displayWin() {
			for (int i = 0; i < 7; i++) {
				for (int j = 0; j < 6; j++) {
					array[i][j].setDisable(true);
					array[i][j].setStyle("-fx-background-color: #FFFFE0");
				}
			}
			for (int i =0; i < winBoxes.size(); i++) {
				winBoxes.elementAt(i).setStyle("-fx-background-color: Green");;
			}
			winBoxes.clear();
		}
		private boolean checkRow(int row) {
			int counter = 0;
			for (int i = 0; i < 4; i++) {
				String style = array[i][row].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[i][row]);
					for (int j = i + 1; j<= i+3; j++) {
						String style2 = array[j][row].getStyle();
						if(style.equals(style2)){
							// array[j][row].setText(row+","+j);
							winBoxes.add(array[j][row]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON ROW!!!!");
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
					for (int j = i+1; j<= i+3; j++) {
						if(style.equals(array[col][j].getStyle())){
							winBoxes.add(array[col][j]);
							counter++;
						} else {
							winBoxes.clear();
							counter = 0;
						}
					}
				}
				if (counter == 3) {
					validity.setText("PLAYER " + player + " WON COLUMN!!!!");
					return true;
				}
			}
			winBoxes.clear();
			return false;
		}
		
		
		private boolean checkDiagonal() {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 3; j++) {
					if (checkLeft(i,j)) {
						return true;
					}
				}
			}
			for (int i = 6; i > 2; i--) {
				for (int j = 0; j < 3; j++) {
					if (checkRight(i,j)) {
						return true;
					}
				}
			}
			return false;
		}
		private boolean checkLeft(int i, int j) {
			int counter = 0;
			while ((i+3) < 7 && (j+3) < 6) {
				String style = array[i][j].getStyle();
				if (!(style.equals(defaultStyle))) {
					winBoxes.add(array[i][j]);
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
		public void newGame() {
			GameButton remove;
			while (!stack_Buttons.isEmpty()) {
				remove = stack_Buttons.pop();
				remove.setStyle("-fx-background-color: Black");
				remove.setDisable(false);
			}
			validity.setText("No - Move!");
			validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
			player = 1;
		}
		public boolean getWin() {
			return isWin;
		}
		
		public Label getValidity() {
			return validity;
		}
		public GridPane getBoard() {
			return board;
		}
		public Scene getScene() {
			return gameScene;
		}
	}
}
