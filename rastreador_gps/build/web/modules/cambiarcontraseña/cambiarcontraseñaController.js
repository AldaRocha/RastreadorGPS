
export function inicializar(){
    mostrarToastContrasenia();
}

export function mostrarToastContrasenia(){
    var toastObligatorio=document.getElementById("toastContrasenia");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export function habilitarConfirmarContraseña(){
    let contrasenia=document.getElementById("txtContraseniaNueva").value;
    if(contrasenia.length>7){
        document.getElementById("txtConfirmarContraseniaNueva").disabled=false;
    } else{
        document.getElementById("txtConfirmarContraseniaNueva").disabled=true;
    }
}

export function habilitarBtn(){
    let contrasenia=document.getElementById("txtContraseniaNueva").value;
    let contraseniaConfirmar=document.getElementById("txtConfirmarContraseniaNueva").value;
    if(contrasenia.length===contraseniaConfirmar.length){
        document.getElementById("btnCambiarContrasenia").disabled=false;
    } else{
        document.getElementById("btnCambiarContrasenia").disabled=true;
    }
}

export async function cambiarContrasenia(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let contrasenia=sanitizar(document.getElementById("txtContraseniaNueva").value);
    let confirmarContrasenia=sanitizar(document.getElementById("txtConfirmarContraseniaNueva").value);
    
    if(contrasenia === confirmarContrasenia){
        let datos=null;
        let params=null;

        let empleado=new Object();
        empleado.usuario=new Object();
    
        let currentUser=localStorage.getItem('currentUser');
        let emp=JSON.parse(currentUser);
        
        if(emp===null){
            empleado.usuario.idUsuario=emp.usuario.idUsuario;
            let contraseniaEncriptada=await encriptar(confirmarContrasenia);
            empleado.usuario.contrasenia=contraseniaEncriptada;

            const tokenn="ñ";
            datos={
                datosEmpleado: JSON.stringify(empleado),
                token: tokenn
            };
        } else{
            empleado.usuario.idUsuario=emp.usuario.idUsuario;
            let contraseniaEncriptada=await encriptar(confirmarContrasenia);
            empleado.usuario.contrasenia=contraseniaEncriptada;
            
            const tokennn=emp.usuario.lastToken;
            datos={
                datosEmpleado: JSON.stringify(empleado),
                token: tokennn
            };
        }
        
        params=new URLSearchParams(datos);
        
        fetch("../../api/empleado/cambiarContrasenia",{
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
                                Swal.fire('', "La contraseña se cambio con exito", 'success');
                                document.querySelector(".spinner-container").classList.add("d-none");
                            inicializar();
                            }
                        });
    } else{
        Swal.fire('', 'Las contraseñas no coinciden', 'warning');
        document.getElementById("txtContraseniaNueva").value="";
        document.getElementById("txtConfirmarContraseniaNueva").value="";
        
        document.getElementById("txtConfirmarContraseniaNueva").disabled=true;
        document.getElementById("btnCambiarContrasenia").disabled=true;
        document.querySelector(".spinner-container").classList.add("d-none");
    }
}
