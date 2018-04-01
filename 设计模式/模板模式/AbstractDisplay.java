/**
 * Template Method模式
 * 带有模板功能的模式，组成模板的方法被定义在父类上中。
 * 由于这些方法是抽象方法，
 * 所以只查看父类的代码是无法知道这些方法最终会进行何种具体处理的，
 * 唯一能知道的就是父类是如何调用这些方法的。
 *
 * 实现上述这些抽象方法的是子类。
 * 在子类中实现了抽象方法也就决定了具体的处理。
 * 也就是说，只要在不同的子类中实现不同的具体处理，
 * 当父类的模板方法被调用时程序行为也会不同。
 * 但是，无论子类中的具体实现如何，
 * 处理的流程都会按照父类中所定义的那样进行。
 *
 * 像这样在父类中定义处理流程的框架，
 * 在子类中实现具体处理的模式就称为模板模式
 *
 */

public abstract class AbstractDisplay {
    public abstract void open();
    public abstract void print();
    public abstract void close();

    public final void display() {
        open();
        for (int i = 0; i < 5; i++) {
            print();
        }
        close();
    }
}

public class CharDisplay extends AbstractDisplay {
    private char ch;

    public CharDisplay(char ch) {
        this.ch = ch;
    }

    public void open() {
        System.out.print("<<");
    }

    public void print() {
        System.out.print("ch");
    }

    public void close() {
        System.out.println(">>");
    }
}

public class StringDisplay extends AbstractDisplay {
    private String string;
    private int width;

    public StringDisplay (String string) {
        this.string = string;
        this.width = string.getBytes().length;
    }

    public void open() {
        printLine();
    }

    public void print() {
        System.out.println("|" + string + "|");
    }

    public void close() {
        printLine();
    }

    private void printline() {
        System.out.print("+");
        for (int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}

public class Main {
    public static void main(String[] args) {
        AbstractDisplay d1 = new CharDisplay('H');
        AbstractDisplay d2 = new StringDisplay("Hello, world.");
        AbstractDisplay d3 = new StringDisplay("你好，世界。");
        d1.display();
        d2.display();
        d3.display();
    }
}

/**
 * 使用父类类型的变量保存子类实例的优点是，
 * 即使没有用instanceof等指定子类的种类，
 * 程序也能正常工作。
 *
 * 无论在父类类型的变量中保存哪个子类的实例，
 * 程序都可以正常工作，
 * 这种原则称为里氏替换原则（The Liskov Substitution Principle, LSP).
 * LSP是一个通用的继承原则
 */
