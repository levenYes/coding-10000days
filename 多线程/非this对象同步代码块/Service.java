/**
 * 在多个线程持有“对象监视器”为同一个对象的前提下，
 * 同一时间只有一个线程可以执行synchronized(非this对象)同步代码块中的代码。
 */

public class Service {
    private String usernameParam;
    private String passwordParam;
    private String anyString = new String();

    public void setUsernamePassword(String username, String password) {
        try {
            synchronized (anyString) {
                System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在" + System.currentTimeMillis() + "进入同步块");
                usernameParam = username;
                Thread.sleep(3000);
                passwordParam = password;
                System.out.println("线程名称：" + Thread.currentThread().getName()
                + "在" + System.currentTimeMillis() + "离开同步块");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadA extends Thread {
    private Service service;

    public ThreadA (Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.setUsernamePassword("a", "aa");
    }
}


public class ThreadB extends Thread {
    private Service service;

    public ThreadA (Service service) {
        super();
        this.service = service;
    }

    @Override
    public void run() {
        service.setUsernamePassword("b", "bb");
    }
}

public class Run {
    public static void main(String[] args) {
        Service service = new Service();
        ThreadA a = new ThreadA(service);
        a.setName("A");
        a.start();
        ThreadB b = new ThreadB(service);
        b.setName("B");
        b.start();
    }
}