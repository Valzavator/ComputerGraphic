package com.gmail.max.airplane;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import java.awt.event.*;

public class Airplane extends JFrame implements ActionListener {

    private static final float UPPER_EYE_LIMIT = 20.0f;
    private static final float LOWER_EYE_LIMIT = -20.0f;
    private static final float FARTHEST_EYE_LIMIT = 20.0f;
    private static final float NEAREST_EYE_LIMIT = 2.0f;
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
                    case 68 ->
                        zAngle+= DELTA_ANGLE;

                    case 65 ->
                        zAngle -= DELTA_ANGLE;

                    case KeyEvent.VK_UP -> {
                        if (eyeHeight < UPPER_EYE_LIMIT) {
                            eyeHeight+= DELTA_DISTANCE;
                        }
                    }

                    case KeyEvent.VK_DOWN -> {
                        if (eyeHeight > LOWER_EYE_LIMIT) {
                            eyeHeight-= DELTA_DISTANCE;
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

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),1000.0);
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

    private void buildAirplane() {
        //Add a new cube to the
        rotateTransformGroup.addChild(new Cylinder(1, 10));

        Cone cone = new Cone(1, 2);
        Transform3D coneT = new Transform3D();
        coneT.setTranslation(new Vector3f(0, 6f , 0));
        TransformGroup coneTG = new TransformGroup();
        coneTG.setTransform(coneT);
        coneTG.addChild(cone);

        Sphere sphere = new Sphere(1f,3,60);
        Transform3D sphereT = new Transform3D();
        sphereT.setTranslation(new Vector3f(0, -5f , -0.2f));
        sphereT.setScale(new Vector3d(0.95f, 2.3f, 0.8));
        TransformGroup sphereTG = new TransformGroup();
        sphereTG.setTransform(sphereT);
        sphereTG.addChild(sphere);

        Cone cone2 = new Cone(1, 2);
        Transform3D coneT2 = new Transform3D();
        coneT2.rotZ(Math.PI);
        coneT2.setTranslation(new Vector3f(0, -6f , 0));
        TransformGroup coneTG2 = new TransformGroup();
        coneTG2.setTransform(coneT2);
        coneTG2.addChild(cone2);

//        Sphere sphere2 = new Sphere(1f);
//        Transform3D sphereT2 = new Transform3D();
//        sphereT2.setTranslation(new Vector3f(0, -5f , 0));
//        TransformGroup sphereTG2 = new TransformGroup();
//        sphereTG2.setTransform(sphereT2);
//        sphereTG2.addChild(sphere2);

        rotateTransformGroup.addChild(coneTG);
        rotateTransformGroup.addChild(sphereTG);
        rotateTransformGroup.addChild(coneTG2);

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
