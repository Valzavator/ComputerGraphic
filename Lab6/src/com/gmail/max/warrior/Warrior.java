package com.gmail.max.warrior;


import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Hashtable;

class Warrior extends JFrame {
    private Canvas3D canvas;
    private SimpleUniverse universe;

    private TransformGroup headTG = new TransformGroup();

    Warrior() {

        configureWindow();
        configureCanvas();
        configureUniverse();

        createSceneGraph();
        addLight();

        canvas.addKeyListener(new WarriorAnimation(headTG));

        setVisible(true);
    }

    private void configureWindow() {
        setTitle("Warrior");
        setSize(700, 700);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void configureCanvas() {
        canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
        canvas.setDoubleBufferEnable(true);
        canvas.setFocusable(true);
        add(canvas, BorderLayout.CENTER);
    }

    private void configureUniverse() {
        universe = new SimpleUniverse(canvas);
        universe.getViewingPlatform().setNominalViewingTransform();

        OrbitBehavior ob = new OrbitBehavior(canvas);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        universe.getViewingPlatform().setViewPlatformBehavior(ob);
    }

    @SuppressWarnings("Duplicates")
    private void createSceneGraph() {

        ObjectFile f = new ObjectFile(ObjectFile.RESIZE);
        Scene warriorScene = null;

        try {
            warriorScene = f.load("resource/models/warrior.obj");
        } catch (Exception e) {
            System.out.println("File loading failed:" + e);
        }

        Transform3D scaling = new Transform3D();
        scaling.setScale(1.0 / 6);

        Transform3D tfWarrior = new Transform3D();
        tfWarrior.rotX(Math.PI / 3);
        tfWarrior.mul(scaling);

        TransformGroup tgWarrior = new TransformGroup();

        TransformGroup sceneGroup = new TransformGroup();

        Hashtable mikeNamedObjects = warriorScene != null
                ? warriorScene.getNamedObjects()
                : new Hashtable();

        Enumeration enumer = mikeNamedObjects.keys();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }

        Appearance bodyApp = new Appearance();
        setDefaultAppearance(bodyApp, new Color3f(new Color(220, 207, 130)));

        Appearance axeApp = new Appearance();
        setDefaultAppearance(axeApp, new Color3f(new Color(86, 86, 86)));

        Shape3D head = (Shape3D) mikeNamedObjects.get("head");
        head.setAppearance(bodyApp);

        Shape3D leftHand = (Shape3D) mikeNamedObjects.get("left_hand");
        leftHand.setAppearance(bodyApp);

        Shape3D rightHand = (Shape3D) mikeNamedObjects.get("right_hand");
        rightHand.setAppearance(bodyApp);

        Shape3D axe = (Shape3D) mikeNamedObjects.get("axe");
        axe.setAppearance(axeApp);

        Shape3D upperBody = (Shape3D) mikeNamedObjects.get("body2");
        upperBody.setAppearance(bodyApp);

        Shape3D lowerBody = (Shape3D) mikeNamedObjects.get("body1");
        lowerBody.setAppearance(axeApp);

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        TransformGroup leftHandTG = new TransformGroup();
        TransformGroup rightHandTG = new TransformGroup();
        TransformGroup axeTG = new TransformGroup();
        TransformGroup upperBodyTG = new TransformGroup();
        TransformGroup lowerBodyTG = new TransformGroup();

        headTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        leftHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rightHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        axeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        upperBodyTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        headTG.addChild(head.cloneTree());
        leftHandTG.addChild(leftHand.cloneTree());
        rightHandTG.addChild(rightHand.cloneTree());
        axeTG.addChild(axe.cloneTree());
        upperBodyTG.addChild(upperBody.cloneTree());
        lowerBodyTG.addChild(lowerBody.cloneTree());

        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), Double.MAX_VALUE);

        int timeStart = 1000;
        int timeRotationHour = 2000;

        // HEAD

        TransformGroup transformedHeadTG = new TransformGroup();
        transformedHeadTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformedHeadTG.addChild(headTG);

        Transform3D headRotationAxis = new Transform3D();
        headRotationAxis.set(new Vector3d(-0.17, 0, -0.05));
        headRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        Alpha headRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator headRotation = new RotationInterpolator(headRotationAlpha, transformedHeadTG,
                headRotationAxis, 0f, (float) Math.PI / 6);
        headRotation.setSchedulingBounds(bounds);

        // UPPER BODY

        Transform3D upperBodyRotationAxis = new Transform3D();
        upperBodyRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        Alpha upperBodyAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator upperBodyRotation = new RotationInterpolator(upperBodyAlpha, upperBodyTG,
                upperBodyRotationAxis, 0f, (float) Math.PI / 6);
        upperBodyRotation.setSchedulingBounds(bounds);

        // LEFT HAND

        Transform3D leftHandTransform = new Transform3D();

        Transform3D leftHandXRot = new Transform3D();
        leftHandXRot.setRotation(new AxisAngle4d(-0.1, 0, 0, 50 * Math.PI / 180));
        leftHandTransform.mul(leftHandXRot, leftHandTransform);

        Transform3D leftHandYRot = new Transform3D();
        leftHandYRot.setRotation(new AxisAngle4d(0, -0.1, 0, 50 * Math.PI / 180));
        leftHandTransform.mul(leftHandYRot, leftHandTransform);

        Transform3D leftHandTranslate = new Transform3D();
        leftHandTranslate.set(new Vector3d(-0.13, 0.12, 0.15));
        leftHandTransform.mul(leftHandTranslate, leftHandTransform);

        leftHandTG.setTransform(leftHandTransform);

        TransformGroup transformedLeftHandTG = new TransformGroup();
        transformedLeftHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformedLeftHandTG.addChild(leftHandTG);

        Transform3D leftHandRotationAxis = new Transform3D();
        leftHandRotationAxis.set(new Vector3d(0, 0.23, -0.05));
        leftHandRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        Alpha leftHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator leftHandRotation = new RotationInterpolator(leftHandRotationAlpha, transformedLeftHandTG,
                leftHandRotationAxis, (float) -Math.PI / 2, (float) Math.PI / 4);
        leftHandRotation.setSchedulingBounds(bounds);

        // RIGHT HAND

        Transform3D rightHandTransform = new Transform3D();

        Transform3D rightHandXRot = new Transform3D();
        rightHandXRot.setRotation(new AxisAngle4d(-0.1, 0, 0, 50 * Math.PI / 180));
        rightHandTransform.mul(rightHandXRot, rightHandTransform);

        Transform3D rightHandYRot = new Transform3D();
        rightHandYRot.setRotation(new AxisAngle4d(0, -0.1, 0, -50 * Math.PI / 180));
        rightHandTransform.mul(rightHandYRot, rightHandTransform);

        Transform3D rightHandTranslate = new Transform3D();
        rightHandTranslate.set(new Vector3d(0.02, 0.12, -0.12));
        rightHandTransform.mul(rightHandTranslate, rightHandTransform);

        rightHandTG.setTransform(rightHandTransform);

        TransformGroup transformedRightHandTG = new TransformGroup();
        transformedRightHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformedRightHandTG.addChild(rightHandTG);

        Transform3D rightHandRotationAxis = new Transform3D();
        rightHandRotationAxis.set(new Vector3d(0, 0.23, -0.05));
        rightHandRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        Alpha rightHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator rightHandRotation = new RotationInterpolator(rightHandRotationAlpha, transformedRightHandTG,
                rightHandRotationAxis, (float) -Math.PI / 2, (float) Math.PI / 4);
        rightHandRotation.setSchedulingBounds(bounds);

        // AXE

        Transform3D axeTransform = new Transform3D();

        Transform3D axeYRot = new Transform3D();
        axeYRot.setRotation(new AxisAngle4d(0, -0.1, 0, Math.PI / 2));
        axeTransform.mul(axeYRot, axeTransform);

        Transform3D axeXRot = new Transform3D();
        axeXRot.setRotation(new AxisAngle4d(-0.1, 0, 0, -Math.PI / 6));
        axeTransform.mul(axeXRot, axeTransform);

        Transform3D axeTranslate = new Transform3D();
        axeTranslate.set(new Vector3d(-0.15, 0.5, 0.3));
        axeTransform.mul(axeTranslate, axeTransform);

        axeTG.setTransform(axeTransform);

        TransformGroup transformedAxeTG = new TransformGroup();
        transformedAxeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformedAxeTG.addChild(axeTG);

        Transform3D axeRotationAxis = new Transform3D();
        axeRotationAxis.set(new Vector3d(0, 0.23, -0.05));
        axeRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI / 2));

        Alpha axeRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator axeRotation = new RotationInterpolator(axeRotationAlpha, transformedAxeTG,
                axeRotationAxis, (float) -Math.PI / 2, (float) Math.PI / 4);
        axeRotation.setSchedulingBounds(bounds);

        transformedHeadTG.addChild(headRotation);
        transformedLeftHandTG.addChild(leftHandRotation);
        transformedRightHandTG.addChild(rightHandRotation);
        transformedAxeTG.addChild(axeRotation);
        upperBodyTG.addChild(upperBodyRotation);

        sceneGroup.addChild(transformedHeadTG);
        sceneGroup.addChild(transformedLeftHandTG);
        sceneGroup.addChild(transformedRightHandTG);
        sceneGroup.addChild(transformedAxeTG);
        sceneGroup.addChild(upperBodyTG);
        sceneGroup.addChild(lowerBodyTG);

        tgWarrior.addChild(sceneGroup);

        BranchGroup mainScene = new BranchGroup();

        mainScene.addChild(tgWarrior);

        Background bg = new Background(new Color3f(0.5f, 0.5f, 0.5f));

        bg.setApplicationBounds(bounds);
        mainScene.addChild(bg);
        mainScene.compile();

        universe.addBranchGraph(mainScene);
    }

    private void addLight() {
        BranchGroup bgLight = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

        Color3f lightColour = new Color3f(new Color(193, 193, 193));

        DirectionalLight directionalLight1 = new DirectionalLight(lightColour, new Vector3f(-1.0f, 0, -0.5f));
        directionalLight1.setInfluencingBounds(bounds);

        DirectionalLight directionalLight2 = new DirectionalLight(lightColour, new Vector3f(0, -1.0f, -1.0f));
        directionalLight2.setInfluencingBounds(bounds);

        bgLight.addChild(directionalLight1);
        bgLight.addChild(directionalLight2);

        universe.addBranchGraph(bgLight);
    }

    private static void setDefaultAppearance(Appearance app, Color3f col) {
        Material material = new Material();
        material.setEmissiveColor(new Color3f(Color.BLACK));
        material.setAmbientColor(col);
        material.setDiffuseColor(col);
        material.setSpecularColor(col);
        material.setShininess(64);
        material.setLightingEnable(true);

        app.setMaterial(material);
    }
}