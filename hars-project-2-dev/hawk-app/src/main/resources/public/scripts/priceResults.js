			/* Raymond's Code */
			let spanFlights = document.getElementById("spanFlights");
			let backBtn = document.getElementById("backBtn");
			backBtn.addEventListener("click", function() {
				history.back();
			});
			
			/* Ashar's Code */
			
			var body = document.getElementById("ok");
			var form = document.getElementById("form");
			var button = document.getElementById("button");
			console.log(document.cookie);
			var cookieValue = document.cookie
							  .split('; ')
							  .find(row => row.startsWith('flight='))
							  .split('=')[1];
			var flight_search_offer = JSON.parse(cookieValue);
			console.log(flight_search_offer);
			flight_search_offer.disablePricing = true;
			flight_search_offer.paymentCardRequired = false;
			var flight_price_offer = {
				"data": {
					"type": "flight-offers-pricing",
					"flightOffers": [flight_search_offer]
				}
			};
			/* Raymond's Code */
			function createCard() {
			let div1 = document.createElement("div");
					div1.classList.add("card")
					div1.classList.add("text-center")
					div1.classList.add("resultCards")
					
					let div2 = document.createElement("div");
					div2.classList.add("card-header");
					
					let cardHeader = document.createElement("h4");
					cardHeader.classList.add("cardHeader");
					cardHeader.innerHTML = "Flight Order Overview";
					
					div2.appendChild(cardHeader);
					div1.appendChild(div2);
					
					
					let div3 = document.createElement("div");
					div3.classList.add("card-body");
					
					let grand_total = flight_search_offer.price.grandTotal;
					let itineraries = flight_search_offer.itineraries;
					
					for (let itinerary_index in itineraries) {
						let segments = itineraries[itinerary_index].segments;
						let total_duration = itineraries[itinerary_index].duration;
						let departH4 = document.createElement("h4");
						departH4.classList.add("card-title");
						if (itinerary_index == 0) {
							departH4.innerHTML = "Departing Flight";
							div3.appendChild(departH4);			
						} else {
							departH4.innerHTML = "Returning Flight";
							div3.appendChild(departH4);		
						}
						
						
						for (let segment_index in segments) {
							let carrier = segments[segment_index].carrierCode;
							let number = segments[segment_index].number;
							let departure_time = segments[segment_index].departure.at;
							let departure_iata = segments[segment_index].departure.iataCode;
							let arrival_time = segments[segment_index].arrival.at;
							let arrival_iata = segments[segment_index].arrival.iataCode;
							let duration = segments[segment_index].duration;
							
							// Duration Converted
							let newDuration = duration.slice(2);
							let hourIdx = newDuration.indexOf("H");
							let minIdx = newDuration.indexOf("M");
							let hour = newDuration.substring(0, hourIdx);
							let minute = newDuration.substring(hourIdx + 1, minIdx);
							let trueDuration = hour + " " + "Hours and " + minute + " Minutes";
							
							// Departing Converted
							let timeIdx = departure_time.indexOf("T");
							let departDate = departure_time.substring(0, timeIdx);
							let departMilitaryTime = departure_time.substring(timeIdx + 1);
							
							function convertTime(time, date) {
								time = time.split(':');
								let hours = Number(time[0]);
								let minutes = Number(time[1]);
								let seconds = Number(time[2]);
								// calculate
								let timeValue;
								
								if (hours > 0 && hours <= 12) {
								  timeValue= "" + hours;
								} else if (hours > 12) {
								  timeValue= "" + (hours - 12);
								} else if (hours == 0) {
								  timeValue= "12";
								}
								timeValue += (minutes < 10) ? ":0" + minutes : ":" + minutes;  // get minutes
								timeValue += (hours >= 12) ? " P.M." : " A.M."; 
								let newTime = date + " at " + timeValue;	
								return newTime;
							}
							
							let newDepart = convertTime(departMilitaryTime, departDate);
							
							// Arival Converted
							let arriveTimeIdx = arrival_time.indexOf("T");
							let arriveDate = arrival_time.substring(0, arriveTimeIdx);
							let arriveMilitaryTime = arrival_time.substring(arriveTimeIdx + 1);
							let newArrive = convertTime(arriveMilitaryTime, arriveDate);
							

							let div4 = document.createElement("div");
							div4.classList.add("departingDiv");
							
							let fromIataH5 = document.createElement("h5");
							fromIataH5.classList.add("fromIata");
							fromIataH5.innerHTML = `${departure_iata} `;
							
							let iconDep = document.createElement("i");
							iconDep.classList.add("fas");
							iconDep.classList.add("fa-plane-departure");
							
							fromIataH5.appendChild(iconDep);
							div4.appendChild(fromIataH5);
							
							let hr = document.createElement("hr");
							div4.appendChild(hr);
							
							let ariveIataH5 = document.createElement("h5");
							ariveIataH5.classList.add("ariveIata");
							
							let iconAr = document.createElement("i");
							iconAr.classList.add("fas");
							iconAr.classList.add("fa-plane-arrival");
							ariveIataH5.innerHTML = `${arrival_iata} `;
							ariveIataH5.appendChild(iconAr);
							div4.appendChild(ariveIataH5);
							
							let carrierH6 = document.createElement("h6");
							carrierH6.classList.add("departBody");
							carrierH6.classList.add("carInfo");
							carrierH6.innerHTML = `Carrier Information: ${carrier}${number}`;
							div4.appendChild(carrierH6);
							
							let durationP = document.createElement("p");
							durationP.classList.add("departBody");
							durationP.innerHTML = `Duration of Flight: ${trueDuration}`;
							div4.appendChild(durationP);
							
							let divRow = document.createElement("div");
							divRow.classList.add("row");
							divRow.classList.add("rowIn");
							
							let div5 = document.createElement("div");
							div5.classList.add("col-6");
							div5.classList.add("departInfo");
							
							let departP = document.createElement("p");
							departP.classList.add("departBody");
							departP.innerHTML = `Departing from ${departure_iata} on ${newDepart}`;
							div5.appendChild(departP);
							divRow.appendChild(div5);
							
							let div6 = document.createElement("div");
							div6.classList.add("col-6");
							div6.classList.add("arriveInfo");
							
							let ariveP = document.createElement("p");
							ariveP.classList.add("departBody");
							ariveP.innerHTML = `Arriving at ${arrival_iata} on ${newArrive}`;
							div6.appendChild(ariveP);
							divRow.appendChild(div6);
							div4.appendChild(divRow);
							div3.appendChild(div4);
							
							let indexCheck = segment_index + 1;
							if(indexCheck <= segments.length) {
								let connH5 = document.createElement("h5");
								connH5.classList.add("conn");
								connH5.innerHTML = "Connecting Flight";
								div3.appendChild(connH5);
							}
						}
					}
					let grandH5 = document.createElement("h5");
						grandH5.classList.add("grandTotal");
						grandH5.innerHTML = `Grand Total: $${grand_total}`;
						div3.appendChild(grandH5);
						div1.appendChild(div3);
						spanFlights.appendChild(div1);	
				}
			/* Ashar's Code */
			async function FlightOffersPrice() {
				var request_access = await RequestAccessToken();
				
				var url = "https://test.api.amadeus.com/v1/shopping/flight-offers/pricing?forceClass=false";
				var access_token = request_access.access_token;
				var response = await fetch(url, {
					method: 'POST', 
					headers: {
						'Authorization': 'Bearer ' + access_token,
						'Content-Type': 'application/json'
					}, 
					body: JSON.stringify(flight_price_offer)
				});
				var json = await response.json();
				console.log(json);
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
			
			window.onload = function() {
				createCard();
			};
