package com.fireal99.NestedClasses;

import org.junit.jupiter.api.Test;

public class NestedClassesTest {
    @Test
    public void testEnclosingWithStaticNestedClass() {
        EnclosingWithStaticNestedClass.StaticNestedClass xd = new EnclosingWithStaticNestedClass.StaticNestedClass();
        xd.publicRun();
        xd.protectedRun();
        xd.packagePrivateRun();

        EnclosingWithStaticNestedClass.StaticNestedClass.staticRun();

        EnclosingWithStaticNestedClass enclosing = new EnclosingWithStaticNestedClass();
        /// enclosing.StaticNestedClass
        enclosing.run();
    }

    @Test
    public void testEnclosingWithNonStaticNestedClass() {
        EnclosingWithNonStaticNestedClass enclosingWithNonStaticNestedClass = new EnclosingWithNonStaticNestedClass();
        EnclosingWithNonStaticNestedClass.InnerClass inner = enclosingWithNonStaticNestedClass.new InnerClass();
        inner.x = 8;

        System.out.println(inner.x);
    }

    @Test
    public void testShadowing() {
        Shadowing outer = new Shadowing();
        Shadowing.InnerClass inner = outer.new InnerClass();
        inner.run();
    }
}
