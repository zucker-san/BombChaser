

public class BombField extends CustomParamsField {
	int height;
	int width;
	HeightField heightField;
	WidthField widthField;
	
	BombField(HeightField heightField, WidthField widthField){
		this.heightField = heightField;
		this.widthField = widthField;
		lowestValueAccepted = 10;
		this.setText(Integer.toString(lowestValueAccepted));
	}
	
	public boolean valueIsCorrected(){
		highestValueAccepted = (int) (Integer.parseInt(heightField.getText()) * 
				Integer.parseInt(widthField.getText()) * 0.9);
		return restrictField(lowestValueAccepted, highestValueAccepted);
	}
}
