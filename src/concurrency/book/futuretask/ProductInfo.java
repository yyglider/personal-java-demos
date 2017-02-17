package concurrency.book.futuretask;

/**
 * 产品信息。
 *
 * @author Bert Lee 2015年08月16日 18:38
 */
public class ProductInfo {
    private long id;
    private String name;
    private String description;

    ProductInfo(){
        id = 111L;
        name = "test";
        description = "test description";
    }

    @Override
    public String toString() {
        return "ProductInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    private long getId() {
        return id;
    }

    private void setId(long id) {
        this.id = id;
    }

    private String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private String getDescription() {
        return description;
    }

    private void setDescription(String description) {
        this.description = description;
    }
}
