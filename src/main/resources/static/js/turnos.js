function listarTurnos() {
    fetch('/turnos/listar')
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al listar turnos');
            }
            return response.json();
        })
        .then(data => {
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = '<h2>Lista de Turnos Registrados</h2>';
            let lista = '<ul>';
            data.forEach(turno => {
                let [fecha, hora] = turno.fechaYHora.split('T');
                lista += `<li># ${turno.id}, Odontólogo ID: ${turno.odontologoSalidaDto.id}, Paciente ID: ${turno.pacienteSalidaDto.id}, Fecha: ${fecha}, Hora: ${hora}</li>`;
            });
            lista += '</ul>';
            contenido.innerHTML += lista;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al listar los turnos');
        });
}

function buscarTurnoPorId() {
    let idTurno = prompt('Ingrese el ID del turno a buscar:');
    if (!idTurno) {
        alert('ID de Turno no válido');
        return;
    }
    let id = parseInt(idTurno);
    fetch(`/turnos/${id}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Turno no encontrado')
            }
            return response.json();
        })
        .then(data => {
            let [fecha, hora] = data.fechaYHora.split('T');
            let contenido = document.getElementById('contenido');
            contenido.innerHTML = `<h2>Turno Encontrado</h2>
                                   <p>ID: ${data.id}</p>
                                   <p>Fecha: ${fecha}</p>
                                   <p>Hora: ${hora}</p>
                                   <p>Odontólogo ID: ${data.odontologoSalidaDto.id}</p>
                                   <p>Paciente ID: ${data.pacienteSalidaDto.id}</p>`;
        })
        .catch(error => {
            console.error('Error:', error);
            alert('No se encontró ningún turno con el ID: ' + id);
        });
}

function mostrarFormularioRegistro() {
    let contenido = document.getElementById('contenido');
    contenido.innerHTML = `<h2>Registrar Turno</h2>
                           <form id="turnoForm">
                               <p>Selecciona una fecha: fecha:</p>
                               <input type="date" id="fecha" required>
                               <p>Selecciona una hora:</p>
                               <input type="time" id="hora" required>
                               <input type="text" id="pacienteId" placeholder="ID del Paciente" required>
                               <input type="text" id="odontologoId" placeholder="ID del Odontólogo" required>
                               <button type="submit">Registrar</button>
                           </form>`;
    document.getElementById('turnoForm').addEventListener('submit', registrarTurno);
    let today = new Date().toISOString().split('T')[0];
    document.getElementById('fecha').setAttribute('min', today);
}

function registrarTurno(event) {
    event.preventDefault();
    let fecha = document.getElementById('fecha').value;
    let hora = document.getElementById('hora').value;
    let pacienteId = parseInt(document.getElementById('pacienteId').value);
    let odontologoId = parseInt(document.getElementById('odontologoId').value);

    let fechaYHora = `${fecha}T${hora}:00`;

    let turno = {
        fechaYHora: fechaYHora,
        pacienteId: pacienteId,
        odontologoId: odontologoId
    };

    fetch('/turnos/registrar', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(turno)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al registrar turno');
            }
            return response.json();
        })
        .then(() => {
            alert('Turno registrado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al registrar el turno');
        });
}

function eliminarTurno() {
    let idTurno = prompt('Ingrese el ID del turno a eliminar:');
    if (!idTurno) {
        alert('ID de turno no válido');
        return;
    }
    let id = parseInt(idTurno);
    fetch(`/turnos/eliminar?id=${id}`, {
        method: 'DELETE'
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Error al eliminar turno');
            }
            alert('Turno eliminado correctamente');
            document.getElementById('contenido').innerHTML = '';
        })
        .catch(error => {
            console.error('Error:', error);
            alert('Hubo un problema al eliminar el turno');
        });
}
