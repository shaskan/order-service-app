## URL's and function
Use Any Rest clients(REST API CLIENT,POSTMAN CLIENT etc) to access the REST end points of the application 

## Functional Flow

1. Once order is placed, status in DB would be B ---> Booked and request will be published through RabbitMq (if profile is enabled)
2. Inventory module will push the Order back in response based on the following condition
    <br>1. If order is processed successfully, status will be updated as S ---> Success.
    <br>2. If the quantity in inventory is less than what is placed in order then the order placed will be updated as E --->Error.
    <br>3. Once the order is cancelled it will be C --->Cancelled.

## Requests
### Post/Create
1.http://localhost:8080/api/order and POST Request method helps you to add a new order and persist the data in mysql db
    For example your Json input should as 
    
REQUEST BODY:		

    {	
        "client_ID": 1,
        "productName":"CELO",
        "locationName":"INDIA",
        "external_ref":"local",
        "quantity":2
    }

### Get/Read
2. http://localhost:8080/api/order/{id} and GET Request method helps to fetch the persisted data from mysql db with respect to id
  	For example the response body :
  		
		{
            "client_ID": 1,
            "productName": "CELO",
            "locationName": "INDIA",
            "status": "C",
            "external_ref": "ex",
            "quantity": 2,
            "order_ID": 1
		}
		
3. http://localhost:8080/api/order/getByProductName/{productName} and GET Request method helps to fetch the persisted data from mysql db with respect to productName.

4.  http://localhost:8080/api/order/getByLocationName/{locationName} and GET Request method helps to fetch the persisted data from mysql db with respect to locationName.

5. http://localhost:8080/api/order/getByStatus/{status} and GET Request method helps to fetch the persisted data from mysql db with respect to order status.

6. http://localhost:8080/api/order/getByExternalRef/{externalRef} and GET Request method helps to fetch the persisted data from mysql db with respect to order externalRef.

### Put/Update
7. http://localhost:8080/api/order/updateByExternalRef and PUT Request method helps to fetch the persisted data from mysql db with respect to order externalRef.

REQUEST BODY :
    
    {	
        "client_ID": 1,
        "productName":"CELO",
        "locationName":"INDIA",
        "external_ref":"local",
        "quantity":2
    }
	
  		
### Put/Soft Delete
3. http://localhost:8080/api/order/cancelByOrderId and PUT Request method helps to update the data

REQUEST BODY :
    
    {
        "order_ID":34
    } 			
		
## Run MySQL 5.6 in Docker container:
~~~
docker run --name demo-mysql -e MYSQL_ROOT_PASSWORD=sa -d mysql:5.6
~~~
To expose the port 3306
~~~
docker run --name comm_mysql -d -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.7
~~~

Check the log to make sure the server is running OK:
~~~
docker logs demo-mysql
~~~

To access the mysql db instance running in docker to run queries connect using the following:
~~~
docker exec -it test-mysql bash
~~~
~~~
mysql -uroot -psa -h 172.17.0.20 -P 3306
~~~

There is a additional profile defined for RabbitMq, which can enabled by adding "rmq" along with other profile.

## Run application in Docker container

~~~
docker run -p 8080:8080 --name order-app --link demo-mysql:mysql -e "SPRING_PROFILES_ACTIVE=dev,rmq" -d pes/order 
~~~

You can check the log by
~~~
docker logs order-app
~~~

# Running as Spring-boot

~~~
mvn spring-boot:run -Dspring.profiles.active="dev,rmq"
~~~
		
		
		