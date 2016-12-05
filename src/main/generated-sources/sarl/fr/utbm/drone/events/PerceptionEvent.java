package fr.utbm.drone.events;

import fr.utbm.drone.environment.Percept;
import fr.utbm.drone.time.TimePercept;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class PerceptionEvent extends Event {
  public final Percept body;
  
  public final TimePercept time;
  
  public final List<Percept> perceptions;
  
  public PerceptionEvent(final List<Percept> p, final Percept b, final TimePercept t) {
    this.perceptions = p;
    this.body = b;
    this.time = t;
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
    PerceptionEvent other = (PerceptionEvent) obj;
    if (this.body == null) {
      if (other.body != null)
        return false;
    } else if (!this.body.equals(other.body))
      return false;
    if (this.time == null) {
      if (other.time != null)
        return false;
    } else if (!this.time.equals(other.time))
      return false;
    if (this.perceptions == null) {
      if (other.perceptions != null)
        return false;
    } else if (!this.perceptions.equals(other.perceptions))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.body== null) ? 0 : this.body.hashCode());
    result = prime * result + ((this.time== null) ? 0 : this.time.hashCode());
    result = prime * result + ((this.perceptions== null) ? 0 : this.perceptions.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the PerceptionEvent event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("body  = ").append(this.body);
    result.append("time  = ").append(this.time);
    result.append("perceptions  = ").append(this.perceptions);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 5664008194L;
}
