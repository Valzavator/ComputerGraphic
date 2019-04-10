package com.gmail.max.examples.ice_сream;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

// Vector3f - float, Vector3d - double
public class IceCream implements ActionListener {
    private float upperEyeLimit = 8.0f; // 5.0
    private float lowerEyeLimit = 5.0f; // 1.0
    private float farthestEyeLimit = 7.0f; // 6.0
    private float nearestEyeLimit = 4.0f; // 3.0

    private TransformGroup treeTransformGroup;
    private TransformGroup viewingTransformGroup;
    private Transform3D treeTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float eyeHeight;
    private float eyeDistance;
    private boolean descend = true;
    private boolean approaching = true;

    public static void main(String[] args) {
        new IceCream();
    }

    private IceCream() {
        Timer timer = new Timer(50, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        eyeHeight = upperEyeLimit;
        eyeDistance = farthestEyeLimit;
        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildIceCream();
        objRoot.addChild(treeTransformGroup);

        Background background = new Background(new Color3f(0.9f, 0.9f, 0.9f)); // white color
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }

    private void buildIceCream() {
        var body = new Cylinder(1, 4, Utils.getBodyAppearence());
        var bodyT = new Transform3D();
        bodyT.setTranslation(new Vector3f());
        bodyT.rotX(Math.PI / 2);
        var bodyTG = new TransformGroup();
        bodyTG.setTransform(bodyT);
        bodyTG.addChild(body);

        var ball = new Sphere(1.1f, Utils.getBallAppearence());
        var ballT = new Transform3D();
        ballT.setTranslation(new Vector3f(0, 2, 0));
        var ballTG = new TransformGroup();
        ballTG.setTransform(ballT);
        ballTG.addChild(ball);
        bodyTG.addChild(ballTG);

        var choco1 = new Sphere(0.2f , Utils.getChocoAppearence());
        var choco1T = new Transform3D();
        choco1T.setTranslation(new Vector3f(0, 1f , 0));
        var choco1TG = new TransformGroup();
        choco1TG.setTransform(choco1T);
        choco1TG.addChild(choco1);


        var choco2 = new Sphere(0.2f , Utils.getChocoAppearence());
        var choco2T = new Transform3D();
        choco2T.setTranslation(new Vector3f(0.7f, 0.8f , 0));
        var choco2TG = new TransformGroup();
        choco2TG.setTransform(choco2T);
        choco2TG.addChild(choco2);

        var choco3 = new Sphere(0.2f , Utils.getChocoAppearence());
        var choco3T = new Transform3D();
        choco3T.setTranslation(new Vector3f(0, 0.7f , -0.7f));
        var choco3TG = new TransformGroup();
        choco3TG.setTransform(choco3T);
        choco3TG.addChild(choco3);

        var choco4 = new Sphere(0.2f , Utils.getChocoAppearence());
        var choco4T = new Transform3D();
        choco4T.setTranslation(new Vector3f(-0.5f, 0.6f , -0.6f));
        var choco4TG = new TransformGroup();
        choco4TG.setTransform(choco4T);
        choco4TG.addChild(choco4);

        var choco5 = new Sphere(0.2f , Utils.getChocoAppearence());
        var choco5T = new Transform3D();
        choco5T.setTranslation(new Vector3f(-0.4f, 0.7f , 0.5f));
        var choco5TG = new TransformGroup();
        choco5TG.setTransform(choco5T);
        choco5TG.addChild(choco5);

        ballTG.addChild(choco1TG);
        ballTG.addChild(choco2TG);
        ballTG.addChild(choco3TG);
        ballTG.addChild(choco4TG);
        ballTG.addChild(choco5TG);


        treeTransformGroup.addChild(bodyTG);


//        Box body2 = Utils.getBody(0.125f, 0.6f);
//        Transform3D body2T = new Transform3D();
//        body2T.setTranslation(new Vector3f(.0f, .0f, 0.25f));
//        TransformGroup body2TG = new TransformGroup();
//        body2TG.setTransform(body2T);
//        body2TG.addChild(body2);
//        treeTransformGroup.addChild(body2TG);

    }

    // ActionListener interface
    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        // rotation of the castle
        treeTransform3D.rotZ(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle += delta;

        // change of the camera position up and down within defined limits
        if (eyeHeight > upperEyeLimit){
            descend = true;
        }else if(eyeHeight < lowerEyeLimit){
            descend = false;
        }
        if (descend){
            eyeHeight -= delta;
        }else{
            eyeHeight += delta;
        }

        // change camera distance to the scene
        if (eyeDistance > farthestEyeLimit){
            approaching = true;
        }else if(eyeDistance < nearestEyeLimit){
            approaching = false;
        }
        if (approaching){
            eyeDistance -= delta;
        }else{
            eyeDistance += delta;
        }

        Point3d eye = new Point3d(eyeDistance, eyeDistance, eyeHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f ,0.1f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);; // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
