package com.fireal99.NestedClasses;

public class Shadowing {

    int a = 1;
    static int b = 2;

    public class InnerClass {
        int a = 3;
        static final int b = 4;

        public void run() {
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("Shadowing.this.a = " + Shadowing.this.a);
            System.out.println("Shadowing.b = " + Shadowing.b);
            System.out.println("Shadowing.this.b = " + Shadowing.this.b);
        }
    }
}