/**
 * Day 1
 * 2018年3月28日
 */

/**
 * 饿汉模式
 * 每个对象在没有使用之前就已经初始化了，可能会带来潜在的性能问题。
 */

public final class EagerSingleton {

    private static EagerSingleton singleton = new EagerSingleton();

    private EagerSingleton(){
    }

    public static EagerSingleton getSingleInstance(){
        return singleton;
    }
}

/**
 * 延迟加载、懒汉模式
 * 容易带来线程不安全的问题
 * 解决线程不安全的问题，只需要在getSingleInstance()方法前面加Synchronized关键字即可
 */

public final class LazySingleton {
    private static LazySingleton singleton = null;

    private LazySingleton(){
    }

    public static LazySingleton getSingleInstance(){
        if(null == singleton) {
            singleton = new LazySingleton();
        }
        return singleton;
    }
}

/**
 * 双重检查锁
 * 公认是一个Anti-Pattern，不推荐使用
 */

public final class DoubleCheckedSingleton{
    private static DoubleCheckedSingleton singleton = null;

    private DoubleCheckedSingleton(){
    }

    public static DoubleCheckedSingleton getSingleInstance(){
        if(null == singleton) {
            Synchronized(DoubleCheckedSingleton.class){
                if(null == singleton) {
                    singleton = new DoubleCheckedSingleton();
                }
            }
        }
        return singleton;
    }
}

/**
 * 使用内部类来做到延迟加载对象
 * 在初始化这个内部类的时候，JLS（Java Language Specification）会保证这个类的线程安全。
 * 这种写法的优雅自傲与，完全使用了Java虚拟机的机制进行同步保证，没有一个同步的关键字。
 */
public class Singleton {
    private static class SingletonHolder {
        public final static Singleton instance = new Singleton();
    }

    public static Singleton getInstance(){
        return SingletonHolder.instance;
    }
}

