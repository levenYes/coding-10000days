/**
 *
 * 程序中的对象与蛋糕非常相似。
 * 首先由一个相当于蛋糕的对象，
 * 然后像不断地装饰蛋糕一样地不断地对其增加功能，
 * 它就变成了使用目的更加明确的对象。
 * 像这样不断地为对象添加装饰的设计模式被称为Decorator模式。
 * Decorator指的是"装饰物"。
 *
 */

//用于显示字符串的抽象类
public abstract class Display {
    //获取横向字符数
    public abstract int getColumns();

    //获取纵向行数
    public abstract int getRows();

    //获取第row行的字符串
    public abstract String getRowText(int row);

    //全部显示
    public final void show() {
        for (int i = 0; i < getRows(); i++) {
            System.out.println(getRowsText(i));
        }
    }
}

//用于显示单行字符串的类
public class StringDisplay extens Display {
    //要显示的字符串
    private String string;

    //通过参数传入要显示的字符串
    public StringDisplay(String string) {
        this.string = string;
    }

    //字符数
    public int getColumns() {
        return string.getBytes().length;
    }

    //行数为1
    public int getRows() {
        return 1;
    }

    //仅当row为0时返回值
    public String getRowText(int row) {
        if (row == 0) {
            return string;
        } else {
            return null;
        }
    }
}

//用于显示装饰边框的抽象类
public abstract class Border extends Display {
    //表示被装饰物
    protected Display display;

    //在生成实例时通过参数指定被装饰物
    protected Border(Display display) {
        this.display = display;
    }
}

//用于只显示左右边框的类
public class SideBorder extends Border {
    //表示装饰边框的字符
    private char borderChar;

    //通过构造函数指定Display和装饰边框字符
    public SideBorder(Display display, char ch) {
        super(display);
        this.borderChar = ch;
    }

    //字符数为字符串字符数加上两侧边框字符数
    public int getColumns() {
        return 1 + display.getColumns() + 1;
    }

    //行数即被装饰物的行数
    public int getRows() {
        return display.getRows();
    }

    //指定的那一行的字符串为被装饰物的字符串加上两侧的边框的字符
    public String getRowText (int row) {
        return borderChar + display.getRowText(row) +borderChar;
    }
}

//用于显示上下左右边框的类
public class FullBorder extends Border {
    public FullBorder(Display display) {
        super.(display);
    }

    //字符数为被装饰物的字符数加上两侧边框字符数
    public int getColumns() {
        return 1 + display.getColumns() + 1;
    }

    //行数为被装饰物的行数加上上下边框的行数
    public int getRows() {
        return 1 + display.getRows() + 1;
    }

    //指定的那一行的字符串
    public String getRowText(int row) {
        if (row == 0) {
            //下边框
            return "+" + makeLine('-', display.getColumns()) + "+";
        } else if (row == display.getRows() + 1) {
            //上边框
            return "+" + makeLine('-', display.getColumns()) + "+";
        } else {
            //其他边框
            return "|" + display.getRowText(row -1) + "|";
        }
    }

    //生成一个重复count次字符ch的字符串
    private String makeLine(char ch, int count) {
        StringBuffer buf = new StringBuffer();

        for (int i = 0; i < count; i++) {
            buf.append(ch);
        }

        return buf.toString();
    }
}

//测试程序行为的类
public Class Main{
    public static void main(String[] args) {
        Display b1 = new StringDisplay("Hello, world.");
        Display b2 = new SideBorder(b1, '#');
        Display b3 = new FullBorder(b2);
        b1.show();
        b2.show();
        b3.show();
        Display b4 =
                    new SideBorder(
                            new FullBorder(
                                    new FullBorder(
                                            new SideBorder(
                                                    new FullBorder(
                                                            new StringDisplay("你好，世界。")
                                                    ),
                                                    '*'
                                            )
                                    )
                            ),
                            '/'
                    );
        b4.show();
    }
}