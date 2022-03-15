import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import processing.core.PApplet;

public class App extends PApplet {

	// Declaration
	int score = 0, asteroidIndexToRemove, torpedoIndexToRemove;
	boolean isGameOver = false, hasGameStarted = false, asteroidNeedsDeletion = false, torpedoNeedsDeletion = false,
			scoreNeedsToBeAdded = false;
	UsernameInput usernameInput;
	Ship ship;
	ScoreBoard scoreBoard;
	ArrayList<String> highScoresList = new ArrayList<String>();
	ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
	ArrayList<Torpedo> torpedos = new ArrayList<Torpedo>();
	ParticleSystem shipExplosion;
	Button restartButton;
	String highScoresSortMethod = "";

	public void settings() {
		size(2000, 1300);
		fullScreen();
	}

	public void setup() {
		surface.setTitle("Asteroids");
		surface.setResizable(true);
		// Initialization
		usernameInput = new UsernameInput(width, height);
		ship = new Ship(color(255, 255, 255), -30, -20, -30, 20, 30, 0, this);
		scoreBoard = new ScoreBoard();
		scoreBoard.readScoresFromFile(this);
		shipExplosion = new ParticleSystem(200, ship.xPos, ship.yPos, this);
		restartButton = new Button(color(50, 170, 80), color(255, 255, 255), "RESTART", width - 100, 10, 90, 25);
		reset();
	}

	public void draw() {
		background(0, 0, 0);
		scoreBoard.display(this);

		if (!hasGameStarted) {
			usernameInput.display(this);
		}

		if (hasGameStarted && !isGameOver) {
			ship.display(this);
			for (Asteroid asteroid : asteroids) {
				asteroid.display(this);
			}
			for (Torpedo torpedo : torpedos) {
				torpedo.display(this);
			}
			// Check if all asteroids are destroyed & end game if so
			if (asteroids.isEmpty()) {
				endGame();
			}
		} else if (hasGameStarted && isGameOver) {
			restartButton.display(this);

			// Explode ship
			shipExplosion.display(this);
		}

		// Remove hit asteroid and/or torpedo (Done here instead of in Asteroid deletion
		// method to avoid ConcurrentModificationException. I tried using a delete
		// methid within torpedo but kept running into the same concurrent error)
		if (asteroidNeedsDeletion) {
			asteroids.remove(asteroidIndexToRemove);
			asteroidNeedsDeletion = false;
			torpedos.remove(torpedoIndexToRemove);
			torpedoNeedsDeletion = false;
			// System.out.println(asteroids);
		} else if (torpedoNeedsDeletion) {
			// Special case to delete torpedos that go offscreen
			torpedos.remove(torpedoIndexToRemove);
			torpedoNeedsDeletion = false;
			// System.out.println(torpedos);
		}

		// Change cursor to hand to better show that the button is clickable
		if (mouseX > restartButton.xPos && mouseX < restartButton.xPos + restartButton.width && mouseY > restartButton.yPos
				&& mouseY < restartButton.yPos + restartButton.height && isGameOver) {
			cursor(HAND);
			restartButton.boxColor = color(70, 210, 100);
		} else {
			cursor(ARROW);
			restartButton.boxColor = color(50, 170, 80);
		}
	}

	public void keyPressed() {
		// Used to find key & keyCode of pressed key
		// println("key: " + key + " keyCode: " + keyCode);

		if (!hasGameStarted) {
			// Let user input username
			if (key == BACKSPACE) {
				if (usernameInput.username.length() > 0) {
					usernameInput.username = usernameInput.username.substring(0, usernameInput.username.length() - 1);
				}
			} else if (key == ENTER) {
				hasGameStarted = true;
				reset();
			} else {
				usernameInput.username = usernameInput.username + key;
			}
		} else if (key == CODED) {
			// Rotate ship
			if (keyCode == LEFT) {
				ship.angle -= 90;
				// System.out.println(ship.angle);
			} else if (keyCode == RIGHT) {
				ship.angle += 90;
				// System.out.println(ship.angle);
				// Move ship forward
			} else if (keyCode == UP) {
				// System.out.println(ship.angle);
				if (abs(ship.angle) / 90 % 4 == 0) {
					ship.moveShip('h', 12);
					// System.out.println("Situation 1");
				} else if (abs(ship.angle) / 90 % 4 == 1) {
					ship.moveShip('v', 12);
					// System.out.println("Situation 2");
				} else if (abs(ship.angle) / 90 % 4 == 2) {
					ship.moveShip('h', -12);
					// System.out.println("Situation 3");
				} else if (abs(ship.angle) / 90 % 4 == 3) {
					ship.moveShip('v', -12);
					// System.out.println("Situation 4");
				}
			}
		}
	}

	public void keyReleased() {
		if (hasGameStarted && key == ' ') {
			// System.out.println("Space pressed");
			if (abs(ship.angle) / 90 % 4 == 0) {
				torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos + ship.x3, ship.yPos, 5, 0, this));
				// System.out.println("Situation 1");
			} else if (abs(ship.angle) / 90 % 4 == 1) {
				if (ship.angle >= 0) {
					torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos, ship.yPos + ship.x3, 0, 5, this));
				} else {
					torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos, ship.yPos - ship.x3, 0, -5, this));
				}
				// System.out.println("Situation 2");
			} else if (abs(ship.angle) / 90 % 4 == 2) {
				torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos - ship.x3, ship.yPos, -5, 0, this));
				// System.out.println("Situation 3");
			} else if (abs(ship.angle) / 90 % 4 == 3) {
				if (ship.angle >= 0) {
					torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos, ship.yPos - ship.x3, 0, -5, this));
				} else {
					torpedos.add(new Torpedo(color(255, 0, 0), ship.xPos, ship.yPos + ship.x3, 0, 5, this));
				}
				// System.out.println("Situation 4");
			}
		} else if (hasGameStarted && key == 'h' || key == 'H') {
			if (highScoresSortMethod == "alphabetical") {
				Collections.sort(highScoresList, new Comparator<String>() {
					public int compare(String first, String second) {
						return extractInt(first) - extractInt(second);
					}

					int extractInt(String s) {
						String num = s.replaceAll("\\D", "");
						return Integer.parseInt(num);
					}
				});
				scoreBoard.saveScoresToFile(this);
				highScoresSortMethod = "numerical";
				// System.out.println("Sorted numerically: " + highScoresList);
			} else {
				Collections.sort(highScoresList);
				scoreBoard.saveScoresToFile(this);
				highScoresSortMethod = "alphabetical";
				// System.out.println("Sorted alphabetically: " + highScoresList);
			}
		}
	}

	public void mouseClicked() {
		if (mouseX > restartButton.xPos && mouseX < restartButton.xPos + restartButton.width && mouseY > restartButton.yPos
				&& mouseY < restartButton.yPos + restartButton.height && isGameOver) {
			reset();
		}
	}

	public static void main(String[] args) {
		PApplet.main("App");
	}

	public void reset() {
		asteroids.clear();
		addAsteroids();
		torpedos.clear();
		score = 0;
		isGameOver = false;
	}

	public void endGame() {
		highScoresList.add(usernameInput.username + " - " + score);
		scoreNeedsToBeAdded = false;

		isGameOver = true;
		scoreNeedsToBeAdded = true;
		scoreBoard.saveScoresToFile(this);
	}

	public void addAsteroids() {
		for (int i = 0; i < 5; i++) {
			asteroids.add(new Asteroid(color(156, 58, 80), 1, this));
			asteroids.add(new Asteroid(color(135, 102, 45), 2, this));
			asteroids.add(new Asteroid(color(125, 120, 111), 3, this));
		}
	}
}
