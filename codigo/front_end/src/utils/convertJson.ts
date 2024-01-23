// convertJson.ts

import { universalData, Location } from "@/app/types/interfaces";

export function formatData(data: universalData[]): Location[] {
  const locationArray: Location[] = [];
  let currentLocation: Location | null = null;

  for (const item of data) {
    if (item.isFuncionario) {
      // If found an employee, start a new location
      if (currentLocation !== null) {
        locationArray.push(currentLocation);
      }

      currentLocation = {
        IdentifierTecnico: item.identifier,
        pontoDePartidaLatitude: item.latitude.toString(),
        pontoDePartidaLongitude: item.longitude.toString(),
        periodo: item.periodo.toString(),
        demandas: [],
      };
    } else if (currentLocation !== null) {
      // If you are not an employee and a location already exists, add it as a demand
      currentLocation.demandas.push({
        demandaId: item.id.toString(),
        demandaLatitude: item.latitude.toString(),
        demandaLongitude: item.longitude.toString(),
        isFuncionario: false,
        periodo: item.periodo.toString(),
        IdentifierDemanda: item.identifier,
      });
    }
  }

  // Add last location if exists
  if (currentLocation !== null) {
    locationArray.push(currentLocation);
  }

  return locationArray;
}
