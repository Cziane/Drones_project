package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.environment.motions.SeekBehaviour;
import fr.utbm.drone.maths.Point3f;
import fr.utbm.drone.maths.Vector3f;
import io.sarl.lang.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class SteeringSeekBehaviour implements SeekBehaviour {
  @Override
  public BehaviourOutput runSeek(final Point3f position, final float linearSpeed, final float maxLinearAcceleration, final Point3f target) {
    final Vector3f direction = target.operator_minus(position);
    direction.setLength(maxLinearAcceleration);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      it.setLinear(direction);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
