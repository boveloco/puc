package util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * A class maintaining a Map from typed Object keys to double values.
 * 
 * @author grenager
 *
 * @param <T>
 */
public class Counter<T> {

  private Map<T, Double> map;

  public double getTotalCount() {
    double result = 0.0;
    for (T key : map.keySet()) {
      result += map.get(key);
    }
    return result;
  }

  public void setCount(T key, double count) {
    map.put(key, count);
  }

  public void improveCount(T key, double count) {
    if (!map.containsKey(key)) {
      setCount(key, count);
    } else {
      // otherwise, we have some other count already
      double oldCount = getCount(key);
      if (count > oldCount) {
        setCount(key, count); // only set it if it's better!
      }
    }
  }

  public void incrementCount(T key, double count) {
    double d = getCount(key);
    map.put(key, d + count);
  }

  public double remove(T key) {
    Double d = map.get(key);
    if (d == null)
      return 0.0;
    map.remove(key);
    return d;
  }

  public double getCount(T key) {
    Double d = map.get(key);
    if (d == null)
      return 0.0;
    return d;
  }

  public boolean containsKey(T key) {
    return map.containsKey(key);
  }

  public Set<T> keySet() {
    return map.keySet();
  }

  public int size() {
    return map.size();
  }

  public String toString() {
    return map.toString();
  }

  public String toVerticalString() {
    StringBuilder b = new StringBuilder();
    for (T key : keySet()) {
      double count = getCount(key);
      b.append(key + "\t" + count + "\n");
    }
    return b.toString();
  }

  public T argmax() {
    T best = null;
    double max = Double.NEGATIVE_INFINITY;
    for (T key : keySet()) {
      double count = getCount(key);
      if (count > max) {
        max = count;
        best = key;
      }
    }
    return best;
  }

  public Set<T> keysAbove(double d) {
    Set<T> result = new HashSet<T>();
    for (T key : keySet()) {
      double count = getCount(key);
      if (count >= d)
        result.add(key);
    }
    return result;
  }

  public double max() {
    double max = Double.NEGATIVE_INFINITY;
    for (T key : keySet()) {
      double count = getCount(key);
      if (count > max) {
        max = count;
      }
    }
    return max;
  }

  public Counter<T> exp() {
    Counter<T> result = new Counter<T>();
    for (T key : keySet()) {
      result.setCount(key, Math.exp(getCount(key)));
    }
    return result;
  }

  public Counter<T> normalize() {
    Counter<T> result = new Counter<T>();
    double totalCount = getTotalCount();
    for (T key : keySet()) {
      result.setCount(key, getCount(key) / totalCount);
    }
    return result;
  }

  public T sampleFromDistribution(Random random) {
    double total = getTotalCount();
    double d = random.nextDouble(); // between 0 and 1 inclusive
    double accumulator = 0;
    for (T key : keySet()) {
      accumulator += getCount(key);
      if (d <= accumulator / total)
        return key;
    }
    return keySet().iterator().next(); // this is very rare occurrence, a
                                        // result of doubule math
  }

  public Counter() {
    map = new HashMap<T, Double>();
  }

  public Counter<T> scale(double d) {
    Counter<T> result = new Counter<T>();
    for (T key : keySet()) {
      result.setCount(key, getCount(key) * d);
    }
    return result;
  }

}
