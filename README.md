####discounts application

This app computes net payable given bill item line and amount per item.

### Prerequisites

- Java 8 and above
- Maven
- swagger
- Postman

### Installing

######Checkout the project from git

```
git clone https://github.com/charbelfannoun/retail-app-services.git
```

######To run the application
```
mvn spring-boot:run
```

######To run tests

```
mvn clean install
```


### Postman 
######Request:
```
{
"id":"2",
"billNumber":"bill1222",
"dateBill":"2020-01-01",
"customerId":1,
"productLines":[
	{"id":"2",
	"desc":"T-shirt",
    "productType":"CLOTHES",	
    "amountPerLine":"500"
	},
		{"id":"2",
	"desc":"T-shirt",
    "productType":"OTHER",	
    "amountPerLine":"500"
	}
	]
}
```
######Response:
```
{
    "netPayable": 665.0,
    "totalPayable": 1000.0
}
```
### API documentation
http://localhost:8184/swagger-ui.html


### Sonar results



### Authors

Salim rahal


