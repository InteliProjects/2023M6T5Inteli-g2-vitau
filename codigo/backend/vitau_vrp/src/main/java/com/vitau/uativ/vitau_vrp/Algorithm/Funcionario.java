package com.vitau.uativ.vitau_vrp.Algorithm;

import java.util.ArrayList;

public class Funcionario implements Cloneable
{
  private double longitude; // Longitude do nó
  private double latitude;  // Latitude do nó
  private int id; // Identificação única do nó
  private ArrayList<Ordem> ordens;
  private double deslocamento;
  private double jornada_de_trabalho;
  private String periodo;
  private String identifier;
  

  public Funcionario(double longitude, double latitude, int id, String identifier) {
    this.longitude = longitude;
    this.latitude = latitude;
    this.id = id;
    this.deslocamento = 0;
    this.jornada_de_trabalho = 0;
    this.ordens = new ArrayList<Ordem>();
    this.periodo = "manha";
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
   * Obtém a identificação única do nó.
   *
   * @return A identificação única do nó.
   */
  public int getId() {
    return id;
  }

  public String getIdentifier() {
    return identifier;
  }

  public double getDeslocamento() {
    return deslocamento;
  }

  public double getJornada_de_trabalho() {
    return jornada_de_trabalho;
  }

  public ArrayList<Ordem> getOrdens() {
    return ordens;
  }

  public String getPeriodo() {
    return periodo;
  }

  public void setPeriodo(String periodo) {
    this.periodo = periodo;
  }

  public void setDeslocamento(double deslocamento) {
    this.deslocamento = deslocamento;
  }

  public void setJornada_de_trabalho(double jornada_de_trabalho) {
    this.jornada_de_trabalho = jornada_de_trabalho;
  }

  public void setOrdens(ArrayList<Ordem> ordens) {
    this.ordens = ordens;
  }

  public void addOrdem(Ordem ordem) {
    this.ordens.add(ordem);
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public Funcionario clone() {
    try {
        return (Funcionario) super.clone();
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(); // Can't happen
    }
  }
}

