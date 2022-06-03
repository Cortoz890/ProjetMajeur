//const { response } = require("express");

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
    while(i < listeMarqueur.length){
        map.removeLayer(listeMarqueur[i])
        i++;
    }
    i=0;
    while(i<response.length){
        var marker = L.marker([response[i].lat, response[i].lon], {icon: fireIcon}, {title: response[i].type}).addTo(map);
        marker.bindPopup("id : " +response[i].id + "<br> Position : [ " + response[i].lat + "," + response[i].lon + "] <br> Type feu : " + response[i].type+"<br> Intensité : " + response[i].intensity + "<br> Range : " + response[i].range);
        marker.on('click', onClick);
        if(document.getElementById(response[i].type).checked && response[i].intensity < document.getElementById("Intensity").value && response[i].range < document.getElementById("Range").value){
            marker.setOpacity(1);
        }
        else{
            marker.setOpacity(0)

        }
        listeMarqueur.push(marker);
        i++;
    }
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
            idCamion = response[i].vehicleIdSet;
        }
        else{
            icone = stationOtherIcon;
        }
        var marker = L.marker([response[i].lat, response[i].lon], {icon: icone}).addTo(map);
        marker.bindPopup(response[i].name +"<br> idCaserne :" + response[i].id + "<br> Position : [ " + response[i].lat + "," + response[i].lon + "] <br> Espace max : " + response[i].maxVehicleSpace +"<br> ID véhicules : " + response[i].vehicleIdSet + "<br> Capacité max : " + response[i].peopleCapacity + "<br> ID pompiers : " + response[i].peopleIdSet);
        marker.on('click', onClick);
        i++
    }
    updateCaserneList();
    generateVehicles();
}

function err_callbackCaserne(error){
    console.log(error);
}


// Requête location Vehicule


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
    i=0;
    while(i<response.length){
        if(idCamion.includes(response[i].id)){
            icone = vehicleIcon;
        }
        else{
            icone = vehicleOtherIcon;
        }
        var marker = L.marker([response[i].lat, response[i].lon], {icon: icone}).addTo(map);
        marker.bindPopup("id" + response[i].id + "<br> Position : [ " + response[i].lat + "," + response[i].lon + "] <br> Type : " + response[i].type +"<br> Carburant : " + response[i].fuel + "<br> Liquide : " + response[i].liquidType + "<br> Quantité : " + response[i].liquidQuantity + "<br> membre : " + response[i].crewMember);
        marker.on('click', onClick);
        i++
    }
    updateVehicleList();
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


//Gestion des formulaires 

URL_base = "http://localhost:3080"
function onClick(e) {
    var popup = e.target.getPopup();
    var content = popup.getContent();
 }

function updateVehicleList(){
    i=0;
    document.getElementById("del_camion").innerHTML=""
    while(i<idCamion.length){
        document.getElementById("del_camion").innerHTML+="<option value="+idCamion[i]+">Camion "+idCamion[i]+"</option>";
        i++;
    }
}

function updateCaserneList(){
    i=0;
    document.getElementById("camion_caserne").innerHTML=""
    while(i<IdCasernes.length){
        document.getElementById("camion_caserne").innerHTML+="<option value="+IdCasernes[i]+">Caserne "+IdCasernes[i]+"</option>";
        i++;
    }
}

function supprimerCamion(){
    if(confirm("Voulez vous supprimer le véhicule "+document.getElementById("del_camion").value+" ?")){
        const DEL_URL= URL_base +"/camion/"+document.getElementById("del_camion").value;
        let context =   {
                            method: 'DEL'
                        };
            
        fetch(DEL_URL,context)
    }
}


function generateCasPos(idCaserne){
    console.log("je rentre")
    const GET_URL="http://vps.cpe-sn.fr:8081/facility/"+idCaserne;
        let context =   {
                            method: 'GET'
                        };
            
        fetch(GET_URL,context)
            .then(response => response.json())
                .then(response => callbackCasPos(response))
                .catch(error => err_callbackCasPos(error));
}

function err_callbackCasPos(error){
    console.log(error);
}

function callbackCasPos(response){
    console.log("callback");

    lat =  response.lat;
    lon =  response.lon;
    
    data = {
        "crewMember": 2,
        "facilityRefID": idCas,
        "fuel": 100,
        "id": 0,
        "lat": lat,
        "liquidQuantity": 100,
        "liquidType": form2.querySelector('select[name="crea_camion_extincteur"]').value,
        "lon": lon,
        "type": form2.querySelector('select[name="crea_camion_type"]').value
      }

    console.log(data);
    sendCamion(data);
}

function creerCamion(){

    form2 = document.querySelector("#creation_camion");
    idCas = form2.querySelector('select[name="crea_camion_caserne"]').value
    generateCasPos(idCas)

}

function sendCamion(data){

    const POST_URL=URL_base + "/addVehicle"; 
    let context =   {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json',
                        },
                        body: JSON.stringify(data),
                    };
        
    fetch(POST_URL,context)
            .then(response => callbackCreationCamion(response))
            .catch(error => err_callback(error));
}

function callbackCreationCamion(response){
    console.log(response.value)
    idCamion.push(response.value)
}


//Appels des fonctions pour les requètes
listeMarqueur=[];
idCamion=[];
generateFeux();
generateCasernes();
