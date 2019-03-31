package lab3;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import lab3.util.PrintingImage;

public class BombBird extends Application {
    private final static int TRANSITION_TIME_MS = 1500;
    private final static int DEFAULT_TIME_MS = 3000;

    private final static boolean USE_BMP_TRAJECTORY = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 1250, 650);
        scene.setFill(Color.rgb(171, 217, 241));

        // BODY

        Ellipse mainBody = new Ellipse();
        mainBody.setCenterX(x(94));
        mainBody.setCenterY(y(128));
        mainBody.setRadiusX(89);
        mainBody.setRadiusY(91);
        mainBody.setFill(Color.BLACK);

        root.getChildren().add(mainBody);

        QuadCurve rightBodyArc = new QuadCurve();
        rightBodyArc.setStartX(x(100));
        rightBodyArc.setStartY(y(219));
        rightBodyArc.setEndX(x(183));
        rightBodyArc.setEndY(y(132));
        rightBodyArc.setControlX(x(181));
        rightBodyArc.setControlY(y(218));
        rightBodyArc.setFill(Color.BLACK);

        root.getChildren().add(rightBodyArc);

        QuadCurve leftBodyArc = new QuadCurve();
        leftBodyArc.setStartX(x(5));
        leftBodyArc.setStartY(y(128));
        leftBodyArc.setEndX(x(37));
        leftBodyArc.setEndY(y(198));
        leftBodyArc.setControlX(x(3));
        leftBodyArc.setControlY(y(175));
        leftBodyArc.setFill(Color.BLACK);

        root.getChildren().add(leftBodyArc);

        // HAIR

        Path blackHairPath = new Path();

        MoveTo hairMoveTo = new MoveTo();
        hairMoveTo.setX(x(84));
        hairMoveTo.setY(y(37));

        QuadCurveTo leftCurve = new QuadCurveTo();
        leftCurve.setX(x(59));
        leftCurve.setY(y(30));
        leftCurve.setControlX(x(75));
        leftCurve.setControlY(y(30));

        QuadCurveTo upperCurve = new QuadCurveTo();
        upperCurve.setX(x(70));
        upperCurve.setY(y(6));
        upperCurve.setControlX(x(53));
        upperCurve.setControlY(y(15));

        QuadCurveTo rightCurve = new QuadCurveTo();
        rightCurve.setX(x(120));
        rightCurve.setY(y(41));
        rightCurve.setControlX(x(99));
        rightCurve.setControlY(y(5));

        blackHairPath.getElements().addAll(hairMoveTo, leftCurve, upperCurve, rightCurve);
        blackHairPath.setFill(Color.BLACK);

        root.getChildren().add(blackHairPath);

        Polygon upperHair = new Polygon(
                x(65), y(25),
                x(63), y(17),
                x(69), y(12),
                x(83), y(15),
                x(76), y(19),
                x(79), y(26)
        );
        upperHair.setFill(Color.rgb(246, 172, 11));

        root.getChildren().add(upperHair);

        Ellipse forehead = new Ellipse();
        forehead.setCenterX(x(119));
        forehead.setCenterY(y(60));
        forehead.setRadiusX(13);
        forehead.setRadiusY(10);
        forehead.setRotate(30);
        forehead.setFill(Color.WHITE);

        root.getChildren().add(forehead);

        // LEFT EYE

        Path backOfLeftEyePath = new Path();
        MoveTo backOfLeftEyeMoveTo = new MoveTo();
        backOfLeftEyeMoveTo.setX(x(80));
        backOfLeftEyeMoveTo.setY(y(100));

        ArcTo backOfLeftEyeArcTo = new ArcTo();
        backOfLeftEyeArcTo.setX(x(60));
        backOfLeftEyeArcTo.setY(y(70));
        backOfLeftEyeArcTo.setRadiusX(19);
        backOfLeftEyeArcTo.setRadiusY(19);
        backOfLeftEyeArcTo.setLargeArcFlag(true);

        QuadCurveTo backOfLeftEyeCurve = new QuadCurveTo();
        backOfLeftEyeCurve.setX(x(80));
        backOfLeftEyeCurve.setY(y(100));
        backOfLeftEyeCurve.setControlX(x(35));
        backOfLeftEyeCurve.setControlY(y(101));

        backOfLeftEyePath.getElements().addAll(backOfLeftEyeMoveTo, backOfLeftEyeArcTo, backOfLeftEyeCurve);
        backOfLeftEyePath.setFill(Color.rgb(67, 67, 67));

        root.getChildren().add(backOfLeftEyePath);

        Path leftEyePath = new Path();
        MoveTo leftEyeMoveTo = new MoveTo();
        leftEyeMoveTo.setX(x(86));
        leftEyeMoveTo.setY(y(87));

        ArcTo leftEyeArcTo = new ArcTo();
        leftEyeArcTo.setX(x(67));
        leftEyeArcTo.setY(y(87));
        leftEyeArcTo.setRadiusX(15);
        leftEyeArcTo.setRadiusY(14);
        leftEyeArcTo.setLargeArcFlag(true);

        QuadCurveTo leftEyeCurveTo = new QuadCurveTo();
        leftEyeCurveTo.setX(x(86));
        leftEyeCurveTo.setY(y(87));
        leftEyeCurveTo.setControlX(x(77));
        leftEyeCurveTo.setControlY(y(80));

        leftEyePath.getElements().addAll(leftEyeMoveTo, leftEyeArcTo, leftEyeCurveTo);
        leftEyePath.setFill(Color.WHITE);
        leftEyePath.setStrokeWidth(2.5);
        leftEyePath.setStroke(Color.BLACK);

        root.getChildren().add(leftEyePath);

        Ellipse leftPupilEllipse = new Ellipse();
        leftPupilEllipse.setCenterX(x(81));
        leftPupilEllipse.setCenterY(y(77));
        leftPupilEllipse.setRadiusX(5);
        leftPupilEllipse.setRadiusY(5);
        leftPupilEllipse.setFill(Color.BLACK);

        root.getChildren().add(leftPupilEllipse);

        Polygon leftEyebrow = new Polygon(
                x(40), y(50),
                x(52), y(41),
                x(95), y(66),
                x(89), y(73)
        );
        leftEyebrow.setFill(Color.rgb(194, 66, 3));

        root.getChildren().add(leftEyebrow);

        // RIGHT EYE

        Path backOfRightEyePath = new Path();
        MoveTo backOfRightEyeMoveTo = new MoveTo();
        backOfRightEyeMoveTo.setX(x(157));
        backOfRightEyeMoveTo.setY(y(100));

        QuadCurveTo backOfRightEyeCurveTo = new QuadCurveTo();
        backOfRightEyeCurveTo.setX(x(166));
        backOfRightEyeCurveTo.setY(y(90));
        backOfRightEyeCurveTo.setControlX(x(168));
        backOfRightEyeCurveTo.setControlY(y(103));

        ArcTo backOfRightEyeArcTo = new ArcTo();
        backOfRightEyeArcTo.setX(x(136));
        backOfRightEyeArcTo.setY(y(93));
        backOfRightEyeArcTo.setRadiusX(15);
        backOfRightEyeArcTo.setRadiusY(15);

        backOfRightEyePath.getElements().addAll(backOfRightEyeMoveTo, backOfRightEyeCurveTo, backOfRightEyeArcTo);
        backOfRightEyePath.setFill(Color.rgb(67, 67, 67));

        root.getChildren().add(backOfRightEyePath);

        Path rightEyePath = new Path();
        MoveTo rightEyeMoveTo = new MoveTo();
        rightEyeMoveTo.setX(x(160));
        rightEyeMoveTo.setY(y(81));

        LineTo rightEyeRightLineTo = new LineTo();
        rightEyeRightLineTo.setX(x(155));
        rightEyeRightLineTo.setY(y(73));

        LineTo rightEyeLeftLineTo = new LineTo();
        rightEyeLeftLineTo.setX(x(140));
        rightEyeLeftLineTo.setY(y(81));

        QuadCurveTo rightEyeLeftCurve = new QuadCurveTo();
        rightEyeLeftCurve.setX(x(142));
        rightEyeLeftCurve.setY(y(88));
        rightEyeLeftCurve.setControlX(x(134));
        rightEyeLeftCurve.setControlY(y(87));

        LineTo rightEyeLowerLineTo = new LineTo();
        rightEyeLowerLineTo.setX(x(152));
        rightEyeLowerLineTo.setY(y(88));

        QuadCurveTo rightEyeRightCurve = new QuadCurveTo();
        rightEyeRightCurve.setX(x(160));
        rightEyeRightCurve.setY(y(81));
        rightEyeRightCurve.setControlX(x(164));
        rightEyeRightCurve.setControlY(y(95));

        rightEyePath.getElements().addAll(
                rightEyeMoveTo,
                rightEyeRightLineTo,
                rightEyeLeftLineTo,
                rightEyeLeftCurve,
                rightEyeLowerLineTo,
                rightEyeRightCurve
        );
        rightEyePath.setFill(Color.WHITE);
        rightEyePath.setStrokeWidth(2.5);
        rightEyePath.setStroke(Color.BLACK);

        root.getChildren().add(rightEyePath);

        Ellipse pupilEllipse = new Ellipse();
        pupilEllipse.setCenterX(x(153));
        pupilEllipse.setCenterY(y(82));
        pupilEllipse.setRadiusX(4);
        pupilEllipse.setRadiusY(4);
        pupilEllipse.setFill(Color.BLACK);

        root.getChildren().add(pupilEllipse);

        Polygon rightEyebrow = new Polygon(
                x(138), y(72),
                x(175), y(58),
                x(181), y(66),
                x(144), y(80)
        );
        rightEyebrow.setFill(Color.rgb(194, 66, 3));

        root.getChildren().add(rightEyebrow);

        // BIRD BEAK

        Path upperBeakPath = new Path();
        MoveTo upperBeakMoveTo = new MoveTo();
        upperBeakMoveTo.setX(x(165));
        upperBeakMoveTo.setY(y(111));

        QuadCurveTo upperBeakRightCurveTo = new QuadCurveTo();
        upperBeakRightCurveTo.setX(x(125));
        upperBeakRightCurveTo.setY(y(90));
        upperBeakRightCurveTo.setControlX(x(158));
        upperBeakRightCurveTo.setControlY(y(94));

        QuadCurveTo upperBeakUpperCurveTo = new QuadCurveTo();
        upperBeakUpperCurveTo.setX(x(95));
        upperBeakUpperCurveTo.setY(y(101));
        upperBeakUpperCurveTo.setControlX(x(108));
        upperBeakUpperCurveTo.setControlY(y(89));

        QuadCurveTo upperBeakLowerCurveTo = new QuadCurveTo();
        upperBeakLowerCurveTo.setX(x(165));
        upperBeakLowerCurveTo.setY(y(111));
        upperBeakLowerCurveTo.setControlX(x(121));
        upperBeakLowerCurveTo.setControlY(y(97));

        upperBeakPath.getElements().addAll(
                upperBeakMoveTo,
                upperBeakRightCurveTo,
                upperBeakUpperCurveTo,
                upperBeakLowerCurveTo
        );

        upperBeakPath.setFill(Color.rgb(255, 173, 0));
        upperBeakPath.setStrokeWidth(2.5);
        upperBeakPath.setStroke(Color.BLACK);

        root.getChildren().add(upperBeakPath);

        Path middleBeakPath = new Path();
        MoveTo middleBeakMoveTo = new MoveTo();
        middleBeakMoveTo.setX(x(128));
        middleBeakMoveTo.setY(y(105));

        QuadCurveTo middleBeakUpperCurveTo = new QuadCurveTo();
        middleBeakUpperCurveTo.setX(x(95));
        middleBeakUpperCurveTo.setY(y(106));
        middleBeakUpperCurveTo.setControlX(x(106));
        middleBeakUpperCurveTo.setControlY(y(98));

        QuadCurveTo middleBeakLeftCurveTo = new QuadCurveTo();
        middleBeakLeftCurveTo.setX(x(99));
        middleBeakLeftCurveTo.setY(y(114));
        middleBeakLeftCurveTo.setControlX(x(94));
        middleBeakLeftCurveTo.setControlY(y(113));

        QuadCurveTo middleBeakLowerCurveTo = new QuadCurveTo();
        middleBeakLowerCurveTo.setX(x(128));
        middleBeakLowerCurveTo.setY(y(105));
        middleBeakLowerCurveTo.setControlX(x(108));
        middleBeakLowerCurveTo.setControlY(y(107));

        middleBeakPath.getElements().addAll(
                middleBeakMoveTo,
                middleBeakUpperCurveTo,
                middleBeakLeftCurveTo,
                middleBeakLowerCurveTo
        );

        middleBeakPath.setFill(Color.WHITE);

        root.getChildren().add(middleBeakPath);

        Path lowerBeakPath = new Path();
        MoveTo lowerBeakMoveTo = new MoveTo();
        lowerBeakMoveTo.setX(x(149));
        lowerBeakMoveTo.setY(y(120));

        QuadCurveTo lowerBeakRightCurveTo = new QuadCurveTo();
        lowerBeakRightCurveTo.setX(x(132));
        lowerBeakRightCurveTo.setY(y(104));
        lowerBeakRightCurveTo.setControlX(x(162));
        lowerBeakRightCurveTo.setControlY(y(107));

        QuadCurveTo lowerBeakLeftCurveTo = new QuadCurveTo();
        lowerBeakLeftCurveTo.setX(x(98));
        lowerBeakLeftCurveTo.setY(y(125));
        lowerBeakLeftCurveTo.setControlX(x(92));
        lowerBeakLeftCurveTo.setControlY(y(116));

        QuadCurveTo lowerBeakLowerCurveTo = new QuadCurveTo();
        lowerBeakLowerCurveTo.setX(x(149));
        lowerBeakLowerCurveTo.setY(y(120));
        lowerBeakLowerCurveTo.setControlX(x(115));
        lowerBeakLowerCurveTo.setControlY(y(152));


        lowerBeakPath.getElements().addAll(
                lowerBeakMoveTo,
                lowerBeakRightCurveTo,
                lowerBeakLeftCurveTo,
                lowerBeakLowerCurveTo
        );

        lowerBeakPath.setFill(Color.rgb(255, 173, 0));

        root.getChildren().add(lowerBeakPath);

        // STOMACH

        Path stomachPath = new Path();

        MoveTo stomachMoveTo = new MoveTo();
        stomachMoveTo.setX(x(32));
        stomachMoveTo.setY(y(192));

        QuadCurveTo upperStomach = new QuadCurveTo();
        upperStomach.setX(x(163));
        upperStomach.setY(y(192));
        upperStomach.setControlX(x(97));
        upperStomach.setControlY(y(132));

        QuadCurveTo lowerStomach = new QuadCurveTo();
        lowerStomach.setX(x(32));
        lowerStomach.setY(y(192));
        lowerStomach.setControlX(x(102));
        lowerStomach.setControlY(y(241));

        stomachPath.getElements().addAll(stomachMoveTo, upperStomach, lowerStomach);
        stomachPath.setFill(Color.rgb(67, 67, 67));

        root.getChildren().add(stomachPath);

        // ANIMATION

        PrintingImage pr = new PrintingImage();
        Path path = USE_BMP_TRAJECTORY
                ? pr.getPathFromBmpFile(
                "C:\\Users\\Valzavator\\IdeaProjects\\MAOKG\\Lab3\\resources\\bird_trajectory.bmp")
                : null;

        PathTransition pathTransition = new PathTransition(Duration.millis(5000), path, root);
        pathTransition.setAutoReverse(true);
        pathTransition.setCycleCount(Timeline.INDEFINITE);

        TranslateTransition bottomRight = buildTranslateTransition(
                root,
                0,
                0,
                450,
                150,
                TRANSITION_TIME_MS);

        TranslateTransition topRight = buildTranslateTransition(
                root,
                0,
                0,
                450,
                -150,
                TRANSITION_TIME_MS);

        TranslateTransition topLeft = buildTranslateTransition(
                root,
                0,
                0,
                -450,
                -150,
                TRANSITION_TIME_MS);

        TranslateTransition bottomLeft = buildTranslateTransition(
                root,
                0,
                0,
                -450,
                150,
                TRANSITION_TIME_MS);

        SequentialTransition translateTransitions = new SequentialTransition();
        translateTransitions.getChildren().addAll(
                bottomRight,
                bottomLeft,
                topRight,
                topLeft
        );
        translateTransitions.setCycleCount(Timeline.INDEFINITE);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(DEFAULT_TIME_MS), root);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(DEFAULT_TIME_MS), root);
        scaleTransition.setToX(2);
        scaleTransition.setToY(2);
        scaleTransition.setFromX(0.5);
        scaleTransition.setFromY(0.5);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().add(
                USE_BMP_TRAJECTORY ? pathTransition : translateTransitions
        );
        parallelTransition.getChildren().add(scaleTransition);
        parallelTransition.getChildren().add(rotateTransition);

        parallelTransition.play();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Bomb-bird");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private double x(double originalX) {
        return originalX + 531;
    }

    private double y(double originalY) {
        return originalY + 198;
    }

    private TranslateTransition buildTranslateTransition(
            Node node,
            int fromX,
            int fromY,
            int toX,
            int toY,
            int duration) {
        TranslateTransition newTransition = new TranslateTransition(Duration.millis(duration), node);
        newTransition.setFromX(fromX);
        newTransition.setFromY(fromY);
        newTransition.setToX(toX);
        newTransition.setToY(toY);
        newTransition.setCycleCount(2);
        newTransition.setAutoReverse(true);
        return newTransition;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
