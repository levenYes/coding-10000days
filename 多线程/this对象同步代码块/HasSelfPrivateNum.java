/**
 * 如果多个线程访问多个对象，则JVM会创建出多个锁。
 */

public class HasSelfPrivateNum {
    private int num = 0;

    synchronized public void addI(String username) {
        try {
            if (username.equals("a")) {
                num = 100;
                System.out.println("a set over !");
                Thread.sleep(2000);
            } else {
                num = 200;
                System.out.println("b set over !");
            }
            System.out.println(username + " num=" + num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/**
 * ThreadA
 */

public class ThreadA extends Thread {
    private HasSelfPrivateNum numRef;

    public ThreadA(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super();
        numRef.addI("a");
    }
}


/**
 * ThreadB
 */

public class ThreadB extends Thread {
    private HasSelfPrivateNum numRef;

    public ThreadB(HasSelfPrivateNum numRef) {
        super();
        this.numRef = numRef;
    }

    @Override
    public void run() {
        super();
        numRef.addI("b");
    }
}

/**
 * Run
 */

public class Run {
    public static void main(String[] args) {
        HasSelfPrivateNum numRef1 = new HasSelfPrivateNum();
        HasSelfPrivateNum numRef2 = new HasSelfPrivateNum();
        THreadA athread = new ThreadA(numRef1);
        athread.start();
        ThreadB bthread = new ThreadB(numRef2);
        bthread.start();
    }
}