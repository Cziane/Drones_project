package fr.utbm.drone.physics;

import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

public class KinematicMotionState extends MotionState {
	private Transform worldTransform;
	public KinematicMotionState(Transform transform) {
		worldTransform=transform;
	}

	@Override
	public Transform getWorldTransform(Transform out) {
		out.set(worldTransform);
		return out;
	}

	@Override
	public void setWorldTransform(Transform worldTransform){
		this.worldTransform.set(worldTransform);
	}

}
