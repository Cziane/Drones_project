package fr.utbm.drone.gui.graph.items;

import fr.utbm.drone.gui.graph.Material;
import fr.utbm.drone.gui.graph.Mesh;
import fr.utbm.drone.gui.graph.Texture;
import fr.utbm.drone.gui.loaders.OBJLoader;

public class TargetItem extends GuiItem {

	public TargetItem(float scale, float x, float y, float z) throws Exception {
		super(OBJLoader.loadMesh("/models/arrow4.obj"),new Material(new Texture("/textures/Red-Metal-Texture.png"), 1.0f),scale,x,y,z);
	}

}
