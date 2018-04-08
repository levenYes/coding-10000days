/**
 * 在这里我们看到，与依赖注入关系特别密切的方法有createBeanInstance和populateBean。
 * 下面我们分别介绍这两个方法。
 * 在createBeanInstance中生成Bean所包含的Java对象，
 * 这个对象的生成有很多种不同的方式，
 * 可以通过工厂方法生成，
 * 也可以通过容器的autowire特性生成，
 * 这些生成方式都是由相关的BeanDefinition来指定的。
 */

    protected BeanWrapper createBeanInstance(String beanName, RootBeanDefinition mbd, Object[] args) {
        //需要确认创建的Bean实例的类可以实例化
        Class beanClass = resoulveBeanClass(mbd, beanName);
        //这里使用工厂方法对Bean进行实例化
        if (mbd.getFactoryMethodName() != null) {
            return instantiateUsingFactoryMethod(beanName, mbd, args);
        }
        if (mbd.resolvedConstructorOrFactoryMethod != null) {
            if (mbd.constructorArgumentsResolved) {
                return autowireConstructor(beanName, mbd, null, args);
            } else {
                return instantiateBean(beanName, mbd);
            }
        }
        //使用构造函数进行实例化
        Constructor[] ctors = determineConstructorsFromBeanPostProcessors(beanClass, beanName);
        if (ctors != null ||
                mbd.getResolvedAutowireMode() == RootBeanDefinition.AUTOWIRE_CONSTRUCTOR ||
                mbd.hasConstructorArgumentValues() || !ObjectUtils.isEmpty(args)) {
            //使用默认的构造函数对Bean进行实例化
            return instantiateBean(beanName, mbd);
        }
        //最常见的实例化过程instantiateBean
        protected BeanWrapper instantiateBean(String beanName, RootBeanDefinition mbd) {
            /**
             * 使用默认的实例化策略对Bean进行实例化，
             * 默认的实例化策略是CglibSubclassingInstantiationStrategy的实现
             */
            try{
               Object beanInstance = getInstantiationStrategy().instantiate(mbd, beanName, this);
               BeanWrapper bw = new BeanWrapperImpl(beanInstance);
               initBeanWrapper(bw);
               return bw;
            } catch (Throwable ex) {
                throw new BeanCreationException(mbd.getResourceDescription(), beanName,
                "Instantiation of bean failed", ex);
            }
        }
    }