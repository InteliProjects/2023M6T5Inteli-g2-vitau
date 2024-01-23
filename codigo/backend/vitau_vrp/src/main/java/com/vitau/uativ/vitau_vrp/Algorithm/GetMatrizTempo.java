package com.vitau.uativ.vitau_vrp.Algorithm;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.vitau.uativ.vitau_vrp.DTO.VrpDTO;

/**
 * Classe responsável por obter a matriz de tempo entre diferentes pontos.
 */
public class GetMatrizTempo {

    static List<Array> indexMatriz;
    static double[][] matriz;
    double tempoN1EN2;

    
    /**
     * Obtém a matriz de tempo entre os nodos especificados.
     * 
     * @param nodos Array de VrpDTO representando os nodos.
     * @throws Exception Se ocorrer um erro durante a obtenção da matriz.
     */
    public static void getMatrizTempo(VrpDTO[] nodos) throws Exception {
        //double[][] matriz = null;
    
        String url = "http://localhost:5000/calculate_durations";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
    
        try {
            con.setRequestMethod("POST");
    
            // Configuração do cabeçalho
            con.setRequestProperty("Content-Type", "application/json");
            con.setDoOutput(true);
    
            // Converte a lista de nodos para JSON
            Gson gson = new Gson();
            String jsonInputString = gson.toJson(Collections.singletonMap("dados", nodos));
    
            // Envia os dados no corpo da requisição
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(jsonInputString.getBytes(StandardCharsets.UTF_8));
            }
    
            // Obtenção da resposta
            int responseCode = con.getResponseCode();
    
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            StringBuilder response = new StringBuilder();
            String inputLine;
    
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
    
            // Converte a resposta JSON para uma matriz bidimensional
            JsonParser parser = new JsonParser();
            JsonObject jsonObject = parser.parse(response.toString()).getAsJsonObject();
    
            if (jsonObject.has("matrix")) {
                JsonArray matrixArray = jsonObject.getAsJsonArray("matrix");
                matriz = new double[matrixArray.size()][];
                for (int i = 0; i < matrixArray.size(); i++) {
                    JsonArray rowArray = matrixArray.get(i).getAsJsonArray();
                    matriz[i] = new double[rowArray.size()];
                    for (int j = 0; j < rowArray.size(); j++) {
                        JsonElement element = rowArray.get(j);
                        if (element.isJsonNull()) {
                            matriz[i][j] = Double.POSITIVE_INFINITY;
                        } else {
                            matriz[i][j] = element.getAsDouble();
                        }
                    }
                }
            } else {
                System.out.println("A chave 'matrix' não foi encontrada na resposta JSON.");
            }
        } finally {
            con.disconnect(); 
        }
    }



    /**
     * Retorna o tempo entre dois pontos identificados por seus IDs.
     * 
     * @param idN1 ID do primeiro ponto.
     * @param idN2 ID do segundo ponto.
     * @return O tempo entre os dois pontos.
     */
    public static double getTempoEntrePontos(int idN1, int idN2) {
        return matriz[idN1][idN2];
    }

    

}
