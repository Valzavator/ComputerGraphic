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
    private static final float DELTA_ANGLE = 0.08f;
    private static final float DELTA_DISTANCE = 0.8f;

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

        //Add the transform group to the BranchGroup
        objRoot.addChild(rotateTransformGroup);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0);

        Color3f light1Color = new Color3f(1.0f, 1f, 1f);
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

        // main body
        Cylinder centreFuselage = new Cylinder(
                1,
                10,
                AppearanceUtils.getGreyAppearance()
        );

        // Rear Fuselage

        Sphere rearFuselageSphere = new Sphere(
                0.9f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D rearFuselageSphereT = new Transform3D();
        rearFuselageSphereT.setTranslation(new Vector3f(0, 5f, -0.09f));
        TransformGroup rearFuselageSphereTG = new TransformGroup();
        rearFuselageSphereTG.setTransform(rearFuselageSphereT);
        rearFuselageSphereTG.addChild(rearFuselageSphere);

        Cone rearFuselageCone = new Cone(
                1,
                3,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D rearFuselageConeT = new Transform3D();
        rearFuselageConeT.rotX(18 * Math.PI / 180.0);
        rearFuselageConeT.setTranslation(new Vector3f(0, 6.5f, 0.5f));
        TransformGroup rearFuselageConeTG = new TransformGroup();
        rearFuselageConeTG.setTransform(rearFuselageConeT);
        rearFuselageConeTG.addChild(rearFuselageCone);


        // Forward Fuselage

        Sphere forwardFuselageSphere = new Sphere(
                1f,
                3,
                60,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D forwardFuselageSphereT = new Transform3D();
        forwardFuselageSphereT.setTranslation(new Vector3f(0, -5f, -0.19f));
        forwardFuselageSphereT.setScale(new Vector3d(0.9f, 2f, 0.8));
        TransformGroup forwardFuselageSphereTG = new TransformGroup();
        forwardFuselageSphereTG.setTransform(forwardFuselageSphereT);
        forwardFuselageSphereTG.addChild(forwardFuselageSphere);

        Cone forwardFuselageSphereCone = new Cone(
                1,
                1.5f,
                AppearanceUtils.getAppearance(
                        new Color(56, 74, 174), null)
        );
        Transform3D forwardFuselageSphereConeT = new Transform3D();
        forwardFuselageSphereConeT.rotZ(Math.PI);
        forwardFuselageSphereConeT.setTranslation(new Vector3f(0, -5.75f, 0));
        TransformGroup forwardFuselageSphereConeTG = new TransformGroup();
        forwardFuselageSphereConeTG.setTransform(forwardFuselageSphereConeT);
        forwardFuselageSphereConeTG.addChild(forwardFuselageSphereCone);

        // Tail Fin

        Box tailFin1 = new Box(
                0.07f,
                0.4f,
                1.5f,
                AppearanceUtils.getAppearance(new Color(53, 43, 224), null)
        );
        Transform3D tailFinT1 = new Transform3D();
        tailFinT1.rotX(-18 * Math.PI / 180.0);
        tailFinT1.setTranslation(new Vector3f(0, 6.5f, 1.5f));
        TransformGroup tailFinTG1 = new TransformGroup();
        tailFinTG1.setTransform(tailFinT1);
        tailFinTG1.addChild(tailFin1);

        Box tailFin2 = new Box(
                0.07f,
                0.4f,
                1.7f,
                AppearanceUtils.getAppearance(new Color(53, 43, 224), null)
        );
        Transform3D tailFinT2 = new Transform3D();
        tailFinT2.rotX(-40 * Math.PI / 180.0);
        tailFinT2.setTranslation(new Vector3f(0, 5.8f, 1.5f));
        TransformGroup tailFinTG2 = new TransformGroup();
        tailFinTG2.setTransform(tailFinT2);
        tailFinTG2.addChild(tailFin2);

        // Horizontal Stabilizer

        Box leftHorizStabilizer = new Box(
                2f,
                0.4f,
                0.07f,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)
        );
        Transform3D leftHorizStabilizerT = new Transform3D();
        leftHorizStabilizerT.rotZ(40 * Math.PI / 180.0);
        leftHorizStabilizerT.setTranslation(new Vector3f(1, 5.8f, 0.4f));
        TransformGroup leftHorizStabilizerTG = new TransformGroup();
        leftHorizStabilizerTG.setTransform(leftHorizStabilizerT);
        leftHorizStabilizerTG.addChild(leftHorizStabilizer);

        Box rightHorizStabilizer = new Box(
                2f,
                0.4f,
                0.07f,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)
        );
        Transform3D rightHorizStabilizerT = new Transform3D();
        rightHorizStabilizerT.rotZ(-40 * Math.PI / 180.0);
        rightHorizStabilizerT.setTranslation(new Vector3f(-1, 5.8f, 0.4f));
        TransformGroup rightHorizStabilizerTG = new TransformGroup();
        rightHorizStabilizerTG.setTransform(rightHorizStabilizerT);
        rightHorizStabilizerTG.addChild(rightHorizStabilizer);

        Box pairHorizStabilizer = new Box(
                1.5f,
                0.5f,
                0.07f,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)
        );
        Transform3D pairHorizStabilizerT = new Transform3D();
        pairHorizStabilizerT.setTranslation(new Vector3f(0, 6.3f, 0.4f));
        TransformGroup pairHorizStabilizerTG = new TransformGroup();
        pairHorizStabilizerTG.setTransform(pairHorizStabilizerT);
        pairHorizStabilizerTG.addChild(pairHorizStabilizer);

        // Wings

        Box leftWing1 = new Box(
                4f,
                1f,
                0.07f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D leftWingT1 = new Transform3D();
        leftWingT1.rotZ(30 * Math.PI / 180.0);
        leftWingT1.setTranslation(new Vector3f(4, 0.5f, -0.1f));
        TransformGroup leftWingTG1 = new TransformGroup();
        leftWingTG1.setTransform(leftWingT1);
        leftWingTG1.addChild(leftWing1);

        Box leftWing2 = new Box(
                0.455f,
                0.894f,
                0.07f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D leftWingT2 = new Transform3D();
        leftWingT2.setTranslation(new Vector3f(7.47f, 2.48f, -0.1f));
        TransformGroup leftWingTG2 = new TransformGroup();
        leftWingTG2.setTransform(leftWingT2);
        leftWingTG2.addChild(leftWing2);

        Box rightWing1 = new Box(
                4f,
                1f,
                0.07f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D rightWingT1 = new Transform3D();
        rightWingT1.rotZ(-30 * Math.PI / 180.0);
        rightWingT1.setTranslation(new Vector3f(-4, 0.5f, -0.1f));
        TransformGroup rightWingTG1 = new TransformGroup();
        rightWingTG1.setTransform(rightWingT1);
        rightWingTG1.addChild(rightWing1);

        Box rightWing2 = new Box(
                0.455f,
                0.894f,
                0.07f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D rightWingT2 = new Transform3D();
        rightWingT2.setTranslation(new Vector3f(-7.47f, 2.48f, -0.1f));
        TransformGroup rightWingTG2 = new TransformGroup();
        rightWingTG2.setTransform(rightWingT2);
        rightWingTG2.addChild(rightWing2);

        Box pairWing = new Box(
                4f,
                1f,
                0.07f,
                AppearanceUtils.getGreyAppearance()
        );
        Transform3D pairWingT = new Transform3D();
        pairWingT.setTranslation(new Vector3f(0, 0.7f, -0.1f));
        TransformGroup pairWingTG = new TransformGroup();
        pairWingTG.setTransform(pairWingT);
        pairWingTG.addChild(pairWing);

        // left engines

        Sphere leftEngineSphere1 = new Sphere(
                0.4f,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)
        );
        Transform3D leftEngineSphereT1 = new Transform3D();
        leftEngineSphereT1.setTranslation(new Vector3f(2.5f, -1.3f, -0.45f));
        leftEngineSphereT1.setScale(new Vector3d(1f, 2.5f, 1));
        TransformGroup leftEngineSphereTG1 = new TransformGroup();
        leftEngineSphereTG1.setTransform(leftEngineSphereT1);
        leftEngineSphereTG1.addChild(leftEngineSphere1);

        Sphere leftEngineSphere2 = new Sphere(
                0.4f,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)
        );
        Transform3D leftEngineSphereT2 = new Transform3D();
        leftEngineSphereT2.setTranslation(new Vector3f(4.5f, -0.1f, -0.45f));
        leftEngineSphereT2.setScale(new Vector3d(1f, 2.5f, 1));
        TransformGroup leftEngineSphereTG2 = new TransformGroup();
        leftEngineSphereTG2.setTransform(leftEngineSphereT2);
        leftEngineSphereTG2.addChild(leftEngineSphere2);

        // right engines

        Sphere rightEngineSphere1 = new Sphere(
                0.4f,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)
        );
        Transform3D rightEngineSphereT1 = new Transform3D();
        rightEngineSphereT1.setTranslation(new Vector3f(-2.5f, -1.3f, -0.45f));
        rightEngineSphereT1.setScale(new Vector3d(1f, 2.5f, 1));
        TransformGroup rightEngineSphereTG1 = new TransformGroup();
        rightEngineSphereTG1.setTransform(rightEngineSphereT1);
        rightEngineSphereTG1.addChild(rightEngineSphere1);

        Sphere rightEngineSphere2 = new Sphere(
                0.4f,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)
        );
        Transform3D rightEngineSphereT2 = new Transform3D();
        rightEngineSphereT2.setTranslation(new Vector3f(-4.5f, -0.1f, -0.45f));
        rightEngineSphereT2.setScale(new Vector3d(1f, 2.5f, 1));
        TransformGroup rightEngineSphereTG2 = new TransformGroup();
        rightEngineSphereTG2.setTransform(rightEngineSphereT2);
        rightEngineSphereTG2.addChild(rightEngineSphere2);

        rotateTransformGroup.addChild(centreFuselage);

        rotateTransformGroup.addChild(rearFuselageSphereTG);
        rotateTransformGroup.addChild(rearFuselageConeTG);

        rotateTransformGroup.addChild(forwardFuselageSphereTG);
        rotateTransformGroup.addChild(forwardFuselageSphereConeTG);

        rotateTransformGroup.addChild(tailFinTG1);
        rotateTransformGroup.addChild(tailFinTG2);

        rotateTransformGroup.addChild(leftHorizStabilizerTG);
        rotateTransformGroup.addChild(rightHorizStabilizerTG);
        rotateTransformGroup.addChild(pairHorizStabilizerTG);

        rotateTransformGroup.addChild(leftWingTG1);
        rotateTransformGroup.addChild(leftWingTG2);
        rotateTransformGroup.addChild(rightWingTG1);
        rotateTransformGroup.addChild(rightWingTG2);
        rotateTransformGroup.addChild(pairWingTG);

        rotateTransformGroup.addChild(leftEngineSphereTG1);
        rotateTransformGroup.addChild(leftEngineSphereTG2);

        rotateTransformGroup.addChild(rightEngineSphereTG1);
        rotateTransformGroup.addChild(rightEngineSphereTG2);
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
