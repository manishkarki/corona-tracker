##Corona-tracker periodically monitors the corona record in last 24 hours

To get kick-started, do following: 

> mvn clean install -DskipTest

> docker build -t corona-tracker . 
## grab the image id and then run:
docker run -d -p 8080:8080 {imageId}

##open in the browser
localhost:8080