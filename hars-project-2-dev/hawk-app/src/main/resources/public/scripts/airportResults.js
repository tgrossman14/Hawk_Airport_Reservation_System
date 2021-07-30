			/* Ashar's Date Code */
			var today = new Date();
            var dd = today.getDate();
            var mm = today.getMonth()+1; //January is 0!
            var yyyy = today.getFullYear();
             if(dd<10){
                    dd='0'+dd
                } 
                if(mm<10){
                    mm='0'+mm
                } 

            today = yyyy+'-'+mm+'-'+dd;
			
			// Raymond's Code
			const oneRadio = document.getElementById("inlineRadio1");
			const roundRadio = document.getElementById("inlineRadio2");
			const returnDate = document.getElementById("returnDate");
			const returnDateInput = document.getElementById("returnDateInput");
			document.getElementById("departureDate").setAttribute("min", today);
            returnDateInput.setAttribute("min", today);
			
			let backBtn = document.getElementById("backBtn");
			backBtn.addEventListener("click", function() {
				history.back();
			});
			
			oneRadio.addEventListener("click", function () {
			  returnDate.classList.add("invisible");
			  returnDateInput.removeAttribute("name");
			  console.log(returnDate);
			});
			
			roundRadio.addEventListener("click", function () {
			  returnDate.classList.remove("invisible");
			  returnDateInput.setAttribute("name", "returnDate");
			});
			
			let fromSelect = document.getElementById("originLocationCode");
			let departSelect = document.getElementById("destinationLocationCode");
			
			// Ashar's Code
			var urlParams = new URLSearchParams(window.location.search);
			var latitude_query = urlParams.get('latitude');
			var longitude_query = urlParams.get('longitude');
			var radius_query = urlParams.get('radius');
			var destination_latitude_query = urlParams.get('destination-latitude');
			var destination_longitude_query = urlParams.get('destination-longitude');
			var destination_radius_query = urlParams.get('destination-radius')
			console.log(latitude_query, longitude_query, radius_query, destination_latitude_query, destination_longitude_query, destination_radius_query);
			
			var form = document.getElementById("form");
			var button = document.getElementById("button");
			var modal = document.getElementById("myModal");
			
			var airports = null;
			async function AirportsNearest(latitude_query, longitude_query, radius_query, current=true) {
				var request_access = await RequestAccessToken();
				
				var url = "https://test.api.amadeus.com/v1/reference-data/locations/airports?latitude=" + latitude_query + "&longitude=" + longitude_query + "&radius=" + radius_query;
				var access_token = request_access.access_token;
				var response = await fetch(url, {
					method: 'GET', 
					headers: {
						'Authorization': 'Bearer ' + access_token
					}
				});
				var json = await response.json();
				airports = json.data;
				console.log(airports);
				if (current) {
					for (let index in airports) {
						let option = document.createElement("option");
						//option.setAttribute("name", "originLocationCode");
						option.setAttribute("id", `${airports[index].iataCode}`);
						option.setAttribute("value", `${airports[index].iataCode}`);
						option.setAttribute("for", `${airports[index].iataCode}`);
						option.innerHTML = `${airports[index].iataCode}, ${airports[index].address.cityCode}`;
						fromSelect.appendChild(option);
					}
				} else {
					for (let index in airports) {
						let option = document.createElement("option");
						//option.setAttribute("name", "originLocationCode");
						option.setAttribute("id", `${airports[index].iataCode}`);
						option.setAttribute("value", `${airports[index].iataCode}`);
						option.setAttribute("for", `${airports[index].iataCode}`);
						option.innerHTML = `${airports[index].iataCode}, ${airports[index].address.cityCode}`;
						departSelect.appendChild(option);
					}
				}
			}
			
			async function RequestAccessToken() {
				var request_access = await fetch('https://test.api.amadeus.com/v1/security/oauth2/token', {
				    method: 'POST',
				    body: new URLSearchParams({
				        'grant_type': 'client_credentials',
				        'client_id': 'OGORt5hAGLuW5GzewaxGCPFVNrKiB0WV',
				        'client_secret': 'ik6GIT6Y7ui09KcA'
				    })
				});
				var access_json = await request_access.json();
				return access_json;
			}
			
			window.onload = function () {
				AirportsNearest(latitude_query, longitude_query, radius_query);
				AirportsNearest(destination_latitude_query, destination_longitude_query, destination_radius_query, false);
			}
