/**
 * 
 */
package pacman;

import java.util.Collection;

/**
 * A class representing a location on the Pac-Man game board, with x and y coordinates.
 * @author grenager
 *
 */
public class Location {

  int x;

  int y;

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public boolean equals(Object o) {
    if (!(o instanceof Location))
      return false;
    Location l = (Location) o;
    if (l.x != x)
      return false;
    if (l.y != y)
      return false;
    return true;
  }

  public int hashCode() {
    return x * 37 + y;
  }
  
  public String toString() {
    return "("+x+","+y+")";
  }

  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public static double euclideanDistance(Location a, Location b) {
    // sqrt(x^2 + y^2)
    return Math.sqrt((a.getX()-b.getX())*(a.getX()-b.getX())+(a.getY()-b.getY())*(a.getY()-b.getY()));
  }

  public static double manhattanDistance(Location a, Location b) {
    return Math.abs(a.getX()-b.getX())+Math.abs(a.getY()-b.getY());
  }
  
  public static double euclideanDistanceToClosest(Location a, Collection<Location> c) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location b : c) {
      double distance = euclideanDistance(a, b);
      if (distance<minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }

  public static double manhattanDistanceToClosest(Location a, Collection<Location> c) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location b : c) {
      double distance = manhattanDistance(a, b);
      if (distance<minDistance) {
        minDistance = distance;
      }
    }
    return minDistance;
  }

}