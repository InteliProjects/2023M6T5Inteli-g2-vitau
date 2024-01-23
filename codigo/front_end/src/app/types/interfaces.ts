
export interface Demanda {
  demandaId: string;
  demandaLatitude: string;
  demandaLongitude: string;
  isFuncionario: boolean;
  periodo: string;
  IdentifierDemanda: string;
}

export interface Location {
  IdentifierTecnico: string;
  pontoDePartidaLatitude: string;
  pontoDePartidaLongitude: string;
  periodo: string;
  demandas: Demanda[];
}

export interface universalData {
  id: number;
  latitude: number;
  longitude: number;
  isFuncionario: boolean;
  periodo: string;
  identifier: string;
}
