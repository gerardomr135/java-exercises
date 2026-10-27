package com.fireal99.Collections.HashMap;

import java.util.List;
import java.util.Objects;

public class Product implements Cloneable {
    private String name;
    private String description;
    private List<String> tags;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<String> getTags() {
        return tags;
    }

    public Product addTagsOfOtherProduct(Product product) {
        this.tags.addAll(product.getTags());
        return this;
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(description, product.description) && Objects.equals(tags, product.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, tags);
    }
}
