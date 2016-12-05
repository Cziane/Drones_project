package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.motions.AlignBehaviour;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Vector3f;
import io.sarl.lang.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SarlSpecification("0.4")
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
    float _x = orientation.getX();
    float _y = orientation.getY();
    float _z = orientation.getZ();
    float _x_1 = target.getX();
    float _y_1 = target.getY();
    float _z_1 = target.getZ();
    final Orientation3f sAngle = Orientation3f.getOrientation(_x, _y, _z, _x_1, _y_1, _z_1);
    float _lateralAngle = sAngle.getLateralAngle();
    final float angleL = Math.abs(_lateralAngle);
    float _elevationAngle = sAngle.getElevationAngle();
    final float angleE = Math.abs(_elevationAngle);
    Orientation3f a = new Orientation3f();
    if (((angleL <= this.stopAngle) || (angleE <= this.stopAngle))) {
      if ((angleL <= this.stopAngle)) {
        a.setLateralAngle(((-angularSpeed) / 1));
      }
      if ((angleE <= this.stopAngle)) {
        a.setElevationAngle(((-angularSpeed) / 1));
      }
      final Orientation3f oa = a;
      BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.STEERING);
      final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
        it.setAngular(oa);
      };
      return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
    }
    if (((angleL > this.decelerateAngle) || (angleE > this.decelerateAngle))) {
      if ((angleL > this.decelerateAngle)) {
        float _lateralAngle_1 = sAngle.getLateralAngle();
        float _signum = Math.signum(_lateralAngle_1);
        float _multiply = (_signum * maxAngularAcceleration);
        a.setLateralAngle(_multiply);
      }
      if ((angleE > this.decelerateAngle)) {
        float _elevationAngle_1 = sAngle.getElevationAngle();
        float _signum_1 = Math.signum(_elevationAngle_1);
        float _multiply_1 = (_signum_1 * maxAngularAcceleration);
        a.setElevationAngle(_multiply_1);
      }
      final Orientation3f oa_1 = a;
      BehaviourOutput _behaviourOutput_1 = new BehaviourOutput(DynamicType.STEERING);
      final Procedure1<BehaviourOutput> _function_1 = (BehaviourOutput it) -> {
        it.setAngular(oa_1);
      };
      return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput_1, _function_1);
    }
    BehaviourOutput _behaviourOutput_2 = new BehaviourOutput(DynamicType.STEERING);
    final Procedure1<BehaviourOutput> _function_2 = (BehaviourOutput it) -> {
      float _lateralAngle_2 = sAngle.getLateralAngle();
      double _power = Math.pow(this.timeToReachTarget, 2);
      double _divide = (_lateralAngle_2 / _power);
      float _elevationAngle_2 = sAngle.getElevationAngle();
      double _power_1 = Math.pow(this.timeToReachTarget, 2);
      double _divide_1 = (_elevationAngle_2 / _power_1);
      Orientation3f _orientation3f = new Orientation3f(((float) _divide), ((float) _divide_1));
      it.setAngular(_orientation3f);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput_2, _function_2);
  }
}
