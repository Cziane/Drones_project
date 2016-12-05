package fr.utbm.drone.environment.motions;

import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.maths.MathUtil;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class KinematicAlignBehaviour implements AlignBehaviour {
  @Override
  public BehaviourOutput runAlign(final Vector3f orientation, final float angularSpeed, final float maxAngularSpeed, final Vector3f target) {
    final float angle = orientation.signedAngle(target);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      float _clamp = MathUtil.clamp(angle, (-maxAngularSpeed), maxAngularSpeed);
      it.setAngular(new Orientation3f(_clamp, _clamp));
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
