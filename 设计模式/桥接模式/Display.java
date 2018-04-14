/**
 * 父类具有基本功能，
 * 在子类中增加新的功能。
 * 以上这种层次结构被称为"类的功能层次结构"。
 *
 * 父类通过声明抽象方法来定义接口（API）
 * 子类通过实现具体方法来实现接口（API）
 * 这种层次结构被称为"类的实现层次结构"。
 *
 * 当我们想要编写子类时，
 * 就需要像这样先确认自己的意图：
 * "我是要增加功能呢？还是要增加实现呢？"
 * 当类的层次结构只有一层时，
 * 功能层次结构与实现层次结构是混杂在一个层次结构中的。
 * 这样很容易使类的层次结构变得复杂，
 * 也难以透彻地理解类的层次结构。
 * 因为自己难以确定究竟应该在类的哪一个层次结构中去增加子类。
 *
 * 因此，我们需要将"类的功能层次结构"与"类的实现层次结构"分离为两个独立的类层次结构。
 * 当然，如果只是简单地将它们分开，
 * 两者之间必然会缺少联系。
 * 所以我们还需要在它们之间搭建一座桥梁。
 * 桥接模式的作用就是搭建这座桥梁。
 */

/**
 * 类的功能层次结构
 * 负责"显示"的类
 */
public class Display {
    private DisplayImpl impl;

    public Display(DisplayImpl impl) {
        this.impl = impl;
    }

    public void open() {
        impl.rawOpen();
    }

    public void print() {
        impl.rawPrint();
    }

    public void close() {
        impl.rawClose();
    }

    public final void display() {
        open();
        print();
        close();
    }
}

/**
 * 类的功能层次结构
 * 增加了"只显示规定次数"这一功能的类
 */
public class CountDisplay extends Display {
    public CountDisplay(DisplayImpl impl) {
        super(impl);
    }

    public void multiDisplay(int times) {
        open();
        for (int i = 0; i < times; i++) {
            print();
        }
        close();
    }
}


/**
 * 类的实现层次结构
 * 负责"显示"的类
 */
public abstract class DisplayImpl {
    public abstract void rawOpen();
    public abstract void rawPrint();
    public abstract void rawClose():
}

/**
 * 类的实现层次结构
 * "用字符串显示"的类
 */
public class StringDisplayImpl extends DisplayImpl {
    private String string;
    private int width;
    public StringDisplayImpl(String string) {
        this.string = string;
        this.width = string.getBytes().length;
    }

    public void rawOpen() {
        printLine();
    }

    public void rawPrint() {
        System.out.println("|" + string + "|"):
    }

    public void rawClose() {
        printLine();
    }

    private void printLine() {
        System.out.print("+");
        for(int i = 0; i < width; i++) {
            System.out.print("-");
        }
        System.out.println("+");
    }
}

/**
 * 测试程序行为的类
 */
public class Main {
    public static void main(String[] args) {
        Display d1 = new Display(new StringDisplayImpl("Hello, China."));
        Display d2 = new CountDisplay(new StringDisplayImpl("Hello, World."));
        CountDisplay d3 = new CountDisplay(new StringDisplayImpl("Hello, Universe"));
        d1.display();
        d2.display();
        d3.display();
        d3.multiDisplay(5);
    }
}