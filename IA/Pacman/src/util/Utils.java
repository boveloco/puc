package util;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

/**
 * A class containing some static utility methods.
 * 
 * @author grenager
 *
 */
public class Utils {
  
  public static double min(double[] a) {
    return a[argmin(a)];
  }
  
  public static double max(double[] a) {
    return a[argmax(a)];
  }

  public static int argmin(double[] a) {
    int bestIndex = -1;
    double bestValue = Double.POSITIVE_INFINITY;
    for (int i=0; i<a.length; i++) {
      if (a[i]<bestValue) {
        bestIndex = i;
        bestValue = a[i];
      }
    }
    return bestIndex;
  }

  public static int argmax(double[] a) {
    int bestIndex = -1;
    double bestValue = Double.NEGATIVE_INFINITY;
    for (int i=0; i<a.length; i++) {
      if (a[i]>bestValue) {
        bestIndex = i;
        bestValue = a[i];
      }
    }
    return bestIndex;
  }
  
  /**
   * A simpler form of command line argument parsing. Dan thinks this is highly
   * superior to the overly complexified code that comes before it. Parses
   * command line arguments into a Map. Arguments of the form -flag1 arg1 -flag2
   * -flag3 arg3 will be parsed so that the flag is a key in the Map (including
   * the hyphen) and the optional argument will be its value (if present). In
   * this version, if the argument is numeric, it will be a Double value in the
   * map, not a String.
   * 
   * @param args
   * @return A Map from keys to possible values (String or null)
   */
  public static Map<String, Object> parseCommandLineArguments(String[] args, boolean parseNumbers) {
    Map<String, Object> result = new HashMap<String, Object>();
    String key, value;
    for (int i = 0; i < args.length; i++) {
      key = args[i];
      if (key.charAt(0) == '-') {
        if (i + 1 < args.length) {
          value = args[i + 1];
          if (value.charAt(0) != '-') {
            if (parseNumbers) {
              Object numericValue = value;
              try {
                numericValue = Double.parseDouble(value);
              } catch (NumberFormatException e2) {
              }
              result.put(key, numericValue);
            } else {
              result.put(key, value);
            }
            i++;
          } else {
            result.put(key, null);
          }
        } else {
          result.put(key, null);
        }
      }
    }
    return result;
  }

  public static Object getNewObjectByName(String className, Class[] classes, Object[] args) {
    Object o = null;
    try {
      Class classDefinition = Class.forName(className);
      Constructor constructor = classDefinition.getConstructor(classes);
      o = constructor.newInstance(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return o;
  }



}
