## Example of Spring boot 3.3.5 (Java 21) project with using Multiple data sources (different type) 
- implemented 2 data sources: DB2 and Postgresql
- implemented 2 migration scripts for each data source.
 
### Test Db initialization:
- Start `Docker-compose` file as result after execution we will get 2 started
Docker containers with `DB2` and `Postgresql` for using in our application.

### Project structure:
```
src
└───main
    ├───java
    │   └───dev.semeshin.multids
    │       ├───configuration
    │       │   ├───datasources (configs for data sources 1 and 2)
    │       │   └───liquibase (configs for sources 1 and 2)
    │       ├───entity
    │       │   ├───datasource1 (entity classes for source 1)
    │       │   └───datasource2 (entity classes for source 2)
    │       ├───repository
    │       │   ├───datasource1 (repo interfaces for source 1)
    │       │   └───datasource2 (repo interfaces for source 2)
    │       └───MultiDatasourceApplication.class (main application started class)
    └───resources
        ├───db.changelog
        │   ├───datasource1 (liqubase scripts for source 1)
        │   └───datasource2 (liqubase scripts for source 2)
        └───application.yml
```


