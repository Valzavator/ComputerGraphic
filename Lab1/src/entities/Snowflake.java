package entities;


import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.Random;

public class Snowflake extends Circle {
    private static final int DEFAULT_RADIUS = 3;
    private static final int MAX_X_OFFSET = 10;
    private static final int MAX_Y_OFFSET = 20;

    public Snowflake(double x, double y, double radius, Color color) {
        super(x, y, radius);
        this.setFill(color);
    }

    public Snowflake(double x, double y, int radius) {
        this(x, y, radius, Color.WHITE);
    }

    public Snowflake(double x, double y) {
        this(x, y, DEFAULT_RADIUS, Color.WHITE);
    }

    public void changePosition() {
        Random rnd = new Random();
        int xOffset = rnd.nextInt() % 2 == 0
                ? -rnd.nextInt(MAX_X_OFFSET)
                : rnd.nextInt(MAX_X_OFFSET);
        int yOffset = rnd.nextInt(MAX_Y_OFFSET);
        this.setCenterX(getCenterX() + xOffset);
        this.setCenterY(getCenterY() + yOffset);
    }
}
