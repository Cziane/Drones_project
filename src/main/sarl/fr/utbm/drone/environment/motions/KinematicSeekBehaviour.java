package fr.utbm.drone.environment.motions;

import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class KinematicSeekBehaviour implements SeekBehaviour {
  @Override
  public BehaviourOutput runSeek(final Point3f position, final float linearSpeed, final float maxLinearSpeed, final Point3f target) {
    final Vector3f direction = target.operator_minus(position);
    direction.setLength(maxLinearSpeed);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      it.setLinear(direction);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
