L.mapbox.accessToken = 'pk.eyJ1IjoibGpldGJhcHRpc3RlIiwiYSI6ImNsM3ZhYTNlMzBwM3Izam5wOGNycGoxdG0ifQ.NWzdwquyLVm5ZjHe3jrQbQ';
var map = L.map('map').setView([45.75,4.83], 13);


L.tileLayer(
    'https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/{z}/{x}/{y}?access_token=' + L.mapbox.accessToken, {
        tileSize: 512,
        zoomOffset: -1,
        attribution: '© <a href="https://www.mapbox.com/contribute/">Mapbox</a> © <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);



function generateFeux(){

    const GET_URL="http://vps.cpe-sn.fr:8081/fire"; 
    let context =   {
                        method: 'GET'
                    };
        
    fetch(GET_URL,context)
        .then(response => response.json())
            .then(response => callback(response))
            .catch(error => err_callback(error));

}

function callback(response){
    console.log(response)
    i=0;
    while(i<response.length){
        var marker = L.marker([response[i].lat, response[i].lon]).addTo(map);
        i++
    }

}

function err_callback(error){
    console.log(error);
}

generateFeux()