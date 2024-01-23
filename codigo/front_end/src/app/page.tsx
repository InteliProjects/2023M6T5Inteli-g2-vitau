// Enable the "use client" directive for Prisma Client
"use client";

// Import necessary dependencies and components
import React, { useEffect, useState } from 'react';
import Map from "../components/Map"; // Import the Map component
import Sidebar from '../components/sideBar'; // Import the Sidebar component
import dynamic from "next/dynamic";
import Button from '@mui/material/Button';
import DownloadIcon from '@mui/icons-material/Download';
import { ToastContainer, toast } from 'react-toastify';
import exportToCsv from '@/utils/exportToCsv';
import { Location } from '@/app/types/interfaces';

// Use dynamic import for the Map component with SSR (Server-Side Rendering) set to false
const MapComponent = dynamic(() => import("../components/Map"), { ssr: false });

// Page component definition
const Page: React.FC = () => {

  const [data, setData] = useState<Location[]>([]);

  const mapData = (data: Location[]) => {
    setData(data);
  };

  const exportCsv = () => {
    if (data.length === 0) {
      toast.error('Não há dados para exportar');
      return;
    } else {
      toast.success('Dados exportados com sucesso');
      exportToCsv(data, "Resultado.csv");
    }
  }

  // Render the page with a Sidebar and Map component
  return (
    <>
      <div className="flex">
        {/* Same as */}
        {/* Sidebar component with a specified city name */}
        <Sidebar cityName="Curitiba" sendMapData={mapData} />
        <div className='flex-1 relative' style={{ zIndex: 0 }}>
          {/* Use the dynamic Map component and pass the test data as props */}
          <MapComponent data={data} />
        </div>
        {/* Button for SLA */}
        <Button
          variant="contained"
          size='small'
          style={{ backgroundColor: 'rgba(0, 0, 0, 0.7)', color: 'white', zIndex: 1, position: 'absolute', top: '16px', right: '16px' }}
          className="p-2 text-base hover:opacity-100"
          onClick={exportCsv}
        >
          <DownloadIcon fontSize="small" />
        </Button>
        {/* SLA Modal */}
        <ToastContainer
          position="top-right"
          autoClose={2000}
          hideProgressBar={false}
          newestOnTop={false}
          closeOnClick={true}
          rtl={false}
          className={'text-center'}
          pauseOnFocusLoss={false}
          draggable={true}
          pauseOnHover={true}
          theme="light"
        />
      </div>
    </>
  );
};

// Export the Page component
export default Page;
