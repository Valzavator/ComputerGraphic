package lab3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PrintingImage extends Application {

    private HeaderBitmapImage image; // приватне поле, яке зберігає об'єкт з інформацією про заголовок зображення
    private int numberOfPixels; // приватне поле для збереження кількості пікселів з чорним кольором

    public PrintingImage() {
    }

    public PrintingImage(HeaderBitmapImage image) // перевизначений стандартний конструктор
    {
        this.image = image;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        ReadingImageFromFile.loadBitmapImage("C:\\Users\\Valzavator\\IdeaProjects\\MAOKG\\Lab3\\resources\\trajectory.bmp");
        this.image = ReadingImageFromFile.pr.image;
        int width = (int) this.image.getWidth();
        int height = (int) this.image.getHeight();
        int half = (int) image.getHalfOfWidth();

        Group root = new Group();
        Scene scene = new Scene(root, 1200, 600);
        Circle cir;

        int let = 0;
        int let1 = 0;
        int let2 = 0;
        char[][] map = new char[width][height];
        // виконуємо зчитування даних про пікселі
        BufferedInputStream reader = new BufferedInputStream(new FileInputStream("pixels.txt"));


        for (int i = 0; i < height; i++)     // поки не кінець зображення по висоті
        {
            for (int j = 0; j < half; j++)         // поки не кінець зображення по довжині
            {
                let = reader.read();  // зчитуємо один символ з файлу
                let1 = let;
                let2 = let;
                let1 = let1 & (0xf0);   // старший байт - перший піксель
                let1 = let1 >> 4;       // зсув на 4 розряди
                let2 = let2 & (0x0f);   // молодший байт - другий піксель
                if (j * 2 < width) // так як 1 символ кодує 2 пікселі нам необхідно пройти до середини ширини зображення
                {
                    cir = new Circle((j) * 2, (height - 1 - i), 1, Color.valueOf((returnPixelColor(let1)))); // за допомогою стандартного
                    // примітива Коло радіусом в 1 піксель та кольором визначеним за допомогою методу returnPixelColor малюємо піксель
                    //root.getChildren().add(cir); //додаємо об'єкт в сцену
                    if (returnPixelColor(let1) == "BLACK") // якщо колір пікселя чорний, то ставимо в масиві 1
                    {
                        map[j * 2][height - 1 - i] = '1';
                        numberOfPixels++; // збільшуємо кількість чорних пікселів
                    } else {
                        map[j * 2][height - 1 - i] = '0';
                    }
                }
                if (j * 2 + 1 < width) // для другого пікселя
                {
                    cir = new Circle((j) * 2 + 1, (height - 1 - i), 1, Color.valueOf((returnPixelColor(let2))));
                    //root.getChildren().add(cir);
                    if (returnPixelColor(let2) == "BLACK") {
                        map[j * 2 + 1][height - 1 - i] = '1';
                        numberOfPixels++;
                    } else {
                        map[j * 2 + 1][height - 1 - i] = '0';
                    }
                }
            }
        }
        primaryStage.setScene(scene); // ініціалізуємо сцену
        primaryStage.show(); // візуалізуємо сцену

        reader.close();

        int[][] black;
        black = new int[numberOfPixels][2];
        int lich = 0;

        BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("map.txt")); // записуємо карту для руху по траекторії в файл
        for (int i = 0; i < height; i++)     // поки не кінець зображення по висоті
        {
            for (int j = 0; j < width; j++)         // поки не кінець зображення по довжині
            {
                if (map[j][i] == '1') {
                    black[lich][0] = j;
                    black[lich][1] = i;
                    lich++;
                }
                writer.write(map[j][i]);
            }
            writer.write(10);
        }
        writer.close();

        System.out.println("number of black color pixels = " + numberOfPixels);

//        Path path2 = new Path();
//        for (int l = 0; l < numberOfPixels - 1; l++) {
//            path2.getElements().addAll(
//                    new MoveTo(black[l][0], black[l][1]),
//                    new LineTo(black[l + 1][0], black[l + 1][1])
//            );
//        }

//        final Rectangle rectPath = new Rectangle(0, 0, 60, 60);
//        rectPath.setArcHeight(10);
//        rectPath.setArcWidth(10);
//        rectPath.setFill(Color.ORANGE);
//        root.getChildren().add(rectPath);
//
//        PathTransition pathTransition = new PathTransition();
//        pathTransition.setDuration(Duration.millis(5000));
//        pathTransition.setPath(path2);
//        pathTransition.setNode(rectPath);
//        pathTransition.play();
//
//        Arc arc1 = new Arc(40, 340, 40, 40, 30, 200);
//        arc1.setFill(Color.YELLOW);
//        root.getChildren().add(arc1);
//
//        Arc arc2 = new Arc(42, 340, 40, 40, 160, 170);
//        arc2.setFill(Color.YELLOW);
//        root.getChildren().add(arc2);
//
//        Circle circ = new Circle(40, 340, 10, Color.WHITE);
//        circ.setStroke(Color.BLACK);
//        circ.setStrokeWidth(2);
//        root.getChildren().add(circ);
//
//        Arc arc3 = new Arc(40, 340, 10, 10, 30, 150);
//        arc1.setFill(Color.YELLOW);
//        root.getChildren().add(arc3);
//
//        Circle cir1 = new Circle(40, 340, 3, Color.BLACK);
//        root.getChildren().add(cir1);
//
//        RotateTransition rotForArc1 =
//                new RotateTransition(Duration.millis(500), arc1);
//        rotForArc1.setByAngle(20f);
//        rotForArc1.setCycleCount(20);
//        rotForArc1.setAutoReverse(true);
//
//        RotateTransition rotForArc2 =
//                new RotateTransition(Duration.millis(500), arc2);
//        rotForArc2.setByAngle(-20f);
//        rotForArc2.setCycleCount(20);
//        rotForArc2.setAutoReverse(true);
//
//        TranslateTransition tran = new TranslateTransition(Duration.seconds(4.0), arc3);
//        tran.setFromX(0);
//        tran.setToX(200);
//        tran.play();
//
//        TranslateTransition tran4 = new TranslateTransition(Duration.seconds(4.0), cir1);
//        tran4.setFromX(0);
//        tran4.setToX(200);
//        tran4.play();
//
//        TranslateTransition tran1 = new TranslateTransition(Duration.seconds(4.0), arc1);
//        tran1.setFromX(0);
//        tran1.setToX(200);
//        tran1.play();
//
//        TranslateTransition tran2 = new TranslateTransition(Duration.seconds(4.0), arc2);
//        tran2.setFromX(0);
//        tran2.setToX(200);
//        tran2.play();
//
//        TranslateTransition tran3 = new TranslateTransition(Duration.seconds(4.0), circ);
//        tran3.setFromX(0);
//        tran3.setToX(200);
//        tran3.play();
//
//        FadeTransition fadeTransition =
//                new FadeTransition(Duration.seconds(12.0), rectPath);
//        fadeTransition.setFromValue(1.0f);
//        fadeTransition.setToValue(0.0f);
//        fadeTransition.setCycleCount(1);
//
//        ScaleTransition scaleTransition =
//                new ScaleTransition(Duration.seconds(20.0), rectPath);
//        scaleTransition.setToX(-1f);
//        scaleTransition.setToY(-1f);
//
//


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

        Polygon leftEyebrow = new Polygon(
                x(40), y(50),
                x(52), y(41),
                x(95), y(66),
                x(89), y(73)
        );
        leftEyebrow.setFill(Color.rgb(194, 66, 3));

        root.getChildren().add(leftEyebrow);

        Polygon rightEyebrow = new Polygon(
                x(138), y(72),
                x(175), y(58),
                x(181), y(66),
                x(144), y(80)
        );
        rightEyebrow.setFill(Color.rgb(194, 66, 3));

        root.getChildren().add(rightEyebrow);

        Ellipse forehead = new Ellipse();
        forehead.setCenterX(x(119));
        forehead.setCenterY(y(60));
        forehead.setRadiusX(13);
        forehead.setRadiusY(10);
        forehead.setRotate(30);
        forehead.setFill(Color.WHITE);

        root.getChildren().add(forehead);

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

        int time = 3000;

        TranslateTransition translateTransition =new TranslateTransition(Duration.millis(time), root);
        translateTransition.setFromX(0);
        translateTransition.setToX(900);
        translateTransition.setCycleCount(Timeline.INDEFINITE);
        translateTransition.setAutoReverse(true);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(time), root);
        rotateTransition.setByAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.setAutoReverse(true);

        ScaleTransition scaleTransition = new ScaleTransition(Duration.millis(time), root);
        scaleTransition.setToX(2f);
        scaleTransition.setToY(2f);
        scaleTransition.setCycleCount(Timeline.INDEFINITE);
        scaleTransition.setAutoReverse(true);

        ParallelTransition parallelTransition = new ParallelTransition();
        parallelTransition.getChildren().addAll(
                translateTransition,
                rotateTransition,
                scaleTransition
        );

        parallelTransition.play();

        primaryStage.setResizable(false);
        primaryStage.setTitle("Bomb-bird");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private String returnPixelColor(int color) // метод для співставлення кольорів 16-бітного зображення
    {
        String col = "BLACK";
        switch (color) {
            case 0:
                return "BLACK";     //BLACK;
            case 1:
                return "LIGHTCORAL";  //LIGHTCORAL;
            case 2:
                return "GREEN";     //GREEN
            case 3:
                return "BROWN";     //BROWN
            case 4:
                return "BLUE";      //BLUE;
            case 5:
                return "MAGENTA";   //MAGENTA;
            case 6:
                return "CYAN";      //CYAN;
            case 7:
                return "LIGHTGRAY"; //LIGHTGRAY;
            case 8:
                return "DARKGRAY";  //DARKGRAY;
            case 9:
                return "RED";       //RED;
            case 10:
                return "LIGHTGREEN";//LIGHTGREEN
            case 11:
                return "YELLOW";    //YELLOW;
            case 12:
                return "LIGHTBLUE"; //LIGHTBLUE;
            case 13:
                return "LIGHTPINK";    //LIGHTMAGENTA
            case 14:
                return "LIGHTCYAN";    //LIGHTCYAN;
            case 15:
                return "WHITE";    //WHITE;
        }
        return col;
    }

    private double x(double originalX) {
        return originalX + 0;
    }

    private double y(double originalY) {
        return originalY + 150;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
