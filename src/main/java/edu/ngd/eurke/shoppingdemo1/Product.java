//package edu.ngd.eurke.shoppingdemo1;
//
//public class Product {
//    private Long id;
//    private String name;
//    private Double price;
//    private String description;
//    private String imageUrl;
//
//    // 构造函数
//    public Product() {}
//
//    public Product(Long id, String name, Double price, String description, String imageUrl) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.description = description;
//        this.imageUrl = imageUrl;
//    }
//
//
//    // Getter 和 Setter
//    public Long getId() { return id; }
//    public void setId(Long id) { this.id = id; }
//
//    public String getName() { return name; }
//    public void setName(String name) { this.name = name; }
//
//    public Double getPrice() { return price; }
//    public void setPrice(Double price) { this.price = price; }
//
//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }
//
//    public String getImageUrl() { return imageUrl; }
//    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
//}

package edu.ngd.eurke.shoppingdemo1;

public class Product {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String imageUrl;

    // 无参构造器
    public Product() {}

    // 新增：仅 name 和 price 的构造器（用于 Controller 初始化示例数据）
    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
        // 其他字段可留空或设默认值
        this.description = "";
        this.imageUrl = "";
    }

    // 原有的五参构造器
    public Product(Long id, String name, Double price, String description, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // Getter 和 Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public static class CartItem {
        private String name;
        private Double price;
        private Integer quantity;

        public CartItem(String name, Double price, Integer quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        // Getters and Setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }

        public Double getPrice() { return price; }
        public void setPrice(Double price) { this.price = price; }

        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }

        public Double getSubtotal() {
            return price * quantity;
        }
    }
}