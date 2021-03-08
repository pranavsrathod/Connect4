import javafx.scene.control.Button;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Font;
public class GameButton extends Button{
	public int color;
	public 
	GameButton(){
		// setting the default instances of the button
		super("");
		this.setPrefSize(100, 100);
		this.setStyle("-fx-background-color: Black");
		this.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 15));
		
	}
}
