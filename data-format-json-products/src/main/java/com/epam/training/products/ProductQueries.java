package com.epam.training.products;

import com.epam.training.products.domain.Product;

import java.util.Comparator;
import java.util.List;

public record ProductQueries(List<Product> products) {

    public List<String> getSubCategoriesOf(String categoryName) {
        return products
                .stream()
                .filter((Product product) ->
                        (product.hasCategory() && product.hasSubCategory()) && product.getCategory().equals(categoryName))
                .map(Product::getSubCategory)
                .distinct()
                .toList();
    }

    public List<Product> getSweetsWherePriceIsLowerThan(double price) {
        return products.stream()
                .filter(product -> product.hasCategory() && product.getCategory().equals("sweets"))
                .filter(product -> product.getPrice() < price)
                .toList();
    }

    public Product getTheMostExpensiveProduct() {
        var productOpt = products
                .stream()
                .max(Comparator.comparingDouble(Product::getPrice));

        return productOpt.orElse(null);
    }
}
