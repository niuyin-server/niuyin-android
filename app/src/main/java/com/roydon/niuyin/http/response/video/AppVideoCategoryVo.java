package com.roydon.niuyin.http.response.video;

public class AppVideoCategoryVo {
    /**
     * 分类id
     */
    private Long id;
    /**
     * 分类name
     */
    private String name;

    private String categoryImage;

    public AppVideoCategoryVo(Long id, String name, String categoryImage) {
        this.id = id;
        this.name = name;
        this.categoryImage = categoryImage;
    }

    @Override
    public String toString() {
        return "AppVideoCategoryVo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", categoryImage='" + categoryImage + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryImage() {
        return categoryImage;
    }

    public void setCategoryImage(String categoryImage) {
        this.categoryImage = categoryImage;
    }
}
