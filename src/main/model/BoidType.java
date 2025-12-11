package main.model;

import main.behavior.BehaviorStrategy;
import main.behavior.FlockBehavior;
import main.behavior.RandomBehavior;

import java.awt.Color;

public enum BoidType {
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
}
