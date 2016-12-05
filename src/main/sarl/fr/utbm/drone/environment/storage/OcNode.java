package fr.utbm.drone.environment.storage;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;

import fr.utbm.drone.environment.AbstractEnvObject;
import fr.utbm.drone.maths.Cube3f;
import fr.utbm.drone.maths.Shape3f;

public class OcNode implements Iterable<AbstractEnvObject> {
	private static int MAX_NODES = 8;
	private static int MAX_OBJECTS = 10;
	private WeakReference<OcTree> tree;
	private List<AbstractEnvObject> envObjects = new ArrayList<>();
	private WeakReference<OcNode> parent;
	private List<OcNode> children = new ArrayList<>();
	private OcNode extraNode;
	private OcNodeIterator nodeIt = new OcNodeIterator();
	private Cube3f shape = new Cube3f();
	private Point3f shapePosition = new Point3f();
	
	public OcNode() {
		//super();
	}
	
	public OcNode(OcTree tree) {
		super();
		this.tree = new WeakReference<>(tree);
	}
	
	public OcNode(OcTree tree, OcNode parent) {
		super();
		this.tree = new WeakReference<>(tree);
		this.parent = new WeakReference<>(parent);
	}

	public OcNode(Point3f shapePosition, Cube3f shape) {
		super();
		this.shape.set(shape);
		this.shapePosition.set(shapePosition);
	}
	
	public OcTree getTree() {
		return tree.get();
	}

	public void setTree(OcTree tree) {
		this.tree = new WeakReference<>(tree);
	}

	public OcNode getParent() {
		return parent.get();
	}

	public void setParent(OcNode parent) {
		this.parent = new WeakReference<>(parent);
	}

	public List<AbstractEnvObject> getEnvObjects() {
		return envObjects;
	}

	public List<OcNode> getChildren() {
		return children;
	}

	public Cube3f getShape() {
		return shape.clone();
	}

	public void setShape(Cube3f shape) {
		this.shape.set(shape);
	}

	public Point3f getShapePosition() {
		return (Point3f) shapePosition.clone();
	}

	public void setShapePosition(Point3f shapePosition) {
		this.shapePosition.set(shapePosition);
	}

	public boolean hasExtraNode() {
		return this.extraNode != null;
	}
	
	public OcNode getExtraNode() {
		return extraNode;
	}
	
	@Override
	public OcNodeIterator iterator() {
		return nodeIt;
	}
	
	public void addObject(AbstractEnvObject obj) {
		if(!this.isFull()){
			obj.setNode(this);
			this.envObjects.add(obj);
		}else{
			if(!this.hasChildren()) this.split();
			List<OcNode> containers = this.getChildContainers(obj.getShape());
			if(containers.size() > 1){
				this.envObjects.add(obj);
				/*if(this.extraNode == null){
					this.extraNode = new OcNode(this.tree.get(), this.parent.get());
					this.extraNode.setShape(obj.getBox());
				}
				this.extraNode.addObject(obj);*/
			}else{
				containers.get(0).addObject(obj);
			}
		}
	}
	
	public boolean isFull(){
		return this.envObjects.size() >= OcNode.MAX_OBJECTS;
	}
	
	public boolean hasChildren(){
		return !this.children.isEmpty();
	}
	
	public List<OcNode> getChildContainers(Shape3f<?> s){
		List<OcNode> result = new ArrayList<OcNode>();
		for(OcNode c : this.children){
			if(c.getShape().intersects(s)) result.add(c);
		}
		return result;
	}
	
	public void split(){
		for(int i = 0; i < OcNode.MAX_NODES; i++){
			Point3f initPos = new Point3f(
					this.shapePosition.getX() + (i%2)*this.shape.getWidth()/2,
					this.shapePosition.getY() + ((i/2)%2)*this.shape.getHeight()/2,
					this.shapePosition.getZ() + (i/4)*this.shape.getDepth()/2
					);
			
			OcNode child = new OcNode(
					initPos, 
					new Cube3f(
							initPos, 
							this.getShape().getWidth()/2, 
							this.getShape().getHeight()/2, 
							this.getShape().getDepth()/2
							)
					);
			child.setParent(this);
			child.setTree(this.tree.get());
			
			this.children.add(child);
		}
	}

	public List<? extends AbstractEnvObject> findObjectsIn(Shape3f<?> f) {
		List<AbstractEnvObject> objList = new ArrayList<>();
		if(f.contains(this.shape)){
			objList.addAll(this.getCoveredObjects());
			return objList;
		}
		
		if(this.hasChildren()){
			List<OcNode> iChildren = this.getIntersectingChildren(f);
			for(OcNode c:iChildren){
				objList.addAll(c.findObjectsIn(f));
			}
			return objList;
		}
		return this.getIntersectingObjects(f);
	}
	
	private List<? extends AbstractEnvObject> getCoveredObjects() {
		List<AbstractEnvObject> objList = new ArrayList<>();
		objList.addAll(envObjects);
		if(this.hasChildren()){
			for(OcNode c:this.children){
				objList.addAll(c.getCoveredObjects());
			}
		}
		return objList;
	}

	private List<OcNode> getIntersectingChildren(Shape3f<?> f) {
		List<OcNode> cList = new ArrayList<>();
		for(OcNode c:this.children){
			if(c.getShape().intersects(f)){
				cList.add(c);
			}
		}
		return cList;
	}

	private List<? extends AbstractEnvObject> getIntersectingObjects(Shape3f<?> f) {
		List<AbstractEnvObject> objList = new ArrayList<>();
		for(AbstractEnvObject o:this.envObjects){
			if(o.getShape().intersects(f)){
				objList.add(o);
			}
		}
		return objList;
	}

	/**
	 * Update the given object in the node
	 * 
	 * @param obj
	 */
	public void update(AbstractEnvObject obj) {
		if(!this.shape.contains(obj.getShape())){
			this.remove(obj);
			if(this.parent != null)this.getParent().addObject(obj);
			else this.addObject(obj);
		}
	}

	public boolean remove(AbstractEnvObject obj) {
		if(this.envObjects.contains(obj))
			return this.envObjects.remove(obj);
		if(this.hasChildren()){
			for(OcNode c:this.children){
				if(c.remove(obj)) return true;
			}
		}
		return false;
	}

	public void update() {
		for(AbstractEnvObject o:this.envObjects){
			update(o);
		}
	}
}
