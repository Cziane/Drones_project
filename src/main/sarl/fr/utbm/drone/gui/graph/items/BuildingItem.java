package fr.utbm.drone.gui.graph.items;

import fr.utbm.drone.gui.graph.Material;
import fr.utbm.drone.gui.graph.Texture;
import fr.utbm.drone.gui.loaders.OBJLoader;

public class BuildingItem extends GuiItem {
	public BuildingItem(float scale, float x, float y, float z) throws Exception {
		super(OBJLoader.loadMesh("/models/cube.obj"),new Material(new Texture("/textures/Building_texture.png"), 1.0f),scale,x,y,z);
	}
}
