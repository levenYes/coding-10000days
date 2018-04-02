/**
 * Factory有"工厂"的意思。
 * 用Template Method模式来构建生成实例的工厂，
 * 这就是Factory Method模式。
 *
 * 在Factory Method模式中，
 * 父类决定实例的生成方式，
 * 但并不决定所要生成的具体的类，
 * 具体的处理全部交给子类负责。
 * 这样就可以将生成实例的框架（framework）和实际负责生成实例的类解耦。
 */

public abstract class Factory {
    public final Product create(String owner) {
        Product p = creaeProduct(owner);
        registerProduct(p);
        return p;
    }

    protected abstract Product creaeProduct(String owner);
    protected abstract void registerProduct(Product product);
}

public abstract class Product {
    public abstract void use();
}

public class IDCard extends Product {
    private String owner;

    IDCard(String owner) {
        System.out.println("制作" + owner + "的ID卡。"):
        this.owner = owner;
    }

    public void use() {
        System.out.println("使用" + owner +"的ID卡。"):
    }

    public String getOwner() {
        return owner;
    }
}

public class IDCardFactory extends Factory {
    private List owners = new ArrayList();

    protected Product createProduct(String owner) {
        return new IDCard(owner);
    }

    protected void registerProduct(Product product) {
        owners.add(((IDCard)product).getOwner());
    }

    public List getOwners() {
        return owners;
    }
}

public class Main {
    public static void main (String[] args) {
        Factory factory = new IDCardFactory();
        Product card1 = factory.create("小明");
        Product card2 = factory.create("小红");
        Product card3 = factory.create("小刚");
        card1.use();
        card2.use();
        card3.use();
    }
}