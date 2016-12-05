/**
 * 
 */
package fr.utbm.drone.environment;

import javax.vecmath.Vector3f;

/**
 * @author Willy KOUETE
 *
 */
public final class EnvironmentLaws {
	public static float MAX_DRONE_LINEAR_SPEED =3.0f;
	public static float MAX_DRONE_LINEAR_ACCELERATION = 0.025f;
	public static float MAX_DRONE_ANGULAR_SPEED = 10.0f;
	public static float MAX_DRONE_ANGULAR_ACCELERATION = 5.0f;
	
	public static Vector3f DRONE_SIZE=new Vector3f(2.0f,2.0f,2.0f);
	public static Vector3f BUILDING_SIZE=new Vector3f(12.0f,12.0f,12.0f);
	public static float DRONE_MASS=70.0f;
	public static float BUILDING_MASS =0.0f;
	
	public static  Vector3f DRONE_INERTIA = new Vector3f(1,1,1);
	public static Vector3f BUILDING_INERTIA=new Vector3f(0,0,0);

}
