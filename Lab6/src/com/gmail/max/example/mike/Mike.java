package com.gmail.max.example.mike;


import javax.vecmath.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import com.sun.j3d.utils.behaviors.vp.*;

import javax.swing.JFrame;
import com.sun.j3d.loaders.*;
import com.sun.j3d.loaders.objectfile.*;

public class Mike extends JFrame
{
    public Canvas3D myCanvas3D;

    public Mike() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        myCanvas3D = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        SimpleUniverse simpUniv = new SimpleUniverse(myCanvas3D);

        simpUniv.getViewingPlatform().setNominalViewingTransform();

        createSceneGraph(simpUniv);

        addLight(simpUniv);

        OrbitBehavior ob = new OrbitBehavior(myCanvas3D);
        ob.setSchedulingBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE));
        simpUniv.getViewingPlatform().setViewPlatformBehavior(ob);

        setTitle("Sample");
        setSize(700, 700);
        getContentPane().add("Center", myCanvas3D);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new Mike();
    }

    public void createSceneGraph(SimpleUniverse su)
    {

        var f = new ObjectFile(ObjectFile.RESIZE);
        Scene mikeScene = null;
        try
        {
            mikeScene = f.load("resource/models/mike.obj");
        }
        catch (Exception e)
        {
            System.out.println("File loading failed:" + e);
        }

        var scaling = new Transform3D();
        scaling.setScale(1.0/6);
        var tfMike = new Transform3D();
        tfMike.rotX(Math.PI/3);
        tfMike.mul(scaling);
        var tgMike = new TransformGroup(tfMike);
        var sceneGroup = new TransformGroup();


        var mikeNamedObjects = mikeScene.getNamedObjects();
        var enumer = mikeNamedObjects.keys();
        String name;
        while (enumer.hasMoreElements())
        {
            name = (String) enumer.nextElement();
            System.out.println("Name: "+name);
        }

        var leftLeg = (Shape3D) mikeNamedObjects.get("left_leg");
        var rightLeg = (Shape3D) mikeNamedObjects.get("right_leg");
        var leftHand = (Shape3D) mikeNamedObjects.get("left_hand");
        var rightHand = (Shape3D) mikeNamedObjects.get("right_hand");
        var monstr = (Shape3D) mikeNamedObjects.get("monstr");

        var texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        var transformGroup = new TransformGroup();
        transformGroup.addChild(monstr.cloneTree());


        var leftLegGr = new TransformGroup();
        var rightLegGr = new TransformGroup();
        var leftHandGr = new TransformGroup();
        var rightHandGr = new TransformGroup();
        leftLegGr.addChild(leftLeg.cloneTree());
        rightLegGr.addChild(rightLeg.cloneTree());
        leftHandGr.addChild(leftHand.cloneTree());
        rightHandGr.addChild(rightHand.cloneTree());


        var bounds = new BoundingSphere(new Point3d(120.0,250.0,100.0),Double.MAX_VALUE);
        var theScene = new BranchGroup();
        var tCrawl = new Transform3D();
        var tCrawl1 = new Transform3D();
        tCrawl.rotY(-90D);
        tCrawl1.rotX(-90D);
        long crawlTime = 10000;
        var crawlAlpha = new Alpha(1,
                Alpha.INCREASING_ENABLE,
                0,
                0, crawlTime,0,0,0,0,0);
        float crawlDistance = 3.0f;
        var posICrawl = new PositionInterpolator(crawlAlpha,
                sceneGroup,tCrawl, -9.0f, crawlDistance);

        long crawlTime1 = 30000;
        var crawlAlpha1 = new Alpha(1,
                Alpha.INCREASING_ENABLE,
                3000,
                0, crawlTime1,0,0,0,0,0);
        float crawlDistance1 = 15.0f;
        var posICrawl1 = new PositionInterpolator(crawlAlpha1,
                sceneGroup,tCrawl1, -9.0f, crawlDistance1);


        var bs = new BoundingSphere(new Point3d(0.0,0.0,0.0),Double.MAX_VALUE);
        posICrawl.setSchedulingBounds(bs);
        posICrawl1.setSchedulingBounds(bs);
        sceneGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        sceneGroup.addChild(posICrawl);


        int timeStart = 500;
        int timeRotationHour = 500;

        var leftLegRotationAxis = new Transform3D();
        leftLegRotationAxis.rotZ(Math.PI / 2);
        var leftLegRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, timeStart, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        var leftLegRotation = new RotationInterpolator(leftLegRotationAlpha, leftLegGr,
                leftLegRotationAxis, (float) Math.PI / 4, 0.0f);
        leftLegRotation.setSchedulingBounds(bounds);


        var rightHandRotationAxis = new Transform3D();
        rightHandRotationAxis.rotZ(Math.PI / 2);
        var rightHandRotation = new RotationInterpolator(leftLegRotationAlpha, rightHandGr,
                rightHandRotationAxis, (float) Math.PI / 4, 0.0f);
        rightHandRotation.setSchedulingBounds(bounds);


        var rightLegRotationAxis = new Transform3D();
        rightLegRotationAxis.rotZ(Math.PI / 2);
        var rightLegRotationAlpha = new Alpha(-1, Alpha.INCREASING_ENABLE | Alpha.DECREASING_ENABLE, 0, 0,
                timeRotationHour, 0, 0, timeRotationHour, 0, 0);
        var rightLegRotation = new RotationInterpolator(rightLegRotationAlpha, rightLegGr,
                rightLegRotationAxis, (float) Math.PI / 4, 0.0f);
        rightLegRotation.setSchedulingBounds(bounds);

        var leftHandRotationAxis = new Transform3D();
        leftHandRotationAxis.rotZ(Math.PI / 2);
        var leftHandRotation = new RotationInterpolator(rightLegRotationAlpha, leftHandGr,
                leftHandRotationAxis, (float) Math.PI / 4, 0.0f);
        leftHandRotation.setSchedulingBounds(bounds);


        leftLegGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rightLegGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        leftHandGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        rightHandGr.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        leftLegGr.addChild(leftLegRotation);
        rightLegGr.addChild(rightLegRotation);
        leftHandGr.addChild(leftHandRotation);
        rightHandGr.addChild(rightHandRotation);

        sceneGroup.addChild(transformGroup);
        sceneGroup.addChild(leftLegGr);
        sceneGroup.addChild(rightLegGr);
        sceneGroup.addChild(leftHandGr);
        sceneGroup.addChild(rightHandGr);
        tgMike.addChild(sceneGroup);
        theScene.addChild(tgMike);

        var bg = new Background(new Color3f(0.5f,0.5f,0.5f));

        bg.setApplicationBounds(bounds);
        theScene.addChild(bg);
        theScene.compile();

        su.addBranchGraph(theScene);
    }


    public void addLight(SimpleUniverse su)
    {
        var bgLight = new BranchGroup();
        var bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 100.0);
        var lightColour1 = new Color3f(0.5f,1.0f,1.0f);
        var lightDir1 = new Vector3f(-1.0f,0.0f,-0.5f);
        var light1 = new DirectionalLight(lightColour1, lightDir1);
        light1.setInfluencingBounds(bounds);
        bgLight.addChild(light1);
        su.addBranchGraph(bgLight);
    }
}