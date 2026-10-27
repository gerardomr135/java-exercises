# java classes

base package should be: com.epam.training.movie

# Java Coding

1. Use lombok to define constructor, getter, setter, toString, equals and hashCode.
2. Avoid using short variable names (2-3 characters), assign meaningful name to variables.
3. Implement defensive programming: methods should check arguments.
   If the argument is not valid, throw IllegalArgumentException with meaningful message.
4. Avoid unnecessary null checks.
   - Do not check methods return values for null.
   - Do not check null if you get element from an array or collection.
5. Catching exceptions
- Never ignore Exceptions.
- Wrap checked exceptions to runtime exceptions.

# Spring and dependency injection

1. Define interface for each service. Inject the service via the interface.
2. Use constructor injection for mandatory dependencies.

# Maven

- Project dependency versions must be extracted to properties section
- Dependencies that are defined by spring boot starter, must not define version explicitly.

# Observability
1. Use SLF4J for logging.
2. In controller and service  methods log at the beginning of the method calls.
3. Define LOG variable in controller and service classes with Lombok
