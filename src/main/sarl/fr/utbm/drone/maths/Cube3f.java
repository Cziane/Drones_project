package fr.utbm.drone.maths;

import java.util.ArrayList;
import java.util.List;

import javax.vecmath.Point3f;

import org.joml.Vector3f;

public class Cube3f extends Shape3f<Cube3f> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5270010296673879844L;
	private Point3f lowerNearCorner = new Point3f();
	private Point3f upperFarCorner = new Point3f();
	
	public Cube3f() {
		super();
	}

	public Cube3f(Point3f lowerNearCorner, Point3f upperFarCorner) {
		super();
		this.lowerNearCorner.set(
				Math.min(lowerNearCorner.getX(), upperFarCorner.getX()),
				Math.min(lowerNearCorner.getY(), upperFarCorner.getY()),
				Math.min(lowerNearCorner.getZ(), upperFarCorner.getZ())
				);
		this.upperFarCorner.set(
				Math.max(lowerNearCorner.getX(), upperFarCorner.getX()),
				Math.max(lowerNearCorner.getY(), upperFarCorner.getY()),
				Math.max(lowerNearCorner.getZ(), upperFarCorner.getZ())
				);
	}

	public Cube3f(Point3f lowerNearCorner, float width, float height, float depth) {
		super();
		this.lowerNearCorner.set(lowerNearCorner);
		this.upperFarCorner.set(
				lowerNearCorner.getX() + width, 
				lowerNearCorner.getY() + height, 
				lowerNearCorner.getZ() + depth);
	}

	public Point3f getLowerNearCorner() {
		return (Point3f) lowerNearCorner.clone();
	}
	
	public Point3f getUpperFarCorner() {
		return (Point3f) upperFarCorner.clone();
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Cube3f clone() {
		return new Cube3f(this.lowerNearCorner, this.upperFarCorner);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
	public float getWidth(){
		return this.upperFarCorner.getX() - this.lowerNearCorner.getX();
	}

	public float getHeight(){
		return this.upperFarCorner.getY() - this.lowerNearCorner.getY();
	}

	public float getDepth(){
		return this.upperFarCorner.getZ() - this.lowerNearCorner.getZ();
	}

	@Override
	public boolean intersects(Shape3f<?> s){
		if (s instanceof Cube3f) {
			Cube3f c = (Cube3f) s;
			for(Point3f corn:c.getAllCorners()){
				if(this.contains(corn)){
					//System.out.print("Object: "+s.toString()+" \nIntersecting: "+this.toString());
					return true;
				}
			}
			return false;
		}
		if(s instanceof Sphere3f){
			Sphere3f sphere = (Sphere3f)s;
			return sphere.intersects(this.clone());
		}
		throw new IllegalArgumentException("Shape type: ");
	}

	private boolean intersects(float a1, float a2, float b1, float b2) {
		assert (a1 <= a2);
		assert (b1 <= b2);
		return ((a2 >= b2) && (b2 >= a1)) ||
				((a2 >= b1) && (b1 >= a1));
	}

	@Override
	public boolean contains(Point3f pt) {
		return (this.lowerNearCorner.getX() <= pt.getX() &&
				this.lowerNearCorner.getY() <= pt.getY()&&
				this.lowerNearCorner.getZ() <= pt.getZ()&&
				this.upperFarCorner.getX() >= pt.getX() &&
				this.upperFarCorner.getY() >= pt.getY()&&
				this.upperFarCorner.getZ() >= pt.getZ());
	}

	@Override
	public Point3f getClosestPointTo(Point3f pt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Point3f getFarthestPointTo(Point3f pt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set(Cube3f shape) {
		this.lowerNearCorner.set(shape.getLowerNearCorner());
		this.upperFarCorner.set(shape.getUpperFarCorner());
	}
	
	@Override
	public String toString() {
		return "[" + this.lowerNearCorner.toString() + "-" + this.upperFarCorner.toString() + "]";
	}

	@Override
	public Cube3f getBox() {		
		return this.clone();
	}

	@Override
	public Cube3f translate(Vector3f r) {
		return new Cube3f(
				new Point3f(
						this.lowerNearCorner.x + r.x,
						this.lowerNearCorner.y + r.y,
						this.lowerNearCorner.z + r.z
						),
				new Point3f(
						this.upperFarCorner.x + r.x,
						this.upperFarCorner.y + r.y,
						this.upperFarCorner.z + r.z
						)
				);
	}

	public List<Point3f> getAllCorners() {
		List<Point3f> corners = new ArrayList<>();
		corners.add(lowerNearCorner);
		corners.add(upperFarCorner);
		corners.add(new Point3f(lowerNearCorner.x, upperFarCorner.y, lowerNearCorner.z));
		corners.add(new Point3f(upperFarCorner.x, lowerNearCorner.y, lowerNearCorner.z));
		corners.add(new Point3f(lowerNearCorner.x, lowerNearCorner.y, upperFarCorner.z));
		corners.add(new Point3f(upperFarCorner.x, lowerNearCorner.y, upperFarCorner.z));
		corners.add(new Point3f(upperFarCorner.x, upperFarCorner.y, lowerNearCorner.z));
		corners.add(new Point3f(lowerNearCorner.x, upperFarCorner.y, upperFarCorner.z));
		return corners;
	}

	@Override
	public boolean contains(Shape3f<?> box) {
		Cube3f b = box.getBox();
		return contains(b.getLowerNearCorner()) && contains(b.getUpperFarCorner());
	}

	@Override
	public void setPosition(Point3f nCenter) {
		// TODO Auto-generated method stub
		
	}
}
