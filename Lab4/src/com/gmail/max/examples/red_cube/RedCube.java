package com.gmail.max.examples.red_cube;

import javax.media.j3d.BranchGroup;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.universe.SimpleUniverse;

public class RedCube {

	public static void main(String[] args) {
		new RedCube();
	}

	public RedCube()

	{
		// створюємо простір, в якому будемо працювати
		SimpleUniverse universe = new SimpleUniverse();
		// створюємо групу, в яку додаємо об'єкти для відображення
		BranchGroup group = new BranchGroup();
		// додаємо в групу куб зі стороною, що дорівнює 0.3 від штрини вікна
		group.addChild(new ColorCube(0.3));
		// встановлюємо точку перегляду за замовченням
		universe.getViewingPlatform().setNominalViewingTransform();
		// додаємо створену групу у простір
		universe.addBranchGraph(group);
	}
}
