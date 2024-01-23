package com.vitau.uativ.vitau_vrp.Algorithm;

/**
 * Representa um nó em um sistema de coordenadas geográficas.
 *
 * <p>Cada nó possui informações sobre sua longitude, latitude, identificação e se é um funcionário.</p>
 */
public class Node {
  
  private double longitude; // Longitude do nó
  private double latitude;  // Latitude do nó
  private boolean isFuncionario; // Indica se o nó representa um funcionário
  private int id; // Identificação única do nó
  private String periodo;
  private String identifier;

  /**
   * Construtor para a classe Node.
   *
   * @param longitude Longitude do nó.
   * @param latitude Latitude do nó.
   * @param isFuncionario Indica se o nó é um funcionário.
   * @param id Identificação única do nó.
   */
  public Node(double longitude, double latitude, boolean isFuncionario, int id, String periodo, String identifier) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.isFuncionario = isFuncionario;
    this.id = id;
    this.periodo = periodo;
    this.identifier = identifier;
  }

  /**
   * Obtém a longitude do nó.
   *
   * @return A longitude do nó.
   */
  public double getLongitude() {
    return longitude;
  }
  
  /**
   * Obtém a latitude do nó.
   *
   * @return A latitude do nó.
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Verifica se o nó representa um funcionário.
   *
   * @return True se o nó é um funcionário, False caso contrário.
   */
  public boolean getIsFuncionario() {
    return isFuncionario;
  }

  /**
   * Obtém a identificação única do nó.
   *
   * @return A identificação única do nó.
   */
  public int getId() {
    return id;
  }

  public String getPeriodo() {
    return periodo;
  }

  public String getIdentifier() {
    return identifier;
  }
}
