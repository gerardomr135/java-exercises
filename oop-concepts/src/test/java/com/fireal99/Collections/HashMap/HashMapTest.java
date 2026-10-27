package com.fireal99.Collections.HashMap;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HashMapTest {
    @Test
    public void objectsAsKeys() throws CloneNotSupportedException {
        HashMap<Product,String> map = new HashMap<>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");

        map.put(eBike, "E-Bike");

        map.put(roadBike, "Road bike");
        System.out.println(map.putIfAbsent(roadBike, "Road bike"));

        map.forEach( (k,v) -> {
            System.out.println(k + " : " + v);
        });

        System.out.println("After merge: " + map.merge(roadBike, " Black", String::concat));

        Product eBikeClone = (Product) eBike.clone();
        assertTrue(map.containsKey(eBikeClone));

        eBikeClone = new Product("E-Bike", "A bike with a battery");
        assertTrue(map.containsKey(eBikeClone));
    }

    @Test
    public void testHashMap() {
        Map<String, Product> productsByName = new HashMap<String, Product>();

        Product eBike = new Product("E-Bike", "A bike with a battery");
        Product roadBike = new Product("Road bike", "A bike for competition");
        productsByName.put(eBike.getName(), eBike);
        productsByName.put(roadBike.getName(), roadBike);

        // Get
        Product nextPurchase = productsByName.get("E-Bike");
        assertEquals("A bike with a battery", nextPurchase.getDescription());

        nextPurchase = productsByName.get("Car");
        assertNull(nextPurchase);

        // Null as the Key
        Product defaultProduct = new Product("Chocolate", "At least buy chocolate");
        productsByName.put(null, defaultProduct);

        nextPurchase = productsByName.get(null);
        assertEquals("At least buy chocolate", nextPurchase.getDescription());

        // Remove Value
        productsByName.remove("E-Bike");
        assertNull(productsByName.get("E-Bike"));

        // Check If a Key or Value Exists in the Map
        assertFalse(productsByName.containsKey("E-Bike"));
        assertFalse(productsByName.containsValue(eBike));

        // Iterating
        for(String key : productsByName.keySet()) {
            Product product = productsByName.get(key);
            assertNotNull(product);
        }

        for(Map.Entry<String, Product> entry : productsByName.entrySet()) {
            Product product =  entry.getValue();
            String key = entry.getKey();
            //do something with the key and value
            System.out.println("Key: " + key + " Value: " + product);
        }

        List<Product> products = new ArrayList<>(productsByName.values());
        for (Product product : products) {
            System.out.println(product);
        }
    }
}
