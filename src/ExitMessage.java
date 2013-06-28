import javax.swing.JFrame;
import javax.swing.JOptionPane;


public class ExitMessage extends JOptionPane{
	private int answer;
	
	ExitMessage(JFrame frame){
		Object[] options = {"Ну, да", "Ой, нє"};
		answer = JOptionPane.showOptionDialog(frame, "Невже все?", "?",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, 
				null, options, options[0]);
	}
	
	public int getAnswer(){
		return answer;
	}
}
