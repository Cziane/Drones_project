package fr.utbm.drone.maths;

import org.joml.Vector3f;

public class Orientation3f {
	private float lateralAngle;
	private float elevationAngle;

	/**
	 * 
	 */
	public Orientation3f() {
		super();
		this.lateralAngle = 0.0f;
		this.elevationAngle = 0.0f;
	}
	

	public Orientation3f(Vector3f v) {
		super();
		this.setOrientation(v);
	}
	

	public Orientation3f(float x, float y, float z) {
		super();
		this.setOrientation(x, y, z);
	}
	
	/**
	 * @param lateralAngle
	 * @param elevationAngle
	 */
	public Orientation3f(float lateralAngle, float elevationAngle) {
		super();
		this.lateralAngle = lateralAngle;
		this.elevationAngle = elevationAngle;
	}
	
	
	public Orientation3f(Orientation3f angle) {
		this.elevationAngle = angle.elevationAngle;
		this.lateralAngle = angle.lateralAngle;
	}

	/**
	 * @return the lateralAngle
	 */
	public float getLateralAngle() {
		return lateralAngle;
	}
	/**
	 * @param lateralAngle the lateralAngle to set
	 */
	public void setLateralAngle(float lateralAngle) {
		this.lateralAngle = lateralAngle;
	}
	/**
	 * @return the elevationAngle
	 */
	public float getElevationAngle() {
		return elevationAngle;
	}
	/**
	 * @param elevationAngle the elevationAngle to set
	 */
	public void setElevationAngle(float elevationAngle) {
		this.elevationAngle = elevationAngle;
	}

	/**
	 * Returns the direction given by this orientation
	 * @return the direction
	 */
	public Vector3f getDirection() {
		return new Vector3f(
				(float)(Math.sqrt(2)*Math.cos(this.lateralAngle)),
				(float)Math.sin(this.elevationAngle), 
				(float)(Math.sqrt(2)*Math.sin(this.lateralAngle))
				);
	}
	
	public static Orientation3f getOrientation(float x1, float y1, float z1, float x2, float y2, float z2){
		Orientation3f o1 = new Orientation3f(x1, y1, z1);
		Orientation3f o2 = new Orientation3f(x2, y2, z2);
		return new Orientation3f(
				o2.lateralAngle - o1.lateralAngle,
				o2.elevationAngle - o1.elevationAngle
				);
	}

	/**
	 * Sets this orientation to the orientation given by a specific direction
	 * @param dir: the specific given direction
	 */
	public void setOrientation(Vector3f dir){
		setOrientation(dir.x, dir.y, dir.z);
	}
	

	public void setOrientation(float x, float y, float z){
		Vector3f dir = new Vector3f(x, y, z).normalize();
		this.elevationAngle = (float)Math.asin(dir.y);
		this.lateralAngle = (float)Math.atan2(dir.x, dir.z);
	}
	
	public void add(Orientation3f o){
		this.elevationAngle += o.elevationAngle;
		this.lateralAngle += o.lateralAngle;
	}
	
	public String toString(){
		return "[Lateral: "+this.lateralAngle+" --- Elevation: "+this.elevationAngle+"]";
	}

	/**
	 * This function returns the X component of the elevation angle. That means the (OX)'s rotation part of the elevation angle
	 * @return the angle requested
	 */
	public float getElevationAngleX() {
		Vector3f dir = this.getDirection();
		return (float)Math.atan2(dir.y, dir.z);
	}

	/**
	 * This function returns the Z component of the elevation angle. That means the (OZ)'s rotation part of the elevation angle
	 * @return the angle requested
	 */
	public float getElevationAngleZ() {
		Vector3f dir = this.getDirection();
		return (float)Math.atan2(dir.y, dir.x);
	}
}
