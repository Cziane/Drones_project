package fr.utbm.drone.environment.storage;

import java.util.List;

import fr.utbm.drone.environment.AbstractEnvObject;
import fr.utbm.drone.maths.Cube3f;
import fr.utbm.drone.maths.Shape3f;

public class OcTree implements Iterable<OcNode> {
	private OcNode root = new OcNode();
	private OcTreeIterator treeIt;
	
	public OcTree(OcNode root) {
		super();
		this.root = root;
	}

	public OcTree(Cube3f shape) {
		super();
		this.root.setTree(this);
		this.root.setShape(shape);
	}

	public OcNode getRoot() {
		return root;
	}

	public void setRoot(OcNode root) {
		this.root = root;
	}
	
	public void addObject(AbstractEnvObject obj){
		this.root.addObject(obj);
	}

	@Override
	public OcTreeIterator iterator() {
		return this.treeIt;
	}

	public List<? extends AbstractEnvObject> findPerceivedObjects(Shape3f<?> f) {
		return this.root.findObjectsIn(f);
	}

	/**
	 * Update the given object in the tree
	 * 
	 * @param obj
	 */
	public void update(AbstractEnvObject obj) {
		obj.getNode().update(obj);
	}
	
	/**
	 * Update the whole tree
	 */
	public void update(){
		this.root.update();
	}
	
	public boolean remove(AbstractEnvObject obj){
		return this.root.remove(obj);
	}
}
