import java.awt.Dimension;

import javax.swing.JLabel;


public class BombCountLabel extends JLabel{
	private int bombs;
	private GameMode gameMode;
	
	BombCountLabel(){
		this.setPreferredSize(new Dimension(20, 40));
		this.setHorizontalAlignment(CENTER);
	}
	
	public void setGameMode(GameMode gameMode){
		bombs = gameMode.getBombQuantity();
		this.setText(Integer.toString(bombs));
		this.gameMode = gameMode;
	}
	
	public void removeBomb(){
		setText(Integer.toString(--bombs));
	}
	
	public void addBomb(){
		setText(Integer.toString(++bombs));
	}
}
