package com.gmail.max.airplane.model;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import com.gmail.max.airplane.shape_transform.ShapeTransform;
import com.gmail.max.airplane.utils.AppearanceUtils;
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import java.awt.event.*;

public class Airplane extends JFrame implements ActionListener {

    private static final float UPPER_EYE_LIMIT = 15.0f; //30.0f
    private static final float LOWER_EYE_LIMIT = -15.0f; //-30.0f
    private static final float FARTHEST_EYE_LIMIT = 15.0f; //30.0f
    private static final float NEAREST_EYE_LIMIT = 2.0f; //5.0f
    private static final float DELTA_ANGLE = 0.06f;
    private static final float DELTA_DISTANCE = 1f;

    private TransformGroup airplaneTransformGroup;
    private Transform3D airplaneTransform3D = new Transform3D();
    private TransformGroup viewingTransformGroup;
    private Transform3D viewingTransform = new Transform3D();

    private float eyeHeight = UPPER_EYE_LIMIT;
    private float eyeDistance = FARTHEST_EYE_LIMIT;
    private float zAngle = 0;

    private boolean isLightOn = true;
    private DirectionalLight light1;
    private DirectionalLight light2;
    private AmbientLight ambientLight;
    private BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

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

                    case 32 -> {
                        isLightOn = !isLightOn;
                        if (isLightOn) {
                            light1.setInfluencingBounds(bounds);
                            light2.setInfluencingBounds(bounds);
                            ambientLight.setInfluencingBounds(bounds);
                        } else {
                            light1.setInfluencingBounds(null);
                            light2.setInfluencingBounds(null);
                            ambientLight.setInfluencingBounds(null);
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
        airplaneTransformGroup = new TransformGroup();
        //Allows the cube to the rotated
        airplaneTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        buildAirplane();

        TransformGroup tg = new TransformGroup();
        Transform3D t = new Transform3D();
        t.setScale(new Vector3d(0.5f,0.5f,0.5f));
        tg.setTransform(t);
        tg.addChild(airplaneTransformGroup);

//        Background background = new Background(new Color3f(new Color(84, 84, 84)));
//        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
//        background.setApplicationBounds(sphere);
//        objRoot.addChild(background);

        //Add the transform group to the BranchGroup
        objRoot.addChild(tg);

        addLights(objRoot);

        return objRoot;
    }

    private void addLights(BranchGroup objRoot) {
        Color3f lightColor1 = new Color3f(1.0f, 1f, 1f);
        Vector3f lightDirection1 = new Vector3f(4.0f, -7.0f, -12.0f);
        light1 = new DirectionalLight(lightColor1, lightDirection1);
        light1.setCapability(DirectionalLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f lightColor2 = new Color3f(1f, 1f, 1f);
        Vector3f lightDirection2 = new Vector3f(4.0f, 7.0f, 12.0f);
        light2 = new DirectionalLight(lightColor2, lightDirection2);
        light2.setCapability(DirectionalLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        light2.setInfluencingBounds(bounds);
        objRoot.addChild(light2);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        ambientLight = new AmbientLight(ambientColor);
        ambientLight.setCapability(AmbientLight.ALLOW_INFLUENCING_BOUNDS_WRITE);
        ambientLight.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLight);
    }

    private void buildAirplane() {
        ShapeTransform shapeTransform = new ShapeTransform();
        int primFlags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;

        // main body
        Cylinder centreFuselage = new Cylinder(
                1,
                10,
                primFlags,
                AppearanceUtils.getGreyAppearance()
        );

        // Rear Fuselage

        TransformGroup rearFuselageSphereTG = shapeTransform.setShape(new Sphere(
                0.9f,
                primFlags,
                60,
                AppearanceUtils.getGreyAppearance()))
                .setTranslation(0, 5f, -0.09f)
                .getTransformGroup();

        TransformGroup rearFuselageConeTG = shapeTransform.setShape(new Cone(
                1,
                3,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .rotX(18)
                .setTranslation(0, 6.5f, 0.5f)
                .getTransformGroup();

        // Forward Fuselage

        TransformGroup forwardFuselageSphereTG = shapeTransform.setShape(new Sphere(
                1f,
                primFlags,
                60,
                AppearanceUtils.getGreyAppearance()))
                .setTranslation(0, -5f, -0.19f)
                .setScale(0.9f, 2f, 0.8)
                .getTransformGroup();

        TransformGroup forwardFuselageSphereConeTG = shapeTransform.setShape(new Cone(
                1,
                1.5f,
                primFlags,
                AppearanceUtils.getAppearance(
                        new Color(56, 74, 174),
                        "resource\\images\\glass-texture.jpg")))
                .rotZ(180)
                .setTranslation(0, -5.75f, 0)
                .getTransformGroup();

        // Tail Fin

        TransformGroup tailFinTG1 = shapeTransform.setShape(new Box(
                0.07f,
                0.4f,
                1.5f,
                primFlags,
                AppearanceUtils.getAppearance(new Color(53, 43, 224), null)))
                .rotX(-18)
                .setTranslation(0, 6.5f, 1.5f)
                .getTransformGroup();

        TransformGroup tailFinTG2 = shapeTransform.setShape(new Box(
                0.07f,
                0.4f,
                1.7f,
                primFlags,
                AppearanceUtils.getAppearance(new Color(53, 43, 224), null)))
                .rotX(-40)
                .setTranslation(0, 5.8f, 1.5f)
                .getTransformGroup();

        // Horizontal Stabilizer

        TransformGroup leftHorizStabilizerTG = shapeTransform.setShape(new Box(
                2f,
                0.4f,
                0.07f,
                primFlags,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)))
                .rotZ(40)
                .setTranslation(1, 5.8f, 0.4f)
                .getTransformGroup();

        TransformGroup rightHorizStabilizerTG = shapeTransform.setShape(new Box(
                2f,
                0.4f,
                0.07f,
                primFlags,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)))
                .rotZ(-40)
                .setTranslation(-1, 5.8f, 0.4f)
                .getTransformGroup();

        TransformGroup pairHorizStabilizerTG = shapeTransform.setShape(new Box(
                1.5f,
                0.5f,
                0.07f,
                primFlags,
                AppearanceUtils.getAppearance(new Color(217, 208, 46), null)))
                .setTranslation(0, 6.3f, 0.4f)
                .getTransformGroup();

        // Wings

        TransformGroup leftWingTG1 = shapeTransform.setShape(new Box(
                4f,
                1f,
                0.07f,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .rotZ(30)
                .setTranslation(4, 0.5f, -0.1f)
                .getTransformGroup();

        TransformGroup leftWingTG2 = shapeTransform.setShape(new Box(
                0.455f,
                0.894f,
                0.07f,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .setTranslation(7.47f, 2.48f, -0.1f)
                .getTransformGroup();

        TransformGroup rightWingTG1 = shapeTransform.setShape(new Box(
                4f,
                1f,
                0.07f,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .rotZ(-30)
                .setTranslation(-4, 0.5f, -0.1f)
                .getTransformGroup();

        TransformGroup rightWingTG2 = shapeTransform.setShape(new Box(
                0.455f,
                0.894f,
                0.07f,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .setTranslation(-7.47f, 2.48f, -0.1f)
                .getTransformGroup();

        TransformGroup pairWingTG = shapeTransform.setShape(new Box(
                4f,
                1f,
                0.07f,
                primFlags,
                AppearanceUtils.getGreyAppearance()))
                .setTranslation(0, 0.7f, -0.1f)
                .getTransformGroup();

        // left engines

        TransformGroup leftEngineSphereTG1 = shapeTransform.setShape(new Sphere(
                0.4f,
                primFlags,
                60,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)))
                .setTranslation(2.5f, -1.3f, -0.45f)
                .setScale(1, 2.5f, 1)
                .getTransformGroup();

        TransformGroup leftEngineSphereTG2 = shapeTransform.setShape(new Sphere(
                0.4f,
                primFlags,
                60,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)))
                .setTranslation(4.5f, -0.1f, -0.45f)
                .setScale(1, 2.5f, 1)
                .getTransformGroup();

        // right engines

        TransformGroup rightEngineSphereTG1 = shapeTransform.setShape(new Sphere(
                0.4f,
                primFlags,
                60,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)))
                .setTranslation(-2.5f, -1.3f, -0.45f)
                .setScale(1, 2.5f, 1)
                .getTransformGroup();

        TransformGroup rightEngineSphereTG2 = shapeTransform.setShape(new Sphere(
                0.4f,
                primFlags,
                60,
                AppearanceUtils.getAppearance(new Color(50, 49, 49), null)))
                .setTranslation(-4.5f, -0.1f, -0.45f)
                .setScale(1, 2.5f, 1)
                .getTransformGroup();

        // Navigation lights

        TransformGroup leftNavigationLightTG = shapeTransform.setShape(new Sphere(
                0.1f,
                primFlags,
                60,
                AppearanceUtils.getEmissiveAppearance(Color.RED)))
                .setTranslation(7.9f, 2.4f, 0.1f)
                .getTransformGroup();

        TransformGroup rightNavigationLightTG = shapeTransform.setShape(new Sphere(
                0.1f,
                primFlags,
                60,
                AppearanceUtils.getEmissiveAppearance(Color.GREEN)))
                .setTranslation(-7.9f, 2.4f, 0.1f)
                .getTransformGroup();

        TransformGroup tailNavigationLightTG = shapeTransform.setShape(new Sphere(
                0.1f,
                primFlags,
                60,
                AppearanceUtils.getEmissiveAppearance(Color.GREEN)))
                .setTranslation(0, 7.8f, 0.9f)
                .getTransformGroup();

        TransformGroup centralNavigationLightTG = shapeTransform.setShape(new Sphere(
                0.1f,
                primFlags,
                60,
                AppearanceUtils.getEmissiveAppearance(Color.GREEN)))
                .setTranslation(0, -1, 1)
                .getTransformGroup();

        TransformGroup bottomNavigationLightTG = shapeTransform.setShape(new Sphere(
                0.1f,
                primFlags,
                60,
                AppearanceUtils.getEmissiveAppearance(Color.GREEN)))
                .setTranslation(0, 5, -1)
                .getTransformGroup();

        airplaneTransformGroup.addChild(centreFuselage);

        airplaneTransformGroup.addChild(rearFuselageSphereTG);
        airplaneTransformGroup.addChild(rearFuselageConeTG);

        airplaneTransformGroup.addChild(forwardFuselageSphereTG);
        airplaneTransformGroup.addChild(forwardFuselageSphereConeTG);

        airplaneTransformGroup.addChild(tailFinTG1);
        airplaneTransformGroup.addChild(tailFinTG2);

        airplaneTransformGroup.addChild(leftHorizStabilizerTG);
        airplaneTransformGroup.addChild(rightHorizStabilizerTG);
        airplaneTransformGroup.addChild(pairHorizStabilizerTG);

        airplaneTransformGroup.addChild(leftWingTG1);
        airplaneTransformGroup.addChild(leftWingTG2);
        airplaneTransformGroup.addChild(rightWingTG1);
        airplaneTransformGroup.addChild(rightWingTG2);
        airplaneTransformGroup.addChild(pairWingTG);

        airplaneTransformGroup.addChild(leftEngineSphereTG1);
        airplaneTransformGroup.addChild(leftEngineSphereTG2);

        airplaneTransformGroup.addChild(rightEngineSphereTG1);
        airplaneTransformGroup.addChild(rightEngineSphereTG2);

        airplaneTransformGroup.addChild(leftNavigationLightTG);
        airplaneTransformGroup.addChild(rightNavigationLightTG);
        airplaneTransformGroup.addChild(tailNavigationLightTG);
        airplaneTransformGroup.addChild(centralNavigationLightTG);
        airplaneTransformGroup.addChild(bottomNavigationLightTG);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        airplaneTransform3D.rotZ(zAngle);
        airplaneTransformGroup.setTransform(airplaneTransform3D);

        Point3d eye = new Point3d(eyeDistance, .0f, eyeHeight); // spectator's eye
        Point3d center = new Point3d(.0f, .0f, .0f); // sight target
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);

        // the camera frustum
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }


}
