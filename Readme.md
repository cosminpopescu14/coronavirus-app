This is a simple web application in Java and Spring Boot that tracks corona virus cases.
The data source is a csv file form this
https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv

In athe application the data is presented in a datatable, 
with pagination and search functions and a basic graph
using https://www.highcharts.com/

The requirements are 
* Java 11 or 13
* MySQL for saving data
* Maven

All you need to do, is to download/clone the repo and import the project into your favourite ide
and leave it to do the maven imports.

You can buld the app using `mvn clean install` and start the app with `java -jar jar_created_by_maven.jar`

Thank you for your interest in this app :)

Statistics for Romania are in progress.
I would like to thank http://geo-spatial.org/ for the apis created :D

https://covid19.geo-spatial.org/dashboard/about
<br>
If you like it, you can star it :D