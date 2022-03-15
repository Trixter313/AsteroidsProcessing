public class Ship {

	// Declaration
	int color;
	float xPos, yPos, x1, y1, x2, y2, x3, y3, angle = 0;

	Ship(int tempColor, float tempX1, float tempY1, float tempX2, float tempY2, float tempX3, float tempY3, App main) {
		color = tempColor;
		xPos = main.width / 2;
		yPos = main.height / 2;
		x1 = tempX1;
		y1 = tempY1;
		x2 = tempX2;
		y2 = tempY2;
		x3 = tempX3;
		y3 = tempY3;
	}

	public void display(App main) {
		main.pushMatrix();
		main.translate(xPos, yPos);
		main.fill(color);
		main.rotate(App.radians(angle));
		main.triangle(x1, y1, x2, y2, x3, y3);
		main.popMatrix();

		// For infinite movement
		if (xPos - x1 <= 0) {
			xPos = main.width - x3;
		} else if (xPos + x1 >= main.width) {
			xPos = 0 + x3;
		} else if (yPos - x1 <= 0) {
			yPos = main.height - x3;
		} else if (yPos + x1 >= main.height) {
			yPos = 0 + x3;
		}

		// For asteroid collision
		for (Asteroid asteroid : main.asteroids) {
			if (asteroid.xPos + asteroid.radius / 1.6 >= xPos + x1
					&& asteroid.xPos - asteroid.radius / 1.6 <= xPos + x1 + (App.abs(x1) + App.abs(x3))
					&& asteroid.yPos + asteroid.radius / 1.6 >= yPos + y1
					&& asteroid.yPos - asteroid.radius / 1.6 <= yPos + y1 + (App.abs(y1) + App.abs(y2))) {
				// if (asteroid.xPos > xPos + x1 && asteroid.xPos < xPos + x1 + (App.abs(x1) +
				// App.abs(x3))
				// && asteroid.yPos > yPos + y1 && asteroid.yPos < yPos + y1 + (App.abs(y1) +
				// App.abs(y2))) {
				main.endGame();
			}
		}
	}

	public void moveShip(char horizontalOrVertical, int unitsToMove) {
		if (horizontalOrVertical == 'h') {
			xPos += unitsToMove;
		} else if (horizontalOrVertical == 'v') {
			// Need logic to correct for when angle is negative and logic is switched
			if (angle >= 0) {
				yPos += unitsToMove;
			} else {
				yPos -= unitsToMove;
			}
			// System.out.println("Vertical movement");
		} else {
			System.out.println(
					"moveShip has invalid 1st parameter. Must be 'h' for horizontal movemnet or 'v' for vertical movement.");
		}
	}
}
