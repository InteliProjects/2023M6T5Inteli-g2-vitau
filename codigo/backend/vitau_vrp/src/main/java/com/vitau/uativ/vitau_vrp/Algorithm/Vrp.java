package com.vitau.uativ.vitau_vrp.Algorithm;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import com.vitau.uativ.vitau_vrp.DTO.VrpDTO;

/**
 * Classe principal para o algoritmo de Roteamento de Veículos.
 */
public class Vrp {

    /**
     * Executa o algoritmo de roteamento de veículos.
     * 
     * @param request Array de VrpDTO contendo dados dos funcionários e ordens.
     * @return Uma resposta do tipo VrpResponse contendo os resultados do algoritmo.
     */
    public static VrpResponse algo (VrpDTO[] request) {
        ArrayList<Funcionario> funcionarios = new ArrayList<Funcionario>();
        ArrayList<Ordem> ordens = new ArrayList<Ordem>();
        int countFunc = 0;
        int countOrdem = 0;
        for (int i = 0; i < request.length; i++) {
            if (request[i].getIsFuncionario()) {
                funcionarios.add(new Funcionario(request[i].getLongitude(), request[i].getLatitude(), request[i].getId(), request[i].getIdentifier()));
                countFunc++;
            } else {
                ordens.add(new Ordem(request[i].getLongitude(), request[i].getLatitude(), request[i].getId(), request[i].getPeriodo(), request[i].getIdentifier()));
                countOrdem++;
            }
        }
        ArrayList<Ordem> ordensNaoAtendidas = new ArrayList<Ordem>();
        for(Ordem ordem : ordens){
            ordensNaoAtendidas.add(ordem);
        }

        System.out.println("Quantidade de funcionários: " + countFunc);
        System.out.println("Quantidade de ordens: " + countOrdem);
        funcionarios = orderByMinCost(funcionarios, ordens);
        System.out.print("Função objetivo normal: ");
        System.out.println(funcaoObjetivo(funcionarios));
        funcionarios = otimizacao(funcionarios);
        System.out.print("Função objetivo otimizada: ");
        System.out.println(funcaoObjetivo(funcionarios));
        funcionarios = largestNeighberhoodSearch(funcionarios);
        System.out.print("Função objetivo mega otimizada: ");
        System.out.println(funcaoObjetivo(funcionarios));

        int numOrdensNaoAtendidas = encontraOrdensNaoAtendidas(ordensNaoAtendidas, funcionarios);

        ArrayList<Node> nodos = returnSchedule(funcionarios);
        VrpResponse vrpResponse = new VrpResponse(nodos, numOrdensNaoAtendidas);
        vrpResponse.setNodos(nodos);
        vrpResponse.setNumOrdensNaoAtendidas(numOrdensNaoAtendidas);

        return vrpResponse;
    }

    /**
     * Ordena os funcionários pelo custo mínimo.
     * 
     * @param funcionarios Lista de funcionários.
     * @param ordens Lista de ordens.
     * @return Lista de funcionários ordenada pelo custo mínimo.
     */
    public static ArrayList<Funcionario> orderByMinCost(ArrayList<Funcionario> funcionarios, ArrayList<Ordem> ordens) {

        while (!ordens.isEmpty()) {
            double menorTempo = Integer.MAX_VALUE;
            Funcionario funcionarioEscolhido = null;
            Ordem ordemEscolhida = null;
            for (Funcionario funcionario : funcionarios) {
                int id = funcionario.getId();
                if (!funcionario.getOrdens().isEmpty()) {
                    id = funcionario.getOrdens().get(funcionario.getOrdens().size() - 1).getId();
                }
                for (Ordem ordem : ordens) {
                    double tempoAtual = GetMatrizTempo.getTempoEntrePontos(id, ordem.getId());
                    if (tempoAtual < menorTempo) {
                        if (funcionario.getDeslocamento() + tempoAtual <= 14400
                            && funcionario.getJornada_de_trabalho() + tempoAtual + 7200 <= 28800
                            && funcionario.getPeriodo().equals(ordem.getPeriodo())) {
                            
                            menorTempo = tempoAtual;
                            funcionarioEscolhido = funcionario;
                            ordemEscolhida = ordem;
                        }
                    }
                }
            }

            if (funcionarioEscolhido != null) {
                funcionarioEscolhido.addOrdem(ordemEscolhida);;
                ordens.remove(ordemEscolhida);
                funcionarioEscolhido.setDeslocamento(funcionarioEscolhido.getDeslocamento() + menorTempo);
                funcionarioEscolhido.setJornada_de_trabalho(funcionarioEscolhido.getJornada_de_trabalho() + menorTempo + 7200);
                if (funcionarioEscolhido.getJornada_de_trabalho() > 14400) {
                    funcionarioEscolhido.setPeriodo("tarde");
                }
            } else {
                boolean flag = false;
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getJornada_de_trabalho() < 14400) {
                        funcionario.setPeriodo("tarde");
                        funcionario.setJornada_de_trabalho(14400);
                        flag = true;
                    }
                }
                if (!flag) {
                    break;
                }
            }
            
        }
        
        return funcionarios;
    }

    /**
     * Ordena os funcionários pelo custo mínimo considerando um único funcionário.
     * 
     * @param funcionarios Lista de funcionários.
     * @param ordens Lista de ordens.
     * @return Lista de funcionários ordenada pelo custo mínimo.
     */
    public static ArrayList<Funcionario> orderByMinCostFuncionario(ArrayList<Funcionario> funcionarios, ArrayList<Ordem> ordens) {

        for (Funcionario funcionario : funcionarios) {
            while (funcionario.getJornada_de_trabalho() <= 28800) {
                double menorTempo = Integer.MAX_VALUE;
                Ordem ordemEscolhida = null;
                for (Ordem ordem : ordens) {
                    double tempoAtual = GetMatrizTempo.getTempoEntrePontos(funcionario.getId(), ordem.getId());
                    if (tempoAtual < menorTempo) {
                        if (funcionario.getDeslocamento() + tempoAtual <= 14400
                            && funcionario.getJornada_de_trabalho() + tempoAtual + 7200 <= 28800
                            && funcionario.getPeriodo().equals(ordem.getPeriodo())) {
                            
                            menorTempo = tempoAtual;
                            ordemEscolhida = ordem;
                            
                        }
                    }
                }
                if (ordemEscolhida == null) {
                    break;
                }
                funcionario.addOrdem(ordemEscolhida);
                ordens.remove(ordemEscolhida);
                funcionario.setDeslocamento(funcionario.getDeslocamento() + menorTempo);
                funcionario.setJornada_de_trabalho(funcionario.getJornada_de_trabalho() + menorTempo + 7200);
                if (funcionario.getJornada_de_trabalho() > 14400) {
                    funcionario.setPeriodo("tarde");
                }
            }
        }
        
        return funcionarios;
    }

    /**
     * Ordena funcionários pelo maior deslocamento.
     * 
     * @param funcionarios Lista de funcionários.
     */
    public static void ordenaFuncMaiorDeslocamento(ArrayList<Funcionario> funcionarios) {
        Funcionario funcionarioEscolhido = null;
        funcionarios.sort(new Comparator<Funcionario>(){
            @Override
            public int compare(Funcionario o1, Funcionario o2) {
                return Double.compare(o2.getDeslocamento(),o1.getDeslocamento());
            }
        });
    }

    /**
     * Realiza a destruição do planejamento para um determinado funcionário.
     * 
     * @param funcionarios Lista de funcionários.
     * @param funcionarioTarget Funcionário alvo.
     * @return Lista de ordens resultantes da destruição.
     */
    public static ArrayList<Ordem> destroi(ArrayList<Funcionario> funcionarios, Funcionario funcionarioTarget) {
        if (funcionarioTarget.getOrdens().isEmpty()) {
            return new ArrayList<Ordem>();
        }

        ArrayList<Ordem> ordens = new ArrayList<>();

        ArrayList<Double> limites = limites(funcionarioTarget);

        for (Funcionario funcionario : funcionarios) {
            boolean estaDentro = false;
            if ((funcionario.getLatitude() >= limites.get(0) &&
                    funcionario.getLatitude() <= limites.get(1) &&
                    funcionario.getLongitude() >= limites.get(2) &&
                    funcionario.getLongitude() <= limites.get(3)) || cruzamento(funcionario, limites) ) {
                        estaDentro = true;
            }
            for (Ordem ordem : funcionario.getOrdens()) {
                if (estaDentro) break;
                if (ordem.getLatitude() >= limites.get(0) &&
                    ordem.getLatitude() <= limites.get(1) &&
                    ordem.getLongitude() >= limites.get(2) &&
                    ordem.getLongitude() <= limites.get(3)) {
                        estaDentro = true;
                }
            }

            if (estaDentro) {
                funcionario.setPeriodo("manha");
                funcionario.setDeslocamento(0);
                funcionario.setJornada_de_trabalho(0);
                if (!funcionario.getOrdens().isEmpty()) {
                    for (Ordem ordem : funcionario.getOrdens()) {
                        ordens.add(ordem);
                    }
                    funcionario.setOrdens(new ArrayList<Ordem>());
                }
            }
        }

        return ordens;
    }

    /**
     * Verifica se existe cruzamento na rota de um funcionário com base nos limites especificados.
     *
     * @param funcionario O funcionário cuja rota será verificada.
     * @param limites Lista de valores Double representando os limites geográficos.
     * @return Verdadeiro se houver cruzamento, falso caso contrário.
     */
    public static boolean cruzamento(Funcionario funcionario, ArrayList<Double> limites){
            if(funcionario.getOrdens().isEmpty()){
                return false;
            }
            //identificar cruzamento(funcionário para a primeira ordem) em relação a longitude 
            if (funcionario.getLongitude() > limites.get(2) &&
                funcionario.getLongitude() < limites.get(3) &&
                funcionario.getLatitude() < limites.get(0) &&
                funcionario.getLatitude() < limites.get(1) &&

                funcionario.getOrdens().get(0).getLongitude() > limites.get(2) && //longitude da ordem ordem é MAIOR do que a menor longitude
                funcionario.getOrdens().get(0).getLongitude() < limites.get(3) && //longitude da  ordem é MENOR do que a maior longitude
                funcionario.getOrdens().get(0).getLatitude() > limites.get(0) && //latitude da ordem é maior do que a menor latitude
                funcionario.getOrdens().get(0).getLatitude() > limites.get(1)) {  
                    return true;
            }

            //identificar cruzamento(funcionário para a primeira ordem) em relação a longitude 
            if (funcionario.getLongitude() > limites.get(2) &&
                funcionario.getLongitude() < limites.get(3) &&
                funcionario.getLatitude() > limites.get(0) &&
                funcionario.getLatitude() > limites.get(1) &&

                funcionario.getOrdens().get(0).getLongitude() > limites.get(2) && //longitude da ordem ordem é MAIOR do que a menor longitude
                funcionario.getOrdens().get(0).getLongitude() < limites.get(3) && //longitude da  ordem é MENOR do que a maior longitude
                funcionario.getOrdens().get(0).getLatitude() < limites.get(0) && //latitude da ordem é maior do que a menor latitude
                funcionario.getOrdens().get(0).getLatitude() < limites.get(1)) {
                    return true;    
            }

            //identificar cruzamento(funcionário para a primeira ordem) em relação a latitude
            if (
                funcionario.getLatitude() > limites.get(0) &&
                funcionario.getLatitude() < limites.get(1) &&
                funcionario.getLongitude() < limites.get(2) &&
                funcionario.getLongitude() < limites.get(3) &&

                funcionario.getOrdens().get(0).getLatitude() > limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                funcionario.getOrdens().get(0).getLatitude() < limites.get(1) && //latitude da segunda ordem é menor do que a maior latitude
                funcionario.getOrdens().get(0).getLongitude() > limites.get(2) && // longitude da segunda ordem é maior do que a menor longitude
                funcionario.getOrdens().get(0).getLongitude() > limites.get(3)) {
                    return true;
            }

            //identificar cruzamento(funcionário para a primeira ordem) em relação a latitude
            if (
                funcionario.getLatitude() > limites.get(0) &&
                funcionario.getLatitude() < limites.get(1) &&
                funcionario.getLongitude() > limites.get(2) &&
                funcionario.getLongitude() > limites.get(3) &&

                funcionario.getOrdens().get(0).getLatitude() > limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                funcionario.getOrdens().get(0).getLatitude() < limites.get(1) && //latitude da segunda ordem é menor do que a maior latitude
                funcionario.getOrdens().get(0).getLongitude() < limites.get(2) && // longitude da segunda ordem é maior do que a menor longitude
                funcionario.getOrdens().get(0).getLongitude() < limites.get(3)) {
                    return true;
            }

            for (int i = 0; i < funcionario.getOrdens().size(); i++){
                if (i < funcionario.getOrdens().size() - 1) {
                    //identificar cruzamento em relação a longitude
                    if (funcionario.getOrdens().get(i).getLongitude() > limites.get(2) && //longitude da primeira ordem é MAIOR do que a menor longitude
                        funcionario.getOrdens().get(i).getLongitude() < limites.get(3) && //longitude da primeira ordem é MENOR do que a maior longitude
                        funcionario.getOrdens().get(i).getLatitude() < limites.get(0) && //latitude da primeira ordem é menor do que a menor latitude
                        funcionario.getOrdens().get(i).getLatitude() < limites.get(1) && //latitude da primeira ordem é menor do que a maior latitude
    
                        funcionario.getOrdens().get(i+1).getLongitude() > limites.get(2) && //longitude da segunda ordem ordem é MAIOR do que a menor longitude
                        funcionario.getOrdens().get(i+1).getLongitude() < limites.get(3) && //longitude da seguuda ordem é MENOR do que a maior longitude
                        funcionario.getOrdens().get(i+1).getLatitude() > limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i+1).getLatitude() > limites.get(1)){ //latitude da segunda ordem é maior do que a maior latitude
                            return true;
                    }else if (funcionario.getOrdens().get(i).getLongitude() > limites.get(2) && //longitude da primeira ordem é MAIOR do que a menor longitude
                        funcionario.getOrdens().get(i).getLongitude() < limites.get(3) && //longitude da primeira ordem é MENOR do que a maior longitude
                        funcionario.getOrdens().get(i).getLatitude() > limites.get(0) && //latitude da primeira ordem é menor do que a menor latitude
                        funcionario.getOrdens().get(i).getLatitude() > limites.get(1) && //latitude da primeira ordem é menor do que a maior latitude
    
                        funcionario.getOrdens().get(i+1).getLongitude() > limites.get(2) && //longitude da segunda ordem ordem é MAIOR do que a menor longitude
                        funcionario.getOrdens().get(i+1).getLongitude() < limites.get(3) && //longitude da seguuda ordem é MENOR do que a maior longitude
                        funcionario.getOrdens().get(i+1).getLatitude() < limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i+1).getLatitude() < limites.get(1)){ //latitude da segunda ordem é maior do que a maior latitude
                            return true;
                    }else if (//identificar cruzamento em relação a latitude
                        funcionario.getOrdens().get(i).getLatitude() > limites.get(0) && // latitude da primeira ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i).getLatitude() < limites.get(1) && //latitude da primeira ordem é menor do que a maior latitude
                        funcionario.getOrdens().get(i).getLongitude() < limites.get(2) && // longitude da primeira ordem é menor do que a menor longitude
                        funcionario.getOrdens().get(i).getLongitude() < limites.get(3) && // longitude da primeira ordem é menor do que a maior longitude
    
                        funcionario.getOrdens().get(i+1).getLatitude() > limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i+1).getLatitude() < limites.get(1) && //latitude da segunda ordem é menor do que a maior latitude
                        funcionario.getOrdens().get(i+1).getLongitude() > limites.get(2) && // longitude da segunda ordem é maior do que a menor longitude
                        funcionario.getOrdens().get(i+1).getLongitude() > limites.get(3)){  // longitude da segunda ordem é maior do que a maior longitude  
                            return true;
                    }else if (//identificar cruzamento em relação a latitude
                        funcionario.getOrdens().get(i).getLatitude() > limites.get(0) && // latitude da primeira ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i).getLatitude() < limites.get(1) && //latitude da primeira ordem é menor do que a maior latitude
                        funcionario.getOrdens().get(i).getLongitude() > limites.get(2) && // longitude da primeira ordem é menor do que a menor longitude
                        funcionario.getOrdens().get(i).getLongitude() > limites.get(3) && // longitude da primeira ordem é menor do que a maior longitude
    
                        funcionario.getOrdens().get(i+1).getLatitude() > limites.get(0) && //latitude da segunda ordem é maior do que a menor latitude
                        funcionario.getOrdens().get(i+1).getLatitude() < limites.get(1) && //latitude da segunda ordem é menor do que a maior latitude
                        funcionario.getOrdens().get(i+1).getLongitude() < limites.get(2) && // longitude da segunda ordem é maior do que a menor longitude
                        funcionario.getOrdens().get(i+1).getLongitude() < limites.get(3)){  // longitude da segunda ordem é maior do que a maior longitude  
                            return true;
                    }
                }
            }
            return false;
    }

    /**
     * Calcula os limites geográficos com base nas coordenadas dos funcionários e das ordens.
     *
     * @param funcionario Funcionário para o qual os limites serão calculados.
     * @return Lista de valores Double representando os limites geográficos.
     */
    public static ArrayList<Double> limites(Funcionario funcionario){
        ArrayList<double[]> coordenadas = new ArrayList<>();
        //preenche coordenadas funcionário
        coordenadas.add(new double[]{funcionario.getLongitude(), funcionario.getLatitude()});
        //preenche coordenadas das ordens
        for(Ordem ordem : funcionario.getOrdens()) {
            coordenadas.add(new double[]{ordem.getLongitude(), ordem.getLatitude()});
        }

        double menorLatitude = Double.MAX_VALUE;
        double maiorLatitude = -9999;
        double menorLongitude = Double.MAX_VALUE;
        double maiorLongitude = -9999;
        ArrayList<Double> limites = new ArrayList<>();
        for (double[] coordenada : coordenadas) {
            double longitude = coordenada[0];
            double latitude = coordenada[1];
            if (longitude > maiorLongitude) {
                maiorLongitude = longitude;
            }
            if(longitude<menorLongitude){
                menorLongitude = longitude;
            }
            if (latitude > maiorLatitude) {
                maiorLatitude = latitude;
            }
            if(latitude<menorLatitude){
                menorLatitude = latitude;
            }
        }
        limites.add(menorLatitude);//limites[0]
        limites.add(maiorLatitude);//limites[1]
        limites.add(menorLongitude);//limites[2]
        limites.add(maiorLongitude);//limites[3]
        
        return limites;


    }

    /**
     * Otimiza a alocação de ordens aos funcionários.
     *
     * @param funcionarios Lista de funcionários.
     * @return Lista de funcionários após a otimização.
     */
    public static ArrayList<Funcionario> otimizacao(ArrayList<Funcionario> funcionarios) {
        boolean otimizado = false;
        ArrayList<Ordem> ordens = new ArrayList<Ordem>();

        while (!otimizado) {
            otimizado = true;
            double funcaoObjetivo = funcaoObjetivo(funcionarios);
            ordenaFuncMaiorDeslocamento(funcionarios);

            ArrayList<Funcionario> funcionariosCopia = new ArrayList<Funcionario>();

            for (int i = 0; i < funcionarios.size(); i++) {
                
                funcionariosCopia = new ArrayList<Funcionario>();
                for (int j = 0; j < funcionarios.size(); j++) {
                    Funcionario funcionario = funcionarios.get(j);
                    funcionariosCopia.add(funcionario.clone());
                    funcionariosCopia.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        funcionariosCopia.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }

                ordens = destroi(funcionariosCopia, funcionariosCopia.get(i));
                orderByMinCost(funcionariosCopia, ordens);
                double newFuncaoObjetivo = funcaoObjetivo(funcionariosCopia);
                if (newFuncaoObjetivo < funcaoObjetivo && newFuncaoObjetivo != 0) {
                    otimizado = false;
                    funcionarios = funcionariosCopia;
                    funcaoObjetivo = newFuncaoObjetivo;
                }
            }
        }

        return funcionarios;

    }

    /**
     * Busca pela maior vizinhança para alocar as ordens de maneira mais eficiente.
     *
     * @param funcionarios Lista de funcionários.
     * @return Lista de funcionários após a realização da busca.
     */
    public static ArrayList<Funcionario> largestNeighberhoodSearch(ArrayList<Funcionario> funcionarios) {
        ArrayList<Funcionario> melhorAlocacaoFuncionarios = new ArrayList<Funcionario>();
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario funcionario = funcionarios.get(i);
            melhorAlocacaoFuncionarios.add(funcionario.clone());
            melhorAlocacaoFuncionarios.get(i).setOrdens(new ArrayList<Ordem>());
            for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                melhorAlocacaoFuncionarios.get(i).addOrdem(funcionario.getOrdens().get(h).clone());
            }
        }
        double melhorFuncaoObjetivo = funcaoObjetivo(melhorAlocacaoFuncionarios);

        ArrayList<Double> coordenadasLatLong = limitesSetor(funcionarios);
        ArrayList<double[]> coordenadasSetoresLatLong = divideSetor(coordenadasLatLong);

        for (int i = 0; i < 4; i++) {

            ArrayList<Ordem> ordens = null;

            int z = 1;
            if (i<3) {
                ordens = destroiSetor(funcionarios, coordenadasSetoresLatLong.get(i));
                ordens.addAll(destroiSetor(funcionarios, coordenadasSetoresLatLong.get(i+1)));
            } else {
                ordens = destroiSetor(funcionarios, coordenadasSetoresLatLong.get(z));
                ordens.addAll(destroiSetor(funcionarios, coordenadasSetoresLatLong.get(i)));
                z--;
            }
            
            orderByMinCost(funcionarios, ordens);
            otimizacao(funcionarios);

            double funcaoObjetivo = funcaoObjetivo(funcionarios);
            if (funcaoObjetivo < melhorFuncaoObjetivo) {
                melhorAlocacaoFuncionarios = new ArrayList<Funcionario>();
                for (int j = 0; j < funcionarios.size(); j++) {
                    Funcionario funcionario = funcionarios.get(j);
                    melhorAlocacaoFuncionarios.add(funcionario.clone());
                    melhorAlocacaoFuncionarios.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        melhorAlocacaoFuncionarios.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }
                melhorFuncaoObjetivo = funcaoObjetivo;
            } else {
                funcionarios = new ArrayList<Funcionario>();
                for (int j = 0; j < melhorAlocacaoFuncionarios.size(); j++) {
                    Funcionario funcionario = melhorAlocacaoFuncionarios.get(j);
                    funcionarios.add(funcionario.clone());
                    funcionarios.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        funcionarios.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }
            }
        }
        
        for (int i = 0; i < coordenadasSetoresLatLong.size(); i++) {
            ArrayList<Ordem> ordens = null;

            ordens = destroiSetor(funcionarios, coordenadasSetoresLatLong.get(i));
            
            
            orderByMinCost(funcionarios, ordens);
            otimizacao(funcionarios);

            double funcaoObjetivo = funcaoObjetivo(funcionarios);
            if (funcaoObjetivo < melhorFuncaoObjetivo) {
                melhorAlocacaoFuncionarios = new ArrayList<Funcionario>();
                for (int j = 0; j < funcionarios.size(); j++) {
                    Funcionario funcionario = funcionarios.get(j);
                    melhorAlocacaoFuncionarios.add(funcionario.clone());
                    melhorAlocacaoFuncionarios.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        melhorAlocacaoFuncionarios.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }
                melhorFuncaoObjetivo = funcaoObjetivo;
            } else {
                funcionarios = new ArrayList<Funcionario>();
                for (int j = 0; j < melhorAlocacaoFuncionarios.size(); j++) {
                    Funcionario funcionario = melhorAlocacaoFuncionarios.get(j);
                    funcionarios.add(funcionario.clone());
                    funcionarios.get(j).setOrdens(new ArrayList<Ordem>());
                    for (int h = 0; h<funcionario.getOrdens().size(); h++) {
                        funcionarios.get(j).addOrdem(funcionario.getOrdens().get(h).clone());
                    }
                }
            }
        }

        return melhorAlocacaoFuncionarios;
    }

    /**
     * Destroi setores específicos para redistribuição de ordens.
     *
     * @param funcionarios Lista de funcionários.
     * @param setorLatLong Array de valores Double representando as coordenadas do setor.
     * @return Lista de ordens resultante da destruição do setor.
     */
    public static ArrayList<Ordem> destroiSetor(ArrayList<Funcionario> funcionarios, double[] setorLatLong) {

        if (funcionarios.isEmpty() || setorLatLong.length != 4 || setorLatLong == null) {
            return new ArrayList<Ordem>();
        }

        ArrayList<Ordem> ordens = new ArrayList<>();


        for (Funcionario funcionario : funcionarios) {
            boolean estaDentro = false;
            if ((funcionario.getLatitude() >= setorLatLong[0] &&
                funcionario.getLatitude() <= setorLatLong[1] &&
                funcionario.getLongitude() >= setorLatLong[2] &&
                funcionario.getLongitude() <= setorLatLong[3]) ) {
                    estaDentro = true;
            }
            for (Ordem ordem : funcionario.getOrdens()) {
                if (estaDentro) break;
                if (ordem.getLatitude() >= setorLatLong[0] &&
                    ordem.getLatitude() <= setorLatLong[1] &&
                    ordem.getLongitude() >= setorLatLong[2] &&
                    ordem.getLongitude() <= setorLatLong[3]) {
                        estaDentro = true;
                }
            }

            if (estaDentro) {
                funcionario.setPeriodo("manha");
                funcionario.setDeslocamento(0);
                funcionario.setJornada_de_trabalho(0);
                if (!funcionario.getOrdens().isEmpty()) {
                    for (Ordem ordem : funcionario.getOrdens()) {
                        ordens.add(ordem);
                    }
                    funcionario.setOrdens(new ArrayList<Ordem>());
                }
            }
        }

        return ordens;
    }
    
    /**
     * Divide a área de atuação em setores para otimização da alocação de ordens.
     *
     * @param coordenadasLatLong Lista de coordenadas representando a área de atuação.
     * @return Lista de setores representados por arrays de valores Double.
     */
    public static ArrayList<double[]> divideSetor(ArrayList<Double> coordenadasLatLong){
        ArrayList<double[]> coordenadasSetoresLatLong = new ArrayList<>();
        double mediaLat = (coordenadasLatLong.get(0) + coordenadasLatLong.get(1))/2;
        double mediaLong = (coordenadasLatLong.get(2) + coordenadasLatLong.get(3))/2;

        double[] setorA =  {coordenadasLatLong.get(0),mediaLat,mediaLong,coordenadasLatLong.get(3)};
        double[] setorB =  {mediaLat,coordenadasLatLong.get(1),mediaLong,coordenadasLatLong.get(3)};
        double[] setorC =  {coordenadasLatLong.get(0),mediaLat,coordenadasLatLong.get(2),mediaLong};
        double[] setorD =  {mediaLat,coordenadasLatLong.get(1),coordenadasLatLong.get(2),mediaLong};

        coordenadasSetoresLatLong.add(setorA);
        coordenadasSetoresLatLong.add(setorB);
        coordenadasSetoresLatLong.add(setorC);
        coordenadasSetoresLatLong.add(setorD);

        return coordenadasSetoresLatLong;

    }

    /**
     * Calcula os limites geográficos da área de atuação com base nas coordenadas dos funcionários.
     *
     * @param funcionarios Lista de funcionários.
     * @return Lista de valores Double representando os limites geográficos da área de atuação.
     */
    public static ArrayList<Double> limitesSetor(ArrayList<Funcionario> funcionarios) {
        ArrayList<Double> coordenadasLatLong = new ArrayList<>();
        double minLatitude = Double.MAX_VALUE;
        double maxLatitude = -99999;
        double minLongitude = Double.MAX_VALUE;
        double maxLongitude = -99999;

        for (Funcionario funcionario : funcionarios) {
            if(funcionario.getLatitude() > maxLatitude){
                maxLatitude = funcionario.getLatitude();
            }else if(funcionario.getLatitude() < minLatitude){
                minLatitude = funcionario.getLatitude();
            }
            if(funcionario.getLongitude() > maxLongitude){
                maxLongitude = funcionario.getLongitude();
            }else if(funcionario.getLongitude() < minLongitude){
                minLongitude = funcionario.getLongitude();
            }
        }
        coordenadasLatLong.add(minLatitude);
        coordenadasLatLong.add(maxLatitude);
        coordenadasLatLong.add(minLongitude);
        coordenadasLatLong.add(maxLongitude);

        return coordenadasLatLong;
    }

    /**
     * Encontra ordens que não foram atendidas.
     *
     * @param ordensNaoAtendidas Lista inicial de ordens não atendidas.
     * @param funcionarios Lista de funcionários.
     * @return Lista atualizada de ordens não atendidas.
     */
    public static int encontraOrdensNaoAtendidas(ArrayList<Ordem> ordensNaoAtendidas, ArrayList<Funcionario> funcionarios){
        int count = ordensNaoAtendidas.size();

        for (Funcionario funcionario : funcionarios) {
            for (Ordem ordem : funcionario.getOrdens()) {
                count--;
            }
        }


        return count;
    }

    /**
     * Calcula a função objetivo, somando o deslocamento de todos os funcionários.
     *
     * @param funcionarios Lista de funcionários.
     * @return Valor total do deslocamento dos funcionários.
     */
    public static double funcaoObjetivo(ArrayList<Funcionario> funcionarios) {
        double funcaoObjetivo = 0;
        for (Funcionario funcionario : funcionarios) {
            funcaoObjetivo += funcionario.getDeslocamento();
        }
        return funcaoObjetivo;
    }

    /**
     * Gera um cronograma com base na alocação de ordens aos funcionários.
     *
     * @param funcionarios Lista de funcionários.
     * @return Lista de nodos representando o cronograma.
     */
    public static ArrayList<Node> returnSchedule(ArrayList<Funcionario> funcionarios) {
        ArrayList<Node> nodos = new ArrayList<Node>();

        int countFunc = 0;
        int countOrdem = 0;

        for (Funcionario funcionario : funcionarios) {
            nodos.add(new Node(funcionario.getLongitude(), funcionario.getLatitude(), true, funcionario.getId(), "", funcionario.getIdentifier()));
            countFunc++;
            for (Ordem ordem : funcionario.getOrdens()) {
                nodos.add(new Node(ordem.getLongitude(), ordem.getLatitude(), false, ordem.getId(), ordem.getPeriodo(), ordem.getIdentifier()));
                countOrdem++;
            }
        }

        System.out.println("Quantidade de funcionários: " + countFunc);
        System.out.println("Quantidade de ordens: " + countOrdem);
        return nodos;
    }
}
