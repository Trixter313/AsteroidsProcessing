public class Asteroid {
	int color;
	float xVelocity, yVelocity, radius, diameter, xPos, yPos;

	// Constructor
	Asteroid(int tempColor, int asteroidSize, App main) {
		color = tempColor;
		xPos = (float) (Math.random() * main.width);
		yPos = (float) (Math.random() * main.height);
		xVelocity = (float) (Math.random() * (5 - -5 + 1) + -5);
		yVelocity = (float) (Math.random() * (5 - -5 + 1) + -5);
		if (asteroidSize == 1) {
			radius = (float) (main.height * .01);
			diameter = radius * 2;
		} else if (asteroidSize == 2) {
			radius = (float) (main.height * .02);
			diameter = radius * 2;
		} else if (asteroidSize == 3) {
			radius = (float) (main.height * .03);
			diameter = radius * 2;
		}
	}

	public void display(App main) {
		main.fill(color);
		main.ellipse(xPos, yPos, diameter, diameter);
		xPos += xVelocity;
		yPos += yVelocity;

		// For infinte movement
		if (xPos + radius <= 0) {
			xPos = main.width + radius;
		} else if (xPos - radius >= main.width) {
			xPos = 0 - radius;
		} else if (yPos + radius <= 0) {
			yPos = main.height + radius;
		} else if (yPos - radius >= main.height) {
			yPos = 0 - radius;
		}
	}

	public float getXpos() {
		return xPos;
	}

	public float getYpos() {
		return yPos;
	}

	public void scheduleDeletion(App main) {
		main.asteroidIndexToRemove = main.asteroids.indexOf(this);
		main.asteroidNeedsDeletion = true;
	}
}
