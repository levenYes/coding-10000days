/**
 * 使用继承的适配器
 *
 */

public class Banner {
    private String string;

    public Banner(String string) {
        this.string = string;
    }

    public void showWithParen() {
        System.out.println("(" + string + ")");
    }

    public void showWithAster() {
        System.out.println("*" + string + "*");
    }
}

public interface Print {
    public abstract void printWeak();

    public abstract void printStrong();
}

public class PrintBanner extends Banner implements Print {
    public PrintBanner (String string) {
        super(string);
    }

    public void printWeak() {
        showWithParen();
    }

    public void printStrong() {
        showWithAster();
    }
}

/**
 * Main类并不知道PrintBanner类是如何实现的，
 * 这样就可以在不用对Main类进行修改的情况下改变PrintBanner类的具体实现。
 */
public class Main {
    public static void main (String[] args) {
        Print p = new PrintBanner("Hello");
        p.printWeak();
        p.printStrong();
    }
}