package com.gmail.max.spaceship;

import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.lw3d.Lw3dLoader;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class Spaceship extends JFrame {

    private Canvas3D canvas;
    private SimpleUniverse universe;
    private BranchGroup root;

    private TransformGroup space = new TransformGroup();
    private TransformGroup spaceship;

    private Map<String, Shape3D> shapeMap;

    public Spaceship() throws IOException {

        configureWindow();
        configureCanvas();
        configureUniverse();

        root = new BranchGroup();
        root.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);


        addImageBackground("resource/image/space-texture.jpg");
        addLightToUniverse();

        ChangeViewAngle();

        spaceship = getSpaceshipGroup();
        space.addChild(spaceship);

        root.addChild(space);

        addAppearance();

        SpaceshipAnimation spaceshipAnimation = new SpaceshipAnimation(this);
        canvas.addKeyListener(spaceshipAnimation);

        root.compile();
        universe.addBranchGraph(root);
    }

    private void configureWindow() {
        setTitle("Spaceship Animation");
        setSize(1000, 622);
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
    }

    private void addImageBackground(String imagePath) {
        TextureLoader t = new TextureLoader(imagePath, canvas);
        Background background = new Background(t.getImage());
        background.setImageScaleMode(Background.SCALE_FIT_ALL);
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        background.setApplicationBounds(bounds);
        root.addChild(background);
    }

    private void addLightToUniverse() {
        BoundingSphere bounds = new BoundingSphere();
        bounds.setRadius(1000);

        DirectionalLight directionalLight = new DirectionalLight(
                new Color3f(new Color(255, 255, 255)),
                new Vector3f(0, -0.5f, -0.5f));
        directionalLight.setInfluencingBounds(bounds);

        AmbientLight ambientLight = new AmbientLight(
                new Color3f(new Color(255, 255, 245)));
        ambientLight.setInfluencingBounds(bounds);

        root.addChild(directionalLight);
        root.addChild(ambientLight);
    }

    private TransformGroup getSpaceshipGroup() throws IOException {
        Transform3D transform3D = new Transform3D();

        transform3D.setTranslation(new Vector3d(1, 0,0));

        TransformGroup group = getModelGroup("resource/spaceship.obj");
        group.setTransform(transform3D);

        return group;
    }

    private TransformGroup getModelGroup(String path) throws IOException {
        Scene scene = getSceneFromFile(path);
        shapeMap = scene.getNamedObjects();

        printModelElementsList(shapeMap);

        TransformGroup group = new TransformGroup();

        for (String shapeName: shapeMap.keySet()) {
            Shape3D shape = shapeMap.get(shapeName);

            scene.getSceneGroup().removeChild(shape);
            group.addChild(shape);
        }

        group.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        return group;
    }



    private void printModelElementsList(Map<String, Shape3D> shapeMap) {
        for (String name : shapeMap.keySet()) {
            System.out.printf("Name: %s\n", name);
        }
    }


    private void addAppearance() {
        Appearance bodyAppearance = new Appearance();
//        bodyAppearance.setTexture(getTexture("resource/image/hull.jpg"));
//        TextureAttributes texAttr = new TextureAttributes();
//        texAttr.setTextureMode(TextureAttributes.MODULATE);
        bodyAppearance.setMaterial(getMaterial(
                Color.BLACK,
                new Color(98, 98, 98)));
        shapeMap.get("hull").setAppearance(bodyAppearance);

        Appearance solarPanelsAppearance = new Appearance();
        solarPanelsAppearance.setMaterial(getMaterial(
                Color.BLACK,
                new Color(0, 9, 152)));
        shapeMap.get("solar_panals").setAppearance(solarPanelsAppearance);

        Appearance gunAppearance = new Appearance();
        gunAppearance.setMaterial(getMaterial(
                Color.BLACK,
                new Color(50, 49, 49)));
        shapeMap.get("gun").setAppearance(gunAppearance);

        Appearance glassAppearance = new Appearance();
        glassAppearance.setMaterial(getMaterial(
                Color.BLACK,
                new Color(53, 61, 224)));
        shapeMap.get("glass").setAppearance(glassAppearance);

        Appearance engineAppearance = new Appearance();
        engineAppearance.setMaterial(getMaterial(
                new Color(255, 255, 255),
                Color.BLACK));
        shapeMap.get("03___default").setAppearance(engineAppearance);
    }

    Texture getTexture(String path) {
        TextureLoader textureLoader = new TextureLoader(path, "LUMINANCE", canvas);
        Texture texture = textureLoader.getTexture();
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));
        return texture;
    }

    Material getMaterial(
            Color emissiveColor,
            Color defaultColor) {
        Material material = new Material();
        material.setEmissiveColor(new Color3f(emissiveColor));
        material.setAmbientColor(new Color3f(defaultColor));
        material.setDiffuseColor(new Color3f(defaultColor));
        material.setSpecularColor(new Color3f(defaultColor));
        material.setShininess(64);
        material.setLightingEnable(true);
        return material;
    }

    private void ChangeViewAngle() {
        ViewingPlatform vp = universe.getViewingPlatform();
        TransformGroup vpGroup = vp.getMultiTransformGroup().getTransformGroup(0);
        Transform3D vpTranslation = new Transform3D();
        vpTranslation.setTranslation(new Vector3f(0, 0, 6));
        vpGroup.setTransform(vpTranslation);


//        Point3d eye = new Point3d(0, -5, 6); // spectator's eye
//        Point3d center = new Point3d(.0f, .0f, .0f); // sight target
//        Vector3d up = new Vector3d(.0f, .0f, 1.0f);
//
//        vpTranslation.lookAt(eye, center, up);
//        vpTranslation.invert();
//        vpGroup.setTransform(vpTranslation);
    }

    public static Scene getSceneFromFile(String location) throws IOException {
        ObjectFile file = new ObjectFile(ObjectFile.RESIZE);
        file.setFlags(ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY);
        return file.load(new FileReader(location));
    }

    public void generateShot(float xLoc, float yLoc, float zLoc) {
        root.addChild(new Shot(xLoc, yLoc, zLoc));
    }

    public TransformGroup getSpaceshipTransformGroup() {
        return spaceship;
    }


//    //Not always works
//    public static Scene getSceneFromLwoFile(String location) throws IOException {
//        Lw3dLoader loader = new Lw3dLoader();
//        return loader.load(location);
//    }
}
