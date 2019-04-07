package com.gmail.max.examples.image_example;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class ImageExample extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Group root = new Group();
         
        double width = 640;
        double height = 480;
        Scene scene = new Scene(root, width, height, Color.WHITE);
 
        ColorAdjust colorAdjust = new ColorAdjust();
       // colorAdjust.setContrast(0.1);
        //colorAdjust.setHue(-0.05);
       // colorAdjust.setBrightness(0.4);
        colorAdjust.setSaturation(-1.0);

        Image image = new Image(getClass().getResourceAsStream("/com/gmail/max/examples/image_example/1.jpg"));
        ImageView imageView = new ImageView();
        imageView.setEffect(colorAdjust);        
        imageView.setPreserveRatio(true);
        imageView.setImage(image); 
        
        Rectangle vignette = createVignette(width, height);
        vignette.setBlendMode(BlendMode.MULTIPLY);
        
        Text sample = new Text(50,80,"Author");
        sample.setFont(Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL,36));
        final DropShadow dropShadow = new DropShadow();
        sample.setEffect(dropShadow);
                
        root.getChildren().add(imageView);
        root.getChildren().add(vignette);
        root.getChildren().add(sample);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Rectangle createVignette(double w, double h) {

        Stop[] stops = {new Stop(0, Color.WHITE),
            new Stop(1, Color.BROWN)
        };
        RadialGradient vignetteGradient = new RadialGradient(0.0, 0.0, 320, 240, 500, false, CycleMethod.NO_CYCLE, stops);

        return new Rectangle(w, h, vignetteGradient);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
