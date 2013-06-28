
public interface CellClickListener {
	
	abstract void plantBombs();
	abstract void openCell(Cell cell);
	abstract void bookFirstClick();
	abstract boolean firstClickIsMade();
	abstract void removeBomb();
	abstract void addBomb();
}
