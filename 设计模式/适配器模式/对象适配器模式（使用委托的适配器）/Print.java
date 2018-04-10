/**
 * 形成一种委托关系。
 * 当PrintBanner类的printWeak被调用的时候，
 * 并不是PrintBanner类自己进行处理，
 * 而是将处理交给了其他实例（Banner类的实例）的showWithParen方法。
 */

public abstract class Print {
    public abstract void printWeak();

    public abstract void printStrong();
}

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

public class PrintBanner extends Print {
    private Banner banner;

    public PrintBanner(String string) {
        this.banner = new Banner(string);
    }

    public void printWeak() {
        banner.showWithParen();
    }

    public void printStrong() {
        banner.showWithAster();
    }
}
/**
 * Adapter模式会对现有的类进行适配，
 * 生成新的类。
 * 通过该模式可以很方便地创建我们需要的方法群。
 * 当出现Bug时，
 * 由于我们很明确地知道Bug不再现有的类（Adaptee角色）中，
 * 所以只需调查Adapter角色的类即可。
 * 这样一来，
 * 代码问题的排查就会变得非常简单。
 */

