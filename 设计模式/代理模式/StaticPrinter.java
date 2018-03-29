/**
 * Printer类，被代理的对象
 */

public class Printer implements Printable {
    private String name;

    public Printer() {
        heavyJob("Printer的实例生成中");
    }

    public Printer(String name) {
        this.name = name;
        heavyJob("Printer的实例生成中（" + name + "）");
    }

    public void print(String string) {
        System.out.println("=== " + name + " ===");
        System.out.println(string);
    }

    private void heavyJob(String msg) {
        System.out.print(msg);
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            System.out.print(".");
        }
        System.out.println("结束。");
    }
}

/**
 * Printable接口
 * 用于使PrinterProxy类和Printer类具有一致性
 */

public interface Printable {
    public abstract void setPrinterName(String name);
    public abstract String getPrinterName();
    public abstract void print(String string);
}

/**
 * PrinterProxy类
 * 扮演了代理人的角色，实现了Printable接口
 */

public class PrinterProxy implements Printable {
    private String name;
    private Printer real;

    private PrinterProxy(){
    }

    public PrinterProxy(String name){
        this.name = name;
    }

    public synchronized void setPrinterName(String name) {
        if (real != null) {
            real.setPrinterName(name);
        }
        this.name = name;
    }

    public String getPrinterName() {
        return name;
    }

    public void print(String string) {
        realize();
    }

    private synchronized void realize() {
        if (real == null) {
            real = new Printer(name);
        }
    }
}

/**
 * main函数
 */

public class Main {
    public static void main(String[] args){
        Printable p = new PrinterProxy("Alice");
        p.print("Hello, world");
    }
}
