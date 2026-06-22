package model;

public class CategoryModel {

    private Integer categoryId;
    private String name;
    private boolean generic;
    private Integer parentCategoryId;

    public CategoryModel() {}

    public CategoryModel(Integer categoryId,
                         String name,
                         boolean generic,
                         Integer parentCategoryId) {

        this.categoryId = categoryId;
        this.name = name;
        this.generic = generic;
        this.parentCategoryId = parentCategoryId;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isGeneric() {
        return generic;
    }

    public void setGeneric(boolean generic) {
        this.generic = generic;
    }

    public Integer getParentCategoryId() {
        return parentCategoryId;
    }

    public void setParentCategoryId(Integer parentCategoryId) {
        this.parentCategoryId = parentCategoryId;
    }
}