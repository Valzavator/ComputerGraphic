package com.gmail.max.examples.lighting;

import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.DirectionalLight;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class Lighting {

	public static void main(String[] args) {
		new Lighting();
	}

	public Lighting()

	{
		// Створюємо простір
		SimpleUniverse universe = new SimpleUniverse();
		// Створюємо групу
		BranchGroup group = new BranchGroup();
		// Створюємо кулю, що додаємо у групу об'єктів
		Sphere sphere = new Sphere(0.5f);
		group.addChild(sphere);

		// Створюємо зелене світло, яке  світить з відстані 100м від об'єкту
		Color3f light1Color = new Color3f(0.8f, 1.1f, 0.1f); // параметри конструктору - це відповідно червона, зелена та синя компоненти кольору
		BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0); // вказуємо сферу, внутрішній простір якої буде освітлено
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f); //встановлюємо вектор, що задає напрям освітлення
		DirectionalLight light1 = new DirectionalLight(light1Color,	light1Direction); //створюмо власне об'єкт освітлення
		light1.setInfluencingBounds(bounds); //вказуємо, яка частина сцени має бути освітлена
		group.addChild(light1); //додаємо освітлення ло сцени

		// встановлюємо точку перегляду за замовченням
		universe.getViewingPlatform().setNominalViewingTransform();
		// додаємо створену групу у простір
		universe.addBranchGraph(group);
	}
}
