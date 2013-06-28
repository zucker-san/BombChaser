import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import javax.swing.plaf.metal.MetalButtonUI;


public class Cell extends JButton implements MouseListener{
	private int cellSide = 20;
	private int cellValue = 0;
	private Dimension cellIndex;
	private Cell startingPoint;
	private CellClickListener clickListener = null;
	private boolean flagged = false;
	
	public Cell(){
		this.setPreferredSize(new Dimension(cellSide, cellSide));
		this.setMargin(new Insets (1, 1, 1, 1));
		this.addMouseListener(this);
	}
	
	public int getCellSide(){
		return cellSide;
	}
	
	public int getValue(){
		return cellValue;
	}
	
	public void setValue(int value){
		this.cellValue = value;
	}
	
	public void setIndex(int row, int column){
		this.cellIndex = new Dimension(row, column);
	}
	
	public Dimension getIndex(){
		return this.cellIndex;
	}

	public boolean isStaringPoint(){
		if(this.equals(startingPoint)){
			return true;
		}
		return false;
	}
	
	public void setClickListener(CellClickListener listener){
		this.clickListener = listener;
	}
	
	public void checkCell(){
		clickListener.openCell(this);
	}
	
	public void setBombCountText(final int bombQuantity){
		this.setText(Integer.toString(bombQuantity));
		this.setUI(new MetalButtonUI(){
			@Override
			protected Color getDisabledTextColor(){
				if(bombQuantity == 1) return Color.BLUE;
				if(bombQuantity == 2) return Color.GREEN.darker();
				if(bombQuantity == 3) return Color.RED.darker();
				if(bombQuantity == 4) return Color.DARK_GRAY.darker();
				if(bombQuantity == 5) return Color.ORANGE.darker();
				if(bombQuantity == 6) return Color.YELLOW.darker();
				if(bombQuantity == 7) return Color.MAGENTA.darker();
				if(bombQuantity == 8) return Color.PINK.darker();
				return null;
			}
		});
		this.setEnabled(false);
	}
	
	public boolean isFlagged(){
		return flagged;
	}
	
	private void flagCell(){
		this.setIcon(new ImageIcon(ResourceLoader.loadImage("flag.jpg")));
		this.setDisabledIcon(new ImageIcon(ResourceLoader.loadImage("flag.jpg")));
		this.setEnabled(false);
		flagged = true;
		clickListener.removeBomb();
	}
	
	private void unflagCell(){
		this.setIcon(null);
		this.setEnabled(true);
		flagged = false;
		clickListener.addBomb();
	}
	
	public void fireCell(){
		if(flagged){
			setIcon(null);
		}
		setIcon(new ImageIcon(ResourceLoader.loadImage("boom.jpg")));
		setDisabledIcon(new ImageIcon(ResourceLoader.loadImage("boom.jpg")));
	}
	
	public void setTotallyDisabled(){
		if(flagged){
			flagged = false;
		} else
		setEnabled(false);
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		if(SwingUtilities.isLeftMouseButton(me) && this.isEnabled()){
			if(!clickListener.firstClickIsMade()){
				clickListener.bookFirstClick();
				startingPoint = this;
				clickListener.plantBombs();
			}
			checkCell();
		}
		
		if(SwingUtilities.isRightMouseButton(me)){
			if(this.isEnabled() && !flagged) flagCell();
			else if(flagged) unflagCell();
		}
	}
	

	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
