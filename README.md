# travel-api

A Basic CRUD RESTful API built for managing payments and the passengers for whom those payments are made.

Swagger URL - http://localhost:8082/swagger-ui.html

Sample payloads :-

For Passenger :

```
{
		"firstName" : "Jaby",
		"lastName" : "Koay",
		"travelDate" : "2023-10-15",
		"departureTime" : "09:00pm",
		"source" : "Delhi",
		"destination" : "Chennai",
		"isDeleted" : "false"
}
```

For Payment :-

```
{
  	"amount": 5000,
	"cardType" : "Debit",
	"list" : [
		{
			"firstName" : "Prakhar",
			"lastName" : "Shukla",
			"travelDate" : "2022-10-18",
			"departureTime" : "10:30pm",
			"source" : "Bangalore",
			"destination" : "Kanpur",
			"isDeleted" : "false"
		},
		{
			"firstName" : "Pulkit",
			"lastName" : "Goyal",
			"travelDate" : "2022-11-03",
			"departureTime" : "02:30pm",
			"source" : "Bangalore",
			"destination" : "Kanpur",
			"isDeleted" : "false"
		}
	]
}
```
