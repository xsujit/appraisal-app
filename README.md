# appraisal-app
Enterprise appraisal application which allows employees to submit, comment and vote for appraisals including user privileges.
Display voting results in highcharts, with drill down option to see the voters

* mvn clean package
* docker build -t appraisal-app .
* docker run -p 8080:8080 appraisal-app
* docker run appraisal-app --network=my-network -p 8080:8080

