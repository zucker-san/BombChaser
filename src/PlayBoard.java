import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.swing.JPanel;
import javax.swing.Timer;


public class PlayBoard extends JPanel implements CellClickListener{
	private int columns;
	private int rows;
	private Cell cell = new Cell();
	private GameMode gameMode;
	private Random random = new Random(); 
	private Cell[][] cells;
	private Game game;
	private boolean firstClick = true;
	private int openCells = 0;
	private boolean gameWon = false;
	
	public PlayBoard(GameMode newGameMode, Game game){
		this.game = game;
		gameMode = newGameMode;
		columns = gameMode.getColumns();
		rows = gameMode.getRows();
		if(paramsAreSet()){
			this.setLayout(new GridLayout(rows, columns, 0, 0));
			this.setPreferredSize(new Dimension(columns*cell.getCellSide(), rows*cell.getCellSide()));
			cells = new Cell[rows][columns];
			drawPlayBoard();
		}
	}
	
	private void drawPlayBoard(){
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				Cell newCell = new Cell();
				newCell.setClickListener(this);
				newCell.setIndex(row, column);
				this.add(newCell);	
				cells[row][column] = newCell;
			}
		}
	}
	
	@Override
	public void plantBombs(){
		int bombsPlanted = 0;
		while(bombsPlanted < gameMode.getBombQuantity()){
			int row = random.nextInt(rows);
			int column = random.nextInt(columns);
			if(cells[row][column].getValue() == 1 || cells[row][column].isStaringPoint()){
				continue;
			}
			else {
				cells[row][column].setValue(1);
				bombsPlanted++;
			}
			game.changeGameProgress(true);
		}
	}
	
	@Override
	public void openCell(Cell cell){
		if(cell.getValue() == 1){
			game.getScorePanel().getTimeLabel().stopTimer();
			showBombs();
			if(game.getSoundState()) playSound();
			game.isLost();
			return;
		}
		if(cell.getValue() == 0 && cell.isEnabled()){
			openCells++;
			if(openCells == (rows*columns - gameMode.getBombQuantity())){
				gameWon = true;
				showBombs();
				game.isWon();
				return;
			}
			int row = cell.getIndex().width;
			int column = cell.getIndex().height;
			int bombsAround = 0;
			for(int r = row - 1; r <= row + 1; r++){
				if(r < 0 || r >= rows) continue;
				for(int c = column - 1; c <= column + 1; c++){
					if(c < 0 || c >= columns || (r == rows && c == columns)) continue;
					if(cells[r][c].getValue() == 1){
						bombsAround++;
					}
				}
			}
			cell.setBombCountText(bombsAround);
			if (bombsAround == 0){
				for(int r = row - 1; r <= row + 1; r++){
					if(r < 0 || r >= rows) continue;
					for(int c = column - 1; c <= column + 1; c++){
						if(c < 0 || c >= columns || (r == rows && c == columns)) continue;
						cells[r][c].checkCell();
					}
				}
			}
		}
		
		/*int openCells = 0;
		for(Cell r[] : cells){
			for(Cell c : r){
				if(c.getValue() == 0 && !c.isEnabled() && !c.isFlagged()){
					openCells++;
					if(openCells == (rows*columns - gameMode.getBombQuantity())) game.isWon();
				}
			}
		}*/
	}
	
	private void showBombs(){
		for(int row = 0; row < rows; row++){
			for(int column = 0; column < columns; column++){
				if(cells[row][column].getValue() == 1 && !gameWon)
					cells[row][column].fireCell();
				cells[row][column].setTotallyDisabled();
			}
		}
	}
	
	private synchronized void playSound(){
		try{
			URL url = this.getClass().getClassLoader().getResource("Explosion.wav");
		    AudioInputStream sound = 
		    	AudioSystem.getAudioInputStream(url);
		    DataLine.Info info = new DataLine.Info(Clip.class, sound.getFormat());
		    Clip clip = (Clip) AudioSystem.getLine(info);
		    clip.open(sound);
		    clip.start();
		    }
		catch (Exception e){
			  e.printStackTrace();
			  }
		}
	
	private boolean paramsAreSet(){
		if(rows != 0 && columns != 0 && gameMode.getBombQuantity() != 0){
			return true;
		}
		return false;
	}

	@Override
	public void bookFirstClick() {
		firstClick = false;	
		game.getScorePanel().getTimeLabel().startTimer();
	}
	
	@Override
	public boolean firstClickIsMade(){
		if(firstClick){
			return false;
		}
		return true;
	}

	@Override
	public void removeBomb() {
		game.getScorePanel().getBombCountLabel().removeBomb();
	}

	@Override
	public void addBomb() {
		game.getScorePanel().getBombCountLabel().addBomb();
	}
}
