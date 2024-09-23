package pe.edu.cibertec.rueditas.rueditas_frontend_b.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pe.edu.cibertec.rueditas.rueditas_frontend_b.viewmodel.BuscarModel;

@Controller
@RequestMapping("/inicio")
public class BuscarController {

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

        BuscarModel buscarModel = new BuscarModel("00", "", "KIA", "Sportage",
                5, 26000, "Negro");
        model.addAttribute("buscarModel", buscarModel);
        return "resultado";
    }


}
