import javafx.scene.control.Button;
public class GameButton extends Button{
	
	GameButton(){
		super("");
		this.setPrefSize(100, 100);
		this.setStyle("-fx-background-color: #FFFFE0");
		this.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
	}
}
