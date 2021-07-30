var section = document.getElementById("main-section");
var ol = document.getElementById("ol");

async function reservations() {
    var request_access = await RequestAccessToken();
    var access_token = request_access.access_token;
    var url = "http://localhost:5000/api/reservations.json";
    var response = await fetch(url);
    var json = await response.json();
    for (let i = 0; i < json.length; i++) {
        var reservation_id = JSON.parse(json[i].id);
        var reservation = JSON.parse(json[i].reservationJSON);
        var names = JSON.parse(json[i].namesJSON);
        var passengers = names.passengers;
        var ul = document.createElement("ul");
        for (let k = 0; k < passengers.length; k++) {
            var li = document.createElement("li");
            li.innerText = `Passenger ${k+1} is ${passengers[k].firstName} ${passengers[k].lastName}`;
            ul.appendChild(li);
        }
        ol.appendChild(ul);
        
        var li = document.createElement("li");
        var itineraries = reservation.itineraries;
        var grand_total = reservation.price.grandTotal;
        var h3 = document.createElement("h3");
        var a = document.createElement("a");
        a.setAttribute("href", `/api/seatmap-display/${reservation_id}`);
        a.innerText = "Select or edit your seats here!";
        h3.innerText = `Grand Total $${grand_total}`;
        var form = document.createElement("form");
        form.setAttribute("method", "post");
        form.setAttribute("action", `/api/reservations/${reservation_id}`);
        var input = document.createElement("input");
        input.setAttribute("name", `${reservation_id}`);
        input.style.display = "none";
        var button = document.createElement("button").setAttribute("class", "delete_btn");
        button.setAttribute("type", "submit");
        button.innerText = "Delete reservation here (50% refund)";
        form.appendChild(input);
        form.appendChild(button);
        li.appendChild(h3);
        li.appendChild(a);
        li.appendChild(form);
        for (let itinerary_index in itineraries) {
            var div_itinerary = document.createElement("div");
            var segments = itineraries[itinerary_index].segments;
            var h4_duration = document.createElement("h4");
            var total_duration = itineraries[itinerary_index].duration;
            if (itinerary_index == 0) {
                h4_duration.innerText = `Total duration to the destination: ${total_duration}`;
            } else {
                h4_duration.innerText = `Total duration back to arrival: ${total_duration}`;
            }
            div_itinerary.appendChild(h4_duration);
            var ol_segment = document.createElement("ol");
            for (let segment_index in segments) {
                var li_segment = document.createElement("li");
                var carrier = segments[segment_index].carrierCode;
                var number = segments[segment_index].number;
                var duration = segments[segment_index].duration;
                var departure_time = segments[segment_index].departure.at;
                var departure_date = departure_time.split("T")[0];
                
                var url = `https://test.api.amadeus.com/v2/schedule/flights?carrierCode=${carrier}&flightNumber=${number}&scheduledDepartureDate=${departure_date}`;
                var response = await fetch(url, {
                    method: 'GET', 
                    headers: {
                        'Authorization': 'Bearer ' + access_token
                    }
                });
                var status_json = await response.json();
                var status_data = status_json.data[0];
                console.log(status_data)
                departure_status = status_data.flightPoints[0].departure.timings[0].hasOwnProperty("delays") ? "DELAYED" : "ON TIME";
                arrival_status = status_data.flightPoints[1].arrival.timings[0].hasOwnProperty("delays") ? "DELAYED" : "ON TIME";
                var departure_iata = segments[segment_index].departure.iataCode;
                var arrival_time = segments[segment_index].arrival.at;
                var arrival_iata = segments[segment_index].arrival.iataCode;
                var h4 = document.createElement("h4");
                h4.innerText = `Carrier information: ${carrier} ${number}`;
                var h5 = document.createElement("h5");
                h5.innerText = `Duration for this flight: ${duration}`;
                var p_departure = document.createElement("p");
                var p_arrival = document.createElement("p");
                p_departure.innerText = `Departing from ${departure_iata} at ${departure_time}, Current status: ${departure_status}`;
                p_arrival.innerText = `Arriving at ${arrival_iata} at ${arrival_time}, Current status: ${arrival_status}`;
                li_segment.appendChild(h4);
                li_segment.appendChild(h5);
                li_segment.appendChild(p_departure);
                li_segment.appendChild(p_arrival);
                ol_segment.appendChild(li_segment);
            }
            div_itinerary.appendChild(ol_segment);
            var button = document.createElement("button").setAttribute('class', 'hide_others');
        	li.appendChild(button).innerText = "Hide Other Reservations";
            li.appendChild(div_itinerary);
        }
        ol.appendChild(li).setAttribute("number", str(i));
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

function hide_button(){
    document.querySelectorAll('button').forEach((single_button) => {
        single_button.addEventListener('click', () => {
            single_button.setAttribute('disable', 'true')
            document.getElementById('show_all').setAttribute('disabled', 'false')
            let this_number = single_button.parentElement.getAttribute('number')
            document.querySelectorAll(`[number]:not([number=${this_number}])`).forEach((reservation)=>{
                reservation.setAttribute("class", "hidden")
            })
        })
    })
}

function reveal_button(){
    document.getElementById('show_all').addEventListener('click', () => {
        document.querySelectorAll(`[number]`).forEach((reservation) => {
            reservation.removeAttribute("class")
        })
        document.querySelectorAll(`button`).forEach((button) => {
            button.removeAttribute('disabled')
        })
    })
}

const wait = (delay = 0) =>
  new Promise(resolve => setTimeout(resolve, delay));

const setVisible = (elementOrSelector, visible) => 
  (typeof elementOrSelector === 'string'
    ? document.querySelector(elementOrSelector)
    : elementOrSelector
  ).style.display = visible ? 'block' : 'none';

setVisible('.page', false);
setVisible('#loading', true);

document.addEventListener('DOMContentLoaded', () =>
  wait(1000).then(() => {
    setVisible('.page', true);
    setVisible('#loading', false);
  }));

window.onload = function() {
    reservations();
}