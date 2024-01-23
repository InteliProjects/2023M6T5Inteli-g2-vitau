package com.vitau.uativ.vitau_vrp.Algorithm;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa a resposta do algoritmo de roteamento de veículos.
 */
public class VrpResponse {
    private ArrayList<Node> nodos;
    private int numOrdensNaoAtendidas;

    /**
     * Construtor para criar uma resposta do algoritmo de roteamento.
     *
     * @param nodos Lista de nodos (pontos ou locais) envolvidos na resposta do algoritmo.
     * @param numOrdensNaoAtendidas Número de ordens que não foram atendidas pelo algoritmo.
     */
    public VrpResponse(ArrayList<Node> nodos, int numOrdensNaoAtendidas) {
        this.nodos = nodos;
        this.numOrdensNaoAtendidas = numOrdensNaoAtendidas;
    }

    /**
     * Obtém a lista de nodos.
     *
     * @return Lista de nodos.
     */
    public List<Node> getNodos() {
        return nodos;
    }

    /**
     * Define a lista de nodos.
     *
     * @param nodos Lista de nodos a ser definida.
     */
    public void setNodos(ArrayList<Node> nodos) {
        this.nodos = nodos;
    }

    /**
     * Obtém o número de ordens não atendidas.
     *
     * @return Número de ordens não atendidas.
     */
    public int getNumOrdensNaoAtendidas() {
        return numOrdensNaoAtendidas;
    }

    /**
     * Define o número de ordens não atendidas.
     *
     * @param numOrdensNaoAtendidas Número de ordens não atendidas a ser definido.
     */
    public void setNumOrdensNaoAtendidas(int numOrdensNaoAtendidas) {
        this.numOrdensNaoAtendidas = numOrdensNaoAtendidas;
    }
}