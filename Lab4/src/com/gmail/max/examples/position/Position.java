package com.gmail.max.examples.position;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Position {

	public static void main(String[] args) {
		new Position();
	}

	public Position()

	{
		SimpleUniverse universe = new SimpleUniverse();
		BranchGroup group = new BranchGroup();

		// створюємо вісь Х за допомогою сфер
		for (float x = -1.0f; x <= 1.0f; x = x + 0.1f)
		{
			// створюємо 3д об'єкт (сферу)
			Sphere sphere = new Sphere(0.05f);
			// створюємо групу трансформації
			TransformGroup tg = new TransformGroup();
			// створюємо 3д трансформацію
			Transform3D transform = new Transform3D();
			// створюємо вектор, на яких буде здійснене перенесення
			Vector3f vector = new Vector3f(x, .0f, .0f);
			// задаємо трансформації властивість перенесення на вказаний вектор
			transform.setTranslation(vector);
			// вказуємо створену трансформацію заданій групі трансформації
			tg.setTransform(transform);
			// вказуємо об'єкт, що потрібно трансформувати
			tg.addChild(sphere);
			// додаємо групу трансформації до сцени (сам об'єкт додавати уже не потрібно!)
			group.addChild(tg);
		}

		// вісь У за допомогою конусів
		for (float y = -1.0f; y <= 1.0f; y = y + 0.1f)
		{
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Cone cone = new Cone(0.05f, 0.1f);
			Vector3f vector = new Vector3f(.0f, y, .0f);
			transform.setTranslation(vector);
			tg.setTransform(transform);
			tg.addChild(cone);
			group.addChild(tg);
		}

		// вісь Z за допомогою циліндрів
		for (float z = -1.0f; z <= 1.0f; z = z + 0.1f)
		{
			TransformGroup tg = new TransformGroup();
			Transform3D transform = new Transform3D();
			Cylinder cylinder = new Cylinder(0.05f, 0.1f);
			Vector3f vector = new Vector3f(.0f, .0f, z);
			transform.setTranslation(vector);
			tg.setTransform(transform);
			tg.addChild(cylinder);
			group.addChild(tg);
		}

		// встановлюємо жовте освітлення
		Color3f light1Color = new Color3f(1.7f, 1.6f, .0f);
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1	= new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		group.addChild(light1);

		// встановлюємо точку перегляду та вставляємо групу об'єктів у сцену
		universe.getViewingPlatform().setNominalViewingTransform();
		universe.addBranchGraph(group);
	}
}
