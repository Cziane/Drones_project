
package fr.utbm.drone.environment;

import java.util.List;
import java.util.UUID;

import org.joml.Vector3f;

import fr.utbm.drone.environment.motions.MotionInfluence;
import fr.utbm.drone.maths.MathUtil;
import fr.utbm.drone.maths.Shape3f;
/*
 * The abstract representation of an agent body
 */
public abstract class AbstractAgentBody extends AbstractDynamicObject {
  

	/**
	 * 
	 */
	private static final long serialVersionUID = 2109513862413096502L;
	private Shape3f<?> frustum;
	private List<Percept> perceptions;
	private MotionInfluence motionInfluence;
	
	public AbstractAgentBody(
			UUID id, 
			Shape3f<?> shape, 
			float maxLinearSpeed, 
			float maxLinearAcceleration,
			float maxAngularSpeed, 
			float maxAngularAcceleration) {
		super(
				id, 
				shape, 
				maxLinearSpeed, 
				maxLinearAcceleration, 
				maxAngularSpeed, 
				maxAngularAcceleration);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the frustum
	 */
	public Shape3f<?> getFrustum() {
		return frustum.clone();
	}

	/**
	 * @param frustum the frustum to set
	 */
	public void setFrustum(Shape3f<?> frustum) {
		this.frustum = frustum.clone();
	}

	/**
	 * @return the perceptions
	 */
	public List<Percept> getPerceptions() {
		return perceptions;
	}

	/**
	 * @param perceptions the perceptions to set
	 */
	public void setPerceptions(List<Percept> perceptions) {
		this.perceptions = perceptions;
	}


	/** Invoked to send the given influence to the environment.
	 *
	 * @param influence the influence.
	 */
	public void influence(Influence influence) {
				
		if (influence != null) {
			if (influence instanceof MotionInfluence) {
		//System.out.println("\nAbstractAgentBody::influence(): Analyzing...");
				MotionInfluence mi = (MotionInfluence) influence;
				if (mi.getInfluencedObject() == null || mi.getInfluencedObject().equals(getId())) {
					switch(mi.getType()) {
					case KINEMATIC:
						influenceKinematic(mi.getLinearInfluence(), mi.getElevationAngularInfluence());
						//System.out.println("\nAbstractAgentBody::influence() : Kinematic performed: "+mi.getLinearInfluence());
						break;
					case STEERING:
						influenceSteering(mi.getLinearInfluence(), mi.getElevationAngularInfluence());
						break;
					}
				} 
			}
		}
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceKinematic(Vector3f linearInfluence, float angularInfluence) {
		Vector3f li;
		if (linearInfluence!=null) {
			li = new Vector3f(linearInfluence);
			float nSpeed = li.length();
			if (nSpeed>getMaxLinearSpeed()) {
				li.normalize();
				li.mul(getMaxLinearSpeed());
			}
		}
		else {
			li = new Vector3f();
		}
		float ai = MathUtil.clamp(angularInfluence, -getMaxAngularSpeed(), getMaxAngularSpeed());
		this.motionInfluence = new MotionInfluence(DynamicType.KINEMATIC, getId(), li, ai, ai);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceSteering(Vector3f linearInfluence, float angularInfluence) {
		Vector3f li;
		if (linearInfluence!=null) {
			li = new Vector3f(linearInfluence);
			float nSpeed = li.length();
			if (nSpeed>getMaxLinearAcceleration()) {
				li.normalize();
				li.mul(getMaxLinearAcceleration());
			}
		}
		else {
			li = new Vector3f();
		}
		float ai = MathUtil.clamp(angularInfluence, -getMaxAngularAcceleration(), getMaxAngularAcceleration());
		this.motionInfluence = new MotionInfluence(DynamicType.STEERING, getId(), li, ai, ai);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public void influenceKinematic(Vector3f linearInfluence) {
		influenceKinematic(linearInfluence, 0f);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	public void influenceSteering(Vector3f linearInfluence) {
		influenceSteering(linearInfluence, 0f);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceKinematic(float angularInfluence) {
		influenceKinematic(null, angularInfluence);
	}
	
	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	public void influenceSteering(float angularInfluence) {
		influenceSteering(null, angularInfluence);
	}
	
	/** Replies all the perceived objects.
	 * 
	 * @return the perceived objects.
	 */
	public List<Percept> getPerceivedObjects() {
		return this.perceptions;
	}

	/** Replies the influence.
	 * 
	 * @return the influence.
	 */
	protected MotionInfluence consumeMotionInfluence() {
		MotionInfluence mi = this.motionInfluence;
		this.motionInfluence = null;
		if (mi!=null) mi.setEmitter(getId());
		return mi;
	}

	
}
