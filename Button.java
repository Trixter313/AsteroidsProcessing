public class Button {

	int boxColor, textColor;
	float xPos, yPos, width, height;
	String text;

	Button(int boxC, int textC, String tempText, float tempXpos, float tempYpos, float tempWidth, float tempHeight) {
		boxColor = boxC;
		textColor = textC;
		text = tempText;
		xPos = tempXpos;
		yPos = tempYpos;
		width = tempWidth;
		height = tempHeight;
	}

	public void display(App main) {
		main.fill(boxColor);
		main.rect(xPos, yPos, width, height);
		main.fill(textColor);
		main.text(text, xPos + 4, yPos + 20);
	}
}
