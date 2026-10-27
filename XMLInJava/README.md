# XMLInJava

Exercise on reading customer data in different formats (XML and JSON) from Java and measuring how long each approach takes.

- `dataprovider/DataProvider` loads customers (small / medium / large data sets).
- `model/Customer` is the data model.
- `utilities/Stopwatch` times each parsing strategy.

**Note:** `DataProvider.DATADIR` expects the sample data files (`customers.xml`, `customers.json`, `customers.xsd`, ...) in `../DataProvider/data/`. They are not included in this repository.

## Build

```
mvn clean package
```
