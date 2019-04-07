package com.gmail.max.examples.complex_object;

import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.vecmath.Color3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Primitive;

public class XMassTree {
	public static Cone getCone(float height, float radius) {
		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
		return new Cone(radius, height, primflags, getXMassTreeAppearence());
	}

	private static Appearance getXMassTreeAppearence() {
		Appearance ap = new Appearance();
		Color3f emissive = new Color3f(0.0f, 0.05f, 0.0f);
		Color3f ambient = new Color3f(0.2f, 0.5f, 0.15f);
		Color3f diffuse = new Color3f(0.2f, 0.15f, .15f);
		Color3f specular = new Color3f(0.0f, 0.8f, 0.0f);
		ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
		return ap;
	}
}
