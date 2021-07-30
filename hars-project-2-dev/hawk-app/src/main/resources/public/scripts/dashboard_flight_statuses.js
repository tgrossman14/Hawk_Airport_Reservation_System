async function get_flight_statuses(){

    const url = 'endpoint'
    const response = await fetch(url)
    const my_json = await response.json()
    let status = document.querySelector('#flight_statuses > tbody')
    for (flight in my_json){
        let tr = document.createElement('tr')
        status.appendChild(tr)
        var th = document.createElement('th').setAttribute("scope", "col")
        const date = flight["data"][0]["scheduledDepartureDate"]
        tr.appendChild(th.append(date))
        var th = document.createElement('th').setAttribute("scope", "col")
        const depart = flight["data"][0]["flightPoints"]["departure"]["timings"][0]["value"].slice(11, 16)
        tr.appendChild(th.append(depart))
        var th = document.createElement('th').setAttribute("scope", "col")
        const arrival = flight["data"][0]["flightPoints"]["arrival"]["timings"][0]["value"].slice(11, 16)
        tr.appendChild(th.append(arrival))
        var th = document.createElement('th').setAttribute("scope", "col")
        const flightno = flight["data"][0]["flightDesignator"]["flightNumber"]
        tr.appendChild(th.append(flightno))
        //no destination or origin listed in example json
    }
    rowfade()
}

async function rowfade(){
    let rows = document.querySelectorAll('tbody > tr')
    rows.item(0).setAttribute('id', 'done')
    rows.forEach((row) => {
        row.addEventListener('animationend', ()=>{
            console.log("animation ended")
            row.nextElementSibling.setAttribute('id', 'done')
        })
    })
}


window.onload = () => {
    this.get_flight_statuses()
}