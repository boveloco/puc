package util;

import java.io.Serializable;

/**
 * A class for holding a pair of objects.
 *
 * @author Dan Klein
 * @author Christopher Manning (added stuff from Kristina's, rounded out)
 * @version 2002/08/25
 */
public class Pair <T1,T2> implements Comparable, Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Direct access is deprecated.  Use first().
   *
   * @serial
   */
  public T1 first;

  /**
   * Direct access is deprecated.  Use second().
   *
   * @serial
   */
  public T2 second;

  public Pair() {
    // first = null; second = null; -- default initialization
  }

  public Pair(T1 first, T2 second) {
    this.first = first;
    this.second = second;
  }

  public T1 first() {
    return first;
  }

  public T2 second() {
    return second;
  }

  public void setFirst(T1 o) {
    first = o;
  }

  public void setSecond(T2 o) {
    second = o;
  }

  public String toString() {
    return "(" + first + "," + second + ")";
  }

  public boolean equals(Object o) {
    if (o instanceof Pair) {
      Pair p = (Pair) o;
      return (first == null ? p.first == null : first.equals(p.first)) && (second == null ? p.second == null : second.equals(p.second));
    } else {
      return false;
    }
  }

  public int hashCode() {
    return (((first == null) ? 0 : first.hashCode()) << 16) ^ ((second == null) ? 0 : second.hashCode());
  }
  /**
   * Compares this <code>Pair</code> to another object.
   * If the object is a <code>Pair</code>, this function will work providing
   * the elements of the <code>Pair</code> are themselves comparable.
   * It will then return a value based on the pair of objects, where
   * <code>p &gt; q iff p.first() &gt; q.first() ||
   * (p.first().equals(q.first()) && p.second() &gt; q.second())</code>.
   * If the other object is not a <code>Pair</code>, it throws a
   * <code>ClassCastException</code>.
   *
   * @param o the <code>Object</code> to be compared.
   * @return the value <code>0</code> if the argument is a
   *         <code>Pair</code> equal to this <code>Pair</code>; a value less than
   *         <code>0</code> if the argument is a <code>Pair</code>
   *         greater than this <code>Pair</code>; and a value
   *         greater than <code>0</code> if the argument is a
   *         <code>Pair</code> less than this <code>Pair</code>.
   * @throws ClassCastException if the argument is not a
   *                            <code>Pair</code>.
   * @see java.lang.Comparable
   */
  public int compareTo(Object o) {
    Pair another = (Pair) o;
    int comp = ((Comparable) first()).compareTo(another.first());
    if (comp != 0) {
      return comp;
    } else {
      return ((Comparable) second()).compareTo(another.second());
    }
  }

}
