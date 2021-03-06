import java.util.HashMap;
import java.util.Stack;
import javafx.application.Application;
import javafx.scene.layout.BorderPane;
import javafx.scene.Scene;
import javafx.scene.text.Font; 
import javafx.scene.text.FontPosture; 
import javafx.scene.text.FontWeight;
import javafx.scene.control.Button;
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
//	private Stack<GameButton> stack_Buttons;
	private HBox moveBar;
	Grid object;
	private VBox root;
	private GridPane board;
//	private int player;
//	GameButton array[][];
	
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
		object = new Grid();
		object.setButtonConfigurations();
		board = object.getBoard();
		Label label = new Label("MOVE : ");
		label.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		

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
		reverse.setOnAction(e -> object.reverse());
		moveBar = new HBox(10, label, object.getValidity());	
		// Border Pane execution
		BorderPane pane = new BorderPane();
		pane.setCenter(board);
		pane.setStyle("-fx-background-color: Black;");
		return new Scene(new VBox(20, menu, board, moveBar), 600, 600);
		//return new Scene(object, 600, 600);
	}
}
