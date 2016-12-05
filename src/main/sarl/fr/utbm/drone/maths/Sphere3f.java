package fr.utbm.drone.maths;

import javax.vecmath.Point3f;

import org.joml.Vector3f;

public class Sphere3f extends Shape3f<Sphere3f> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 100572721781176455L;
	private Point3f center = new Point3f();
	private float radius;
	
	/**
	 * @param center
	 * @param radius
	 */
	public Sphere3f(Point3f center, float radius) {
		super();
		this.center = (Point3f) center.clone();
		this.radius = radius;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Sphere3f clone() {
		return new Sphere3f((Point3f) this.center.clone(), this.radius);
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Point3f pt) {		
		return (pt.distanceSquared(center) < radius*radius);
	}

	@Override
	public Point3f getClosestPointTo(Point3f pt) {
		if(this.contains(pt)) return pt;
		Vector3f trans = new Vector3f(pt.x - center.x, pt.y - center.y, pt.z - center.z);
		trans.normalize();
		trans.mul(radius);
		return new Point3f(center.x+trans.x, center.y+trans.y, center.z+trans.z);
	}

	@Override
	public Point3f getFarthestPointTo(Point3f pt) {
		Vector3f trans = new Vector3f(-pt.x + center.x, -pt.y + center.y, -pt.z + center.z);
		trans.normalize();
		trans.mul(radius);
		return new Point3f(center.x+trans.x, center.y+trans.y, center.z+trans.z);
	}

	@Override
	public void set(Sphere3f shape) {
		this.center = (Point3f) shape.center.clone();
		this.radius = shape.radius;
	}

	@Override
	public boolean intersects(Shape3f<?> s) {
		if(s instanceof Cube3f){
			for(Point3f corner : ((Cube3f)s).getAllCorners()){
				if(this.contains(corner)){
					//System.out.print("\nObject: "+s.toString()+" \nIntersecting: "+this.toString());
					return true;
				}
			}
		}else if(s instanceof Sphere3f){
			return ((Sphere3f) s).center.distance(this.center) < this.radius + ((Sphere3f)s).radius;
		}
		return false;
	}

	@Override
	public Cube3f getBox() {			
		Vector3f dx = new Vector3f(-center.x, 0.0f, 0.0f).normalize().mul(radius);
		Vector3f dy = new Vector3f(0.0f, -center.y, 0.0f).normalize().mul(radius);	
		Vector3f dz = new Vector3f(0.0f, 0.0f, -center.z).normalize().mul(radius);	
		
		Vector3f dir = new Vector3f();
		dir = dx.add(dy.add(dz, dir), dir);
		Point3f lower = new Point3f(center.x+dir.x, center.y+dir.y, center.z+dir.z);
		Point3f upper = new Point3f(center.x-dir.x, center.y-dir.y, center.z-dir.z);
		return new Cube3f(lower, upper);
	}

	@Override
	public Sphere3f translate(Vector3f r) {		
		return new Sphere3f(
				new Point3f(center.x + r.x, center.y+r.y, center.z+r.z),
				this.radius);
	}

	@Override
	public boolean contains(Shape3f<?> box) {
		Cube3f b = box.getBox();	
		return contains(b.getLowerNearCorner()) && contains(b.getUpperFarCorner());
	}

	@Override
	public String toString(){
		return "(Center: "+this.center+" \nRadius: "+this.radius+")";
	}
}
