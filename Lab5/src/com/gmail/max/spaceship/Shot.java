package com.gmail.max.spaceship;

import com.sun.j3d.utils.geometry.Cylinder;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Vector3f;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Shot extends BranchGroup implements ActionListener {
    private static final float SPEED = 0.05f;

    private float xLoc;
    private float yLoc;
    private float zLoc;

    private TransformGroup transformGroup = new TransformGroup();
    private Transform3D transform3D = new Transform3D();

    Shot(float xLoc, float yLoc, float zLoc) {
        this.xLoc = xLoc;
        this.yLoc = yLoc;
        this.zLoc = zLoc;

        Appearance bodyAppearance = new Appearance();
        bodyAppearance.setMaterial(
                new Material(
                        new Color3f(Color.BLACK),
                        new Color3f(new Color(255, 4, 6)),
                        new Color3f(Color.BLACK),
                        new Color3f(Color.BLACK),
                        64.0f));

        Cylinder shot = new Cylinder(0.01f, 0.1f, bodyAppearance);

        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        transformGroup.addChild(shot);
        transform3D.setTranslation(new Vector3f(xLoc, yLoc, zLoc));
        transformGroup.setTransform(transform3D);

        addChild(transformGroup);

        Timer timer = new Timer(100, this);
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        yLoc += SPEED;

        transform3D.setTranslation(new Vector3f(xLoc, yLoc, zLoc));
        transformGroup.setTransform(transform3D);
    }
}
