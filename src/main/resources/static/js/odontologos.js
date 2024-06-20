function listarOdontologos() {
    fetch('/odontologos/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al listar odontólogos');
            }
            return response.json();
        })
        .then(data => {
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = '<h2>Lista de Odontólogos Registrados</h2>';
            let lista = '<ul>';
            data.forEach(odontologo => {
                lista += `<li>${odontologo.id} ${odontologo.nombre} ${odontologo.apellido}</li>`;
            });
            lista += '</ul>';
            contenido.innerHTML += lista;
        });
}

function buscarOdontologoPorId() {
    let idOdontologo = prompt('Ingrese el ID del odontólogo a buscar:');
    if (!idOdontologo) {
        alert('ID de odontólogo no válido');
        return;
    }
    let id = parseInt(idOdontologo);

    fetch(`/odontologos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Odontólogo no encontrado');
            }
            return response.json();
        })
        .then(data => {
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = `<h2>Odontólogo Encontrado</h2>
                                   <p>ID: ${data.id}</p>
                                   <p>Nombre: ${data.nombre}</p>
                                   <p>Apellido: ${data.apellido}</p>
                                   <p>Matrícula: ${data.matricula}</p>`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('No se encontró ningún odontólogo con el ID' + id);
        });
}

function mostrarFormularioRegistro() {
    let contenido = document.getElementById('contenido');
    contenido.innerHTML = `<h2>Registrar Odontólogo</h2>
                           <form id="odontologoForm">
                               <input type="text" id="nombre" placeholder="Nombre" required>
                               <input type="text" id="apellido" placeholder="Apellido" required>
                               <input type="text" id="matricula" placeholder="Matrícula" required>
                               <button type="submit">Registrar</button>
                           </form>`;
    document.getElementById('odontologoForm').addEventListener('submit', registrarOdontologo);
}

function registrarOdontologo(event) {
    event.preventDefault();
    let nombre = document.getElementById('nombre').value;
    let apellido = document.getElementById('apellido').value;
    let matricula = document.getElementById('matricula').value;


    let odontologo = {
        nombre: nombre,
        apellido: apellido,
        matricula: matricula
    };

    fetch('/odontologos/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(odontologo)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar odontólogo');
            }
            return response.json();
        })
        .then(() => {
            alert('Odontólogo registrado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al registrar el odontólogo');
        });
}

function eliminarOdontologo() {
    let idOdontologo = prompt('Ingrese el ID del odontólogo a eliminar:');
    if (!idOdontologo) {
        alert('ID de odontólogo no válido');
        return;
    }
    let id = parseInt(idOdontologo); // Convertir a número entero

    fetch(`/odontologos/eliminar?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar odontólogo');
            }
            alert('Odontólogo eliminado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al eliminar el odontólogo');
        });
}