package com.gmail.max.examples.java3d_example;

import java.applet.Applet;

import java.awt.*;

import java.awt.event.*;

import java.awt.event.WindowAdapter;

import com.sun.j3d.utils.applet.MainFrame;

import com.sun.j3d.utils.universe.*;

import javax.media.j3d.*;

import javax.vecmath.*;

import com.sun.j3d.utils.geometry.Sphere;

import javax.swing.Timer;

public class BouncingBall extends Applet implements ActionListener, KeyListener {

	private Button go = new Button("Go");
	private TransformGroup objTrans;
	private Transform3D trans = new Transform3D();
	private float height = 0.0f;
	private float sign = 1.0f; // going up or down
	private Timer timer;
	private float xloc = 0.0f;

	public static void main(String[] args) {
		System.out.println("Program Started");
		BouncingBall bb = new BouncingBall();
		bb.addKeyListener(bb);
	}

	public BranchGroup createSceneGraph() {

		// створюємо групу об'єктів
		BranchGroup objRoot = new BranchGroup();

		// створюємо об'єкт, що будемо додавати до групи
		Sphere sphere = new Sphere(0.25f);
		objTrans = new TransformGroup();
		objTrans.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D pos1 = new Transform3D();
		pos1.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f));
		objTrans.setTransform(pos1);
		objTrans.addChild(sphere);
		objRoot.addChild(objTrans);

		//налаштовуємо освітлення
		BoundingSphere bounds =	new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		Color3f light1Color = new Color3f(1.0f, 0.0f, 0.2f);
		Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
		DirectionalLight light1	= new DirectionalLight(light1Color, light1Direction);
		light1.setInfluencingBounds(bounds);
		objRoot.addChild(light1);

		// встановлюємо навколишнє освітлення
		Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
		AmbientLight ambientLightNode = new AmbientLight(ambientColor);
		ambientLightNode.setInfluencingBounds(bounds);
		objRoot.addChild(ambientLightNode);
		return objRoot;

	}

	public BouncingBall() {

		//налаштовуємо вікно
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D c = new Canvas3D(config);
		// розміщуємо сцену в центрі фрейму
		add("Center", c);
		// підписуємо об'єкт поточного класу на подію натиснення кнопки клавіатури
		c.addKeyListener(this);
		// створюємо об'єкт таймеру з інтервалом 100 мілісекунд та підписуємо
		timer = new Timer(100, this);
		Panel p = new Panel();
		p.add(go);
		add("North", p);
		go.addActionListener(this);
		go.addKeyListener(this);

		// створюємо просту сцену та додаємо її до простору
		BranchGroup scene = createSceneGraph();
		SimpleUniverse u = new SimpleUniverse(c);
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

	}

	public void keyPressed(KeyEvent e) {

		// дія по натисненню на клавішу
		if (e.getKeyChar() == 's') {
			xloc = xloc + .1f;
		}
		if (e.getKeyChar() == 'a') {
			xloc = xloc - .1f;
		}
	}

	public void actionPerformed(ActionEvent e) {

		// запустити таймер по натисненню на клавішу
		if (e.getSource() == go) {
			if (!timer.isRunning()) {
				timer.start();
			}
		}
		else {
			height += .1 * sign;
			if (Math.abs(height * 2) >= 1)
				sign = -1.0f * sign;
			if (height < -0.4f) {
				trans.setScale(new Vector3d(1.0, .8, 1.0));
			}
			else {
				trans.setScale(new Vector3d(1.0, 1.0, 1.0));
			}
			trans.setTranslation(new Vector3f(xloc, height, 0.0f));
			objTrans.setTransform(trans);
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}
}
