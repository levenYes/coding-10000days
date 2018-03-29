/**
 * 动态代理代码
 * JDK提供
 */

public class DynamicProxy implements InvocationHandler {
    private Object target;

    public DynamicProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object result = method.invoke(target,args);
        after();
        return result;
    }
}

/**
 * main函数使用
 */

public static void main(String[] args) {
    Hello hello = new HelloImpl();
    DynamicProxy dynamicProxy = new DynamicProxy(hello);
    Hello helloProxy = (Hello)Proxy.newProxyInstance(
            hello.getClass().getClassLoader(),
            hello.getClass().getInterfaces(),
            dynamicProxy
    );
    helloProxy.sya();
}

/**
 * CGlib动态代理
 */

public class CGLibProxy implements MethodInterceptor {
    public <T> T getProxy(Class<T> cls) {
        return (T) Enhancer.create(cls, this);
    }

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        before();
        Object result = proxy.invokeSuper(obj, args);
        after();
        return result;
    }

    //...有所省略
}