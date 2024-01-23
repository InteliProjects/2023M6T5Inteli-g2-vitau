package com.vitau.uativ.vitau_vrp.Algorithm;

public class Ordem implements Cloneable {
  private double Longitude; // Longitude do nó
  private double Latitude;  // Latitude do nó
  private int Id; // Identificação única do nó
  private int Id_funcionario;
  private String Periodo;
  private String Identifier;

  public Ordem(double Longitude, double Latitude, int Id, String Periodo, String Identifier) {
    this.Longitude = Longitude;
    this.Latitude = Latitude;
    this.Id = Id;
    this.Id_funcionario = -1;
    this.Periodo = Periodo;
    this.Identifier = Identifier;
  }

  public double getLongitude() {
    return Longitude;
  }

  public double getLatitude() {
    return Latitude;
  }

  public int getId() {
    return Id;
  }

  public int getId_funcionario() {
    return Id_funcionario;
  }

  public String getPeriodo() {
    return Periodo;
  }

  public void setId_funcionario(int Id_funcionario) {
    this.Id_funcionario = Id_funcionario;
  }

  public String getIdentifier() {
    return Identifier;
  }

  public Ordem clone() {
    try {
        return (Ordem) super.clone();
    } catch (CloneNotSupportedException e) {
        throw new AssertionError(); // Can't happen
    }
  }
}
