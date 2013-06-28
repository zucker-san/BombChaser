import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;


public class CustomParamsField extends JTextField implements KeyListener {
	protected int lowestValueAccepted;
	protected int highestValueAccepted;
	
	CustomParamsField(){
		this.setHorizontalAlignment(JTextField.CENTER);
		this.addKeyListener(this);
	}
	
	protected boolean restrictField(int lowestValue, int highestValue){
		lowestValueAccepted = lowestValue;
		highestValueAccepted = highestValue;
		if(this.getText().isEmpty() || Integer.parseInt(this.getText()) < lowestValue){
			this.setText(Integer.toString(lowestValue));
			return true;
		}
		if(Integer.parseInt(this.getText()) > highestValue){ 
			this.setText(Integer.toString(highestValue));
			return true;
		}
		return false;
	}
	
	public boolean valueIsCorrected(){
		return restrictField(lowestValueAccepted, highestValueAccepted);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!((c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE) 
				|| (c == KeyEvent.VK_ENTER) || (c == KeyEvent.VK_TAB) || (Character.isDigit(c)))) 
			e.consume();
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {}

	@Override
	public void keyReleased(KeyEvent arg0) {}
}
