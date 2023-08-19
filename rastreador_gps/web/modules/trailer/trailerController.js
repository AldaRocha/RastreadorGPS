let trailers=[];
let inputImagen=null;

export function inicializar(){
    habilitarBtnEliActTrailer(2);
    setDetalleVisible(true);
    pedirDatosTrailer();
    configureTableFilter(document.getElementById("txtBuscarTrailer"), document.getElementById("tblTrailers"));
    inputImagen=document.getElementById("imagenSeleccionadaTrailer");
    inputImagen.onchange=function(e){
        cargarFotografiaTrailer(inputImagen);
        habilitarBtnLimpiarTrailer();
    };
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
                            llenarTablaTrailer(data);
                        }
                    });
}

export function llenarTablaTrailer(data){
    let cuerpo="";
    let activo="";
    let enRuta="";
    trailers=data;
    trailers.forEach(function(trailer){
        if(trailer.estatus===1){
            activo="Si";
        } else{
            activo="No";
        }
        if(trailer.enRuta===1){
            enRuta="Si";
        } else{
            enRuta="No";
        }
        let datos='<tr onclick="modulo.seleccionarTrailer('+trailers.indexOf(trailer)+');">'+
                  '<td>'+trailer.numeroTrailer+'</td>'+
                  '<td>'+trailer.marca+'</td>'+
                  '<td>'+trailer.modelo+'</td>'+
                  '<td>'+trailer.placa+'</td>'+
                  '<td>'+activo+'</td>'+
                  '<td>'+enRuta+'</td>'+
                  '</tr>';
        cuerpo+=datos;
    });
    document.getElementById("tbodyTrailers").innerHTML=cuerpo;
    document.querySelector(".spinner-container").classList.add("d-none");
    mostrarToastTrailer();
}

export async function seleccionarTrailer(index){
    await setDetalleVisible(false);
    let activo="";
    let enRuta="";
    if(trailers[index].estatus===1){
            activo="Si";
        } else{
            activo="No";
        }
    if(trailers[index].enRuta===1){
        enRuta="Si";
    } else{
        enRuta="No";
    }
    
    habilitarBtnEliActTrailer(trailers[index].estatus);
    
    document.getElementById("codigoFotografiaTrailer").value=trailers[index].fotografia;
    let fotoB64="data:image/png;base64,"+document.getElementById("codigoFotografiaTrailer").value;
    document.getElementById("imgTrailer").src=fotoB64;
    document.getElementById("txtNumeroTrailer").value=trailers[index].numeroTrailer;
    document.getElementById("txtMarca").value=trailers[index].marca;
    document.getElementById("txtModelo").value=trailers[index].modelo;
    document.getElementById("txtPlaca").value=trailers[index].placa;
    document.getElementById("txtEstatusTrailer").value=activo;
    document.getElementById("txtEnRutaTrailer").value=enRuta;
    
    document.getElementById("btnLimpiarDatosTrailer").disabled=false;
}

export function habilitarBtnEliActTrailer(estatus){
    let cuerpo=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosTrailer" onclick="modulo.guardarTrailer();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosTrailer" onclick="modulo.limpiarTrailer();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    if(estatus===0 || estatus===1){
        if(estatus===1){
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-danger align-content-center" id="btnEliminarTrailer" onclick="modulo.eliminarTrailer();">
                                Deshabilitar trailer
                            </button>
                          </div>`;
            cuerpo+=registro;
        } else{
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-secondary align-content-center" id="btnActivarTrailer" onclick="modulo.activarTrailer();">
                                Habilitar trailer
                            </button>
                          </div>`;
            cuerpo+=registro;
        }
    }
    
    document.getElementById("divBtnsTrailer").innerHTML=cuerpo;
}

export function activarTrailer(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let trailer=new Object();
    
    trailer.numeroTrailer=document.getElementById("txtNumeroTrailer").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/trailer/activarTrailer",{
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
                            Swal.fire('', "Trailer activado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function eliminarTrailer(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let trailer=new Object();
    
    trailer.numeroTrailer=document.getElementById("txtNumeroTrailer").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/trailer/eliminarTrailer",{
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
                            Swal.fire('', "Trailer desactivado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function mostrarToastTrailer(){
    var toastEmpleado=document.getElementById("toastTablaTrailer");
    var toast=new bootstrap.Toast(toastEmpleado);
    toast.show();
}

export async function setDetalleVisible(bool){
    if(bool){
        document.getElementById("detalleTrailer").style.display="";
        document.getElementById("formularioTrailer").style.display="none";
        limpiarTrailer();
    } else{
        document.getElementById("detalleTrailer").style.display="none";
        document.getElementById("formularioTrailer").style.display="";
        mostrarToastObligatorioTrailer();
    }
}

export function mostrarToastObligatorioTrailer(){
    var toastObligatorio=document.getElementById("toastObligatorioTrailer");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export function cargarFotografiaTrailer(file){
    if(file.files && file.files[0]){
        let reader=new FileReader();
        
        reader.onload=function(e){
          let fotoB64=e.target.result;
          document.getElementById("imgTrailer").src=fotoB64;          
          document.getElementById("codigoFotografiaTrailer").value=fotoB64.substring(fotoB64.indexOf(",")+1, fotoB64.length);
        };       
        reader.readAsDataURL(file.files[0]);
    }
}

export function habilitarBtnLimpiarTrailer(){
    var inputs=document.querySelectorAll("input");
    var todosVacios=true;
    for(var i=0; i<inputs.length; i++){
        if(inputs[i].value.trim()!==""){
            todosVacios=false;
            break;
        }
    }
    if(todosVacios){
        document.querySelector("#btnLimpiarDatosTrailer").disabled=true;
    } else{
        document.querySelector("#btnLimpiarDatosTrailer").disabled=false;
    }
    habilitarBtnGuardarTrailer();
}

export function habilitarBtnGuardarTrailer(){
    let foto=document.getElementById("codigoFotografiaTrailer").value;
    let marca=document.getElementById("txtMarca").value;
    let modelo=document.getElementById("txtModelo").value;
    let placa=document.getElementById("txtPlaca").value;
    
    if(foto!=="" && marca!=="" && modelo!=="" && placa!==""){
        document.querySelector("#btnEnviarDatosTrailer").disabled=false;
    } else{
        document.querySelector("#btnEnviarDatosTrailer").disabled=true;
    }
}

export async function guardarTrailer(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let trailer=new Object();

    if(document.getElementById("txtNumeroTrailer").value.trim().length<1){
        trailer.numeroTrailer=0;
    } else{
        trailer.numeroTrailer=document.getElementById("txtNumeroTrailer").value;
    }

    trailer.placa=normalizar(sanitizar(document.getElementById("txtPlaca").value));
    trailer.marca=normalizar(sanitizar(document.getElementById("txtMarca").value));
    trailer.modelo=normalizar(sanitizar(document.getElementById("txtModelo").value));
    trailer.fotografia=document.getElementById("codigoFotografiaTrailer").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosTrailer: JSON.stringify(trailer),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);

    fetch("../../api/trailer/save",{
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
                            Swal.fire('', "Trailer guardado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function limpiarTrailer(){
    document.getElementById("imgTrailer").src="";
    document.getElementById("imagenSeleccionadaTrailer").value="";
    document.getElementById("codigoFotografiaTrailer").value="";
    document.getElementById("txtNumeroTrailer").value="";
    document.getElementById("txtMarca").value="";
    document.getElementById("txtModelo").value="";
    document.getElementById("txtPlaca").value="";
    document.getElementById("txtEstatusTrailer").value="";
    document.getElementById("txtEnRutaTrailer").value="";
    
    document.querySelector("#btnLimpiarDatosTrailer").disabled=true;
    document.querySelector("#btnEnviarDatosTrailer").disabled=true;
    
    let cuerpo=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosTrailer" onclick="modulo.guardarTrailer();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosTrailer" onclick="modulo.limpiar();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    document.getElementById("divBtnsTrailer").innerHTML=cuerpo;
}
