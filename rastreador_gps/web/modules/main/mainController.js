let permisos=[];
let modulo;

function inicializar(){
    pedirLinks();
}

function pedirLinks(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    let empleado={"empleado": em};
    let params=new URLSearchParams(empleado);

    fetch("../../api/nivel/gestion",{
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
                            let cuerpoDropdownMenu=`<li>
                                                        <a class="dropdown-item title navHover" onclick="cerrarSesion();">
                                                            Cerrar Sesión
                                                        </a>
                                                    </li>`;                            
                            document.getElementById("dropdownMenu").innerHTML=cuerpoDropdownMenu;
                        } else if(data.exception){
                            Swal.fire('', "Error interno del servidor.", 'error');
                            document.querySelector(".spinner-container").classList.add("d-none");
                            let cuerpoDropdownMenu=`<li>
                                                        <a class="dropdown-item title navHover" onclick="cerrarSesion();">
                                                            Cerrar Sesión
                                                        </a>
                                                    </li>`;                            
                            document.getElementById("dropdownMenu").innerHTML=cuerpoDropdownMenu;
                        } else{
                            llenarPantallaMain(data);
                        }
                    });
}

function llenarPantallaMain(data){
    let cuerpoNormales="";
    let cuerpoDropdownMenu="";
    
    permisos=data;

    let currentUser=localStorage.getItem('currentUser');
    let emp=JSON.parse(currentUser);
    let usuario=emp.usuario.nombreUsuario;
    
    document.getElementById("nombreUsuarioSession").innerHTML=usuario;

    permisos.forEach(function(permiso){
        if(permiso.permiso.idPermiso === 1 && permiso.permiso.estatus === 1){
            let registroNormales=`<li class="nav-item navHover mx-2">
                                    <a class="nav-link title navHover" href="`+permiso.permiso.ruta+`">
                                        `+permiso.permiso.nombrePermiso+`
                                    </a>
                                  </li>`;
            cuerpoNormales+=registroNormales;
        } else if(permiso.permiso.idPermiso <= 5 && permiso.permiso.estatus === 1){
            let registroNormales=`<li class="nav-item navHover mx-2">
                                    <a class="nav-link title navHover" onclick="pantalla(`+permisos.indexOf(permiso)+`);">
                                        `+permiso.permiso.nombrePermiso+`
                                    </a>
                                  </li>`;
            cuerpoNormales+=registroNormales;
        } else if(permiso.permiso.estatus === 1){
            let registroDropdownMenu=`<li>
                                        <a class="dropdown-item title navHover" onclick="pantalla(`+permisos.indexOf(permiso)+`);">
                                            `+permiso.permiso.nombrePermiso+`
                                        </a>
                                      </li>`;
            cuerpoDropdownMenu+=registroDropdownMenu;
        }
    });
    let registroCerrarSesion=`<li>
                                <a class="dropdown-item title navHover" onclick="cerrarSesion();">
                                    Cerrar Sesión
                                </a>
                              </li>`;
    cuerpoDropdownMenu+=registroCerrarSesion;
    
    document.getElementById("normales").innerHTML=cuerpoNormales;
    document.getElementById("dropdownMenu").innerHTML=cuerpoDropdownMenu;
    
    document.querySelector(".spinner-container").classList.add("d-none");
}

function pantalla(idPantalla){
    ruta=permisos[idPantalla].permiso.ruta;
    nombre=permisos[idPantalla].permiso.nombrePermiso;
    js=nombre.toLowerCase().replace(/\s/g, "");
    document.getElementById("contenedorPrincipal").classList.remove("tamanio");
    fetch(ruta)
            .then(
                function(response){
                    return response.text();
                }
            )
                .then(
                    function(html){
                        document.getElementById("contenedorPrincipal").innerHTML=html;
                        import("../"+js+"/"+js+"Controller.js").then(
                                function(controller){
                                    modulo=controller;
                                    modulo.inicializar();
                                }
                                );
                    }
                );
}

function cerrarSesion(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let em=localStorage.getItem('currentUser');
    if(em!==null){
        let empleado={"empleado": em};
        let params=new URLSearchParams(empleado);
    
        fetch("../../api/log/out",{
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
                                Swal.fire('', "Sesion cerrada con exito", 'success');
                                localStorage.removeItem('currentUser');
                                document.querySelector(".spinner-container").classList.add("d-none");
                                window.location.replace("../../index.html");
                            }
                        });
    } else{        
        document.querySelector(".spinner-container").classList.add("d-none");
        window.location.replace("../../index.html");
    }
}
