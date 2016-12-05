package fr.utbm.drone.events;

import fr.utbm.drone.environment.AbstractEnvObject;
import io.sarl.lang.annotation.SarlSpecification;
import io.sarl.lang.annotation.SyntheticMember;
import io.sarl.lang.core.Event;
import java.util.List;
import org.eclipse.xtext.xbase.lib.Pure;

@SarlSpecification("0.4")
@SuppressWarnings("all")
public class EnvironmentChanged extends Event {
  public List<AbstractEnvObject> objects;
  
  public EnvironmentChanged(final List<AbstractEnvObject> objects) {
    this.objects = objects;
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
    EnvironmentChanged other = (EnvironmentChanged) obj;
    if (this.objects == null) {
      if (other.objects != null)
        return false;
    } else if (!this.objects.equals(other.objects))
      return false;
    return super.equals(obj);
  }
  
  @Override
  @Pure
  @SyntheticMember
  public int hashCode() {
    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.objects== null) ? 0 : this.objects.hashCode());
    return result;
  }
  
  /**
   * Returns a String representation of the EnvironmentChanged event's attributes only.
   */
  @SyntheticMember
  @Pure
  protected String attributesToString() {
    StringBuilder result = new StringBuilder(super.attributesToString());
    result.append("objects  = ").append(this.objects);
    return result.toString();
  }
  
  @SyntheticMember
  private final static long serialVersionUID = 819441753L;
}
