package fr.utbm.drone.gui.graph.items;

import org.joml.Vector3f;

import fr.utbm.drone.gui.graph.Material;
import fr.utbm.drone.gui.graph.Mesh;

public abstract class GuiItem {

    private final Mesh mesh;
    
    private final Vector3f position;
    private  Vector3f newPosition;
    
    private float scale;

    private final Vector3f rotation;

    protected GuiItem(Mesh mesh,Material mat,float scale,float x, float y, float z) {
        this.mesh = mesh;
        this.mesh.setMaterial(mat);
        position = new Vector3f(x, y, z);
        newPosition = new Vector3f(x, y, z);
        this.scale = scale;
        rotation = new Vector3f(0, 0, 0);
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.newPosition.x = x;
        this.newPosition.y = y;
        this.newPosition.z = z;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
    
    public void refresh(){
    	this.position.x = this.newPosition.x;
        this.position.y = this.newPosition.y;
        this.position.z = this.newPosition.z;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public void setRotation(float x, float y, float z) {
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }
    
    public Mesh getMesh() {
        return mesh;
    }
}