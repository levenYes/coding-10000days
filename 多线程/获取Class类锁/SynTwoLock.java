/**
 * 本质上的不同，
 * synchronized关键字加到static静态方法上是给Class类上锁，
 * 而synchronized关键字加到非static静态方法上是给对象加锁。
 */

public class Service {
    synchronized public static void printA() {
        try {
            System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "进入printA");
            Thread.sleep(3000);
            System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "离开printA");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    synchronized public static void printB() {
        System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "进入printB");
        System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "离开printB");
    }

    synchronized public void printC() {
        System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "进入printC");
        System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在 " + System.currentTimeMillis() + "离开printC");
    }
}

public class ThreadA extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.printA();
    }
}


public class ThreadB extends Thread {
    private Service service;

    public ThreadA(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.printB();
    }
}

public class ThreadC extends Thread {
    private Service service;

    public ThreadC(Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.printC();
    }
}

public class Run {
    public static void main (String[] args) {
        Service service = new Service();
        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();
        ThreadC c = new ThreadC(service);
        c.setName("C");
        c.start();
    }
}