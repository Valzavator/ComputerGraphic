package app;

import entities.Snowflake;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class JavaFXAPP extends Application {
    private final Color[] colors = {
            Color.GOLD,
            Color.GRAY,
            Color.MAGENTA,
            Color.LIME,
            Color.PURPLE,
            Color.NAVY,
            Color.OLIVE,
            Color.ORANGE,
            Color.YELLOW,
            Color.BLUE,
            Color.RED,
            Color.PINK,
            Color.WHITE
    };

    private static final int MAX_SNOWFLAKES = 50;
    private static final int WIDTH = 600;
    private static final int HEIGHT = 360;

    private final ArrayList<Snowflake> snowflakes = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("MAOKG LAB 1");
        Group root = new Group();
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        scene.setFill(Color.rgb(0, 128, 255));

        Rectangle trunkOfTree = new Rectangle(220, 220, 40, 100);
        root.getChildren().add(trunkOfTree);
        trunkOfTree.setFill(Color.rgb(128, 64, 0));

        Polygon coverOfTree = new Polygon();
        coverOfTree.getPoints().addAll(
                229.0, 8.0,
                292.0, 94.0,
                268.0, 94.0,
                310.0, 161.0,
                276.0, 161.0,
                315.0, 220.0,
                172.0, 220.0,
                209.0, 161.0,
                178.0, 161.0,
                211.0, 86.0,
                188.0, 86.0);
        root.getChildren().add(coverOfTree);
        coverOfTree.setFill(Color.GREEN);

        Rectangle upperLightBulb = new Rectangle(250, 115, 18, 19);
        root.getChildren().add(upperLightBulb);
        upperLightBulb.setFill(Color.YELLOW);

        Rectangle middleLightBulb = new Rectangle(207, 126, 18, 19);
        root.getChildren().add(middleLightBulb);
        middleLightBulb.setFill(Color.YELLOW);

        Rectangle bottomLightBulb = new Rectangle(246, 170, 18, 19);
        root.getChildren().add(bottomLightBulb);
        bottomLightBulb.setFill(Color.YELLOW);

        // special effects

        Timeline timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);

        KeyFrame light = new KeyFrame(Duration.millis(500), event -> {
            Random rnd = new Random();
            upperLightBulb.setFill(colors[rnd.nextInt(colors.length)]);
            middleLightBulb.setFill(colors[rnd.nextInt(colors.length)]);
            bottomLightBulb.setFill(colors[rnd.nextInt(colors.length)]);
        });

        KeyFrame snow = new KeyFrame(Duration.millis(100), event -> {
            Random rnd = new Random();

            for (Snowflake snowflake : snowflakes) {
                snowflake.changePosition();
                if (snowflake.getCenterY() > HEIGHT ||
                        snowflake.getCenterX() < 0 ||
                        snowflake.getCenterX() > WIDTH) {
                    root.getChildren().remove(snowflake);
                    Platform.runLater(() -> snowflakes.remove(snowflake));
                }
            }

            if (snowflakes.size() < MAX_SNOWFLAKES) {
                Snowflake newSf = new Snowflake(rnd.nextInt(WIDTH), 0);
                snowflakes.add(newSf);
                root.getChildren().add(newSf);
            }
        });

        timeline.getKeyFrames().addAll(light, snow);
        timeline.play();

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
