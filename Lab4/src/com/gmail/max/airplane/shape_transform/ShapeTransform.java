package com.gmail.max.airplane.shape_transform;

import com.sun.j3d.utils.geometry.Primitive;

import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

public class ShapeTransform {
    private Primitive shape;

    private Transform3D transform3D;
    private TransformGroup transformGroup;

    private boolean isRotated;
    private boolean isTranlated;

    public ShapeTransform() {
        init();
    }

    public ShapeTransform setShape(Primitive shape) {
        if (shape == null) {
            throw new NullPointerException("Shape can`t be null!");
        }
        this.shape = shape;
        return this;
    }

    public ShapeTransform setTranslation(float x, float y, float z) {
        transform3D.setTranslation(new Vector3f(x, y, z));
        isTranlated = true;
        return this;
    }

    public ShapeTransform rotX(float degrees) {
        checkRotated();

        transform3D.rotX(degreesToRadian(degrees));
        isRotated = true;

        return this;
    }

    public ShapeTransform rotY(float degrees) {
        checkRotated();

        transform3D.rotY(degreesToRadian(degrees));
        isRotated = true;

        return this;
    }

    public ShapeTransform rotZ(float degrees) {
        checkRotated();

        transform3D.rotZ(degreesToRadian(degrees));
        isRotated = true;

        return this;
    }

    public ShapeTransform setScale(double x, double y, double z) {
        transform3D.setScale(new Vector3d(x, y, z));
        return this;
    }

    public TransformGroup getTransformGroup() {
        if (shape == null) {
            throw new IllegalStateException("Shape still not be generate!");
        }
        transformGroup.setTransform(transform3D);
        transformGroup.addChild(shape);

        TransformGroup transformGroupToReturn = transformGroup;

        init();

        return transformGroupToReturn;
    }

    private double degreesToRadian(float degrees) {
        return degrees * Math.PI / 180.0;
    }

    private void init() {
        shape = null;
        transformGroup = new TransformGroup();
        transform3D = new Transform3D();
        isRotated = false;
        isTranlated = false;
    }

    private void checkRotated() {
        if (isTranlated) {
            throw new IllegalStateException("Can`t rotate after translation!");
        }
        if (isRotated) {
            throw new IllegalStateException("Shape has been rotated!");
        }
    }
}
