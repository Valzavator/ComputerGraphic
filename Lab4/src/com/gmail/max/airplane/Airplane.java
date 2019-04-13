package com.gmail.max.airplane;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import java.awt.event.*;

public class Airplane extends JFrame implements ActionListener {

    private static final float UPPER_EYE_LIMIT = 30.0f;
    private static final float LOWER_EYE_LIMIT = -30.0f;
    private static final float FARTHEST_EYE_LIMIT = 30.0f;
    private static final float NEAREST_EYE_LIMIT = 5.0f;
    private static final float DELTA_ANGLE = 0.06f;
    private static final float DELTA_DISTANCE = 0.5f;

    private TransformGroup viewingTransformGroup;
    private TransformGroup rotateTransformGroup;
    private Transform3D rotateTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();

    private float eyeHeight = UPPER_EYE_LIMIT;
    private float eyeDistance = FARTHEST_EYE_LIMIT;
    private float zAngle = 0;

    public Airplane() {
        setTitle("Lab 4 - Airplane");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setResizable(false);

        initUniverse();

        Timer timer = new Timer(10, this);
        timer.start();

    }

    private void initUniverse() {
        //Create a Canvas3D using the preferred configuration
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        canvas.setFocusable(true);

        canvas.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {

                switch (e.getKeyCode()) {
                    case 68 -> zAngle += DELTA_ANGLE;

                    case 65 -> zAngle -= DELTA_ANGLE;

                    case KeyEvent.VK_UP -> {
                        if (eyeHeight < UPPER_EYE_LIMIT) {
                            eyeHeight += DELTA_DISTANCE;
                        }
                    }

                    case KeyEvent.VK_DOWN -> {
                        if (eyeHeight > LOWER_EYE_LIMIT) {
                            eyeHeight -= DELTA_DISTANCE;
                        }
                    }

                    case 87 -> {
                        if (eyeDistance > NEAREST_EYE_LIMIT) {
                            eyeDistance -= DELTA_DISTANCE;
                        }
                    }

                    case 83 -> {
                        if (eyeDistance < FARTHEST_EYE_LIMIT) {
                            eyeDistance += DELTA_DISTANCE;
                        }
                    }
                }
            }

        });

        //Add the canvas into the center of the screen
        add(canvas, BorderLayout.CENTER);

        //Create a Simple Universe with view branch
        SimpleUniverse universe = new SimpleUniverse(canvas);

        //Add the branch into the Universe
        universe.addBranchGraph(createSceneGraph());

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
    }

    private BranchGroup createSceneGraph() {
        //Create the root of the branch graph
        BranchGroup objRoot = new BranchGroup();

        //Create a new Transform group
        rotateTransformGroup = new TransformGroup();
        //Allows the cube to the rotated
        rotateTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        buildAirplane();

        //Create Alpha object for the rotation of the cube
        Alpha spinAlpha = new Alpha(-1, 10000);
        //Create object for the spin of the cube passing the Alpha value and the transform group to target
        RotationInterpolator spin = new RotationInterpolator(spinAlpha, rotateTransformGroup);
        //Set the bounds for the spin
        spin.setSchedulingBounds(new BoundingSphere(new Point3d(), 10000));

        //Add the spin to the transform group
//        rotateTransformGroup.addChild(spin);

        //Add the transform group to the BranchGroup
        objRoot.addChild(rotateTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f light1Color2 = new Color3f(1f, 1f, 1f);
        Vector3f light1Direction2 = new Vector3f(4.0f, 7.0f, 12.0f);
        DirectionalLight light2 = new DirectionalLight(light1Color2, light1Direction2);
        light2.setInfluencingBounds(bounds);
        objRoot.addChild(light2);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }

    private void buildAirplane() {
        //Add a new cube to the
        rotateTransformGroup.addChild(new Cylinder(1, 10));

        // back
        Sphere sphere = new Sphere(0.9f, 3, 60);
        Transform3D sphereT = new Transform3D();
        sphereT.setTranslation(new Vector3f(0, 5f, -0.09f));
        TransformGroup sphereTG = new TransformGroup();
        sphereTG.setTransform(sphereT);
        sphereTG.addChild(sphere);

        Cone cone = new Cone(1, 3);
        Transform3D coneT = new Transform3D();
        coneT.rotX(18 * Math.PI / 180.0);
        coneT.setTranslation(new Vector3f(0, 6.5f, 0.5f));
        TransformGroup coneTG = new TransformGroup();
        coneTG.setTransform(coneT);
        coneTG.addChild(cone);


        // front
        Sphere sphere2 = new Sphere(1f, 3, 60);
        Transform3D sphereT2 = new Transform3D();
        sphereT2.setTranslation(new Vector3f(0, -5f, -0.19f));
        sphereT2.setScale(new Vector3d(0.9f, 1.7f, 0.8));
        TransformGroup sphereTG2 = new TransformGroup();
        sphereTG2.setTransform(sphereT2);
        sphereTG2.addChild(sphere2);

        Cone cone2 = new Cone(1, 1.5f);
        Transform3D coneT2 = new Transform3D();
        coneT2.rotZ(Math.PI);
        coneT2.setTranslation(new Vector3f(0, -5.75f, 0));
        TransformGroup coneTG2 = new TransformGroup();
        coneTG2.setTransform(coneT2);
        coneTG2.addChild(cone2);

        //

        Box box1 = new Box(0.07f, 0.4f, 1.5f, null);
        Transform3D boxT1 = new Transform3D();
        boxT1.rotX(-18 * Math.PI / 180.0);
        boxT1.setTranslation(new Vector3f(0, 6.5f, 1.5f));
        TransformGroup boxTG1 = new TransformGroup();
        boxTG1.setTransform(boxT1);
        boxTG1.addChild(box1);

        Box box2 = new Box(0.07f, 0.4f, 1.7f, null);
        Transform3D boxT2 = new Transform3D();
        boxT2.rotX(-40 * Math.PI / 180.0);
        boxT2.setTranslation(new Vector3f(0, 5.8f, 1.5f));
        TransformGroup boxTG2 = new TransformGroup();
        boxTG2.setTransform(boxT2);
        boxTG2.addChild(box2);

        Box leftHorizStabilizer = new Box(2f, 0.4f, 0.07f, null);
        Transform3D leftHorizStabilizerT = new Transform3D();
        leftHorizStabilizerT.rotZ(40 * Math.PI / 180.0);
        leftHorizStabilizerT.setTranslation(new Vector3f(1, 5.8f, 0.4f));
        TransformGroup leftHorizStabilizerTG = new TransformGroup();
        leftHorizStabilizerTG.setTransform(leftHorizStabilizerT);
        leftHorizStabilizerTG.addChild(leftHorizStabilizer);

        Box rightHorizStabilizer = new Box(2f, 0.4f, 0.07f, null);
        Transform3D rightHorizStabilizerT = new Transform3D();
        rightHorizStabilizerT.rotZ(-40 * Math.PI / 180.0);
        rightHorizStabilizerT.setTranslation(new Vector3f(-1, 5.8f, 0.4f));
        TransformGroup rightHorizStabilizerTG = new TransformGroup();
        rightHorizStabilizerTG.setTransform(rightHorizStabilizerT);
        rightHorizStabilizerTG.addChild(rightHorizStabilizer);

        Box pairHorizStabilizer = new Box(1.5f, 0.5f, 0.07f, null);
        Transform3D pairHorizStabilizerT = new Transform3D();
        pairHorizStabilizerT.setTranslation(new Vector3f(0, 6.3f, 0.4f));
        TransformGroup pairHorizStabilizerTG = new TransformGroup();
        pairHorizStabilizerTG.setTransform(pairHorizStabilizerT);
        pairHorizStabilizerTG.addChild(pairHorizStabilizer);

        // left wing

        Box leftWing1 = new Box(5f, 1f, 0.07f, null);
        Transform3D leftWingT1 = new Transform3D();
        leftWingT1.rotZ(30 * Math.PI / 180.0);
        leftWingT1.setTranslation(new Vector3f(4, 0.5f, -0.1f));
        TransformGroup leftWingTG1 = new TransformGroup();
        leftWingTG1.setTransform(leftWingT1);
        leftWingTG1.addChild(leftWing1);

        // right wing

        Box rightWing1 = new Box(5f, 1f, 0.07f, null);
        Transform3D rightWingT1 = new Transform3D();
        rightWingT1.rotZ(-30 * Math.PI / 180.0);
        rightWingT1.setTranslation(new Vector3f(-4, 0.5f, -0.1f));
        TransformGroup rightWingTG1 = new TransformGroup();
        rightWingTG1.setTransform(rightWingT1);
        rightWingTG1.addChild(rightWing1);

        Box pairWing = new Box(4f, 1f, 0.07f, null);
        Transform3D pairWingT = new Transform3D();
        pairWingT.setTranslation(new Vector3f(0, 0.7f, -0.1f));
        TransformGroup pairWingTG = new TransformGroup();
        pairWingTG.setTransform(pairWingT);
        pairWingTG.addChild(pairWing);

        rotateTransformGroup.addChild(coneTG);
        rotateTransformGroup.addChild(sphereTG);

        rotateTransformGroup.addChild(coneTG2);
        rotateTransformGroup.addChild(sphereTG2);

        rotateTransformGroup.addChild(boxTG1);
        rotateTransformGroup.addChild(boxTG2);

        rotateTransformGroup.addChild(leftHorizStabilizerTG);
        rotateTransformGroup.addChild(rightHorizStabilizerTG);
        rotateTransformGroup.addChild(pairHorizStabilizerTG);

        rotateTransformGroup.addChild(leftWingTG1);
        rotateTransformGroup.addChild(pairWingTG);
        rotateTransformGroup.addChild(rightWingTG1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        rotateTransform3D.rotZ(zAngle);
        rotateTransformGroup.setTransform(rotateTransform3D);

        Point3d eye = new Point3d(eyeDistance, .0f, eyeHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f, .0f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);

        // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }


}
