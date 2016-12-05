package fr.utbm.drone.gui.graph.items;

import fr.utbm.drone.gui.graph.Material;
import fr.utbm.drone.gui.graph.Texture;
import fr.utbm.drone.gui.loaders.OBJLoader;

public class BridgeItem extends GuiItem {
	public BridgeItem(float scale, float x, float y, float z) throws Exception {
		super(OBJLoader.loadMesh("/models/sad_drone.obj"),new Material(new Texture("/textures/sad_drone_diffuse.png"), 1.0f),scale,x,y,z);
	}
}
