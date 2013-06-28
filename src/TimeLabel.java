import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;


public class TimeLabel extends JLabel{
	private Timer timer;
	private int timeTaken = 0;
	private int delay = 1000;
	
	TimeLabel(){
		this.setPreferredSize(new Dimension(20, 40));
		this.setHorizontalAlignment(CENTER);
		this.setText(Integer.toString(timeTaken));
	}
	
	private void updateLabelText(){
		this.setText(Integer.toString(++timeTaken));
	}
	
	public void startTimer(){
		ActionListener task = new ActionListener(){
            public void actionPerformed(ActionEvent evt){
            	updateLabelText();
            }
        };
		timer = new Timer(delay, task);
		timer.setInitialDelay(0);
		timer.start();
	}
	
	public void resetTimer(){
		timer.stop();
		timeTaken = 0;
		this.setText(Integer.toString(timeTaken));
	}
	
	public void stopTimer(){
		timer.stop();
	}
	
	public boolean timerIsRunning(){
		return timer != null;
	}
	
	public int getTime(){
		return timeTaken;
	}
}
