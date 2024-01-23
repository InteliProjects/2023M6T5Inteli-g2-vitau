import Papa from 'papaparse';

interface Demanda {
  IdentifierDemanda: string;
}

interface Location {
  IdentifierTecnico: string;
  pontoDePartidaLatitude: string;
  pontoDePartidaLongitude: string;
  periodo: string;
  demandas: Demanda[];
}

const exportToCsv = (data: Location[], fileName: string) => {
  const flatData = data.map(location => {
    const demandaIdentifiers = location.demandas.map(demanda => demanda.IdentifierDemanda);

    const result: Record<string, any> = {
      Matricula: location.IdentifierTecnico,
      Latitude: location.pontoDePartidaLatitude,
      Longitude: location.pontoDePartidaLongitude,
    };

    demandaIdentifiers.forEach((identifier, index) => {
      result[`Demanda_${index + 1}`] = identifier;
    });

    return result;
  });

  const csv = Papa.unparse(flatData, {
    header: true
  });
  const csvData = new Blob([csv], { type: 'text/csv;charset=utf-8;' });
  const csvURL = window.URL.createObjectURL(csvData);
  const tempLink = document.createElement('a');
  tempLink.href = csvURL;
  tempLink.setAttribute('download', fileName);
  tempLink.click();
}

export default exportToCsv;
