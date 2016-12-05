package fr.utbm.drone.environment;

import org.joml.Vector3f;

import fr.utbm.drone.maths.Orientation3f;

public interface MobileObject {
	/** Replies the orientation of the object.
	 * 
	 * @return the angle of orientation from (1,0).
	 */
	Orientation3f getAngle();
	
	/** Replies the orientation of the object.
	 * 
	 * @return the orientation direction.
	 */
	Vector3f getDirection();
	
	/** Replies the max linear speed.
	 * 
	 * @return the max linear speed.
	 */
	float getMaxLinearSpeed();

	/** Replies the max angular speed.
	 * 
	 * @return the max angular speed.
	 */
	float getMaxAngularSpeed();

	/** Replies the max linear acceleration.
	 * 
	 * @return the max linear acceleration.
	 */
	float getMaxLinearAcceleration();

	/** Replies the max angular acceleration.
	 * 
	 * @return the max angular acceleration.
	 */
	float getMaxAngularAcceleration();
	
	/** Replies the current lateral angular speed.
	 * 
	 * @return the current lateral angular speed.
	 */
	float getCurrentLateralAngularSpeed();
	
	/** Replies the current elevation angular speed.
	 * 
	 * @return the current elevation angular speed.
	 */
	float getCurrentElevationAngularSpeed();

	/** Replies the current linear speed.
	 * 
	 * @return the current linear speed.
	 */
	float getCurrentLinearSpeed();

	/** Replies the current linear motion.
	 * 
	 * @return the current linear motion.
	 */
	Vector3f getCurrentLinearMotion();

}
