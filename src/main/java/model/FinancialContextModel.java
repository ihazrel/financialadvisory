package model;

import java.util.ArrayList;
import java.util.List;

public class FinancialContextModel {

    public static class SummaryRow {
        private String label;
        private int count;
        private double incomeTotal;
        private double expenseTotal;
        private double netTotal;

        public SummaryRow() {}

        public SummaryRow(String label, int count, double incomeTotal, double expenseTotal) {
            this.label = label;
            this.count = count;
            this.incomeTotal = incomeTotal;
            this.expenseTotal = expenseTotal;
            this.netTotal = incomeTotal - expenseTotal;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public double getIncomeTotal() {
            return incomeTotal;
        }

        public void setIncomeTotal(double incomeTotal) {
            this.incomeTotal = incomeTotal;
        }

        public double getExpenseTotal() {
            return expenseTotal;
        }

        public void setExpenseTotal(double expenseTotal) {
            this.expenseTotal = expenseTotal;
        }

        public double getNetTotal() {
            return netTotal;
        }

        public void setNetTotal(double netTotal) {
            this.netTotal = netTotal;
        }
    }

    private int transactionCount;
    private double incomeTotal;
    private double expenseTotal;
    private double netTotal;
    private String scopeDescription;
    private String budgetSummary;
    private List<SummaryRow> departmentSummaries = new ArrayList<>();
    private List<SummaryRow> statusSummaries = new ArrayList<>();
    private List<SummaryRow> categorySummaries = new ArrayList<>();
    private List<SummaryRow> paymentMethodSummaries = new ArrayList<>();
    private List<String> recentTransactions = new ArrayList<>();

    public int getTransactionCount() {
        return transactionCount;
    }

    public void setTransactionCount(int transactionCount) {
        this.transactionCount = transactionCount;
    }

    public double getIncomeTotal() {
        return incomeTotal;
    }

    public void setIncomeTotal(double incomeTotal) {
        this.incomeTotal = incomeTotal;
    }

    public double getExpenseTotal() {
        return expenseTotal;
    }

    public void setExpenseTotal(double expenseTotal) {
        this.expenseTotal = expenseTotal;
    }

    public double getNetTotal() {
        return netTotal;
    }

    public void setNetTotal(double netTotal) {
        this.netTotal = netTotal;
    }

    public String getScopeDescription() {
        return scopeDescription;
    }

    public void setScopeDescription(String scopeDescription) {
        this.scopeDescription = scopeDescription;
    }

    public String getBudgetSummary() {
        return budgetSummary;
    }

    public void setBudgetSummary(String budgetSummary) {
        this.budgetSummary = budgetSummary;
    }

    public List<SummaryRow> getDepartmentSummaries() {
        return departmentSummaries;
    }

    public void setDepartmentSummaries(List<SummaryRow> departmentSummaries) {
        this.departmentSummaries = departmentSummaries;
    }

    public List<SummaryRow> getStatusSummaries() {
        return statusSummaries;
    }

    public void setStatusSummaries(List<SummaryRow> statusSummaries) {
        this.statusSummaries = statusSummaries;
    }

    public List<SummaryRow> getCategorySummaries() {
        return categorySummaries;
    }

    public void setCategorySummaries(List<SummaryRow> categorySummaries) {
        this.categorySummaries = categorySummaries;
    }

    public List<SummaryRow> getPaymentMethodSummaries() {
        return paymentMethodSummaries;
    }

    public void setPaymentMethodSummaries(List<SummaryRow> paymentMethodSummaries) {
        this.paymentMethodSummaries = paymentMethodSummaries;
    }

    public List<String> getRecentTransactions() {
        return recentTransactions;
    }

    public void setRecentTransactions(List<String> recentTransactions) {
        this.recentTransactions = recentTransactions;
    }

    public String toPromptText() {
        StringBuilder builder = new StringBuilder();
        builder.append("Financial data scope: ").append(nullToDash(scopeDescription)).append('\n');
        builder.append("Total transactions: ").append(transactionCount).append('\n');
        builder.append("Income total: ").append(formatAmount(incomeTotal)).append('\n');
        builder.append("Expense total: ").append(formatAmount(expenseTotal)).append('\n');
        builder.append("Net position: ").append(formatAmount(netTotal)).append('\n');
        builder.append("Budget summary: ").append(nullToDash(budgetSummary)).append('\n');

        appendRows(builder, "Department summaries", departmentSummaries);
        appendRows(builder, "Status summaries", statusSummaries);
        appendRows(builder, "Category summaries", categorySummaries);
        appendRows(builder, "Payment method summaries", paymentMethodSummaries);

        builder.append("Recent transactions:\n");
        if (recentTransactions.isEmpty()) {
            builder.append("- None\n");
        } else {
            for (String transaction : recentTransactions) {
                builder.append("- ").append(transaction).append('\n');
            }
        }

        return builder.toString();
    }

    private void appendRows(StringBuilder builder, String title, List<SummaryRow> rows) {
        builder.append(title).append(":\n");
        if (rows == null || rows.isEmpty()) {
            builder.append("- None\n");
            return;
        }
        for (SummaryRow row : rows) {
            builder.append("- ").append(nullToDash(row.getLabel()))
                    .append(": count=").append(row.getCount())
                    .append(", income=").append(formatAmount(row.getIncomeTotal()))
                    .append(", expense=").append(formatAmount(row.getExpenseTotal()))
                    .append(", net=").append(formatAmount(row.getNetTotal()))
                    .append('\n');
        }
    }

    private String formatAmount(double amount) {
        return String.format("%.2f", amount);
    }

    private String nullToDash(String value) {
        return value == null || value.trim().isEmpty() ? "-" : value.trim();
    }
}
