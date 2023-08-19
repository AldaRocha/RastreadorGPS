
/* global H */

let claves=[];

export async function inicializar(){
    try {
        claves = await pedirApi();
        verMapa();
    } catch (error) {
        console.error('Error al obtener las claves:', error);
        Swal.fire('Error al mostrar mapa', 'Recargue la pagina', 'warning');
    }
}

export async function pedirApi(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp===null){
        const tokenn="ñ";
        const nombreClavee="apikey";
        datos={
            token: tokenn,
            nombreClave: nombreClavee
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        const nombreClaveee="apikey";
        datos={            
            token: tokennn,
            nombreClave: nombreClaveee
        };
    }
    params=new URLSearchParams(datos);
    
    try{
        const response=await fetch("../../api/clave/getClave",{
            method: "POST",
            headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
            body: params
        });
        
        const data=await response.json();
        
        if (data.error) {
            throw new Error(data.error);
            Swal.fire('', data.error, 'warning');
            document.querySelector(".spinner-container").classList.add("d-none");
        } else if (data.exception) {
            throw new Error("Error interno del servidor.");
            Swal.fire('', "Error interno del servidor.", 'error');
            document.querySelector(".spinner-container").classList.add("d-none");
        } else {
            document.querySelector(".spinner-container").classList.add("d-none");
            return data;
        }
    } catch(error){
        document.querySelector(".spinner-container").classList.add("d-none");
        throw error;
    }
}

export function verMapa(){
    var platform=new H.service.Platform({
        'apikey': claves.clave
    });

    var defaultLayers=platform.createDefaultLayers();
    var map=new H.Map(
        document.getElementById('mapContainer'),
        defaultLayers.vector.normal.map,{
            zoom: 16
        }
    );

    var behavior=new H.mapevents.Behavior(new H.mapevents.MapEvents(map));

    var ui=H.ui.UI.createDefault(map, defaultLayers);
    
    var zoomControl = new H.ui.ZoomControl();
    ui.addControl('zoomControl', zoomControl);
    
    map.addEventListener('pointerenter', function (){
        map.getElement().style.cursor='pointer';
    });
    
    map.addEventListener('pointerdown', function (){
        map.getElement().style.cursor='grab';
    });
    
    map.addEventListener('pointerup', function (){
        map.getElement().style.cursor='pointer';
    });
    
    map.addEventListener('pointerleave', function (){
        map.getElement().style.cursor = 'default';
    });
    
    obtenerCoordenadas(function (lat, lng){
        map.setCenter({lat: lat, lng: lng});
        
        var marker=new H.map.Marker({ lat: lat, lng: lng });
        
        map.addObject(marker);
    });
    
    document.querySelector(".spinner-container").classList.add("d-none");
}

export function obtenerCoordenadas(callback){
    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(
                function (position){
                    var lat=position.coords.latitude;
                    var lng=position.coords.longitude;
                    
                    callback(lat, lng);
                },
                function (error){
                    console.log("Error al obtener la ubicación:", error);
                    Swal.fire('Error al obtener la ubicación', 'Intente cargar la pagina nuevamente', 'warning');
                }
        );
    } else{
        console.log("La geolocalización no es compatible con este navegador.");
        Swal.fire('La geolocalización no es compatible con este navegador', '', 'warning');
    }
}

export async function verRuta(coordenadasOrigen, coordenadasDestino){
    try {
        claves=await pedirApi();
        obtenerRuta(coordenadasOrigen, coordenadasDestino);
    } catch (error){
        console.error('Error al obtener las claves:', error);
        Swal.fire('Error al mostrar mapa', 'Recargue la pagina', 'warning');
    }
}

export async function obtenerRuta(coordenadasOrigen, coordenadasDestino){
    let origenArray=coordenadasOrigen.split(",");
    let latOrigen=origenArray[0].substring(5);
    let lngOrigen=origenArray[1].substring(6);
    let origen=latOrigen+","+lngOrigen;
    
    let destinoArray=coordenadasDestino.split(",");
    let latDestino=destinoArray[0].substring(5);
    let lngDestino=destinoArray[1].substring(6);
    let destino=latDestino+","+lngDestino;
    
    try{
        const platform=new H.service.Platform({
            'apikey': claves.clave
        });
        
        var targetElement=document.getElementById('mapContainer');
        
        var defaultLayers=platform.createDefaultLayers();
        
        var map=new H.Map(
            document.getElementById('mapContainer'),
            defaultLayers.vector.normal.map,{
                zoom: 16
        });
        
        var routingParameters={
            'routingMode': 'fast',
            'transportMode': 'car',
            'origin': origen,
            'destination': destino,
            'return': 'polyline'
        };
        
        var onResult=function(result){
            if(result.routes.length){
                result.routes[0].sections.forEach((section)=>{
                    let linestring=H.geo.LineString.fromFlexiblePolyline(section.polyline);

                    let routeLine=new H.map.Polyline(linestring,{
                        style: { strokeColor: 'blue', lineWidth: 3 }
                    });

                    let startMarker=new H.map.Marker(section.departure.place.location,{
                        icon: new H.map.Icon('https://img.icons8.com/ultraviolet/40/000000/marker.png')
                    });

                    let endMarker=new H.map.Marker(section.arrival.place.location,{
                        icon: new H.map.Icon('https://img.icons8.com/dusk/40/000000/marker.png')
                    });

                    map.addObjects([routeLine, startMarker, endMarker]);

                    map.getViewModel().setLookAtData({ bounds: routeLine.getBoundingBox() });
                });
            }
        };
        
        var router=platform.getRoutingService(null, 8);
        
        router.calculateRoute(routingParameters, onResult, function(error){
            alert(error.message);
        });
        
        map.addEventListener('pointerenter', function() {
            map.getElement().style.cursor = 'pointer';
        });

        map.addEventListener('pointerdown', function() {
            map.getElement().style.cursor = 'grab';
        });

        map.addEventListener('pointerup', function() {
            map.getElement().style.cursor = 'pointer';
        });

        map.addEventListener('pointerleave', function() {
            map.getElement().style.cursor = 'default';
        });

        var behavior = new H.mapevents.Behavior(new H.mapevents.MapEvents(map));
        behavior.enable(H.mapevents.Behavior.WHEELZOOM);
        behavior.enable(H.mapevents.Behavior.DRAGGING);
        
        obtenerCoordenadas(function (lat, lng){
            map.setCenter({lat: lat, lng: lng});

            var carIcon = new H.map.Icon('https://img.icons8.com/ios/50/000000/car--v2.png');
            var marker=new H.map.Marker({ lat: lat, lng: lng }, { icon: carIcon });

            map.addObject(marker);
        });
    } catch (error) {
        console.error('Error al obtener las claves:', error);
        Swal.fire('Error al mostrar mapa', 'Recargue la pagina', 'warning');
    }
}
