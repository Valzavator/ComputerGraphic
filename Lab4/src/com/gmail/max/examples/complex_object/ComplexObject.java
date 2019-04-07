package com.gmail.max.examples.complex_object;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class ComplexObject implements ActionListener {

	private TransformGroup treeTransformGroup;
	private Transform3D treeTransform3D = new Transform3D();
	private Timer timer;
	private float angle = 0;

	public static void main(String[] args) {
		new ComplexObject();
	}

	public ComplexObject() {
		timer = new Timer(50, this);
		timer.start();
		BranchGroup scene = createSceneGraph();
		SimpleUniverse u = new SimpleUniverse();
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);
	}

	public BranchGroup createSceneGraph() {

		// створюємо групу об'єктів
		BranchGroup objRoot = new BranchGroup();

		// створюємо об'єкт, що будемо додавати до групи
		treeTransformGroup = new TransformGroup();
		treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		buildTree();
		objRoot.addChild(treeTransformGroup);

		// налаштовуємо освітлення
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
		Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1 = new DirectionalLight(light1Color,	light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		// встановлюємо навколишнє освітлення
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);
		return objRoot;
	}

	private void buildTree() {
		// створюємо гілки ялинки
		TransformGroup tgTop = new TransformGroup();
		Transform3D transformTop = new Transform3D();
		Cone coneTop = XMassTree.getCone(0.3f, 0.2f);
		Vector3f vectorTop = new Vector3f(.0f, 0.2f, .0f);
		transformTop.setTranslation(vectorTop);
		tgTop.setTransform(transformTop);
		tgTop.addChild(coneTop);
		treeTransformGroup.addChild(tgTop);

		TransformGroup tgMiddle = new TransformGroup();
		Transform3D transformMiddle = new Transform3D();
		Cone coneMiddle = XMassTree.getCone(0.3f, 0.25f);
		Vector3f vectorMiddle = new Vector3f(.0f, .0f, .0f);
		transformMiddle.setTranslation(vectorMiddle);
		tgMiddle.setTransform(transformMiddle);
		tgMiddle.addChild(coneMiddle);
		treeTransformGroup.addChild(tgMiddle);

		TransformGroup tgBottom = new TransformGroup();
		Transform3D transformBottom = new Transform3D();
		Cone coneBottom = XMassTree.getCone(0.3f, 0.3f);
		Vector3f vectorBottom = new Vector3f(.0f, -0.2f, .0f);
		transformBottom.setTranslation(vectorBottom);
		tgBottom.setTransform(transformBottom);
		tgBottom.addChild(coneBottom);
		treeTransformGroup.addChild(tgBottom);

		// прикрашаємо її кульками
		createBall(0.03f, .0f, 0.35f, .0f, "", new Color3f(0.5f, 0.5f, 0.0f));

		// верхній ярус
		createBall(0.02f, 0.07f, 0.15f, 0.12f, "", new Color3f(0.0f, 0.0f, 1.0f));
		createBall(0.02f, -0.12f, 0.13f, 0.1f, "", new Color3f(0.2f, 0.5f, 0.5f));
		createBall(0.02f, 0.13f, 0.12f, 0.1f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.11f, 0.2f, 0.0f, "", new Color3f(0.2f, 1.2f, 1.5f));

		createBall(0.02f, -0.07f, 0.15f, -0.12f, "", new Color3f(0.0f, 0.0f, 1.0f));
		createBall(0.02f, 0.12f, 0.13f, -0.1f, "", new Color3f(0.2f, 0.5f, 0.5f));
		createBall(0.02f, -0.13f, 0.12f, -0.1f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.11f, 0.2f, -0.0f, "", new Color3f(0.2f, 1.2f, 1.5f));

		// середній ярус
		createBall(0.02f, -0.02f, 0.2f, 0.1f, "", new Color3f(0.5f, 0.0f, 0.5f));
		createBall(0.02f, 0.12f, -0.05f, 0.12f, "", new Color3f(0.0f, 0.5f, 0.5f));
		createBall(0.02f, -0.12f, -0.03f, 0.11f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.05f, -0.1f, 0.2f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.05f, -0.02f, 0.13f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.22f, -0.1f, 0.0f, "", new Color3f(1.2f, 1.2f, 0.5f));
		createBall(0.02f, 0.12f, .0f, 0.05f, "", new Color3f(0.2f, 0.2f, 1.5f));

		createBall(0.02f, 0.02f, 0.2f, -0.1f, "", new Color3f(0.5f, 1.0f, 0.5f));
		createBall(0.02f, -0.12f, -0.05f, -0.12f, "", new Color3f(0.0f, 0.5f, 0.5f));
		createBall(0.02f, 0.12f, -0.03f, -0.11f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.025f, 0.05f, -0.1f, -0.2f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.05f, -0.02f, -0.13f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.22f, -0.1f, -0.0f, "", new Color3f(1.2f, 1.2f, 0.5f));
		createBall(0.02f, -0.12f, .0f, -0.05f, "", new Color3f(0.2f, 0.2f, 1.5f));

		//нижній ярус
		createBall(0.02f, -0.02f, -0.3f, 0.25f, "", new Color3f(0.5f, 0.0f, 0.5f));
		createBall(0.02f, -0.12f, -0.23f, 0.14f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.05f, -0.22f, 0.16f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.23f, -0.3f, 0.1f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.12f, -0.28f, 0.2f, "", new Color3f(0.0f, 0.5f, 0.5f));
		createBall(0.02f, 0.12f, -0.25f, 0.16f, "", new Color3f(1.2f, 1.2f, 0.5f));
		createBall(0.02f, -0.2f, -0.28f, 0.13f, "", new Color3f(0.2f, 0.2f, 1.5f));
		createBall(0.02f, 0.15f, -0.2f, 0.05f, "", new Color3f(0.2f, 1.2f, 0.0f));

		createBall(0.02f, 0.02f, -0.3f, -0.25f, "", new Color3f(0.5f, 0.0f, 0.5f));
		createBall(0.02f, 0.12f, -0.23f, -0.14f, "", new Color3f(0.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.05f, -0.22f, -0.16f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, -0.23f, -0.3f, -0.1f, "", new Color3f(1.2f, 0.2f, 0.5f));
		createBall(0.02f, 0.12f, -0.28f, -0.2f, "", new Color3f(0.0f, 0.5f, 0.5f));
		createBall(0.02f, -0.12f, -0.25f, -0.16f, "", new Color3f(1.2f, 1.2f, 0.5f));
		createBall(0.02f, 0.2f, -0.28f, -0.13f, "", new Color3f(0.2f, 0.2f, 1.5f));
		createBall(0.02f, -0.15f, -0.2f, -0.05f, "", new Color3f(0.6f, 1.2f, 0.0f));
	}

	private void createBall(float radius, float x, float y, float z, String picture, Color3f emissive) {
		TransformGroup tg = new TransformGroup();
		Transform3D transform = new Transform3D();
		Sphere cone = XMassBall.getSphere(radius, picture, emissive);
		Vector3f vector = new Vector3f(x, y, z);
		transform.setTranslation(vector);
		tg.setTransform(transform);
		tg.addChild(cone);
		treeTransformGroup.addChild(tg);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		treeTransform3D.rotY(angle);
		treeTransformGroup.setTransform(treeTransform3D);
		angle += 0.05;
	}
}
