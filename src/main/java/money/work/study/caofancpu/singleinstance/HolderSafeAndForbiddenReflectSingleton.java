package money.work.study.caofancpu.singleinstance;

/**
 * 静态内部类的单例
 *
 * @author D8GER
 */
public final class HolderSafeAndForbiddenReflectSingleton {
    private HolderSafeAndForbiddenReflectSingleton() {
        if (getInstance() != null) {
            throw new RuntimeException("妖精, 哪里逃!");
        }
    }

    public static HolderSafeAndForbiddenReflectSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public static class SingletonHolder {
        public static final HolderSafeAndForbiddenReflectSingleton instance = new HolderSafeAndForbiddenReflectSingleton();
    }


}
