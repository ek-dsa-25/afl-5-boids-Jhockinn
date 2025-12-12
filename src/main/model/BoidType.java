package main.model;

import main.behavior.BehaviorStrategy;
import main.behavior.FlockBehavior;
import main.behavior.RandomBehavior;

import java.awt.Color;

public enum BoidType {
    // Regnbuens farver
    RED(new Color(255, 0, 0), new FlockBehavior()),
    ORANGE(new Color(255, 165, 0), new FlockBehavior()),
    YELLOW(new Color(255, 255, 0), new FlockBehavior()),
    GREEN(new Color(0, 255, 0), new FlockBehavior()),
    CYAN(new Color(0, 255, 255), new FlockBehavior()),
    BLUE(new Color(0, 0, 255), new FlockBehavior()),
    PURPLE(new Color(128, 0, 128), new FlockBehavior()),
    MAGENTA(new Color(255, 0, 255), new FlockBehavior()),
    
    // Standard og random
    STANDARD(Color.WHITE, new FlockBehavior()),
    RANDOM(Color.YELLOW, new RandomBehavior());

    private final Color color;
    private final BehaviorStrategy behavior;

    BoidType(Color color, BehaviorStrategy behavior) {
        this.color = color;
        this.behavior = behavior;
    }

    public Color getColor() {
        return color;
    }

    public BehaviorStrategy getBehavior() {
        return behavior;
    }
    
    // Returner en tilfældig regnbuefarve (ikke STANDARD eller RANDOM)
    public static BoidType randomRainbow() {
        BoidType[] rainbowTypes = {RED, ORANGE, YELLOW, GREEN, CYAN, BLUE, PURPLE, MAGENTA};
        return rainbowTypes[(int) (Math.random() * rainbowTypes.length)];
    }
}
