package money.work.study.caofancpu;

import money.work.study.caofancpu.singleinstance.HolderSafeAndForbiddenReflectSingleton;
import money.work.study.caofancpu.singleinstance.LazySafeAndForbiddenReflectSingleton;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 *
 */
public class SingletonTest {

    @Test
    public void testReflectHolderSingleton() {
        try {
            testReflectBefore();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            testReflectAfter();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public static void testLazySingleton() {
        try {
            Class<LazySafeAndForbiddenReflectSingleton> clazz = (Class<LazySafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.LazySafeAndForbiddenReflectSingleton");
            Constructor<LazySafeAndForbiddenReflectSingleton> constructor = clazz.getDeclaredConstructor(null);
            constructor.setAccessible(true);
            LazySafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
            System.out.println(instance2);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        LazySafeAndForbiddenReflectSingleton instance1 = LazySafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
    }

    private void testReflectBefore()
            throws Exception {
        Class<HolderSafeAndForbiddenReflectSingleton> aClass = (Class<HolderSafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.HolderSafeAndForbiddenReflectSingleton");
        Constructor<HolderSafeAndForbiddenReflectSingleton> constructor = aClass.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        HolderSafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
        System.out.println(instance2);
        HolderSafeAndForbiddenReflectSingleton instance1 = HolderSafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
    }

    private void testReflectAfter()
            throws Exception {
        HolderSafeAndForbiddenReflectSingleton instance1 = HolderSafeAndForbiddenReflectSingleton.getInstance();
        System.out.println(instance1);
        Class<HolderSafeAndForbiddenReflectSingleton> aClass = (Class<HolderSafeAndForbiddenReflectSingleton>) Class.forName("com.xyz.caofancpu.trackingtime.studywaitingutils.singleinstance.HolderSafeAndForbiddenReflectSingleton");
        Constructor<HolderSafeAndForbiddenReflectSingleton> constructor = aClass.getDeclaredConstructor(null);
        constructor.setAccessible(true);
        HolderSafeAndForbiddenReflectSingleton instance2 = constructor.newInstance();
        System.out.println(instance2);
    }

}
