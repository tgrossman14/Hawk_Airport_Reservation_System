async function AirportsNearest() {
	const url = "http://localhost:5000/airports.json";
	const response = await fetch(url);
	const data = await response.text;
	console.log(data);
}

window.onload = function () {
	this.AirportsNearest();
}