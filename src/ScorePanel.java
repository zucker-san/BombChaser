import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class ScorePanel extends JPanel{
	//private GroupLayout grl = new GroupLayout(this);
	private TimeLabel timeLabel = new TimeLabel();
	private JLabel clockIconLabel = new JLabel();
	private BombCountLabel bombCountLabel = new BombCountLabel();
	private JLabel bombIconLabel = new JLabel();
	
	ScorePanel(){
		//this.setLayout(grl);
		this.setLayout(new GridLayout(1, 0, 0, 0));
		this.setPreferredSize(new Dimension(120, 40));
		setLabelIcons();
		addElements();
	}
	
	private void setLabelIcons(){
		clockIconLabel.setPreferredSize(new Dimension(40, 40));
		bombIconLabel.setPreferredSize(new Dimension(40, 40));
		clockIconLabel.setHorizontalAlignment(JLabel.RIGHT);
		bombIconLabel.setHorizontalAlignment(JLabel.LEFT);
		clockIconLabel.setIcon(new ImageIcon(ResourceLoader.loadImage("clock.png")));
		bombIconLabel.setIcon(new ImageIcon(ResourceLoader.loadImage("bomb.png")));
	}
	
	/*private void addElements(){
		//grl.setAutoCreateContainerGaps(true);
		grl.setAutoCreateGaps(true);
		
		GroupLayout.SequentialGroup horizontal = grl.createSequentialGroup();
		horizontal.addComponent(clockIconLabel);
		horizontal.addComponent(timeLabel);
		horizontal.addComponent(bombCountLabel);
		horizontal.addComponent(bombIconLabel);
		
		GroupLayout.ParallelGroup vertical = grl.createParallelGroup();
		vertical.addComponent(clockIconLabel);
		vertical.addComponent(timeLabel);
		vertical.addComponent(bombCountLabel);
		vertical.addComponent(bombIconLabel);
		
		grl.setHorizontalGroup(horizontal);
		grl.setVerticalGroup(vertical);
		grl.linkSize(SwingUtilities.VERTICAL, clockIconLabel, timeLabel, bombCountLabel, bombIconLabel);
		grl.linkSize(SwingUtilities.HORIZONTAL, timeLabel, bombCountLabel);
		grl.linkSize(SwingUtilities.HORIZONTAL, clockIconLabel, bombIconLabel);
	}*/
	
	private void addElements(){
		this.add(clockIconLabel);
		this.add(timeLabel);
		this.add(bombCountLabel);
		this.add(bombIconLabel);
	}
	
	public TimeLabel getTimeLabel(){
		return timeLabel;
	}
	
	public BombCountLabel getBombCountLabel(){
		return bombCountLabel;
	}
}
