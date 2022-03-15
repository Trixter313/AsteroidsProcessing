import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ScoreBoard {
	float width = 270, height, xPos = 10, yPos = 20;
	String highScoreText = "";

	public void display(App main) {
		main.fill(255, 255, 255);
		main.textSize(18);
		main.text("Score: " + main.score, 10, main.height - 10);
		main.text("Press H to toggle sort", main.width - 180, main.height - 10);

		main.text("High scores: ", xPos, yPos);
		highScoreText = "";
		for (String nameAndScore : main.highScoresList) {
			highScoreText += nameAndScore;
			highScoreText += "\n";
		}
		main.text(highScoreText, xPos, yPos + 30);
	}

	public void saveScoresToFile(App main) {
		PrintStream scoreFile;
		try {
			scoreFile = new PrintStream("scores.txt");
			for (String nameAndScore : main.highScoresList) {
				scoreFile.println(nameAndScore);
			}
			scoreFile.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void readScoresFromFile(App main) {
		try {
			main.highScoresList = new ArrayList<>(Files.readAllLines(Paths.get("scores.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
