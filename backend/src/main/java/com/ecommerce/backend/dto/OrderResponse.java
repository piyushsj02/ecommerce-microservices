public class OrderResponse {

    private Long id;
    private int quantity;
    private String orderDate;

    private String productName;
    private double price;

    // constructor
    public OrderResponse(Long id, int quantity, String orderDate,
                         String productName, double price) {
        this.id = id;
        this.quantity = quantity;
        this.orderDate = orderDate;
        this.productName = productName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // getters
    
}