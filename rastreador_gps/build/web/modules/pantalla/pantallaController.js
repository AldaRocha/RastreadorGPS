let pantallas=[];

export function inicializar(){
    mostrarToastPantalla();
    pedirDatos();
}

export function mostrarToastPantalla(){
    var toastObligatorio=document.getElementById("toastPantalla");
    var toast=new bootstrap.Toast(toastObligatorio);
    toast.show();
}

export function pedirDatos(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);
    
    fetch("../../api/permiso/getAll",{
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
                            ajustarCheckbox(data);
                        }
                    });
}

export function ajustarCheckbox(data){
    pantallas=data;
    
    pantallas.forEach(function(pantalla){
        let panta=pantalla.nombrePermiso.replace(/\s/g, "");
        
        if(pantalla.idPermiso === 2 || pantalla.idPermiso === 3 || pantalla.idPermiso === 4 || 
                                                pantalla.idPermiso === 5 || pantalla.idPermiso === 8){
            if(pantalla.estatus === 1){
                document.getElementById("chkPantalla"+panta).checked=true;
            } else{
                document.getElementById("chkPantalla"+panta).checked=false;
            }
        }
    });
    
    document.querySelector(".spinner-container").classList.add("d-none");
}

export function guardarPantallas(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let datos=null;
    let params=null;
    
    let permiso=[];
    
    pantallas.forEach(function(pantalla){
        let panta=pantalla.nombrePermiso.replace(/\s/g, "");
        
        if(pantalla.idPermiso === 2 || pantalla.idPermiso === 3 || pantalla.idPermiso === 4 || 
                                                pantalla.idPermiso === 5 || pantalla.idPermiso === 8){
            if(document.getElementById("chkPantalla"+panta).checked){
                pantalla.estatus = 1;
            } else{
                pantalla.estatus = 0;
            }
        }
        permiso.push(pantalla);
    });
    
    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    if(emp === null){
        const tokenn="ñ";
        datos={
            datosPantallas: JSON.stringify(permiso),
            token: tokenn
        };
    } else{
        const tokennn=emp.usuario.lastToken;
        datos={
            datosPantallas: JSON.stringify(permiso),
            token: tokennn
        };
    }
    params=new URLSearchParams(datos);
    
    fetch("../../api/permiso/actualizarPantallas",{
        method: "POST",
        headers: {'Content-Type':'application/x-www-form-urlencoded;charset=UTF-8'},
        body: params.toString()
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
                            Swal.fire('Pantallas guardadas con exito', "Regresa a inicio para actualizarlas", 'success');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            pedirLinks();
                        }
                    });
}
