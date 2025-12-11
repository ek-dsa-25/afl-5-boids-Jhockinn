package main.behavior;

import main.model.Boid;
import main.simulation.Forces;
import main.simulation.Vector2D;

import java.util.List;
import java.util.Random;

/**
 * RandomBehavior gør at boids bevæger sig mere tilfældigt
 * ved at ændre deres retning med små tilfældige ændringer.
 */
public class RandomBehavior implements BehaviorStrategy {
    private final Random random;
    private final double turnStrength;

    public RandomBehavior() {
        this(0.05);
    }

    public RandomBehavior(double turnStrength) {
        this.random = new Random();
        this.turnStrength = turnStrength;
    }

    @Override
    public Forces calculateForces(Boid boid, List<Boid> neighbors) {
        // Tilfældig drejevinkel mellem -15 og +15 grader
        double angleChange = (random.nextDouble() - 0.5) * Math.PI / 6;
        
        // Beregn nuværende hastighed
        double vx = boid.getVx();
        double vy = boid.getVy();
        
        // Rotér hastighedsvektoren med den tilfældige vinkel
        double newVx = vx * Math.cos(angleChange) - vy * Math.sin(angleChange);
        double newVy = vx * Math.sin(angleChange) + vy * Math.cos(angleChange);
        
        // Beregn ændringen som en kraft
        double forceX = (newVx - vx) * turnStrength;
        double forceY = (newVy - vy) * turnStrength;
        
        // Returner kraften som separation (vi misbruger bare feltet)
        return new Forces(new Vector2D(forceX, forceY), Vector2D.ZERO, Vector2D.ZERO);
    }
}
