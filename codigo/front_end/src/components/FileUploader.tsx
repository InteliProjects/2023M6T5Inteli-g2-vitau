// FileUploader.tsx
import React, { ChangeEvent } from "react";
import { toast } from "react-toastify";
import Papa from  "papaparse";

import CloudUploadIcon from "@mui/icons-material/CloudUpload";
import { isValidCSVFormat } from "@/utils/verifyFormatCsv";
import { universalData } from "@/app/types/interfaces";
import Button from "@mui/material/Button";

// Props for the FileUploader component
interface FileUploaderProps {
  handleFileUpload: (data: universalData[]) => void;
}

// FileUploader component
const FileUploader = ({ handleFileUpload }: FileUploaderProps): JSX.Element => {
  // States for storing employee and order data
  let tecnicoData: universalData[] = [];
  let ordensData: universalData[] = [];

  function changeHandler(event: ChangeEvent<HTMLInputElement>) {
    if (event.target.files) {
      // Check if the file is a CSV
      if (event.target.files[0].type !== "text/csv") {
        toast.error("O arquivo deve ser um CSV");
        return;
      }

      // Using PapaParse to parse the uploaded CSV file
      Papa.parse(event.target.files[0], {
        header: true,
        skipEmptyLines: true,
        complete: function (results: any) {
          // Check if the CSV has the required columns
          if (!isValidCSVFormat(results)) {
            toast.error("O arquivo CSV deve conter as colunas id, identifier, isFuncionario, latitude e longitude");
            return;
          }
          
          const parsedData: universalData[] = results.data.map((item: any) => ({
            id: parseInt(item.id),
            identifier: String(item.identifier),
            isFuncionario: item.isFuncionario.toLowerCase() === "true",
            latitude: parseFloat(item.latitude),
            longitude: parseFloat(item.longitude),
            periodo: String(item.periodo).toLowerCase()
          }));

          // Categorize the data
          const tecnicos: universalData[] = parsedData.filter((item) => item.isFuncionario);
          const ordens: universalData[] = parsedData.filter((item) => !item.isFuncionario);

          // Pass the categorized data to the corresponding states
          tecnicoData = tecnicos;
          ordensData = ordens;

          // Pass the raw data to the parent component if necessary
          handleFileUpload(parsedData);

          toast.success("Arquivo CSV carregado com sucesso!");
        },
      });
    }
  }

  return (
    <>
      {/* Button with file input */}
      <Button
        component="label"
        variant="contained"
        startIcon={<CloudUploadIcon />}
        size="small"
        className="bg-zinc-600"
      >
        <input
          id="dropzone-file"
          type="file"
          className="hidden"
          onChange={changeHandler}
        />
        Upload file
      </Button>
    </>
  );
};

export default FileUploader;