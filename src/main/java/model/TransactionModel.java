package model;

import java.util.Date;

public class TransactionModel {

    private Integer transactionId;
    private String name;
    private String description;
    private String invoiceNo;
    private String payer;
    private String payee;
    private Integer categoryId;
    private Integer departmentId;
    private String transactionType;
    private String paymentMethod;
    private double totalAmount;
    private String currency;
    private Date dateTransaction;
    private String status;
    private Integer createdBy;
    private Integer verifiedBy;

    public TransactionModel() {}

    public TransactionModel(Integer transactionId,
                            String name,
                            String description,
                            String invoiceNo,
                            String payer,
                            String payee,
                            Integer categoryId,
                            Integer departmentId,
                            String transactionType,
                            String paymentMethod,
                            double totalAmount,
                            String currency,
                            Date dateTransaction,
                            String status,
                            Integer createdBy,
                            Integer verifiedBy) {

        this.transactionId = transactionId;
        this.name = name;
        this.description = description;
        this.invoiceNo = invoiceNo;
        this.payer = payer;
        this.payee = payee;
        this.categoryId = categoryId;
        this.departmentId = departmentId;
        this.transactionType = transactionType;
        this.paymentMethod = paymentMethod;
        this.totalAmount = totalAmount;
        this.currency = currency;
        this.dateTransaction = dateTransaction;
        this.status = status;
        this.createdBy = createdBy;
        this.verifiedBy = verifiedBy;
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

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public String getPayer() {
        return payer;
    }

    public void setPayer(String payer) {
        this.payer = payer;
    }

    public String getPayee() {
        return payee;
    }

    public void setPayee(String payee) {
        this.payee = payee;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getVerifiedBy() {
        return verifiedBy;
    }

    public void setVerifiedBy(Integer verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
}