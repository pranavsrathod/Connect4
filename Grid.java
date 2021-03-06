import java.util.Stack;

import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
public class Grid extends GridPane {
	private Stack<GameButton> stack_Buttons;
	Label validity;
	private GridPane board;
	private int player;
	private GameButton array[][];
	Grid(){
		super();
		stack_Buttons = new Stack<GameButton> ();
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
				GameButton box = new GameButton();
				board.add(box, i, j);
				array[i][j] = box;
			}
		}
		board.setStyle("-fx-background-color: Black");
		player = 1;
		
	}
	public  void setButtonConfigurations() {
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
//				 Only valid Moves
		for (int i = 0; i < 7; i++) {
			GameButton box = array[i][5];
			final int boxcol = i;
			final int boxrow = 5;
			box.setOnAction(e -> {
				if (player == 1) {
//							stack_Buttons.push(box);
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
	}
	public void reverse() {
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
		remove.setStyle("-fx-background-color: #FFFFE0");
		remove.setDisable(false);
		if (player == 1) {
//			stack_Buttons.push(box);
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
	}
	public void newGame() {
		GameButton remove;
		while (!stack_Buttons.isEmpty()) {
			remove = stack_Buttons.pop();
			remove.setStyle("-fx-background-color: #FFFFE0");
			remove.setDisable(false);
		}
		validity.setText("No - Move!");
		validity.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		player = 1;
	}
	public Label getValidity() {
		return validity;
	}
	public GridPane getBoard() {
		return board;
	}
}

