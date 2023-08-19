
async function encriptar(texto){
    const encoder=new TextEncoder();
    const data=encoder.encode(texto);
    const hash=await crypto.subtle.digest('SHA-256', data);
    const hashArray=Array.from(new Uint8Array(hash));
    const hashHex=hashArray.map((b)=>b.toString(16).padStart(2, '0')).join('');
    return hashHex;
}

function sanitizar(texto){
    for(var i=0; i<texto.length; i++){
        texto=texto.replace("(", "");
        texto=texto.replace(")", "");
        texto=texto.replace(";", "");
        texto=texto.replace("'", "");
        texto=texto.replace("/", "");
        texto=texto.replace("-", "");
        texto=texto.replace("*", "");
        texto=texto.replace("%", "");
        texto=texto.replace("<", "");
        texto=texto.replace(">", "");        
    }
    return texto;
}

function normalizar(texto){
    texto=texto.toUpperCase();
    for(var i=0; i<texto.length; i++){
        texto=texto.replace("Á", "A");
        texto=texto.replace("É", "E");
        texto=texto.replace("Í", "I");
        texto=texto.replace("Ó", "O");
        texto=texto.replace("Ú", "U");
    }
    return texto;
}

function ingresar(){
    document.querySelector(".spinner-container").classList.remove("d-none");
    
    let usuario=sanitizar(document.getElementById("idUsuario").value);
    let contrasenia=sanitizar(document.getElementById("idPassword").value);
    
    encriptar(contrasenia)
            .then((textoEncriptado)=>{
                let datos=JSON.stringify({nombreUsuario: usuario, contrasenia: textoEncriptado.toString()});
                params=new URLSearchParams({datos: datos});
                fetch("api/log/in",{
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
                                        activarAyudas();
                                        document.getElementById("idUsuario").value="";
                                        document.getElementById("idPassword").value="";
                                    } else if(data.exception){
                                        Swal.fire('', "Error interno del servidor.", 'error');
                                        document.querySelector(".spinner-container").classList.add("d-none");
                                        activarAyudas();
                                        document.getElementById("idUsuario").value="";
                                        document.getElementById("idPassword").value="";
                                    }else{
                                        Swal.fire('', "Bienvenido "+data.nombre+" "+data.primerApellido, 'success');
                                        localStorage.setItem('currentUser', JSON.stringify(data));
                                        document.querySelector(".spinner-container").classList.add("d-none");
                                        window.location.replace("modules/main/vista_main.html");
                                    }
                                });
    });
}

function activarAyudas(){
    document.getElementById("divUsuario").classList.add("was-validated");
    document.getElementById("divContrasenia").classList.add("was-validated");
        
    document.getElementById("divMensajeUsuario").classList.remove("d-none");
    document.getElementById("divMensajeContrasenia").classList.remove("d-none");
}
