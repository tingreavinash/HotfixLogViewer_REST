// Create a request variable and assign a new XMLHttpRequest object to it.
var request = new XMLHttpRequest()

// Open a new connection, using the GET request on the URL endpoint
request.open('GET', 'http://localhost:4569/getAllResults?ecpNo=RM_10.0.0.287-1504', true)

request.onload = function() {
	// Begin accessing JSON data here
	var data = JSON.parse(this.response)

	data.forEach(responseObject => {
	  // Log each movie's title
	  console.log(responseObject.count)
	})
}

// Send request
request.send()