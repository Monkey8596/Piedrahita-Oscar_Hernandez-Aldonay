function listarPacientes() {
    fetch('/pacientes/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al listar pacientes');
            }
            return response.json();
        })

        .then(data => {
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = '<h2>Lista de Pacientes Registrados</h2>';
            let lista = '<ul>';
            data.forEach(paciente => {
                lista += `<li>${paciente.id} ${paciente.nombre} ${paciente.apellido}</li>`;
            });
            lista += '</ul>';
            contenido.innerHTML += lista;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al listar los pacientes');
        });
}

function buscarPacientePorId() {
    let idPaciente = prompt('Ingrese el ID del paciente a buscar:');
    if (!idPaciente) {
        alert('ID de paciente no válido');
        return;
    }
    let id = parseInt(idPaciente);

    fetch(`/pacientes/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('No se encontró ningún paciente con el ID: ' + id);
            }
            return response.json();
        })
        .then(data => {
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = `<h2>Paciente Encontrado</h2>
                                   <p>ID: ${data.id}</p>
                                   <p>Nombre: ${data.nombre}</p>
                                   <p>Apellido: ${data.apellido}</p>
                                   <p>DNI: ${data.dni}</p>
                                   <p>Fecha de Ingreso: ${data.fechaIngreso}</p>
                                   <p>Domicilio:</p>
                                   <p>Calle: ${data.domicilioSalidaDto.calle}</p>
                                   <p>Número: ${data.domicilioSalidaDto.numero}</p>
                                   <p>Localidad: ${data.domicilioSalidaDto.localidad}</p>
                                   <p>Provincia: ${data.domicilioSalidaDto.provincia}</p>`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('No se encontró ningún paciente con el ID: ' + id);
        });
}

function mostrarFormularioRegistro() {
    let contenido = document.getElementById('contenido');
    contenido.innerHTML = `<h2>Ingrese los datos del paciente</h2>
                           <form id="pacienteForm">
                               <input type="text" id="nombre" placeholder="Nombre" required>
                               <input type="text" id="apellido" placeholder="Apellido" required>
                               <input type="text" id="dni" placeholder="DNI" required>
                               <p>Fecha de Ingreso:</p>
                               <input type="date" id="fechaIngreso" placeholder="Fecha de Ingreso" required>
                               <p>Domicilio:</p>
                               <input type="text" id="calle" placeholder="Calle" required>
                               <input type="text" id="numero" placeholder="Número" required>
                                <input type="text" id="localidad" placeholder="Localidad" required>
                               <input type="text" id="provincia" placeholder="Provincia" required>
                               
                               <button type="submit">Registrar</button>
                           </form>`;
    document.getElementById('pacienteForm').addEventListener('submit', registrarPaciente);
    let yesterday = new Date(Date.now() - 86400000).toISOString().split('T')[0]; // 86400000 milisegundos en un día
    document.getElementById('fechaIngreso').setAttribute('min', yesterday);
}

function registrarPaciente(event) {
    event.preventDefault();
    let nombre = document.getElementById('nombre').value;
    let apellido = document.getElementById('apellido').value;
    let dni = parseInt(document.getElementById('dni').value);
    let fechaIngreso = document.getElementById('fechaIngreso').value;
    let calle = document.getElementById('calle').value;
    let numero = parseInt(document.getElementById('numero').value);
    let localidad = document.getElementById('localidad').value;
    let provincia = document.getElementById('provincia').value;

    let yesterday = new Date(Date.now() - 86400000).toISOString().split('T')[0];

    if (fechaIngreso < yesterday) {
        alert('La fecha de ingreso no puede ser anterior a la fecha actual.');
        return;
    }

    let paciente = {
        nombre: nombre,
        apellido: apellido,
        dni: dni,
        fechaIngreso: fechaIngreso,
        domicilioEntradaDto: {
            calle: calle,
            numero: numero,
            localidad: localidad,
            provincia: provincia
        }
    };


    fetch('/pacientes/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(paciente)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar paciente');
            }
            return response.json();
        })
        .then(() => {
            alert('Paciente registrado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al registrar el paciente');
        });
}

function eliminarPaciente() {
    let id = prompt('Ingrese el ID del paciente a eliminar:');
    if (!id) {
        alert('ID de paciente no válido');
        return;
    }
    id = parseInt(id); // Convertir a número entero

    fetch(`/pacientes/eliminar?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar paciente');
            }
            alert('Paciente eliminado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al eliminar el paciente');
        });
}