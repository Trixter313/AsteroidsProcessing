public class Torpedo {

	int color, explosionTimer = 0;
	float xVelocity, yVelocity, radius, diameter, xPos, yPos;
	ParticleSystem asteroidExplosion;

	// Constructor
	Torpedo(int tempColor, float tempXpos, float tempYpos, float tempXvelocity, float tempYvelocity, App main) {
		color = tempColor;
		xPos = tempXpos;
		yPos = tempYpos;
		xVelocity = tempXvelocity;
		yVelocity = tempYvelocity;
		radius = (float) (main.height * .004);
		diameter = radius * 2;
	}

	public void display(App main) {
		main.fill(color);
		main.ellipse(xPos, yPos, diameter, diameter);
		xPos += xVelocity;
		yPos += yVelocity;

		// For asteroid collision
		for (Asteroid asteroid : main.asteroids) {
			if (App.dist(xPos, yPos, asteroid.xPos, asteroid.yPos) < radius + asteroid.radius) {
				main.score += 1;
				asteroid.scheduleDeletion(main);
				this.scheduleDeletion(main);

				// Create particle system
				asteroidExplosion = new ParticleSystem(50, asteroid.xPos, asteroid.yPos, main);
				explosionTimer = 255;
			}

			// Display asteroidExplosion particle system only if it has been created.
			// I'm still having troubles with this... I suspect the life of the system or
			// the length of displaying is making things weird but I set it to 255 at
			// creation of a new system so I'm not sure what the issue is
			if (explosionTimer > 0) {
				asteroidExplosion.display(main);
				explosionTimer -= 1;
			}

			// For scheduling deletion of offscreen torpedos
			if (xPos + radius <= 0 || xPos - radius >= main.width || yPos + radius <= 0 || yPos - radius >= main.height) {
				this.scheduleDeletion(main);
			}
		}
	}

	public void scheduleDeletion(App main) {
		main.torpedoIndexToRemove = main.torpedos.indexOf(this);
		main.torpedoNeedsDeletion = true;
	}
}
