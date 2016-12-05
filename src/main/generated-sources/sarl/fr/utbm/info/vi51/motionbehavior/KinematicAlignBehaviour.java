package fr.utbm.info.vi51.motionbehavior;

import fr.utbm.drone.environment.DynamicType;
import fr.utbm.drone.environment.motions.AlignBehaviour;
import fr.utbm.drone.environment.motions.BehaviourOutput;
import fr.utbm.drone.maths.MathUtil;
import fr.utbm.drone.maths.Orientation3f;
import fr.utbm.drone.maths.Vector3f;
import io.sarl.lang.annotation.SarlSpecification;
import org.eclipse.xtext.xbase.lib.ObjectExtensions;
import org.eclipse.xtext.xbase.lib.Procedures.Procedure1;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class KinematicAlignBehaviour implements AlignBehaviour {
  @Override
  public BehaviourOutput runAlign(final Vector3f orientation, final float angularSpeed, final float maxAngularSpeed, final Vector3f target) {
    float _x = orientation.getX();
    float _y = orientation.getY();
    float _z = orientation.getZ();
    float _x_1 = target.getX();
    float _y_1 = target.getY();
    float _z_1 = target.getZ();
    final Orientation3f angle = Orientation3f.getOrientation(_x, _y, _z, _x_1, _y_1, _z_1);
    BehaviourOutput _behaviourOutput = new BehaviourOutput(DynamicType.KINEMATIC);
    final Procedure1<BehaviourOutput> _function = (BehaviourOutput it) -> {
      float _lateralAngle = angle.getLateralAngle();
      float _clamp = MathUtil.clamp(_lateralAngle, (-maxAngularSpeed), maxAngularSpeed);
      float _elevationAngle = angle.getElevationAngle();
      float _clamp_1 = MathUtil.clamp(_elevationAngle, (-maxAngularSpeed), maxAngularSpeed);
      Orientation3f _orientation3f = new Orientation3f(_clamp, _clamp_1);
      it.setAngular(_orientation3f);
    };
    return ObjectExtensions.<BehaviourOutput>operator_doubleArrow(_behaviourOutput, _function);
  }
}
