package model;

import java.util.Date;

public class TransactionModel {

    private Integer transactionId;
    private String transactionName;
    private String transactionDescription;
    private String transactionInvoiceNo;
    private String transactionPayer;
    private String transactionPayee;
    private Integer categoryId;
    private Integer departmentId;
    private String transactionType;
    private String transactionPaymentMethod;
    private double totalAmount;
    private String transactionCurrency;
    private Date dateTransaction;
    private String transactionStatus;
    private Integer createdBy;
    private Integer verifiedBy;

    // Public constructor
    public TransactionModel() {
    }

    // Full constructor
    public TransactionModel(Integer transactionId,
                            String transactionName,
                            String transactionDescription,
                            String transactionInvoiceNo,
                            String transactionPayer,
                            String transactionPayee,
                            Integer categoryId,
                            Integer departmentId,
                            String transactionType,
                            String transactionPaymentMethod,
                            double totalAmount,
                            String transactionCurrency,
                            Date dateTransaction,
                            String transactionStatus,
                            Integer createdBy,
                            Integer verifiedBy) {

        this.transactionId = transactionId;
        this.transactionName = transactionName;
        this.transactionDescription = transactionDescription;
        this.transactionInvoiceNo = transactionInvoiceNo;
        this.transactionPayer = transactionPayer;
        this.transactionPayee = transactionPayee;
        this.categoryId = categoryId;
        this.departmentId = departmentId;
        this.transactionType = transactionType;
        this.transactionPaymentMethod = transactionPaymentMethod;
        this.totalAmount = totalAmount;
        this.transactionCurrency = transactionCurrency;
        this.dateTransaction = dateTransaction;
        this.transactionStatus = transactionStatus;
        this.createdBy = createdBy;
        this.verifiedBy = verifiedBy;
    }

    // Getters and Setters

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionName() {
        return transactionName;
    }

    public void setTransactionName(String transactionName) {
        this.transactionName = transactionName;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public String getTransactionInvoiceNo() {
        return transactionInvoiceNo;
    }

    public void setTransactionInvoiceNo(String transactionInvoiceNo) {
        this.transactionInvoiceNo = transactionInvoiceNo;
    }

    public String getTransactionPayer() {
        return transactionPayer;
    }

    public void setTransactionPayer(String transactionPayer) {
        this.transactionPayer = transactionPayer;
    }

    public String getTransactionPayee() {
        return transactionPayee;
    }

    public void setTransactionPayee(String transactionPayee) {
        this.transactionPayee = transactionPayee;
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

    public String getTransactionPaymentMethod() {
        return transactionPaymentMethod;
    }

    public void setTransactionPaymentMethod(String transactionPaymentMethod) {
        this.transactionPaymentMethod = transactionPaymentMethod;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCurrency() {
        return transactionCurrency;
    }

    public void setCurrency(String currency) {
        this.transactionCurrency = currency;
    }

    public Date getDateTransaction() {
        return dateTransaction;
    }

    public void setDateTransaction(Date dateTransaction) {
        this.dateTransaction = dateTransaction;
    }
    
    public String getStatus() {
		return transactionStatus;
	}
    
    public void setStatus(String status) {
    	this.transactionStatus = status;
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