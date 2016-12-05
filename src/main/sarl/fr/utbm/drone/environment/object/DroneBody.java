package fr.utbm.drone.environment.object;

import java.io.Serializable;
import java.util.UUID;

import javax.vecmath.Point3f;
import javax.vecmath.Vector3f;

import fr.utbm.drone.environment.AbstractAgentBody;
import fr.utbm.drone.environment.ObjectType;
import fr.utbm.drone.maths.Shape3f;
import fr.utbm.drone.physics.PhysicDrone;

public class DroneBody extends AbstractAgentBody {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5059350269456295547L;

	public DroneBody(Point3f position, UUID id, Shape3f<?> shape, float maxLinearSpeed, float maxLinearAcceleration, float maxAngularSpeed,
			float maxAngularAcceleration) {
		super(id, shape, maxLinearSpeed, maxLinearAcceleration, maxAngularSpeed, maxAngularAcceleration);
		this.setPosition(position);
		this.body= new PhysicDrone(new Vector3f(this.getPosition()));
		
	}

	@Override
	public Serializable getType() {
		// TODO Auto-generated method stub
		return ObjectType.DRONE;
	}

}
