package fr.utbm.drone.environment;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.UUID;

import javax.vecmath.Point3f;

import fr.utbm.drone.environment.storage.OcNode;
import fr.utbm.drone.gui.graph.items.GuiItem;
import fr.utbm.drone.maths.Cube3f;
import fr.utbm.drone.maths.Shape3f;
import fr.utbm.drone.physics.physicItem;

/*
 * The abstract representation of all environment objects
 */
public abstract class AbstractEnvObject implements Serializable {
	/**
	 *   
	 */
	private static final long serialVersionUID = 625956252316181230L;
	private WeakReference<OcNode> node;
	private Shape3f<?> shape;
	private Point3f position = new Point3f();
	private final UUID ID;
	private String name;
	protected GuiItem graphic;
	protected physicItem body;
	

	public AbstractEnvObject(Point3f position, Shape3f<?> shape, UUID id2) {
		super();
		this.shape = shape;
		this.position = position;
		this.ID = id2;
	}

	public AbstractEnvObject(UUID id, Shape3f<?> shape) {
		super();
		this.shape = shape;
		this.ID = id;
	}

	
	public OcNode getNode() {
		return node.get();
	}



	public void setNode(OcNode node) {
		this.node = new WeakReference<>(node);
	}



	public Point3f getPosition() {
		return (Point3f) position.clone();
	}



	public void setPosition(Point3f position) {
		this.position.set(position);
	}

	protected void addPosition(float x, float y, float z){
		this.position.x+=x;
		this.position.y+=y;
		this.position.z+=z;
		if(this.graphic!=null){
			this.graphic.setPosition(position.x, position.y, position.z);
		}
	}

	public UUID getId() {
		return ID;
	}


	public void setShape(Shape3f<?> shape) {
		this.shape = shape;
	}


	public Shape3f<?> getShape() {
		return this.shape;
	}


	public Cube3f getBox() {
		return this.shape.getBox();
	}

	protected void setPosition(float x, float y, float z) {
		this.position.set(x, y, z);
	}

	/**
	 * @return the type
	 */
	public abstract Serializable getType();
/*	protected AbstractEnvObject clone(){
		T obj;
		return obj;
	}*/

	public String getName() {
		return this.name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public void setGraphi(GuiItem item){
		this.graphic=item;
	}
	
	public physicItem getPhysic(){
		return this.body;
	}
	
	public abstract void applyTransform();
}
