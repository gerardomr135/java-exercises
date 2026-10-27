# Threads

Multi-module Maven project with Java concurrency exercises.

| Module | Topic |
|---|---|
| `ThreadControl` | Creating threads, `join`, priorities, interrupts, thread states, uncaught exceptions |
| `ThreadPool` | Executors, `Callable` / `Future`, task timing |
| `Synchronization` | Shared state, `volatile`, deadlock (`DealThread`) |
| `ConcurrentCollections` | Producer/consumer with concurrent collections, file search tasks |
| `AdvancedSynchronizers` | `CountDownLatch` auction example, fork/join (`SumOfNumberUsingForkJoin`, `ArraySort`) |

## Build and test

```
mvn clean test
```
