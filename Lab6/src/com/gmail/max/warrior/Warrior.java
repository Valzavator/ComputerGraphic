package com.gmail.max.warrior;


import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.util.Enumeration;
import java.util.Hashtable;

public class Warrior extends JFrame {

    //The canvas to be drawn upon
    public Canvas3D myCanvas3D;

    public Warrior() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);
        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);
        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Warrior");
        setSize(700, 700);
        getContentPane().add(BorderLayout.CENTER, myCanvas3D);
        setVisible(true);
    }

    @SuppressWarnings("Duplicates")
    public void createSceneGraph(SimpleUniverse su) {

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

        Hashtable mikeNamedObjects = warriorScene.getNamedObjects();
        Enumeration enumer = mikeNamedObjects.keys();
        while (enumer.hasMoreElements()) {
            String name = (String) enumer.nextElement();
            System.out.println("Name: " + name);
        }

        Shape3D head = (Shape3D) mikeNamedObjects.get("head");
        Shape3D leftHand = (Shape3D) mikeNamedObjects.get("left_hand");
        Shape3D rightHand = (Shape3D) mikeNamedObjects.get("right_hand");
        Shape3D axe = (Shape3D) mikeNamedObjects.get("axe");
        Shape3D body1 = (Shape3D) mikeNamedObjects.get("body1");
        Shape3D body2 = (Shape3D) mikeNamedObjects.get("body2");

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        TransformGroup bodyTG = new TransformGroup();
        bodyTG.addChild(body1.cloneTree());
        bodyTG.addChild(body2.cloneTree());


        var headTG = new TransformGroup();
        var leftHandTG = new TransformGroup();
        var rightHandTG = new TransformGroup();
        var axeTG = new TransformGroup();

        headTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        leftHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rightHandTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        axeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        headTG.addChild(head.cloneTree());
        leftHandTG.addChild(leftHand.cloneTree());
        rightHandTG.addChild(rightHand.cloneTree());
        axeTG.addChild(axe.cloneTree());

        BoundingSphere bounds = new BoundingSphere(new Point3d(0, 0, 0), Double.MAX_VALUE);
//
//        var tCrawl = new Transform3D();
//        var tCrawl1 = new Transform3D();
//        tCrawl.rotY(-90D);
//        tCrawl1.rotX(-90D);
//        long crawlTime = 10000;
//        var crawlAlpha = new Alpha(1,
//                Alpha.INCREASING_ENABLE,
//                0,
//                0, crawlTime, 0, 0, 0, 0, 0);
//        float crawlDistance = 3.0f;
//        var posICrawl = new PositionInterpolator(crawlAlpha,
//                sceneGroup, tCrawl, -9.0f, crawlDistance);
//
//        long crawlTime1 = 30000;
//        var crawlAlpha1 = new Alpha(1,
//                Alpha.INCREASING_ENABLE,
//                3000,
//                0, crawlTime1, 0, 0, 0, 0, 0);
//        float crawlDistance1 = 15.0f;
//        var posICrawl1 = new PositionInterpolator(crawlAlpha1,
//                sceneGroup, tCrawl1, -9.0f, crawlDistance1);
//
//
//        var bs = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
//        posICrawl.setSchedulingBounds(bs);
//        posICrawl1.setSchedulingBounds(bs);
//        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
//        sceneGroup.addChild(posICrawl1);


        int timeStart = 1000;
        int timeRotationHour = 3000;

        Transform3D headRotationAxis = new Transform3D();
        headRotationAxis.set(new Vector3d(-0.17, 0, -0.05));
//        headRotationAxis.rotY(Math.PI/2);
        headRotationAxis.setRotation(new AxisAngle4d(0, -0.1, 0, Math.PI/2));


        Alpha headRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator headRotation = new RotationInterpolator(headRotationAlpha, headTG,
                headRotationAxis, (float) -Math.PI / 4 , (float) Math.PI / 4);
        headRotation.setSchedulingBounds(bounds);

        Transform3D leftHandTransform = new Transform3D();

        Transform3D leftHandXRot = new Transform3D();
        leftHandXRot.setRotation(new AxisAngle4d(-0.1, 0, 0, 50* Math.PI / 180));
        leftHandTransform.mul(leftHandXRot, leftHandTransform);

        Transform3D leftHandYRot = new Transform3D();
        leftHandYRot.setRotation(new AxisAngle4d(0, -0.1, 0, 50* Math.PI / 180));
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
        leftHandRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI/2));

        Alpha leftHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator leftHandRotation = new RotationInterpolator(leftHandRotationAlpha, transformedLeftHandTG,
                leftHandRotationAxis, (float)  -Math.PI , 0.0f);
        leftHandRotation.setSchedulingBounds(bounds);

        Transform3D rightHandTransform = new Transform3D();

        Transform3D rightHandXRot = new Transform3D();
        rightHandXRot.setRotation(new AxisAngle4d(-0.1, 0, 0, 50* Math.PI / 180));
        rightHandTransform.mul(rightHandXRot, rightHandTransform);

        Transform3D rightHandYRot = new Transform3D();
        rightHandYRot.setRotation(new AxisAngle4d(0, -0.1, 0, -50* Math.PI / 180));
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
        rightHandRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI/2));

        Alpha rightHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        RotationInterpolator rightHandRotation = new RotationInterpolator(rightHandRotationAlpha, transformedRightHandTG,
                rightHandRotationAxis, (float)  -Math.PI , 0.0f);
        rightHandRotation.setSchedulingBounds(bounds);

        Transform3D axeTransform = new Transform3D();

        Transform3D rotate1 = new Transform3D();
        rotate1.setRotation(new AxisAngle4d(-0.1, 0, 0, 50* Math.PI / 180));
        rightHandTransform.mul(rotate1, rightHandTransform);

//        Transform3D rotate2 = new Transform3D();
//        rotate2.setRotation(new AxisAngle4d(0, -0.1, 0, -50* Math.PI / 180));
//        rightHandTransform.mul(rotate2, rightHandTransform);
//
//        Transform3D translate1 = new Transform3D();
//        translate1.set(new Vector3d(0.02, 0.12, -0.12));
//        rightHandTransform.mul(translate1, rightHandTransform);

        axeTG.setTransform(axeTransform);

        TransformGroup transformedAxeTG = new TransformGroup();
        transformedAxeTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformedAxeTG.addChild(axeTG);

//        Transform3D rightHandRotationAxis = new Transform3D();
//        rightHandRotationAxis.set(new Vector3d(0, 0.23, -0.05));
//        rightHandRotationAxis.setRotation(new AxisAngle4d(0, 0, -0.1, Math.PI/2));
//
//        Alpha rightHandRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
//                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
//        RotationInterpolator rightHandRotation = new RotationInterpolator(rightHandRotationAlpha, transformedRightHandTG,
//                rightHandRotationAxis, (float)  -Math.PI , 0.0f);
//        rightHandRotation.setSchedulingBounds(bounds);

        headTG.addChild(headRotation);
        transformedLeftHandTG.addChild(leftHandRotation);
        transformedRightHandTG.addChild(rightHandRotation);

        sceneGroup.addChild(headTG);
        sceneGroup.addChild(bodyTG);
        sceneGroup.addChild(transformedLeftHandTG);
        sceneGroup.addChild(transformedRightHandTG);
        sceneGroup.addChild(transformedAxeTG);

        tgWarrior.addChild(sceneGroup);

        BranchGroup mainScene = new BranchGroup();

        mainScene.addChild(tgWarrior);

        Background bg = new Background(new Color3f(0.5f, 0.5f, 0.5f));

        bg.setApplicationBounds(bounds);
        mainScene.addChild(bg);
        mainScene.compile();

        su.addBranchGraph(mainScene);
    }


    private void addLight(SimpleUniverse simpleUniverse) {
        BranchGroup bgLight = new BranchGroup();

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        Color3f lightColour = new Color3f(1.0f, 1.0f, 1.0f);
        Vector3f lightDir = new Vector3f(-1.0f, 0.0f, -0.5f);
        DirectionalLight light = new DirectionalLight(lightColour, lightDir);
        light.setInfluencingBounds(bounds);

        bgLight.addChild(light);
        simpleUniverse.addBranchGraph(bgLight);
    }
}