let rutas=[];
let empleados=[];
let traileres=[];
let cuerpoEmpleado="";
let cuerpoTrailer="";
let claves=[];

export function inicializar(){
    habilitarBtnEliActRuta(2);
    setDetalleVisible(true);
    pedirDatosRuta();
    pedirDatosEmpleado();
    pedirDatosTrailer();
    pedirClaves();
    configureTableFilter(document.getElementById("txtBuscarRuta"), document.getElementById("tblRutas"));
}

export function pedirDatosRuta(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);
    
    fetch("../../api/ruta/getAll",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                       if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            llenarTablaRuta(data);
                        } 
                    });
}

export function llenarTablaRuta(data){
    let cuerpoTabla="";
    let activo="";
    let ver="";
    rutas=data;
    rutas.forEach(function(ruta){
        if(ruta.estatus===1){
            activo="Si";
            ver=`<button class="btn btn-primary" onclick="modulo.irRuta('`+ruta.coordenadasOrigen+`', '`+ruta.coordenadasDestino+`');">Monitorear</button>`;
        } else{
            activo="No";
            ver=`La ruta finalizó`;
        }
        let datos='<tr onclick="modulo.seleccionarRuta('+rutas.indexOf(ruta)+');">'+
                  '<td>'+ruta.numeroRuta+'</td>'+
                  '<td>'+ruta.empleado.nombre+'</td>'+
                  '<td>'+ruta.trailer.placa+'</td>'+
                  '<td>'+activo+'</td>'+
                  '<td>'+ver+'</td>'+
                  '</tr>';
        cuerpoTabla+=datos;
    });
    
    document.getElementById("tbodyRutas").innerHTML=cuerpoTabla;
    document.querySelector(".spinner-container").classList.add("d-none");
    mostrarToastRuta();
}

export async function seleccionarRuta(index){
    cuerpoEmpleado+=`<option value="`+rutas[index].empleado.numeroEmpleado+`">
                        `+rutas[index].empleado.nombre+" "+rutas[index].empleado.primerApellido+" "+rutas[index].empleado.segundoApellido+`
                     </option>`;
    document.getElementById("cmbEmpleadoParaRuta").innerHTML=cuerpoEmpleado;
    
    cuerpoTrailer+=`<option value="`+rutas[index].trailer.numeroTrailer+`">
                        `+rutas[index].trailer.marca+" - "+rutas[index].trailer.modelo+" - "+rutas[index].trailer.placa+`
                     </option>`;
    document.getElementById("cmbTrailerParaRuta").innerHTML=cuerpoTrailer;
    
    await setDetalleVisible(false);
    
    let activo="";
    if(rutas[index].estatus===1){
        activo="Si";
    } else{
        activo="No";
    }
    
    habilitarBtnEliActRuta(rutas[index].estatus);
    
    document.getElementById("txtNumeroRuta").value=rutas[index].numeroRuta;
    document.getElementById("cmbEmpleadoParaRuta").value=rutas[index].empleado.numeroEmpleado;
    document.getElementById("cmbTrailerParaRuta").value=rutas[index].trailer.numeroTrailer;
    document.getElementById("txtCalleOrigen").value=rutas[index].calleOrigen;
    document.getElementById("txtNumeroCalleOrigen").value=rutas[index].numeroOrigen;
    document.getElementById("txtColoniaOrigen").value=rutas[index].coloniaOrigen;
    document.getElementById("txtCodigoPostalOrigen").value=rutas[index].codigoPostalOrigen;
    document.getElementById("txtCiudadOrigen").value=rutas[index].ciudadOrigen;
    document.getElementById("txtEstadoOrigen").value=rutas[index].estadoOrigen;
    document.getElementById("txtPaisOrigen").value=rutas[index].paisOrigen;
    document.getElementById("txtCalleDestino").value=rutas[index].calleDestino;
    document.getElementById("txtNumeroCalleDestino").value=rutas[index].numeroDestino;
    document.getElementById("txtColoniaDestino").value=rutas[index].coloniaDestino;
    document.getElementById("txtCodigoPostalDestino").value=rutas[index].codigoPostalDestino;
    document.getElementById("txtCiudadDestino").value=rutas[index].ciudadDestino;
    document.getElementById("txtEstadoDestino").value=rutas[index].estadoDestino;
    document.getElementById("txtPaisDestino").value=rutas[index].paisDestino;
    
    document.getElementById("btnLimpiarDatosRuta").disabled=false;
}

export function irRuta(coordenadasOrigen, coordenadasDestino){
    fetch("../monitoreo/vista_monitoreo.html")
            .then(
                function(response){
                    return response.text();
                }
            )
                .then(
                    function(html){
                        document.getElementById("contenedorPrincipal").innerHTML=html;
                        import("../monitoreo/monitoreoController.js").then(
                                function(controller){
                                    modulo=controller;
                                    modulo.verRuta(coordenadasOrigen, coordenadasDestino);
                                }
                                );
                    }
                );
}

export function pedirClaves(){
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
    
    fetch("../../api/clave/getClave",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            claves=data;
                        }
                    });
}

export function habilitarBtnEliActRuta(estatus){
    let cuerpoBtn=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosRuta" onclick="modulo.buscarCoordenadas();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosRuta" onclick="modulo.limpiarRuta();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    if(estatus===0 || estatus===1){
        if(estatus===1){
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-danger align-content-center" id="btnEliminarRuta" onclick="modulo.eliminarRuta();">
                                Terminar Ruta
                            </button>
                          </div>`;
            cuerpoBtn+=registro;
        } else{
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-secondary align-content-center" id="btnActivarRuta" onclick="modulo.activarRuta();">
                                Reanudar Ruta
                            </button>
                          </div>`;
            cuerpoBtn+=registro;
        }
    }
    
    document.getElementById("divBtnsRuta").innerHTML=cuerpoBtn;
}

export function activarRuta(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let ruta=new Object();
    
    ruta.numeroRuta=document.getElementById("txtNumeroRuta").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/ruta/activar",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            Swal.fire('', "Ruta activada con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function eliminarRuta(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let ruta=new Object();
    
    ruta.numeroRuta=document.getElementById("txtNumeroRuta").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/ruta/eliminar",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            Swal.fire('', "Ruta terminada con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function mostrarToastRuta(){
    var toastRuta=document.getElementById("toastTablaRuta");
    var toast=new bootstrap.Toast(toastRuta);
    toast.show();
}

export async function setDetalleVisible(bool){
    if(bool){
        document.getElementById("detalleRuta").style.display="";
        document.getElementById("formularioRuta").style.display="none";
        limpiarRuta();
    } else{
        document.getElementById("detalleRuta").style.display="none";
        document.getElementById("formularioRuta").style.display="";
        mostrarToastObligatorio();
    }
}

export function pedirDatosEmpleado(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);
    
    fetch("../../api/empleado/getAll",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            llenarCmbEmpleados(data);
                        }
                    });
}

export function llenarCmbEmpleados(data){
    cuerpoEmpleado=`<option selected="">
                Seleccione el empleado:
            </option>`;
    
    empleados=data;
    
    empleados.forEach(function(empleado){
        if(empleado.estatus===1 && empleado.enRuta===0){
            let datos=`<option value="`+empleado.numeroEmpleado+`">
                        `+empleado.nombre+" "+empleado.primerApellido+" "+empleado.segundoApellido+`
                       </option>`;
            cuerpoEmpleado+=datos;
        }
    });
    document.getElementById("cmbEmpleadoParaRuta").innerHTML=cuerpoEmpleado;
    document.querySelector(".spinner-container").classList.add("d-none");
}

export function pedirDatosTrailer(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);
    
    fetch("../../api/trailer/getAll",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            llenarCmbTrailer(data);
                        }
                    });
}

export function llenarCmbTrailer(data){
    cuerpoTrailer=`<option selected="">
                        Seleccione el trailer:
                   </option>`;
    
    traileres=data;
    
    traileres.forEach(function(trailer){
        if(trailer.estatus===1 && trailer.enRuta===0){
            let datos=`<option value="`+trailer.numeroTrailer+`">
                        `+trailer.marca+" - "+trailer.modelo+" - "+trailer.placa+`
                       </option>`;
            cuerpoTrailer+=datos;
        }
    });
    document.getElementById("cmbTrailerParaRuta").innerHTML=cuerpoTrailer;
    document.querySelector(".spinner-container").classList.add("d-none");
}

export function mostrarToastObligatorio(){
    var toastObligatorio=document.getElementById("toastObligatorio");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export function habilitarBtnLimpiar(){
    var inputs=document.querySelectorAll("input");
    var todosVacios=true;
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].value.trim()!==""){
            todosVacios=false;
            break;
        }
    }
    if(todosVacios){
        document.querySelector("#btnLimpiarDatosRuta").disabled=true;
    } else{
        document.querySelector("#btnLimpiarDatosRuta").disabled=false;
    }
    habilitarBtnGuardar();
}

export function habilitarBtnGuardar(){
    let empleado=document.getElementById("cmbEmpleadoParaRuta").value;
    let trailer=document.getElementById("cmbTrailerParaRuta").value;
    let caor=document.getElementById("txtCalleOrigen").value;
    let numo=document.getElementById("txtNumeroCalleOrigen").value;
    let colo=document.getElementById("txtColoniaOrigen").value;
    let codo=document.getElementById("txtCodigoPostalOrigen").value;
    let ciuo=document.getElementById("txtCiudadOrigen").value;
    let esto=document.getElementById("txtEstadoOrigen").value;
    let paio=document.getElementById("txtPaisOrigen").value;
    let cade=document.getElementById("txtCalleDestino").value;
    let numd=document.getElementById("txtNumeroCalleDestino").value;
    let cold=document.getElementById("txtColoniaDestino").value;
    let codd=document.getElementById("txtCodigoPostalDestino").value;
    let ciud=document.getElementById("txtCiudadDestino").value;
    let estd=document.getElementById("txtEstadoDestino").value;
    let paid=document.getElementById("txtPaisDestino").value;
    
    if(empleado!=="" && trailer!=="" && caor!=="" && numo!=="" && colo!=="" && 
       codo!=="" && ciuo!=="" && esto!=="" && paio!=="" && cade!=="" && numd!=="" && 
       cold!=="" && codd!=="" && ciud!=="" && estd!=="" && paid!==""){
        document.querySelector("#btnEnviarDatosRuta").disabled=false;
    } else{
        document.querySelector("#btnEnviarDatosRuta").disabled=true;
    }
}

export async function buscarCoordenadas(){
    let coordenadasOrigen=null;
    let coordenadasDestino=null;
    
    let calleOrigen=normalizar(sanitizar(document.getElementById("txtCalleOrigen").value));
    let numeroOrigen=normalizar(sanitizar(document.getElementById("txtNumeroCalleOrigen").value));
    let coloniaOrigen=normalizar(sanitizar(document.getElementById("txtColoniaOrigen").value));
    let codigoPostalOrigen=normalizar(sanitizar(document.getElementById("txtCodigoPostalOrigen").value));
    let ciudadOrigen=normalizar(sanitizar(document.getElementById("txtCiudadOrigen").value));
    let estadoOrigen=normalizar(sanitizar(document.getElementById("txtEstadoOrigen").value));
    let paisOrigen=normalizar(sanitizar(document.getElementById("txtPaisOrigen").value));
    let calleDestino=normalizar(sanitizar(document.getElementById("txtCalleDestino").value));
    let numeroDestino=normalizar(sanitizar(document.getElementById("txtNumeroCalleDestino").value));
    let coloniaDestino=normalizar(sanitizar(document.getElementById("txtColoniaDestino").value));
    let codigoPostalDestino=normalizar(sanitizar(document.getElementById("txtCodigoPostalDestino").value));
    let ciudadDestino=normalizar(sanitizar(document.getElementById("txtCiudadDestino").value));
    let estadoDestino=normalizar(sanitizar(document.getElementById("txtEstadoDestino").value));
    let paisDestino=normalizar(sanitizar(document.getElementById("txtPaisDestino").value));
    
    const apiKey=claves.clave;
    
    const direccionOrigen="'"+calleOrigen+" "+numeroOrigen+", "+coloniaOrigen+", "+codigoPostalOrigen+", "+ciudadOrigen+", "+estadoOrigen+", "+paisOrigen+"'";
    const direccionDestino="'"+calleDestino+" "+numeroDestino+", "+coloniaDestino+", "+codigoPostalDestino+", "+ciudadDestino+", "+estadoDestino+", "+paisDestino+"'";

    const apiUrlOrigen=`https://geocode.search.hereapi.com/v1/geocode?q=${encodeURIComponent(direccionOrigen)}&apiKey=${apiKey}`;
    const apiUrlDestino=`https://geocode.search.hereapi.com/v1/geocode?q=${encodeURIComponent(direccionDestino)}&apiKey=${apiKey}`;
    
    try{
        const responseOrigen=await fetch(apiUrlOrigen);
        const dataOrigen=await responseOrigen.json();
        const latitudOrigen=dataOrigen.items[0].position.lat;
        const longitudOrigen=dataOrigen.items[0].position.lng;
        coordenadasOrigen="lat: "+latitudOrigen+", lng: "+longitudOrigen;
    } catch (error){
        console.error('Error al obtener las coordenadas origen:', error);
        Swal.fire('Error al obtener las coordenadas origen', 'Ingrese de manera correcta la direccion origen', 'warning');
    }

    try{
        const responseDestino=await fetch(apiUrlDestino);
        const dataDestino=await responseDestino.json();
        const latitudDestino=dataDestino.items[0].position.lat;
        const longitudDestino=dataDestino.items[0].position.lng;
        coordenadasDestino="lat: "+latitudDestino+", lng: "+longitudDestino;
    } catch (error){
        console.error('Error al obtener las coordenadas destino:', error);
        Swal.fire('Error al obtener las coordenadas destino', 'Ingrese de manera correcta la direccion destino', 'warning');
    }

    if (coordenadasOrigen !== null && coordenadasDestino !== null) {
        guardarRuta(coordenadasOrigen, coordenadasDestino);
    } else {
        Swal.fire('Error al obtener las coordenadas', 'Intente de nuevo', 'warning');
    }
}

export function guardarRuta(coordenadasOrigen, coordenadasDestino){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let ruta=new Object();
    ruta.empleado=new Object();
    ruta.trailer=new Object();
    
    if(document.getElementById("txtNumeroRuta").value.trim().length<1){
        ruta.numeroRuta=0;
    } else{
        ruta.numeroRuta=document.getElementById("txtNumeroRuta").value;
    }
    
    ruta.empleado.numeroEmpleado=document.getElementById("cmbEmpleadoParaRuta").value;
    
    ruta.trailer.numeroTrailer=document.getElementById("cmbTrailerParaRuta").value;
    
    ruta.calleOrigen=normalizar(sanitizar(document.getElementById("txtCalleOrigen").value));
    ruta.numeroOrigen=normalizar(sanitizar(document.getElementById("txtNumeroCalleOrigen").value));
    ruta.coloniaOrigen=normalizar(sanitizar(document.getElementById("txtColoniaOrigen").value));
    ruta.codigoPostalOrigen=normalizar(sanitizar(document.getElementById("txtCodigoPostalOrigen").value));
    ruta.ciudadOrigen=normalizar(sanitizar(document.getElementById("txtCiudadOrigen").value));
    ruta.estadoOrigen=normalizar(sanitizar(document.getElementById("txtEstadoOrigen").value));
    ruta.paisOrigen=normalizar(sanitizar(document.getElementById("txtPaisOrigen").value));
    ruta.coordenadasOrigen=coordenadasOrigen;
    ruta.calleDestino=normalizar(sanitizar(document.getElementById("txtCalleDestino").value));
    ruta.numeroDestino=normalizar(sanitizar(document.getElementById("txtNumeroCalleDestino").value));
    ruta.coloniaDestino=normalizar(sanitizar(document.getElementById("txtColoniaDestino").value));
    ruta.codigoPostalDestino=normalizar(sanitizar(document.getElementById("txtCodigoPostalDestino").value));
    ruta.ciudadDestino=normalizar(sanitizar(document.getElementById("txtCiudadDestino").value));
    ruta.estadoDestino=normalizar(sanitizar(document.getElementById("txtEstadoDestino").value));
    ruta.paisDestino=normalizar(sanitizar(document.getElementById("txtPaisDestino").value));
    ruta.coordenadasDestino=coordenadasDestino;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosRuta: JSON.stringify(ruta),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/ruta/save",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params
    })
            .then(response=>{
                return response.json();
            })
                    .then(data=>{
                        if(data.error){
                            Swal.fire('', data.error, 'warning');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                        } else{
                            Swal.fire('', "Ruta guardada con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function limpiarRuta(){
    document.getElementById("txtNumeroRuta").value="";
    document.getElementById("cmbEmpleadoParaRuta").selectedIndex=0;
    document.getElementById("cmbTrailerParaRuta").selectedIndex=0;
    document.getElementById("txtCalleOrigen").value="";
    document.getElementById("txtNumeroCalleOrigen").value="";
    document.getElementById("txtColoniaOrigen").value="";
    document.getElementById("txtCodigoPostalOrigen").value="";
    document.getElementById("txtCiudadOrigen").value="";
    document.getElementById("txtEstadoOrigen").value="";
    document.getElementById("txtPaisOrigen").value="";
    document.getElementById("txtCalleDestino").value="";
    document.getElementById("txtNumeroCalleDestino").value="";
    document.getElementById("txtColoniaDestino").value="";
    document.getElementById("txtCodigoPostalDestino").value="";
    document.getElementById("txtCiudadDestino").value="";
    document.getElementById("txtEstadoDestino").value="";
    document.getElementById("txtPaisDestino").value="";
    
    pedirDatosEmpleado();
    pedirDatosTrailer();
    
    document.querySelector("#btnLimpiarDatosRuta").disabled=true;
    document.querySelector("#btnEnviarDatosRuta").disabled=true;
    
    let cuerpo=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosRuta" onclick="modulo.buscarCoordenadas();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosRuta" onclick="modulo.limpiarRuta();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    document.getElementById("divBtnsRuta").innerHTML=cuerpo;
}
