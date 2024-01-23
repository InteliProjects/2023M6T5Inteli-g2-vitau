// SideBar.tsx

import { universalData, Location } from '@/app/types/interfaces';
import CircularProgress from '@mui/material/CircularProgress';
import imagem from '../../public/LogoBranca-vitau-sembg.png';
import ReceiptIcon from '@mui/icons-material/Receipt';
import React, { useEffect, useState } from 'react';
import Manual from '../components/ManualDoUsuario';
import GroupIcon from '@mui/icons-material/Group';
import TransitionModal from '../components/Modal';
import { formatData } from '@/utils/convertJson';
import HelpIcon from '@mui/icons-material/Help';
import Button from '@mui/material/Button';
import FileUploader from './FileUploader';
import { toast } from 'react-toastify';
import { setTimeout } from 'timers';
import Metrics from './Metrics';
import api from '@/api/api';
import { FormControl, InputLabel } from '@mui/material';
import { Input } from 'postcss';

interface SidebarProps {
  cityName: string;
  sendMapData: (mapData: Location[]) => void;
}

// Sidebar Component
const Sidebar: React.FC<SidebarProps> = ({ cityName, sendMapData }) => {
  // States to control the opening/closing of modes
  const [isTecnicoModalOpen, setIsTecnicoModalOpen] = useState(false);
  const [isOrdemModalOpen, setIsOrdemModalOpen] = useState(false);
  const [isManualModalOpen, setManualModalOpen] = useState(false);
  const [isPromiseDone, setIsPromiseDone] = useState(false);

  // Functions to open/close modals
  const [isLoadingTecnico, setIsLoadingTecnico] = useState(false);
  const [isLoadingOrdens, setIsLoadingOrdens] = useState(false);
  const [csvData, setCsvData] = useState<universalData[]>([]);
  const [data, setData] = useState<Location[]>([]);

  // States for storing employee data and orders
  const [tecnicoData, setTecnicoData] = useState<universalData[]>([]);
  const [ordensData, setOrdensData] = useState<universalData[]>([]);
  const [numOrdensNaoAtendidas, setNumOrdensNaoAtendidas] = useState<number>();
  const [tecnicosGap, setTecnicosGap] = useState<number>();

  const openTecnicoModal = () => {
    setIsLoadingTecnico(true);

    setTimeout(() => {
      setIsTecnicoModalOpen(true);
      setIsLoadingTecnico(false);
    }, 1000);
  };

  const closeTecnicoModal = () => {
    setIsTecnicoModalOpen(false);
  };

  const openOrdemModal = () => {
    setIsLoadingOrdens(true);

    setTimeout(() => {
      setIsOrdemModalOpen(true);
      setIsLoadingOrdens(false);
    }, 1000);
  };

  const closeOrdemModal = () => {
    setIsOrdemModalOpen(false);
  };

  const openManualModal = () => {
    setManualModalOpen(true);
  };

  const closeManualModal = () => {
    setManualModalOpen(false);
  };

  const verifyTecnicosOciosos = (data: Location[]) => {
    const tecnicosOciosos = data.filter((item) => item.demandas.length === 0);
    setTecnicosGap(tecnicosOciosos.length > 0 ? tecnicosOciosos.length : 0);
  }

  const verifyTecnicosNecessarios = (numOrdensNaoAtendidas: number) => {
    const tecnicosNecessarios = Math.ceil(numOrdensNaoAtendidas/3)
    return tecnicosNecessarios
  }

  // Function to handle processing when clicking the Calculate button
  const handleCalculateClick = async () => {
    // Process the csvData or call the processing logic here
    console.log("Processing data:", csvData);
    setIsPromiseDone(true);

    if (csvData.length === 0) {
      toast.error('Nenhum arquivo foi importado!');
      setIsPromiseDone(false);
      return;
    }

    // Example: Call the backend API with csvData
    await api.post('/solve', csvData, {
      headers: {
        'Content-Type': 'application/json',
      },
    }).then((response) => {
      const result: any = response.data;
      console.log(result);
      console.log("result nodos", formatData(result.nodos));
      console.log("result ordens", result.numOrdensNaoAtendidas);
      console.log("result tecnicos ociosos", verifyTecnicosOciosos(formatData(result.nodos)));
      setData(formatData(result.nodos));
      setNumOrdensNaoAtendidas(result.numOrdensNaoAtendidas);

      if (result.numOrdensNaoAtendidas > 0) {
        setTecnicosGap(-1*verifyTecnicosNecessarios(result.numOrdensNaoAtendidas));
      } else {
        verifyTecnicosOciosos(formatData(result.nodos));
      }
    }).catch((error) => {
      console.log('Error in POST request:', error);
    }).finally(() => {
      setIsPromiseDone(false);
      toast.success('Previsão de demanda realizada com sucesso! montando mapa...');
    });
  };

  const handleFileUpload = (data: any[]) => {
    setCsvData(data);

    const tecnicos: universalData[] = [];
    const ordens: universalData[] = [];

    data.forEach((item: any) => {
      if (item.isFuncionario) {
        tecnicos.push({
          id: item.id,
          identifier: item.identifier,
          isFuncionario: item.isFuncionario,
          latitude: item.latitude,
          longitude: item.longitude,
          periodo: item.periodo,
        });
      } else {
        ordens.push({
          id: item.id,
          identifier: item.identifier,
          isFuncionario: item.isFuncionario,
          latitude: item.latitude,
          longitude: item.longitude,
          periodo: item.periodo
        });
      }
    });

    setTecnicoData(tecnicos);
    setOrdensData(ordens);
  };

  useEffect(() => {
    console.log("Data received from csv file:", csvData);
  }, [csvData]);

  useEffect(() => {
    console.log("Data received from map:", data);
    sendMapData(data);
  }, [data]);
    
  // Sidebar rendering
  return (
    <aside className="w-full md:w-1/3 h-full h-1/3vh h-screen bg-zinc-700 overflow-y-auto">
      {/* Logo(vitau) Section */}
      <div className="mb-4 border-b-2 border-black pb-4 bg-zinc-800">
        <img src={imagem.src} alt="Logo" className="w-68 h-28 rounded-md " />
      </div>
      {/* City name section */}
      <div className="flex items-center justify-center flex-col text-white">
        <div className="p-3 pt-8 text-5xl mb-6">{cityName}</div>
      </div>

      {/* Excel/CSV Import Section */}
      <div className="ml-0 mb-12 pl-4">
        <p className="mb-2 text-sm text-gray-300">Import</p>
        <FileUploader handleFileUpload={handleFileUpload} />
      </div>

      {/* Metrics Section */}
      <div className="flex justify-center flex-col text-white">
        <div className="pl-4 text-4xl mb-6">Métricas</div>
        <div className="mb-4 pl-4 flex justify-center items-center">
          <Metrics tecnico={true} data={tecnicosGap} />
          <Metrics tecnico={false} data={numOrdensNaoAtendidas} />
        </div>
      </div>


      {/* Calculation-Algorithm Button Section */}
      <div className="flex justify-center text-center sm:w-full w-72 h-24 mb-1">
        <Button
          onClick={handleCalculateClick}
          variant="contained"
          size="medium"
          className={`bg-zinc-600 text-white p-3 text-xl hover:opacity-100 mx-auto w-full sm:w-72 h-10 ${isPromiseDone ? 'opacity-50 cursor-not-allowed' : ''}`}
        >
          {
            isPromiseDone ? (
              <CircularProgress size={25} style={{ marginRight: '8px' }} />
            ) : (
              <>
                Calcular
              </>
            )
          }
        </Button>
      </div>

      {/* Buttons Section */}
      <div className="flex flex-col sm:flex-row justify-center pl-3 pr-3">
        {/* Technicians Button Open-Modal*/}
        <Button
          variant="contained"
          size="small"
          className={`bg-zinc-600 text-white border border-black p-2 text-base hover:opacity-100 w-full sm:w-52 h-8 mb-2 sm:mr-4 sm:mb-0 ${isLoadingTecnico ? 'opacity-50 cursor-not-allowed' : ''
            }`}
          onClick={openTecnicoModal}
          disabled={isLoadingTecnico}
        >
          {isLoadingTecnico ? (
            <CircularProgress size={20} style={{ marginRight: '8px' }} />
          ) : (
            <>Técnicos</>
          )}
        </Button>

        {/* Orders Button Open-Modal */}
        <Button
          variant="contained"
          size="small"
          className="bg-zinc-600 text-white border-black p-2 text-base hover:opacity-100 w-full sm:w-52 h-8"
          onClick={openOrdemModal}
          disabled={isLoadingOrdens}
        >
          {isLoadingOrdens ? (
            <CircularProgress size={20} style={{ marginRight: '8px' }} />
          ) : (
            <>Ordens</>
          )}
        </Button>
      </div>

      {/* Modals */}
      <TransitionModal
        isOpen={isTecnicoModalOpen}
        onClose={closeTecnicoModal}
        title="Curitiba - Funcionários"
        icon={<GroupIcon fontSize="large" className="text-black" />}
        tableData={tecnicoData}
        children={undefined}
        identifier='Matrícula'
      />
      <TransitionModal
        isOpen={isOrdemModalOpen}
        onClose={closeOrdemModal}
        title="Curitiba - Ordens"
        icon={<ReceiptIcon fontSize="large" className="text-black" />}
        tableData={ordensData}
        children={undefined}
        identifier='ID_Venda'
      />
      {/* Help Icon */}
      <button onClick={openManualModal}>
        <div className="absolute bottom-4 left-4">
          <HelpIcon fontSize="large" className="text-white" />
        </div>
      </button>
      <Manual open={isManualModalOpen} onClose={closeManualModal} />
    </aside>
  );
};

export default Sidebar;