public class Particle {
	float xPos, yPos, life, size;
	int color, particleIndexToRemove;
	boolean particleNeedsDeletion = false;

	Particle(int c, float tempXpos, float tempYpos, float tempSize) {
		color = c;
		life = 255;
		xPos = tempXpos;
		yPos = tempYpos;
		size = tempSize;
	}

	public void display(App main) {
		main.fill(color, life);
		main.noStroke();
		main.ellipse(xPos, yPos, size, size);
		xPos += Math.random() * (5 - -5 + 1) + -5;
		yPos += Math.random() * (5 - -5 + 1) + -5;
		life -= 1;
	}
}
