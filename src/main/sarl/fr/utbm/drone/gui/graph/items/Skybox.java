package fr.utbm.drone.gui.graph.items;

import fr.utbm.drone.gui.graph.Material;
import fr.utbm.drone.gui.graph.Texture;
import fr.utbm.drone.gui.loaders.OBJLoader;

public class Skybox extends GuiItem {

    public Skybox() throws Exception {
    	super(OBJLoader.loadMesh("/models/skybox.obj"),new Material(new Texture("/textures/SkyBox1.png"), 0.0f),230.0f,100,217,100);
    }
}