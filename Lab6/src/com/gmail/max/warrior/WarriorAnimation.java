package com.gmail.max.warrior;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.*;
import javax.vecmath.Vector3d;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class WarriorAnimation extends KeyAdapter implements ActionListener {

    private static final double DELTA_ANGLE = 0.1f;

    private TransformGroup headTG;
    private double angle = 0;

    WarriorAnimation(TransformGroup headTG) {
        this.headTG = headTG;

        Timer timer = new Timer(10, this);
        timer.start();
    }

    @Override
    public void keyPressed(KeyEvent ev) {
        switch (ev.getKeyCode()) {
            case 32: // Space
                angle -= angle;
                break;
            case 65: // A
                if (angle < Math.PI/2) {
                    angle += DELTA_ANGLE;
                }
                break;
            case 68: // D
                if (angle > - Math.PI/2) {
                    angle -= DELTA_ANGLE;
                }
                break;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Transform3D headTransform = new Transform3D();

        Transform3D translateToCenter = new Transform3D();
        translateToCenter.set(new Vector3d(0.17, 0 ,0.04));
        headTransform.mul(translateToCenter, headTransform);

        Transform3D headYRot = new Transform3D();
        headYRot.rotY(angle);
        headTG.setTransform(headYRot);
        headTransform.mul(headYRot, headTransform);

        Transform3D backTranslate = new Transform3D();
        backTranslate.set(new Vector3d(-0.17, 0 ,-0.04));
        headTransform.mul(backTranslate, headTransform);

        headTG.setTransform(headTransform);
    }
}
