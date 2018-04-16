/**
 * 访问者模式
 *
 * 在数据结构中保存着许多元素，我们会对这些元素进行"处理"。
 * 这时，"处理"代码放在哪里比较好呢？通常的做法是将它们放在表示数据结构的类中。
 * 但是，如果"处理"有许多种呢？
 * 这种情况下，每当增加一种处理，我们就不得不去修改表示数据结构的类。
 *
 * 在Visitor模式中，数据结构与处理被分离开来。
 * 我们编写表示"访问者"的类来访问数据结构中的元素，并把各元素的处理交给访问者类。
 * 这样，当需要增加新的处理时，我们只需要编写新的访问者，然后让数据结构可以接受访问者的访问即可。
 *
 * Visitor，表示访问者的抽象类，它访问文件和文件夹
 * Element，表示数据结构的接口，它接受访问者的访问
 * ListVisitor，Visitor类的子类，显示文件和文件夹一览
 * Entry，FIle类和Directory类的父类，它是抽象类（实现了Element接口）
 * File，表示文件的类
 * Directory，表示文件夹的类
 * FileTreatementException，表示向文件夹中add时发生的异常的类
 * Main，测试程序行为的类
 */

public abstract class Visitor {
    public abstract void visit(File file);
    public abstract void visit(Directory directory);
}

public interface Element {
    public abstract void accept(Visitor v);
}

public abstract class Entry implements Element {
    public abstract String getName();
    public abstract int getSize();

    public Entry add(Entry entry) throws FileTreatmentException {
        throw new FileTreatmentException();
    }

    public Iterator iterator() throws FileTreatmentException {
        throw new FileTreatmentException();
    }

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

    public void accept(Visitor v) {
        v.visit(this);
    }
}

public class Directory extends Entry {
    private String name;
    private ArrayList dir = ArrayList();

    public Directory(String name) {
        tihi.name = name;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        int size = 0;
        Iterator it = dir.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry)it.next();
            size += entry.getSize();
        }
        return size;
    }

    public Entry add(Entry entry) {
        dir.add(entry);
        return this;
    }

    public Iterator iterator() {
        return dir.iterator();
    }

    public void accept(Visitor v) {
        v.visit(this);
    }
}


public class ListVisitor extends Visitor {
    private String currentdir = "";

    public void viisit(File file) {
        System.out.println(currentdir + "/" + file);
    }

    public void visit(Directory directory) {
        System.out.println(currentdir + "/" + directory);
        String savedir = currentdir;
        currentdir = currentdir + "/ " + directory.getName();
        Iterator it = directory.iterator();
        while (it.hasNext()) {
            Entry entry = (Entry)it.next();
            entry.accept(this);
        }
        currentdir = savedir;
    }
}

public class FileTreatmentException extends RuntimeException {
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
            Directory bindir = new Directory("bin");
            Directory tmpdir = new Directory("tmp");
            Directory usrdir = new Directory("usr");
            rootdir.add(bindir);
            rootdir.add(tmpdir);
            rootdir.add(usrdir);
            bindir.add(new File("vi", 10000));
            bindir.add(new File("latex", 20000)):
            rootdir.accept(new ListVisitor());

            System.out.println("");
            System.out.println("Making user entries...");
            Directory yuki = new Directory("yuki");
            Directory hanako = new Directory("hanako");
            Directory tomura = new Directory("tomura");
            usrdir.add(yuki);
            usrdir.add(hanako);
            usrdir.add(tomura);
            yuki.add(new File("diary.html", 100));
            yuki.add(new File("Composite.java", 200));
            hanako.add(new File("memo.tex", 300));
            tomura.add(new File("game.doc", 400));
            tomura.add(new File("junk.mail", 500));
            rootdir.accept(new ListVisitor());
        } catch (FileTreatmentException E) {
            e.printStackTrace();
        }
    }
}


