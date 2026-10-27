package com.fireal99.NestedClasses;

public class EnclosingWithStaticNestedClass {
    private static String className = "enclosing";

    private String nonStaticField = "nonStaticField";

    // - As with static members, these belong to their enclosing class, and not to an instance of the class
    // - They can have all types of access modifiers in their declaration
    // - They only have access to static members in the enclosing class
    // - They can define both static and non-static members
    public static class StaticNestedClass {
        private void privateRun() {
            System.out.println("privateRun");
            className = "enclosing";
            // System.out.println(nonStaticField);
        }

        public void publicRun() {
            System.out.println("publicRun");
        }

        protected void protectedRun() {
            System.out.println("protectedRun");
        }

        void packagePrivateRun() {
            System.out.println("packagePrivateRun");
        }

        public static void staticRun() {
            System.out.println("staticRun");
        }
    }

    public void run() {
        System.out.println("EnclosingWithStaticNestedClass.run()");
        EnclosingWithStaticNestedClass.StaticNestedClass nested = new EnclosingWithStaticNestedClass.StaticNestedClass();
        nested.privateRun();
        nested.publicRun();
        nested.protectedRun();
        nested.packagePrivateRun();
        System.out.println(className);
        System.out.println(nonStaticField);
    }
}
