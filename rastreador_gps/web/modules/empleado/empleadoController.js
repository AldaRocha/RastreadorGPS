let empleados=[];
let niveles=[];
let inputImagen=null;

export function inicializar(){
    habilitarBtnEliAct(2);
    setDetalleVisible(true);
    pedirDatos();
    pedirNiveles();    
    configureTableFilter(document.getElementById("txtBuscarEmpleado"), document.getElementById("tblEmpleados"));
    inputImagen=document.getElementById("imagenSeleccionada");
    inputImagen.onchange=function(e){
        cargarFotografia(inputImagen);
        habilitarBtnLimpiar();
    };
}

export function pedirDatos(){
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
                            llenarTablaEmpleados(data);
                        }
                    });
}

export function llenarTablaEmpleados(data){
    let cuerpo="";
    let activo="";
    let enRuta="";
    empleados=data;
    empleados.forEach(function(empleado){
        if(empleado.estatus === 1){
            activo="Si";
        } else{
            activo="No";
        }
        if(empleado.enRuta === 1){
            enRuta="Si";
        } else{
            enRuta="No";
        }
        let datos='<tr onclick="modulo.seleccionarEmpleado('+empleados.indexOf(empleado)+');">'+
                  '<td>'+empleado.numeroEmpleado+'</td>'+
                  '<td>'+empleado.nombre+' '+empleado.primerApellido+' '+empleado.segundoApellido+'</td>'+
                  '<td>'+empleado.telCelular+'</td>'+
                  '<td>'+activo+'</td>'+
                  '<td>'+enRuta+'</td>'+
                  '<td>'+empleado.usuario.nivel.nombreNivel+'</td>'+
                  '</tr>';
        cuerpo+=datos;
    });
    document.getElementById("tbodyEmpleados").innerHTML=cuerpo;
    document.querySelector(".spinner-container").classList.add("d-none");
    mostrarToastEmpleado();
}

export async function seleccionarEmpleado(index){
    await setDetalleVisible(false);
    let activo="";
    let enRuta="";
    if(empleados[index].estatus === 1){
            activo="Si";
        } else{
            activo="No";
        }
    if(empleados[index].enRuta === 1){
        enRuta="Si";
    } else{
        enRuta="No";
    }
    
    habilitarBtnEliAct(empleados[index].estatus);
    
    document.getElementById("codigoFotografia").value=empleados[index].fotografia;
    let fotoB64="data:image/png;base64,"+document.getElementById("codigoFotografia").value;
    document.getElementById("imgEmpleado").src=fotoB64;
    document.getElementById("txtNumeroEmpleado").value=empleados[index].numeroEmpleado;
    document.getElementById("txtNombreEmpleado").value=empleados[index].nombre;
    document.getElementById("txtPrimerApellido").value=empleados[index].primerApellido;
    document.getElementById("txtSegundoApellido").value=empleados[index].segundoApellido;
    document.getElementById("txtCurp").value=empleados[index].curp;
    document.getElementById("txtRfc").value=empleados[index].rfc;
    document.getElementById("txtNumeroSeguroSocial").value=empleados[index].numeroSeguroSocial;
    var fechaIngreso=empleados[index].fechaIngreso;
    var fechaIngresoConvertida=convertirFecha(fechaIngreso);
    document.getElementById("txtFechaIngreso").value=fechaIngresoConvertida;
    var fechaBaja=empleados[index].fechaBaja;
    var fechaBajaConvertida=convertirFecha(fechaBaja);
    document.getElementById("txtFechaBaja").value=fechaBajaConvertida;
    document.getElementById("cmbTipoLicencia").value=empleados[index].tipoLicencia;
    document.getElementById("txtNombreCalle").value=empleados[index].calle;
    document.getElementById("txtNumeroCalle").value=empleados[index].numeroExterior;
    document.getElementById("txtNombreColonia").value=empleados[index].colonia;
    document.getElementById("txtCiudad").value=empleados[index].ciudad;
    document.getElementById("txtEstado").value=empleados[index].estado;
    document.getElementById("txtTelCelular").value=empleados[index].telCelular;
    document.getElementById("txtTelEmergencia").value=empleados[index].telEmergencia;
    document.getElementById("txtEstatus").value=activo;
    document.getElementById("txtEnRuta").value=enRuta;
    document.getElementById("txtIdUsuario").value=empleados[index].usuario.idUsuario;
    document.getElementById("txtNombreUsuario").value=empleados[index].usuario.nombreUsuario;
    document.getElementById("txtContrasenia").value=empleados[index].usuario.contrasenia;
    document.getElementById("cmbNivel").value=empleados[index].usuario.nivel.nombreNivel;
    
    document.getElementById("txtContrasenia").type="text";
    document.getElementById("txtContrasenia").readonly=true;
    document.getElementById("txtContrasenia").disabled=true;
    
    document.getElementById("btnLimpiarDatosEmpleado").disabled=false;    
}

export function habilitarBtnEliAct(estatus){
    let cuerpo=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosEmpleado" onclick="modulo.guardar();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosEmpleado" onclick="modulo.limpiar();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    if(estatus===0 || estatus===1){
        if(estatus===1){
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-danger align-content-center" id="btnEliminarEmpleado" onclick="modulo.eliminar();">
                                Deshabilitar empleado
                            </button>
                          </div>`;
            cuerpo+=registro;
        } else{
            let registro=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                            <button class="btn btn-secondary align-content-center" id="btnActivarEmpleado" onclick="modulo.activar();">
                                Habilitar empleado
                            </button>
                          </div>`;
            cuerpo+=registro;
        }
    }
    
    document.getElementById("divBtns").innerHTML=cuerpo;
}

export function convertirFecha(fecha){
    var fechaConvertida;
    if(fecha!=="" && fecha!==null && fecha!==undefined){
        var partes=fecha.split("/");
        var dia=partes[0];
        var mes=partes[1];
        var anio=partes[2];
    
        fechaConvertida=anio+"-"+mes+"-"+dia;
    } else{
        fechaConvertida="";
    }
    
    return fechaConvertida;
}

export function activar(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let empleado=new Object();
    
    empleado.numeroEmpleado=document.getElementById("txtNumeroEmpleado").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/empleado/activar",{
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
                            Swal.fire('', "Empleado activado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function eliminar(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let empleado=new Object();
    
    empleado.numeroEmpleado=document.getElementById("txtNumeroEmpleado").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/empleado/eliminar",{
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
                            Swal.fire('', "Empleado desactivado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function mostrarToastEmpleado(){
    var toastEmpleado=document.getElementById("toastTablaEmpleado");
    var toast=new bootstrap.Toast(toastEmpleado);
    toast.show();
}

export async function setDetalleVisible(bool){
    if(bool){
        document.getElementById("detalleEmpleado").style.display="";
        document.getElementById("formularioEmpleado").style.display="none";
        limpiar();
    } else{
        document.getElementById("detalleEmpleado").style.display="none";
        document.getElementById("formularioEmpleado").style.display="";
        mostrarToastObligatorio();
    }
}

export function pedirNiveles(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);
    
    fetch("../../api/nivel/niveles",{
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
                            llenarCmbNivel(data);
                        }
                    });
}

export function llenarCmbNivel(data){
    let cuerpo=`<option selected="">
                    Seleccione el rol del empleado:
                </option>`;
    
    niveles=data;
    
    niveles.forEach(function(nivel){
        let datos=`<option value="`+nivel.nombreNivel+`">
                    `+nivel.nombreNivel+`
                   </option>`;
        cuerpo+=datos;
    });
    document.getElementById("cmbNivel").innerHTML=cuerpo;
    document.querySelector(".spinner-container").classList.add("d-none");
}

export function mostrarToastObligatorio(){
    var toastObligatorio=document.getElementById("toastObligatorio");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export function cargarFotografia(file){
    if(file.files && file.files[0]){
        let reader=new FileReader();
        
        reader.onload=function(e){
          let fotoB64=e.target.result;
          document.getElementById("imgEmpleado").src=fotoB64;          
          document.getElementById("codigoFotografia").value=fotoB64.substring(fotoB64.indexOf(",")+1, fotoB64.length);          
        };       
        reader.readAsDataURL(file.files[0]);
    }
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
        document.querySelector("#btnLimpiarDatosEmpleado").disabled=true;
    } else{
        document.querySelector("#btnLimpiarDatosEmpleado").disabled=false;
    }
    habilitarBtnGuardar();
}

export function habilitarBtnGuardar(){
    let foto=document.getElementById("codigoFotografia").value;
    let nombre=document.getElementById("txtNombreEmpleado").value;
    let pa=document.getElementById("txtPrimerApellido").value;
    let sa=document.getElementById("txtSegundoApellido").value;
    let curp=document.getElementById("txtCurp").value;
    let rfc=document.getElementById("txtRfc").value;
    let nss=document.getElementById("txtNumeroSeguroSocial").value;
    let fi=document.getElementById("txtFechaIngreso").value;
    let calle=document.getElementById("txtNombreCalle").value;
    let num=document.getElementById("txtNumeroCalle").value;
    let col=document.getElementById("txtNombreColonia").value;
    let ciu=document.getElementById("txtCiudad").value;
    let est=document.getElementById("txtEstado").value;
    let telcel=document.getElementById("txtTelCelular").value;
    let telem=document.getElementById("txtTelEmergencia").value;
    let nomus=document.getElementById("txtNombreUsuario").value;
    let conn=document.getElementById("txtContrasenia").value;
    
    if(foto!=="" && nombre!=="" && pa!=="" && sa!=="" && curp!=="" && rfc!=="" && nss!=="" && fi!=="" && calle!=="" &&
       num!=="" && col!=="" && ciu!=="" && est!=="" && telcel!=="" && telem!=="" && nomus!=="" && conn!==""){
        document.querySelector("#btnEnviarDatosEmpleado").disabled=false;
    } else{
        document.querySelector("#btnEnviarDatosEmpleado").disabled=true;
    }
}

export function mostrarToastContrasenia(){
    var toastObligatorio=document.getElementById("toastContrasenia");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export async function guardar(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let empleado=new Object();
    empleado.usuario=new Object();
    empleado.usuario.nivel=new Object();
    
    if(document.getElementById("txtNumeroEmpleado").value.trim().length<1){
        empleado.numeroEmpleado="0";
        empleado.usuario.idUsuario=0;
        empleado.usuario.nivel.idNivel=0;
        empleado.usuario.contrasenia=await encriptar(sanitizar(document.getElementById("txtContrasenia").value)); 
    } else{
        empleado.numeroEmpleado=document.getElementById("txtNumeroEmpleado").value;
        empleado.usuario.idUsuario=parseInt(document.getElementById("txtIdUsuario").value);
        empleado.usuario.nivel.idNivel=0;
        empleado.usuario.contrasenia=document.getElementById("txtContrasenia").value;
    }
    
    empleado.usuario.nivel.nombreNivel=document.getElementById("cmbNivel").value;
    
    empleado.usuario.nombreUsuario=sanitizar(document.getElementById("txtNombreUsuario").value);    
    
    empleado.nombre=normalizar(sanitizar(document.getElementById("txtNombreEmpleado").value));
    empleado.primerApellido=normalizar(sanitizar(document.getElementById("txtPrimerApellido").value));
    empleado.segundoApellido=normalizar(sanitizar(document.getElementById("txtSegundoApellido").value));
    empleado.curp=normalizar(sanitizar(document.getElementById("txtCurp").value));
    empleado.rfc=normalizar(sanitizar(document.getElementById("txtRfc").value));
    empleado.numeroSeguroSocial=Number(sanitizar(document.getElementById("txtNumeroSeguroSocial").value));
    empleado.fechaIngreso=formatDate(document.getElementById("txtFechaIngreso").value);
    empleado.tipoLicencia=document.getElementById("cmbTipoLicencia").value;
    empleado.calle=normalizar(sanitizar(document.getElementById("txtNombreCalle").value));
    empleado.numeroExterior=sanitizar(document.getElementById("txtNumeroCalle").value);
    empleado.colonia=normalizar(sanitizar(document.getElementById("txtNombreColonia").value));
    empleado.ciudad=normalizar(sanitizar(document.getElementById("txtCiudad").value));
    empleado.estado=normalizar(sanitizar(document.getElementById("txtEstado").value));
    empleado.telCelular=sanitizar(document.getElementById("txtTelCelular").value);
    empleado.telEmergencia=sanitizar(document.getElementById("txtTelEmergencia").value);
    empleado.fotografia=document.getElementById("codigoFotografia").value;
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosEmpleado: JSON.stringify(empleado),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/empleado/save",{
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
                            Swal.fire('', "Empleado guardado con exito", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                        }
                    });
}

export function formatDate(date){
    const dateParts = date.split('-');
    const year = dateParts[0];
    const month = dateParts[1];
    const day = dateParts[2];
    return `${day}/${month}/${year}`;
}

export function limpiar(){
    document.getElementById("imgEmpleado").src="";
    document.getElementById("imagenSeleccionada").value="";
    document.getElementById("codigoFotografia").value="";
    document.getElementById("txtNumeroEmpleado").value="";
    document.getElementById("txtNombreEmpleado").value="";
    document.getElementById("txtPrimerApellido").value="";
    document.getElementById("txtSegundoApellido").value="";
    document.getElementById("txtCurp").value="";
    document.getElementById("txtRfc").value="";
    document.getElementById("txtNumeroSeguroSocial").value="";
    document.getElementById("txtFechaIngreso").value="";
    document.getElementById("txtFechaBaja").value="";
    document.getElementById("cmbTipoLicencia").selectedIndex=0;
    document.getElementById("txtNombreCalle").value="";
    document.getElementById("txtNumeroCalle").value="";
    document.getElementById("txtNombreColonia").value="";
    document.getElementById("txtCiudad").value="";
    document.getElementById("txtEstado").value="";
    document.getElementById("txtTelCelular").value="";
    document.getElementById("txtTelEmergencia").value="";
    document.getElementById("txtEstatus").value="";
    document.getElementById("txtEnRuta").value="";
    document.getElementById("txtIdUsuario").value="";
    document.getElementById("txtNombreUsuario").value="";
    document.getElementById("txtContrasenia").value="";
    document.getElementById("cmbNivel").selectedIndex=0;
    
    document.querySelector("#btnLimpiarDatosEmpleado").disabled=true;
    document.querySelector("#btnEnviarDatosEmpleado").disabled=true;
    
    document.getElementById("txtContrasenia").type="password";
    document.getElementById("txtContrasenia").readonly=false;
    document.getElementById("txtContrasenia").disabled=false;
    
    let cuerpo=`<div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-success align-content-center" id="btnEnviarDatosEmpleado" onclick="modulo.guardar();" disabled="">
                        Guardar datos
                    </button>
                </div>
                <div class="col-lg-4 mt-3 p-2 align-content-center justify-content-center d-grid">
                    <button class="btn btn-info align-content-center" id="btnLimpiarDatosEmpleado" onclick="modulo.limpiar();" disabled="">
                        Limpiar campos
                    </button>
                </div>`;
    document.getElementById("divBtns").innerHTML=cuerpo;
}
