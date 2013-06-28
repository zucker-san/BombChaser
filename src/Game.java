import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;



public class Game {
	private JFrame frame;
	private GameMode currentGameMode;
	private PlayBoard playBoard;
	private ScorePanel scorePanel = new ScorePanel();
	private boolean soundOn = true;
	private boolean inProgress = false;
	
	Game(final JFrame frame){
		this.frame = frame;
		frame.addWindowListener(new WindowAdapter(){
			public void windowIconified(WindowEvent e){
				scorePanel.getTimeLabel().stopTimer();
			}
			public void windowDeiconified(WindowEvent e){
				scorePanel.getTimeLabel().startTimer();
			}
			public void windowClosing(WindowEvent e){
				if(inProgress){
					ExitMessage message = new ExitMessage(frame);
					if(message.getAnswer() == JOptionPane.YES_OPTION){
						System.exit(0);
					}
				}
				else System.exit(0);
			}
		});
	}
	
	public void start(GameMode gameMode){
		currentGameMode = gameMode;
		playBoard = new PlayBoard(gameMode, this);
		frame.getContentPane().add(playBoard, BorderLayout.NORTH);
		frame.getContentPane().add(scorePanel, BorderLayout.SOUTH);
		scorePanel.getBombCountLabel().setGameMode(gameMode);
		frame.getContentPane().setPreferredSize(new Dimension(playBoard.getPreferredSize().width, 
				playBoard.getPreferredSize().height + scorePanel.getPreferredSize().height));
		frame.toFront();
		frame.pack();
	}
	
	public void end(){
		if(scorePanel.getTimeLabel().timerIsRunning()) scorePanel.getTimeLabel().resetTimer();
		inProgress = false;
		frame.getContentPane().removeAll();
	}
	
	public void isWon(){
		scorePanel.getTimeLabel().stopTimer();
		Object[] messageText = {"Усі врятовані!" + "\n" + "Всього за " + 
				Integer.toString(scorePanel.getTimeLabel().getTime()) + " сек."};
		Object[] options = {"Ще раз так хочу", "Та мабуть досить"};
		int answer = JOptionPane.showOptionDialog(frame, messageText, "Epic Win!", 
				JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(ResourceLoader.loadImage("fuck_yea.gif")), options, options[0]);
		if (answer == JOptionPane.YES_OPTION){
			end();
			start(currentGameMode);
		}
		if (answer == JOptionPane.NO_OPTION){
			System.exit(0);
		}
		inProgress = false;
	}
	
	public void isLost(){
		scorePanel.getTimeLabel().stopTimer();
		Object[] options = {"Так, а ну ще раз", "Та ну вас"};
		int answer = JOptionPane.showOptionDialog(frame, "Яка невдача!\n" + "Втрачено " + 
				Integer.toString(scorePanel.getTimeLabel().getTime()) + " сек. життя", 
				"*FACEPALM*", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, 
				new ImageIcon(ResourceLoader.loadImage("trollface.gif")), options, options[0]);
		if (answer == JOptionPane.YES_OPTION){
			end();
			start(currentGameMode);
		}
		if(answer == JOptionPane.NO_OPTION) {
			System.exit(0);
		}
		inProgress = false;
	}
	
	public PlayBoard getPlayBoard(){
		return playBoard;
	}
	
	public GameMode getCurrentMode(){
		return currentGameMode;
	}
	
	public JFrame getMainFrame(){
		return frame;
	}
	
	public void switchSound(){
		soundOn = !soundOn;
	}
	
	public boolean getSoundState(){
		return soundOn;
	}
	
	public ScorePanel getScorePanel(){
		return scorePanel;
	}
	
	public boolean inProgress(){
		return inProgress;
	}
	
	public void changeGameProgress(boolean b){
		inProgress = b;
	}
}
