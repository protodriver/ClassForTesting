import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class TestStarter {
    public static void main(String[] args) {
        start(ToTest.class);
        start(ToTest.class.getName());
    }
    public static void start(Class c) {
        Method[] m = c.getDeclaredMethods();
        Method bef = null;
        Method aft = null;
        Map<Integer, List<Method>> map = new TreeMap<Integer, List<Method>>();
        for (Method method : m) {
            if (method.isAnnotationPresent(BeforeSuite.class)) {
                if (bef == null) {
                    bef = method;
                } else {
                    throw new RuntimeException();
                }
            } else if (method.isAnnotationPresent(AfterSuite.class)) {
                if (aft == null) {
                    aft = method;
                } else {
                    throw new RuntimeException();
                }
            } else if (method.isAnnotationPresent(Test.class)) {
                Integer key = method.getAnnotation(Test.class).priory();
                if (map.containsKey(key)) {
                    map.get(key).add(method);
                } else {
                    map.put(key, new ArrayList<Method>(Arrays.asList(method)));
                }
            }
        }
        try {
            if (bef != null )
                bef.invoke(null);
            for (Integer key : map.keySet()) {
                for (Method method : map.get(key)) {
                        method.invoke(null);
                }
            }
            if (aft != null )
                aft.invoke(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
    public static void start(String className) {
        try {
            Class c = Class.forName(className);
            start(c);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
