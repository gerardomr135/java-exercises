package com.fireal99.NestedClasses;

public class EnclosingWithNonStaticNestedClass {

    // - They are also called inner classes
    // - They can have all types of access modifiers in their declaration
    // - Just like instance variables and methods, inner classes are associated with an instance of the enclosing class
    // - They have access to all members of the enclosing class, regardless of whether they are static or non-static
    // - They can only define non-static members
    public class InnerClass {
        public static Integer x;
    }
}
