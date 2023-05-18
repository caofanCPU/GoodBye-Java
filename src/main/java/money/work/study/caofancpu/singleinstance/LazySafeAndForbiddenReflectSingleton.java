package money.work.study.caofancpu.singleinstance;

/**
 * 懒汉双重判断锁单例
 * 禁止继承
 * 禁止指令重排
 * 禁止外部访问
 * 双重判断, 内层加锁
 *
 * @author D8GER
 */
public final class LazySafeAndForbiddenReflectSingleton {

    /**
     * 禁止指令重排
     */
    private static volatile LazySafeAndForbiddenReflectSingleton instance = null;

    /**
     * 禁止外部访问
     */
    private LazySafeAndForbiddenReflectSingleton() {
        if (instance != null) {
            // 屏蔽反射
            throw new RuntimeException("妖精, 休得胡来!");
        }
    }

    public static LazySafeAndForbiddenReflectSingleton getInstance() {
        if (instance == null) {
            synchronized (LazySafeAndForbiddenReflectSingleton.class) {
                if (instance == null) {
                    instance = new LazySafeAndForbiddenReflectSingleton();
                }
            }
        }
        return instance;
    }
}
