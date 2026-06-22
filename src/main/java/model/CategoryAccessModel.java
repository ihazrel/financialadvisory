package model;

public class CategoryAccessModel {

    private Integer categoryAccessId;
    private Integer categoryId;
    private Integer departmentId;

    public CategoryAccessModel() {}

    public CategoryAccessModel(Integer categoryAccessId,
                               Integer categoryId,
                               Integer departmentId) {

        this.categoryAccessId = categoryAccessId;
        this.categoryId = categoryId;
        this.departmentId = departmentId;
    }

    public Integer getCategoryAccessId() {
        return categoryAccessId;
    }

    public void setCategoryAccessId(Integer categoryAccessId) {
        this.categoryAccessId = categoryAccessId;
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
}