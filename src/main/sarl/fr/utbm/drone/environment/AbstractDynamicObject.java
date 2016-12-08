package fr.utbm.drone.environment;

import java.util.UUID;

import org.joml.Vector3f;

import fr.utbm.drone.maths.Cube3f;
import fr.utbm.drone.maths.MathUtil;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Shape3f;
import fr.utbm.drone.time.TimeManager;
/*
 * The abstract representation of a dynamic object
 */
public abstract class AbstractDynamicObject extends AbstractEnvObject implements MobileObject {

	/**  
	 * 
	 */
	private static final long serialVersionUID = -5693251282748123771L;
	private final float maxLinearSpeed;
	private final float maxLinearAcceleration;
	private final float maxAngularSpeed;
	private final float maxAngularAcceleration;
	
	private Orientation3f angle = new Orientation3f();
	private float currentElevationAngularSpeed = 0;
	private float currentLateralAngularSpeed = 0;
	private Vector3f linearMove = new Vector3f();

	/**
	 * @param id the identifier of the object.
	 * @param shape the shape of the body, considering that it is centered at the (0,0) position.
	 * @param maxLinearSpeed is the maximal linear speed.
	 * @param maxLinearAcceleration is the maximal linear acceleration.
	 * @param maxAngularSpeed is the maximal angular speed.
	 * @param maxAngularAcceleration is the maximal angular acceleration.
	 */
	public AbstractDynamicObject(UUID id, Shape3f<?> shape, float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed, float maxAngularAcceleration) {
		super(id, shape);
		this.maxLinearSpeed = Math.abs(maxLinearSpeed);
		this.maxLinearAcceleration = Math.abs(maxLinearAcceleration);
		this.maxAngularAcceleration = Math.abs(maxAngularAcceleration);
		this.maxAngularSpeed = Math.abs(maxAngularSpeed);
	}
	
	@Override
	public AbstractDynamicObject clone() {
		AbstractDynamicObject clone = null;
		try {
			clone = (AbstractDynamicObject) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		clone.linearMove = new Vector3f(this.linearMove);
		return clone;
	}

	/** {@inheritDoc}
	 */
	@Override
	public Orientation3f getAngle() {
		return this.angle;
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public Vector3f getDirection() {
		return this.angle.getDirection();
	}

	/** Set the orientation of the object.
	 * 
	 * @param angle
	 */
	protected void setAngle(Orientation3f angle) {
		this.angle.setElevationAngle(angle.getElevationAngle());
		this.angle.setLateralAngle(angle.getLateralAngle());
		this.currentElevationAngularSpeed = 0;
		this.currentLateralAngularSpeed = 0;
	}

	/** Set the direction of the object.
	 * 
	 * @param dx
	 * @param dy
	 */
	protected void setDirection(float dx, float dy, float dz) {
		this.angle.setOrientation(new Vector3f(dx, dy, dz));
		this.currentElevationAngularSpeed = 0;
		this.currentLateralAngularSpeed = 0;
	}

	/** {@inheritDoc}
	 */
	@Override
	public float getMaxLinearSpeed() {
		return this.maxLinearSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public float getMaxAngularSpeed() {
		return this.maxAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public float getMaxLinearAcceleration() {
		return this.maxLinearAcceleration;
	}

	/** {@inheritDoc}
	 */
	@Override
	public float getMaxAngularAcceleration() {
		return this.maxAngularAcceleration;
	}
	
	/** {@inheritDoc}
	 */
	@Override
	public float getCurrentElevationAngularSpeed() {
		return this.currentElevationAngularSpeed;
	}
	/** {@inheritDoc}
	 */
	@Override
	public float getCurrentLateralAngularSpeed() {
		return this.currentLateralAngularSpeed;
	}

	/** {@inheritDoc}
	 */
	@Override
	public float getCurrentLinearSpeed() {
		return this.linearMove.length();
	}

	/** {@inheritDoc}
	 */
	@Override
	public Vector3f getCurrentLinearMotion() {
		return new Vector3f(this.linearMove);
	}

	/** Rotate the object.
	 * 
	 * @param rotation is the real instant motion. 
	 * @param simulationDuration is the time during which the motion is applied.
	 */
	protected void rotate(Orientation3f rotation) {
		this.body.setRotation(new javax.vecmath.Vector3f(rotation.getElevationAngleX(),rotation.getElevationAngle(),rotation.getElevationAngleZ()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setPosition(float x, float y, float z) {
		super.setPosition(x, y, z);
		this.linearMove.set(0,0, 0);
	}

	/** Move the situated object.
	 * 
	 * @param dx is the real instant motion. 
	 * @param dy is the real instant motion.
	 * @param simulationDuration is the time during which the motion is applied.
	 * @param worldWidth is the width of the world.
	 * @param worldHeight is the height of the world.
	 * @return the real motion.
	 */
	protected void move(Vector3f mov) {
		//System.out.println(mov);
		this.body.setLinearMotion(new javax.vecmath.Vector3f(mov.x,0,mov.z) );
		this.body.setUpImpulsion(new javax.vecmath.Vector3f(0,mov.y,0));
		/*// Ensure that the motion in inside the bounds of the world.
		Vector3f r = new Vector3f(dx, dy, dz);
		Shape3f<?> currentShape = getShape();
		Shape3f<?> targetShape = currentShape.translate(r);
		Cube3f targetBounds = targetShape.getBox();

		if (targetBounds.getLowerNearCorner().getX() < 0) {
			float exceedingAmount = - targetBounds.getLowerNearCorner().getX();
			r.x+=exceedingAmount;
		} else if (targetBounds.getUpperFarCorner().getX() > worldWidth) {
			float exceedingAmount = targetBounds.getUpperFarCorner().getX() - worldWidth;
			r.x-=exceedingAmount;
		}
		
		if (targetBounds.getLowerNearCorner().getY() < 0) {
			float exceedingAmount = - targetBounds.getLowerNearCorner().getY();
			r.y+=exceedingAmount;
		} else if (targetBounds.getUpperFarCorner().getY() > worldHeight) {
			float exceedingAmount = targetBounds.getUpperFarCorner().getY() - worldHeight;
			r.y-=exceedingAmount;
		}

		if (targetBounds.getLowerNearCorner().getZ() < 0) {
			float exceedingAmount = - targetBounds.getLowerNearCorner().getZ();
			r.z+=exceedingAmount;
		} else if (targetBounds.getUpperFarCorner().getZ() > worldDepth) {
			float exceedingAmount = targetBounds.getUpperFarCorner().getZ() - worldDepth;
			r.z-=exceedingAmount;
		}

		// Update the position
		
		
		// Update dynamic properties
		if (simulationDuration>0) {
			this.linearMove.set(r.x, r.y, r.z);
			float distance = this.linearMove.length();
			if (distance>0) {
				this.linearMove.normalize();
				this.linearMove.mul(distance/simulationDuration);
			}
		}
		else {
			this.linearMove.set(0,0,0);
		}
		//this.graphic.setPosition(r.x, r.y, r.z);*/
	}


	/** Compute a steering move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion, expressed with acceleration.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected Vector3f computeSteeringTranslation(Vector3f move, TimeManager clock) {
		float length = move.length();

		Vector3f v = null;
		
		if (length != 0f) {
			// Clamp acceleration
			float acceleration = MathUtil.clamp(
					(move.dot(this.linearMove) < 0f) ? -length : length, 
					-getMaxLinearAcceleration(), 
					getMaxLinearAcceleration());
			
			// Apply Newton law, first part (from acceleration to speed)
			acceleration = Math.abs(acceleration) / length;
			v = move.mul(acceleration, v);
			
			v.mul(.5f * clock.getLastStepDuration());
			v = this.linearMove.add(v, v);
		}
		else {
			v = new Vector3f(this.linearMove);
		}
		
		// v is a speed - unit: [m/s]
		
		length = (float) Math.sqrt(v.lengthSquared());
		if (length != 0f) {
			// Clamp the speed
			float speed = MathUtil.clamp(
					(v.dot(this.linearMove) < 0f) ? -length : length, 
					0f, 
					getMaxLinearSpeed());

			// Compute the Newton law, part 2 (from speed to distance)
			float factor = clock.getLastStepDuration() * Math.abs(speed) / length;
		
			return v.mul(factor, v);
		}
		
		return new Vector3f();
	}

	/** Compute a kinematic move according to the linear move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion, expressed with speed.
	 * @param clock is the simulation time manager
	 * @return the linear instant motion.
	 */
	protected Vector3f computeKinematicTranslation(Vector3f move, TimeManager clock) {
		float speed = move.length();
		if (speed != 0f) {
			// Apply Newton-Euler-1 law		
			float factor = clock.getLastStepDuration() * MathUtil.clamp(speed, 0, getMaxLinearSpeed()) / speed;
			return new Vector3f(move).mul(factor);
		}
		return new Vector3f();
	}

	/** Compute a kinematic move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion with speed.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected Orientation3f computeKinematicRotation(Orientation3f move, TimeManager clock) {
		Orientation3f m = new Orientation3f();
		float elvSpeed = Math.abs(move.getElevationAngle());
		if (elvSpeed != 0f) {
			// Apply Newton-Euler-1 law
			float factor = clock.getLastStepDuration() * MathUtil.clamp(elvSpeed, 0, getMaxAngularSpeed()) / elvSpeed;
			m.setElevationAngle(move.getElevationAngle() * factor);
		}
		
		float latSpeed = Math.abs(move.getLateralAngle());
		if (latSpeed != 0f) {
			// Apply Newton-Euler-1 law
			float factor = clock.getLastStepDuration() * MathUtil.clamp(latSpeed, 0, getMaxAngularSpeed()) / latSpeed;
			m.setLateralAngle(move.getLateralAngle() * factor);
		}
		return m;
	}

	/** Compute a steering move according to the angular move and to
	 * the internal attributes of this object.
	 * 
	 * @param move is the requested motion.
	 * @param clock is the simulation time manager
	 * @return the angular instant motion.
	 */
	protected Orientation3f computeSteeringRotation(Orientation3f move, TimeManager clock) {
		Orientation3f v = new Orientation3f();
		
		if (move.getElevationAngle() != 0f) {
			// Clamp acceleration
			float acceleration = MathUtil.clamp(
					move.getElevationAngle(), 
					-getMaxAngularAcceleration(), 
					getMaxAngularAcceleration());
			
			// Apply Newton law, first part (from acceleration to speed)
			acceleration = Math.abs(acceleration) / Math.abs(move.getElevationAngle());
			v.setElevationAngle(move.getElevationAngle() * acceleration);
			v.setElevationAngle(v.getElevationAngle() * .5f * clock.getLastStepDuration());
			v.setElevationAngle(v.getElevationAngle()+this.currentElevationAngularSpeed);
		}
		else {
			v.setElevationAngle(this.currentElevationAngularSpeed);
		}
		

		if (move.getLateralAngle() != 0f) {
			// Clamp acceleration
			float acceleration = MathUtil.clamp(
					move.getLateralAngle(), 
					-getMaxAngularAcceleration(), 
					getMaxAngularAcceleration());
			
			// Apply Newton law, first part (from acceleration to speed)
			acceleration = Math.abs(acceleration) / Math.abs(move.getLateralAngle());
			v.setLateralAngle(move.getLateralAngle() * acceleration);
			v.setLateralAngle(v.getLateralAngle() * .5f * clock.getLastStepDuration());
			v.setLateralAngle(v.getLateralAngle()+this.currentLateralAngularSpeed);
		}
		else {
			v.setLateralAngle(this.currentLateralAngularSpeed);
		}
		
		// v is a speed - unit: [m/s]
		
		if (v.getElevationAngle() != 0f) {
			// Clamp the speed
			float speed = MathUtil.clamp(
					v.getElevationAngle(), 
					-getMaxAngularSpeed(), 
					getMaxAngularSpeed());

			// Compute the Newton law, part 2 (from speed to distance)
			float factor = clock.getLastStepDuration() * Math.abs(speed) / Math.abs(v.getElevationAngle());
		
			v.setElevationAngle(v.getElevationAngle() * factor);
		}

		if (v.getLateralAngle() != 0f) {
			// Clamp the speed
			float speed = MathUtil.clamp(
					v.getLateralAngle(), 
					-getMaxAngularSpeed(), 
					getMaxAngularSpeed());

			// Compute the Newton law, part 2 (from speed to distance)
			float factor = clock.getLastStepDuration() * Math.abs(speed) / Math.abs(v.getLateralAngle());
		
			v.setLateralAngle(v.getLateralAngle() * factor);
		}
		
		return v;
	}
	@Override
	public void applyTransform() {
		javax.vecmath.Vector3f pos=this.body.getPosition();
		this.setPosition(pos.x,pos.y,pos.z);
		//this.setAngle(new Orientation3f(this.body.));
		if(this.graphic!=null){
			this.graphic.setPosition(this.getPosition().x, this.getPosition().y, this.getPosition().z);
			this.graphic.setRotation(this.getAngle().getElevationAngleX(),this.getAngle().getElevationAngle(),this.getAngle().getElevationAngleZ());
		}
		
	}

}
