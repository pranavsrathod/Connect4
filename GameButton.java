import javafx.scene.control.Button;
public class GameButton extends Button{
	
	GameButton(){
		super("");
		this.setPrefSize(100, 100);
		this.setOnAction(e -> this.setStyle("-fx-background-color: darkBlue;"));
		
	}
}
