/**
 * Iterator模式
 * 用于在数据集合中按照顺序遍历集合。
 *
 * Aggregate，表示集合的接口
 * Iterator，遍历集合的接口
 * Book，表示书的类
 * BookShelf，表示书架的类
 * BookShelfIterator，遍历书架的类
 * Main，测试程序行为的类
 *
 */

public interface Aggregate {
    public abstract Iterator iterator();
}

public interface Iterator {
    public abstract boolean hasNext();
    public abstract Object next();
}

public class Book {
    private  String name;
    public Book(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
}

public class BookShelf implements Aggregate {
    private Book[] books;
    private int last = 0;

    public BookShelf(int maxsize) {
        this.books = new Book[maxsize];
    }

    public Book getBookAt(int index) {
        return books[index];
    }

    public void appendBook(Book book) {
        this.books[last] = book;
        last ++;
    }

    public int getLength() {
        return last;
    }

    public Iterator iterator() {
        return new BookShelfIterator(this);
    }
}

public class BookShelfIterator implements Iterator {
    private BookShelf bookShelf;
    private int index;

    public BookShelfIterator(BookShelf bookShelf) {
        this.bookShelf = bookShelf;
        this.index = 0;
    }

    public boolean hasNext() {
        if (index < bookShelf.getLength()) {
            return true;
        } else {
            return false;
        }
    }

    public Object next() {
        Book book = bookShelf.getBookAt(index);
        index++;
        return book;
    }
}

public class Main {
    public static void main(String[] args) {
        BookShelf bookShelf = new BookShelf(4);
        bookShelf.appendBook(new Book("Around the World in 80 Days"));
        bookShelf.appendBook(new Book("Bible"));
        bookShelf.appendBook(new Book("Cinderella"));
        bookShelf.appendBook(new Book("Daddy-Long-Legs"));
        Iterator it = bookShelf.iterator();
        while (it.hasNext()) {
            Book book = (Book)it.next();
            System.out.println(book.getName());
        }
    }
}

/**
 * 设计模式的作用就是帮助我们编写可复用的类。
 * 所谓"可复用"，就是指将类实现为"组件"，
 * 当一个组件发生改变时，
 * 不需要对其他的组件进行修改或是指需要很小的修改即可应对。
 *
 * 如果只使用具体的类来解决问题，
 * 很容易导致类之间的强耦合，
 * 这些类也很难以作为组件被再次利用。
 * 为了弱化类之间的耦合，
 * 进而使得类更加容易作为组件被再次利用，
 * 我们需要引入抽象类和接口。
 */



