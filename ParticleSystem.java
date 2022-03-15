import java.util.ArrayList;

public class ParticleSystem {
	ArrayList<Particle> particles = new ArrayList<Particle>();
	ArrayList<Particle> particlesToRemove = new ArrayList<Particle>();
	float xPos, yPos;

	ParticleSystem(int numberOfParticles, float tempXpos, float tempYpos, App main) {
		xPos = tempXpos;
		yPos = tempYpos;
		for (int i = 0; i < numberOfParticles; i++) {
			particles.add(new Particle(main.color(245, 200, 200), xPos, yPos, 3));
		}
	}

	public void display(App main) {
		for (Particle particle : particles) {
			particle.display(main);
			if (particle.life <= 0) {
				particlesToRemove.add(particle);
			}
		}

		particles.removeAll(particlesToRemove);
	}

}
