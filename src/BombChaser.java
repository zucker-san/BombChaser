import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;



public class BombChaser {
	public static void createGUI(){
		final JFrame frame = new JFrame("BombChaser");
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Гра");
		JMenuItem newGameItem = new JMenuItem("Нова гра");
		JMenuItem paramsItem = new JMenuItem("Параметри");
		//JMenuItem statsItem = new JMenuItem("Статистика");
		JMenuItem exitItem = new JMenuItem("Вихід");
		menu.add(newGameItem);
		menu.add(paramsItem);
		//menu.add(statsItem);
		menu.addSeparator();
		menu.add(exitItem);
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);
		
		final Game game = new Game(frame);
		
		frame.setIconImage(ResourceLoader.loadImage("miniBomb.jpg"));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		JFrame.setDefaultLookAndFeelDecorated(true);
		
		game.start(new MediumMode());
		Parameters paramsFrame = new Parameters(game);
		paramsItem.addActionListener(paramsFrame);

		newGameItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(game.inProgress()){
					ExitMessage message = new ExitMessage(frame);
					if(message.getAnswer() == JOptionPane.YES_OPTION){
						game.end();
						game.start(game.getCurrentMode());
					}
				}
				else{
					game.end();
					game.start(game.getCurrentMode());
				}
			}
		});
		
		exitItem.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(game.inProgress()){
					ExitMessage message = new ExitMessage(frame);
					if(message.getAnswer() == JOptionPane.YES_OPTION){
						System.exit(0);
					}
				}
				else System.exit(0);
			}
		});
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		javax.swing.SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				createGUI();
			}
		});
	}
}
