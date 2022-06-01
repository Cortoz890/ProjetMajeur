L.mapbox.accessToken = 'pk.eyJ1IjoibGpldGJhcHRpc3RlIiwiYSI6ImNsM3ZhYTNlMzBwM3Izam5wOGNycGoxdG0ifQ.NWzdwquyLVm5ZjHe3jrQbQ';
var map = L.map('map').setView([45.75,4.83], 13);


L.tileLayer(
    'https://api.mapbox.com/styles/v1/mapbox/streets-v11/tiles/{z}/{x}/{y}?access_token=' + L.mapbox.accessToken, {
        tileSize: 512,
        zoomOffset: -1,
        attribution: '© <a href="https://www.mapbox.com/contribute/">Mapbox</a> © <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
    }).addTo(map);

    
generateFeux()



function affichageFeux(type){
    console.log(type)
    if(document.getElementById(type).checked){
        opacity=1;
    }
    else{
        opacity=0;
    }
    i=0;
    if(listes_marqueurs[type]!=null){
        while(i<listes_marqueurs[type].length){
            listes_marqueurs[type][i].setOpacity(opacity)
            i++;
        }
    }
}

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
    listes_marqueurs={};
    i=0;
    while(i<response.length){
        var marker = L.marker([response[i].lat, response[i].lon], {icon: fireIcon}, {title: response[i].type}).addTo(map);
        marker.bindPopup("Type feu : " + response[i].type+"<br> Intensité : " + response[i].intensity + "<br> Range : " + response[i].range);
        marker.on('click', onClick);
        if(listes_marqueurs[response[i].type]!=null){
            listes_marqueurs[response[i].type].push(marker);
        }
        else{
            listes_marqueurs[response[i].type]=[marker];
        }

        i++;
    }
    console.log(listes_marqueurs)
}

function err_callback(error){
    console.log(error);
}

//Generation des icones
var Icon = L.Icon.extend({
    options: {
        iconSize:     [1.2*27.1, 1.2*29.6]
    }
});

var fireIcon = new Icon({iconUrl: 'IMAGES/fire.png'});
L.icon = function (options) {
    return new L.Icon(options);
};


function onClick(e) {
    var popup = e.target.getPopup();
    var content = popup.getContent();
 
    console.log(content);
}

