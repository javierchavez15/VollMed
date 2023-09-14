package med.voll.api.domain.consulta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegridad;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class AgendaDeConsultasService {

    @Autowired
    private PacienteRepository pacienteRepository;


    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private ConsultaRepository consultaRepository;

    public void agendar(DatosAgendarConsulta datos){

        if(pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegridad("Este id del paciente no fue encontrado");
        }

        if(datos.idMedico()!=null && medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegridad("Este id del medico no fue encontrado");
        }

        var paciente=pacienteRepository.findById(datos.idPaciente()).get();
    
        var medico=seleccionarMedico(datos);

        var consulta= new Consulta(null, medico, paciente,datos.fecha());
        
        consultaRepository.save(consulta);
    }

    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if(datos.idMedico()!=null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if(datos.especialidad()==null)
        throw new ValidacionDeIntegridad("Debe seleccionar una especialida para el medico");
        return medicoRepository.seleccionarMedicoConEspecialidadenFecha(datos.especialidad(), datos.fecha());
    }

}
