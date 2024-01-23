// verifyFormatCsv.ts

export function isValidCSVFormat(results: any): boolean {
  // Define the expected column names in the CSV file
  const expectedColumns = ['id', 'identifier', 'isFuncionario', 'latitude', 'longitude', 'periodo'];

  // Check if all expected columns are present in the CSV data
  return expectedColumns.every((columnName) => results.meta.fields.includes(columnName));
}