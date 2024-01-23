package com.vitau.uativ.vitau_vrp.Service;
import java.util.ArrayList;

import com.vitau.uativ.vitau_vrp.Algorithm.Vrp;
import com.vitau.uativ.vitau_vrp.Algorithm.VrpResponse;
import com.vitau.uativ.vitau_vrp.Algorithm.Node;
import com.vitau.uativ.vitau_vrp.DTO.VrpDTO;
import com.google.gson.JsonObject;
import com.vitau.uativ.vitau_vrp.Algorithm.GetMatrizTempo;

/**
 * Serviço para execução do algoritmo de Roteamento Veicular (VRP - Vehicle Routing Problem).
 */
public class VrpService {

  /**
   * Executa o algoritmo de VRP com base nos dados fornecidos.
   *
   * @param request Um array de objetos VrpDTO contendo informações sobre os pontos no VRP.
   * @return Uma lista de objetos Node representando a solução do VRP após a execução do algoritmo.
   */
  public VrpResponse runAlgo(VrpDTO[] request) {
    try {
      GetMatrizTempo.getMatrizTempo(request);
    } catch (Exception e) {
        e.printStackTrace();
        
    }
    return Vrp.algo(request);
  }

}