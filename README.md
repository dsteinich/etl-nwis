# etl\-nwis

[![Build Status](https://travis-ci.org/NWQMC/etl-nwis.svg?branch=master)](https://travis-ci.org/NWQMC/etl-nwis)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9cfcbdbc3cb64ca5bc2f2b90da8f63d7)](https://www.codacy.com/app/usgs_wma_dev/etl-nwis?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=NWQMC/etl-nwis&amp;utm_campaign=Badge_Grade)

ETL Water Quality Data from the NWIS System

This ETL is in the process of being transformed to a Java project using Spring Batch. For the moment we will include the README's for both.

## Spring Batch ETL

### Environment variables
Create an application.yml file in the project directory containing the following (shown are example values - they should match the values you used in creating the etlDB):

```yaml
WQP_DATABASE_ADDRESS: <localhost>
WQP_DATABASE_PORT: <5437>
WQP_DATABASE_NAME: <wqp_db>
WQP_SCHEMA_OWNER_USERNAME: <wqp_core>
WQP_SCHEMA_OWNER_PASSWORD: <changeme>

NWIS_DATABASE_ADDRESS: <localhost>
NWIS_DATABASE_PORT: <5437>
NWIS_DATABASE_NAME: <wqp_db>
NWIS_SCHEMA_OWNER_USERNAME: <nwis_ws_star>
NWIS_SCHEMA_OWNER_PASSWORD: <changeme>

# Currently I have to open a tunnel to the MYSQL db at EROS. That's why
# the address is localhost. The tunnel is opened as follows: ssh -L 1234:localhost:3306 <actual_database_address
MYSQL_DATABASE_ADDRESS: <localhost>
MYSQL_DATABASE_PORT: <1234>
MYSQL_DATABASE_NAME: <nwisweb>
MYSQL_USERNAME: <nwis_user>
MYSQL_PASSWORD: <changeme>
      
WQP_SCHEMA_NAME: <wqp>
ETL_OWNER_USERNAME: <nwis_ws_star>
GEO_SCHEMA_NAME: <nwis>
ETL_DATA_SOURCE_ID: <2>
ETL_DATA_SOURCE: <NWIS>
QWPORTAL_SUMMARY_ETL: <true>
NWIS_OR_EPA: N

```

### Testing
This project contains JUnit tests. Maven can be used to run them (in addition to the capabilities of your IDE).

To run the unit tests of the application use:

```shell
mvn package
```

To additionally start up a Docker database and run the integration tests of the application use:

```shell
mvn verify -DTESTING_DATABASE_PORT=5437 -DTESTING_DATABASE_ADDRESS=localhost -DTESTING_DATABASE_NETWORK=wqpEtlCore -DMYSQL_DATABASE_ADDRESS=localhost -DMYSQL_DATABASE_PORT=5438
```
