package com.fireal99;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.Stream;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        String xd = "";
        try(Reource r = new Reource()) {
            throw new RuntimeException();
        } catch (Exception e) {
            System.out.println(Arrays.toString(e.getSuppressed()));
            System.out.println("Caught "+e);
        }


    }
}
