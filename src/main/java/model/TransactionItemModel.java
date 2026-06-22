package model;

public class TransactionItemModel {

    private Integer transactionItemId;
    private Integer transactionId;
    private String name;
    private String description;
    private double unitPrice;
    private Integer quantity;

    public TransactionItemModel() {}

    public TransactionItemModel(Integer transactionItemId,
                                Integer transactionId,
                                String name,
                                String description,
                                double unitPrice,
                                Integer quantity) {

        this.transactionItemId = transactionItemId;
        this.transactionId = transactionId;
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.quantity = quantity;
    }

    public Integer getTransactionItemId() {
        return transactionItemId;
    }

    public void setTransactionItemId(Integer transactionItemId) {
        this.transactionItemId = transactionItemId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}