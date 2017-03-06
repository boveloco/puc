package pacman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * An efficient implementation of a Set of object of type Location, which uses bit-packing internally
 * to speed up equals() and hashCode() implementations.
 * @author grenager
 *
 */
public class LocationSet implements Set<Location> {

  int size;
  int[] a;

  public int size() {
    int sizeCheck =0; // TODO remove this
    for (Location l : this) {
      sizeCheck++;
    }
    if (sizeCheck!=size) throw new RuntimeException(); 
    return size;
  }

  public boolean isEmpty() {
    return size==0;
  }

  public boolean contains(Object o) {
    if (!(o instanceof Location)) return false;
    Location l = (Location) o;
    int b = a[l.getX()];
    if ((b>>l.getY())%2==1) { // there's a one in the yth bit of the xth int
      return true;
    }
    return false;
  }

  public Iterator<Location> iterator() {
    List<Location> result = new ArrayList<Location>();
    for (int i=0; i<a.length; i++) {
      for (int j=0; j<32; j++) {
        int b = a[i];
        if ((b>>j)%2==1) { // there's a one in the jth bit
          result.add(new Location(i,j));
        }
      }
    }
    return result.iterator();
  }
  
  public List<Location> list() {
    List<Location> result = new ArrayList<Location>();
    for (int i=0; i<a.length; i++) {
      for (int j=0; j<32; j++) {
        int b = a[i];
        if ((b>>j)%2==1) { // there's a one in the jth bit
          result.add(new Location(i,j));
        }
      }
    }
    return result;
  }

  public Object[] toArray() {
    Object[] a = new Object[size()];
    int i=0;
    for (Location l : this) {
      a[i] = l;
      i++;
    }
    return a;
  }

  public <T> T[] toArray(T[] arg0) {
    Object[] a = new Object[size()];
    int i=0;
    for (Location l : this) {
      a[i] = l;
      i++;
    }
    return (T[]) a;
  }

  public boolean add(Location l) {
    boolean result = contains(l);
    int mask = 1<<l.getY();
    a[l.getX()] |= mask;
    if (! result) size++;
    return result;
  }

  public boolean remove(Object o) {
    Location l = (Location) o;
    boolean result = contains(l);
    int mask = 1<<l.getY();
    mask = ~mask; // complement, so there's only one zero
    a[l.getX()] &= mask;
    if (result) size--;
    return result;
  }

  public boolean containsAll(Collection<?> arg0) {
    throw new UnsupportedOperationException();
  }

  public boolean addAll(Collection<? extends Location> c) {
    for (Location l : c) {
      add(l);
    }
    return true;
  }

  public boolean retainAll(Collection<?> c) {
    throw new UnsupportedOperationException();
  }

  public boolean removeAll(Collection<?> c) {
    for (Object o : c) {
      remove(o);
    }
    return true;
  }

  public void clear() {
    a = new int[32];
  }
  
  public boolean equals(Object o) {
    if (!(o instanceof LocationSet)) return false;
    LocationSet s = (LocationSet) o;
    return Arrays.equals(a, s.a);
  }
  
  public int hashCode() {
    return Arrays.hashCode(a);
  }
  
  public String toString() {
    StringBuilder b = new StringBuilder("{");
    for (Location l : this) {
      b.append(l + ",");
    }
    b.append("}");
    return b.toString();
  }
  
  public LocationSet(LocationSet other) {
    size = other.size;
    a = new int[other.a.length];
    System.arraycopy(other.a, 0, a, 0, other.a.length);
  }
  
  public LocationSet(Collection<Location> other) {
    this();
    addAll(other);
  }
  
  public LocationSet() {
    size = 0;
    a = new int[32];
  }
  
  /**
   * For debugging only.
   * @param args
   */
  public static void main(String[] args) {
    LocationSet s = new LocationSet();
    s.add(new Location(20,20));
    s.add(new Location(3,2));
    s.add(new Location(3,2));
    s.add(new Location(5,2));
    s.add(new Location(3,7));
    System.err.println("size=" + s.size());
    s.remove(new Location(3,2));
    s.remove(new Location(5,2));
    s.remove(new Location(5,2));
    s.remove(new Location(5,2));
    System.err.println("size=" + s.size());
    System.err.println(s);
    System.err.println(s.contains(new Location(3,2)));
    System.err.println(s.contains(new Location(3,7)));
  }

}
