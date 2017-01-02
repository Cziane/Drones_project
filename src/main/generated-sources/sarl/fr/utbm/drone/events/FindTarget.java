package fr.utbm.drone.events;

import fr.utbm.drone.maths.Point3f;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class FindTarget extends Event {
  public Point3f target;
  
  public FindTarget(final Point3f targ) {
    Point3f _clone = targ.clone();
    this.target = _clone;
  }
  
  @Override
  @Pure
  @SyntheticMember
  public boolean equals(final Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    FindTarget other = (FindTarget) obj;
    if (this.target == null) {
      if (other.target != null)
        return false;
    } else if (!this.target.equals(other.target))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.target== null) ? 0 : this.target.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the FindTarget event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("target  = ").append(this.target);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 2778755382L;
}
