/**
 * 基于切面注解的AOP框架
 */

/**
 * 切面注解
 *
 * 通过@Target(ElementType.TYPE)来设置该注解只能应用在类上。
 * 该注解中包含一个名为value的属性，
 * 它是一个注解类，
 * 用来定义Controller这类注解。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    /**
     * 注解
     */
    Class<? extends Annotation> value();
}

/**
 * Proxy接口
 *
 * 这个Proxy接口中包括了一个doProxy方法，
 * 传入一个ProxyChain，
 * 用于执行"链式代理"操作。
 *
 * 所谓链式代理，
 * 也就是说，
 * 可将多个代理通过一条链子串起来，
 * 一个个地去执行，
 * 执行顺序取决于添加到链上的先后顺序。
 */

public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}

/**
 * ProxyChain类
 *
 * 定义了一系列的成员变量，
 * 包括targetClass（目标类）、targetObject（目标对象）、targetMethod（目标方法）
 * methodProxy（方法代理）、methodParams（方法参数），
 * 此外还包括proxyList（代理列表）、proxyIndex（代理索引），
 * 这些成员变量在构造器中进行初始化，
 * 并提供了几个重要的获值方法。
 *
 * 需要注意的是MethodProxy这个类，
 * 它是CGLib开源项目为我们提供的一个方法代理对象，
 * 在doProxyChain方法中被使用。
 *
 * 需要稍作解释的是doProxyChain方法，
 * 在该方法中，我们通过proxyIndex来充当代理对象的计数器，
 * 若尚未达到proxyList的上限，
 * 则从proxyList中取出相应的Proxy对象，
 * 并调用其doProxy方法。
 * 在Proxy接口的实现中会提供相应的横切逻辑，
 * 并调用doProxyChain方法，
 * 随后将再次调用当前ProxyChain对象的doProxyChain方法，
 * 直到proxyIndex达到proxyList的上限为止，
 * 最后调用methodProxy的invokeSuper方法，
 * 执行目标对象的业务逻辑。
 *
 */

public class ProxyChain {
    private final Class<?> targetClass;
    private final Object targetObject;
    private final Method targetMethod;
    private final MethodProxy methodProxy;
    private final Object[] methodParams;

    private List<Proxy> proxyList = new ArrayList<Proxy>();

    private int proxyIndex = 0;

    public ProxyChain(Class<?> targetClass, Object targetObject,
                      Method targetMethod, MethodProxy methodProxy,
                      Object[] methodParams, List<Proxy> proxyList) {
        this.targetClass = targetClass;
        this.targetObject = targetObject;
        this.targetMethod = targetMethod;
        this.methodProxy = methodProxy;
        this.methodParams = methodParams;
        this.proxyList = proxyList;
    }

    public Object[] getMethodParams() {
        return methodParams;
    }

    public Class<?> getTargetClass() {
        return targetClass;
    }

    public Method getTargetMethod() {
        return targetMethod;
    }

    public Object doProxyChain() throws Throwable {
        Object methodResult;
        if (proxyIndex < proxyList.size()) {
            methodResult = proxyList.get(proxyIndex++).doProxy(this);
        } else {
            methodResult = methodProxy.invokeSuper(targetObject, methodParams);
        }
        return methodResult;
    }
}

/**
 * ProxyManager类
 *
 * 它提供一个创建代理对象的方法，
 * 输入一个目标类和一组Proxy接口实现，
 * 输出一个代理对象。
 *
 * 我们使用CGLib提供的Enhancer#create方法来创建代理对象，
 * 将intercept的参数传入ProxyChain的构造器中即可。
 */

public class ProxyManager {
    public static <T> T createProxy(final Class<?> targetClass,
                                    final List<Proxy> proxyList) {
        return (T) Enhancer.create(targetClass, new MethodInterceptor() {
           @Override
           public Object intercept(Object targetObject, Method targetMethod,
                                   Object[] methodParams, MethodProxy methodProxy) throws Throwable {
               return new ProxyChain(targetClass, targetObject, targetMethod,
                       methodProxy, methodParams, proxyList).doProxyChain();
           }
        });
    }
}

/**
 * AspectProxy类
 *
 * 切面代理
 *
 * 谁来调用ProxyManager呢？
 * 当然是切面类了，
 * 因为在切面类中，
 * 需要在目标方法被调用的前后增加相应的逻辑。
 * 这个抽象类提供一个模板方法，
 * 并在该抽象类的具体实现中扩展相应的抽象方法。
 */

public abstract class AspectProxy implements Proxy {
    private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
        Object result = null;

        Class<?> cls = proxyChain.getTargetClass();
        Method method = proxyChain.getTargetMethod();
        Object[] params = proxyChain.getMethodParams();

        begin();
        try {
            if (intercept(cls, method, params)) {
                before(cls, method, params);
                result = proxyChain.doProxyChain();
                after(cls, method, params, result);
            } else {
                result = proxyChain.doProxyChain();
            }
        }catch (Exception e) {
                logger.error("proxy failure", e);
                error(cls, method, params, e);
                throw e;
            } finally {
                end();
            }

            return result;
    }

    public void begin() {
    }

    public boolean intercept (Class<?> cls, Method method, Object[] params) throws Throwable {
        return true;
    }

    public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
    }

    public void after(Class<?> cls, Method method, Object[] params, Obejct result) throws Throwable {
    }

    public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
    }

    public void end() {
    }
}











































