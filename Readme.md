This is a simple web application in Java and Spring Boot that tracks corona virus cases.
The data source is a csv file form this
https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_19-covid-Confirmed.csv

At the moment there are pages for
* Global situation. Updated daily at 7 AM (GMT+1)
* Evolution in Romania
* A basic chart for global cases

The requirements are 
* Java 11 or 13 with Spring Boot
* MySQL for saving data
* Maven

All you need to do, is to download/clone the repo and import the project into your favourite ide
and leave it to do the maven imports.

You can build the app using `mvn clean install` and start the app with `java -jar jar_created_by_maven.jar` or
`nohup java -jar jar_created_by_maven.jar &` for running in the background. 

Thank you for your interest in this app :)

The application can be viewed at this link <br/>
http://chestiiautomate.ro:8070/

<strike>Statistics for Romania are in progress.</strike></br>
They are done! :) <br/>
I would like to thank http://geo-spatial.org/ for the apis created :D

https://covid19.geo-spatial.org/dashboard/about
<br>
If you like it, you can star it :D