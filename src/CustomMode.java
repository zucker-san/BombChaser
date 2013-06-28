
public class CustomMode extends GameMode{
	
	CustomMode(){
		name = "Custom";
	}
	
	public void setModeParams(int rows, int columns, int bombs){
		this.rows = rows;
		this.columns = columns;
		this.bombQuantity = bombs;
	}
}
