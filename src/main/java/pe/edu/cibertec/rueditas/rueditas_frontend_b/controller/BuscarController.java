package pe.edu.cibertec.rueditas.rueditas_frontend_b.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.rueditas.rueditas_frontend_b.dto.BuscarRequestDTO;
import pe.edu.cibertec.rueditas.rueditas_frontend_b.dto.BuscarResponseDTO;
import pe.edu.cibertec.rueditas.rueditas_frontend_b.viewmodel.BuscarModel;

@Controller
@RequestMapping("/inicio")
public class BuscarController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/buscar")
    public String buscar(Model model){
        BuscarModel buscarModel = new BuscarModel("00", "", "", "",
                0, 0, "");
        model.addAttribute("buscarModel", buscarModel);
        return "buscar";
    }

    @PostMapping("/resultado")
    public String resultado(Model model,
                            @RequestParam("numeroPlaca") String numeroPlaca) {

        // Validando campos de entrada
        if (numeroPlaca == null || numeroPlaca.trim().length() == 0 ){

            BuscarModel buscarModel = new BuscarModel("01",
                    "Error: Es necesario ingresar un número de placa", "", "", 0,
                    0, "");
            model.addAttribute("buscarModel", buscarModel);
            return "buscar";

        }

        try {
            String endpoint = "http://localhost:8081/busqueda/resultado";
            BuscarRequestDTO buscarRequestDTO = new BuscarRequestDTO(numeroPlaca);
            BuscarResponseDTO buscarResponseDTO = restTemplate.postForObject(endpoint, buscarRequestDTO, BuscarResponseDTO.class);

            if (buscarResponseDTO.codigo().equals("00")){
                BuscarModel buscarModel =new BuscarModel("00", "", buscarResponseDTO.marca(), buscarResponseDTO.modelo(), buscarResponseDTO.nroAsientos(), buscarResponseDTO.precio(), buscarResponseDTO.color());
                model.addAttribute("buscarModel", buscarModel);
                return "resultado";
            } else {
                BuscarModel buscarModel = new BuscarModel("02", "Error: Vehiculo no encontrado","", "",
                        0, 0, "");
                model.addAttribute("buscarModel", buscarModel);
                return "buscar";
            }
        } catch (Exception e){
            // Manejando errores por si falla el servicio
            BuscarModel buscarModel = new BuscarModel("99", "Error: Ocurrió un problema con la búsqueda","", "",
                    0, 0, "");
            model.addAttribute("buscarModel", buscarModel);
            return "buscar";
        }
    }


}
