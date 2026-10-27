package com.fireal99.Exceptions;

public class Exceptions {
    static class Resource implements AutoCloseable {
        public String run() throws RuntimeException {
            //throw new RuntimeException("Runtime Exception");
            return "xd";
        }

        @Override
        public void close() throws Exception {
            System.out.println("Resource closed");
        }
    }
    public static void throwable() throws RuntimeException {
        throw new RuntimeException("My Exception message");
    }

    public static String handleException() {
        String res;

        try {
            throwable();
        } catch (RuntimeException e) {
            return e.getMessage();
        } finally {
            res = "No exception throw and finally block executed";
        }

        return res;
    }

    public static String handleTryWithResources() {
        String res;

        try(Exceptions.Resource rsc = new Exceptions.Resource()){
            res = rsc.run();
        }catch(Exception e){
            return e.getMessage();
        } finally {
            res = "No exception throw and finally block executed";
        }

        return res;
    }
}
