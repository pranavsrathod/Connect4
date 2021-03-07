import java.util.Stack;
import java.util.Vector;

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
	private int countTile;
	private String buttonColor;
	private GameButton array[][];
	private boolean isWin;
	private String defaultStyle;
	private Vector<GameButton> winBoxes;
	Grid(){
		super();
		stack_Buttons = new Stack<GameButton> ();
		winBoxes = new Vector<GameButton>();
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
						Configure(box,boxrow,boxcol);
						// validity.setText("For Player : " + player +" Last Move On [" + boxrow + "] [" + boxcol + "]");
					} else {
						validity.setText("Player : " + player +" Invalid Move");
					}
				});
			}
		}
//				 Only valid Moves
		for (int i = 0; i < 7; i++) {
			GameButton box = array[i][5];
			final int boxcol = 5;
			final int boxrow = i;
			box.setOnAction(e -> {
				Configure(box,boxrow,boxcol);
				// validity.setText("For Player : " + player +" Last Move On [" + boxrow + "] [" + boxcol + "]");
			});
		}
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
//			box.setStyle("-fx-background-color: Yellow;");
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
}
