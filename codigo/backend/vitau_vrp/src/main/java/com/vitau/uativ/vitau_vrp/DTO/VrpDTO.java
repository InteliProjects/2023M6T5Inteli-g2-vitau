package com.vitau.uativ.vitau_vrp.DTO;

/**
 * DTO (Data Transfer Object) que representa dados relacionados à otimização de roteamento veicular (VRP).
 *
 * <p>Este DTO contém informações sobre a longitude, latitude, identificação e a natureza (funcionário ou não) de um ponto no contexto do VRP.</p>
 */
public class VrpDTO {
  
  private double longitude; // Longitude do ponto no VRP
  private double latitude;  // Latitude do ponto no VRP
  private boolean isFuncionario; // Indica se o ponto representa um funcionário no VRP
  private int id; // Identificação única do ponto no VRP
  private String periodo;
  private String identifier;
  
  /**
   * Construtor para a classe VrpDTO.
   *
   * @param longitude Longitude do ponto no VRP.
   * @param latitude Latitude do ponto no VRP.
   * @param isFuncionario Indica se o ponto representa um funcionário no VRP.
   * @param id Identificação única do ponto no VRP.
   */
  public VrpDTO(double longitude, double latitude, boolean isFuncionario, int id, String periodo, String identifier) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.isFuncionario = isFuncionario;
    this.id = id;
    this.periodo = periodo;
    this.identifier = identifier;
  }

  /**
   * Obtém a longitude do ponto no VRP.
   *
   * @return A longitude do ponto no VRP.
   */
  public double getLongitude() {
    return longitude;
  }
  
  /**
   * Obtém a latitude do ponto no VRP.
   *
   * @return A latitude do ponto no VRP.
   */
  public double getLatitude() {
    return latitude;
  }

  /**
   * Verifica se o ponto representa um funcionário no VRP.
   *
   * @return True se o ponto representa um funcionário, False caso contrário.
   */
  public boolean getIsFuncionario() {
    return isFuncionario;
  }

  /**
   * Obtém a identificação única do ponto no VRP.
   *
   * @return A identificação única do ponto no VRP.
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
