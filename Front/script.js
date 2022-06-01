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
    i=0;
    console.log(listeMarqueur.length);
    while(i < listeMarqueur.length){
        map.removeLayer(listeMarqueur[i])
        console.log("salut")
        i++;
    }
    i=0;
    while(i<response.length){
        var marker = L.marker([response[i].lat, response[i].lon], {icon: fireIcon}, {title: response[i].type}).addTo(map);
        marker.bindPopup("Type feu : " + response[i].type+"<br> Intensité : " + response[i].intensity + "<br> Range : " + response[i].range);
        marker.on('click', onClick);
        console.log(document.getElementById('Intensity').value)
        if(document.getElementById(response[i].type).checked && response[i].intensity < document.getElementById("Intensity").value && response[i].range < document.getElementById("Range").value){
            marker.setOpacity(1);
        }
        else{
            marker.setOpacity(0)

        }
        listeMarqueur.push(marker);
        i++;
    }
    console.log(listeMarqueur);
}

function err_callback(error){
    console.log(error);
}

// Requête location Caserne
j = 0;
var IdCasernes = [82];

function generateCasernes(){
        const GET_URL="http://vps.cpe-sn.fr:8081/facility/";
        let context =   {
                            method: 'GET'
                        };
            
        fetch(GET_URL,context)
            .then(response => response.json())
                .then(response => callbackCaserne(response))
                .catch(error => err_callbackCaserne(error));

}

function callbackCaserne(response){
    i=0;
    while(i<response.length){
        if(IdCasernes.includes(response[i].id)){
            icone = stationIcon;
            //à modifier si plusieurs casernes
            idCamion = [response[i].vehicleIdSet]
        }
        else{
            icone = stationOtherIcon;
        }
        var marker = L.marker([response[i].lat, response[i].lon], {icon: icone}).addTo(map);
        marker.bindPopup(response[i].name + "<br> Espace max : " + response[i].maxVehicleSpace +"<br> ID véhicules : " + response[i].vehicleIdSet + "<br> Capacité max : " + response[i].peopleCapacity + "<br> ID pompiers : " + response[i].peopleIdSet);
        marker.on('click', onClick);
        i++
    }
}

function err_callbackCaserne(error){
    console.log(error);
}


// Requête location Vehicule
j = 0;
var IdCasernes = [82];

function generateVehicles(){
        const GET_URL="http://vps.cpe-sn.fr:8081/vehicle/";
        let context =   {
                            method: 'GET'
                        };
            
        fetch(GET_URL,context)
            .then(response => response.json())
                .then(response => callbackVehicle(response))
                .catch(error => err_callbackVehicle(error));

}

function callbackVehicle(response){
    console.log("la réponse du camion")
    console.log(response);
    i=0;
    while(i<response.length){
        if(idCamion.includes(response[i].id)){
            icone = vehicleIcon;
        }
        else{
            icone = vehicleOtherIcon;
        }
        var marker = L.marker([response[i].lat, response[i].lon], {icon: icone}).addTo(map);
        marker.bindPopup("id" + response[i].id + "<br> Type : " + response[i].type +"<br> Carburant : " + response[i].fuel + "<br> Liquide : " + response[i].liquidType + "<br> Quantité : " + response[i].liquidQuantity + "<br> membre : " + response[i].crewMember);
        marker.on('click', onClick);
        i++
    }
}

function err_callbackVehicle(error){
    console.log(error);
}

//Generation des icones
var Icon = L.Icon.extend({
    options: {
        iconSize:     [1.2*27.1, 1.2*29.6]
    }
});

var fireIcon = new Icon({iconUrl: 'IMAGES/fire.png'});
var stationIcon = new Icon({iconUrl: 'IMAGES/station.png'});
var stationOtherIcon = new Icon({iconUrl: 'IMAGES/stationOther.png'});
var vehicleIcon = new Icon({iconUrl: 'IMAGES/vehicle.png'});
var vehicleOtherIcon = new Icon({iconUrl: 'IMAGES/vehicleOther.png'});



L.icon = function (options) {
    return new L.Icon(options);
};


function onClick(e) {
    var popup = e.target.getPopup();
    var content = popup.getContent();
 
    console.log(content);
}

//Appels des fonctions pour les requètes
listeMarqueur=[];
generateFeux()
generateCasernes()
generateVehicles()
