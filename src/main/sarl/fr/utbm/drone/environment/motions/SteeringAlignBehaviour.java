package fr.utbm.drone.environment.motions;

import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Vector3f;

@SuppressWarnings("all")
public class SteeringAlignBehaviour implements AlignBehaviour {
  private final float stopAngle;
  
  private final float decelerateAngle;
  
  private final float timeToReachTarget;
  
  public SteeringAlignBehaviour(final float stopAngle, final float decelerateAngle, final float timeToReachTarget) {
    this.stopAngle = stopAngle;
    this.decelerateAngle = decelerateAngle;
    this.timeToReachTarget = timeToReachTarget;
  }
  
  @Override
  public BehaviourOutput runAlign(final Vector3f orientation, final float angularSpeed, final float maxAngularAcceleration, final Vector3f target) {
    final float sAngle = orientation.signedAngle(target);
    final float angle = Math.abs(sAngle);
    if ((angle <= this.stopAngle)) {
      BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.STEERING);
      final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
        it.setAngular(new Orientation3f((-angularSpeed) / 1, (-angularSpeed) / 1));
      };
      return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
    }
    if ((angle > this.decelerateAngle)) {
      BehaviourOutput _behaviourOutput_1 = new BehaviourOutput(DynamicType.STEERING);
      final Procedure1<BehaviourOutput> _function_1 = (BehaviourOutput it) -> {
        float _signum = Math.signum(sAngle);
        float _multiply = (_signum * maxAngularAcceleration);
        it.setAngular(new Orientation3f(_multiply, _multiply));
      };
      return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput_1, _function_1);
    }
    BehaviourOutput _behaviourOutput_2 = new BehaviourOutput(DynamicType.STEERING);
    final Procedure1<BehaviourOutput> _function_2 = (BehaviourOutput it) -> {
      double _power = Math.pow(this.timeToReachTarget, 2);
      double _divide = (sAngle / _power);
      it.setAngular(new Orientation3f((float) _divide, (float) _divide));
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput_2, _function_2);
  }
}
