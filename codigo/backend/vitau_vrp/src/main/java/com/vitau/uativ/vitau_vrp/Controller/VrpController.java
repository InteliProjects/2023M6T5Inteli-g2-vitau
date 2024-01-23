package com.vitau.uativ.vitau_vrp.Controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.vitau.uativ.vitau_vrp.DTO.VrpDTO;
import com.vitau.uativ.vitau_vrp.Service.VrpService;
import com.google.gson.JsonObject;
import com.vitau.uativ.vitau_vrp.Algorithm.Node;
import com.vitau.uativ.vitau_vrp.Algorithm.VrpResponse;

/**
 * Controlador responsável por manipular as requisições relacionadas ao Problema do Roteamento de Veículos (VRP).
 */
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/api/vrp")
public class VrpController {

    /**
     * Resolve o Problema do Roteamento de Veículos (VRP) com base nos dados fornecidos.
     *
     * @param request Um array de objetos VrpDTO representando os dados do VRP.
     * @return ResponseEntity contendo a resposta da solicitação. Neste caso, uma lista de nodos representando o resultado do algoritmo.
     */
    @PostMapping("/solve")
    @CrossOrigin(origins = "*", allowedHeaders = { "*" })
    public ResponseEntity<?> solveVrp(@RequestBody VrpDTO[] request) {

        VrpService algo = new VrpService();

        // Executa o algoritmo e obtém uma lista de nodos
        VrpResponse res = algo.runAlgo(request);
        // Retorna a resposta como ResponseEntity
        return ResponseEntity.ok(res);
    }
}
