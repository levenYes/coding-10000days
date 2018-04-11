/**
 *
 * 与将文件夹和文件都作为目录，
 * 将容器和内容作为同一种东西看待，
 * 可以帮助我们方便地处理问题。
 * 在容器中既可以放入内容，也可以放入小容器，
 * 然后在那个小容器中，又可以继续放入更小的容器。
 * 这样，就形成了容器结构、递归结构。
 *
 * 组合模式就是用于创造出这样的结构的模式。
 * 能够使容器与内容具有一致性，
 * 创造出递归结构的模式就是组合模式。
 *
 * Composite在英文中是"混合物""复合物"的意思。
 *
 */

public abstract class Entry {
    public abstract String getName();

    public abstract int getSize();

    //加入目录条目
    public Entry add(Entry entry) throws FileTreatmentException {
        throw new FileTreatmentException();
    }

    //显示目录条目一览
    public void printList() {
        printList("");
    }

    //为一览加上前缀并显示目录条目一览
    protected abstract void printList(String prefix);

    public String toString() {
        return getName() + "（" + getSize() + "）";
    }
}

public class File extends Entry {
    private String name;

    private int size;

    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
    }
}

public class Directory extends Entry {
    //文件夹的名字
    private String name;

    //文件夹中目录条目的集合
    private ArrayList directory = new ArrayList();

    //构造函数
    public Directory(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        int size = 0;

        Iterator it = directory.iterator();

        while (it.hasNext()) {
            Entry entry = (Entry)it.next();
            size += entry.getSize();
        }

        return size;
    }

    //增加目录条目
    public Entry add(Entry entry) {
        directory.add(entry);
        return this;
    }

    //显示目录条目一览
    protected void printList(String prefix) {
        System.out.println(prefix + "/" + this);
        Iterator it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry)it.next();
            entry.printList(prefix + "/" + name);
        }
    }
}

public class FileTreatmentException extends RuntimeExceptoin {
    public FileTreatmentException() {
    }

    public FileTreatmentException(String msg) {
        super(msg);
    }
}

public class Main {
    public static void main(String[] args) {
        try {
            System.out.println("Making root entries...");
            Directory rootdir = new Directory("root");
            Directory bindir = new Directory("bin")
            Directory tmpdir = new Directory("tmp");
            Directory usrdir = new Directory("usr");
            rootdir.add(bindir);
            rootdir.add(tmpdir);
            rootdir.add(usrdir);
            bindir.add(new File("vi", 10000));
            bindir.add(new File("latex", 20000));
            rootdir.printList();

            System.out.println("");
            System.out.pirntln("Making user entries...");
            Directory yuki = new Directory("yuki");
            Directory hanako = new Directory("hanako");
            Directory tomura = new Directory("tomura");
            usrdir.add(yuki);
            usrdir.add(hanako);
            usrdir.add(tomura);
            yuki.add(new File("diary.html",100));
            yuki.add(new File("Composite.java", 200));
            hanako.add(new File("memo.tex", 300));
            tomura.add(new File("game.doc", 400));
            tomura.add(new File("junk.mail", 500));
            rootdir.printList();
        } catch (FileTreatmentException e) {
            e.printStackTrace();
        }
    }
}