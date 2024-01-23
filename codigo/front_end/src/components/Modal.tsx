import React, { ReactNode, useState, useEffect } from 'react';
import SimpleTable from '../components/tabela';
import Pagination from '@mui/material/Pagination';
import Modal from '@mui/material/Modal';
import Fade from '@mui/material/Fade';
import Button from '@mui/material/Button';
import { universalData } from '@/app/types/interfaces';

// Interface defining the props for the TransitionModal component
interface ModalProps {
  isOpen: boolean;
  onClose: () => void;
  children: ReactNode;
  title: string;
  icon: ReactNode;
  identifier: string;
  tableData: universalData[]; // Data for the table in the modal
  onModalOpen?: () => void; // Optional function to be executed when the modal opens
}

const itemsPerPage = 6; // Number of items to display per page

// Functional component representing a modal with a paginated table
const TransitionModal: React.FC<ModalProps> = ({ isOpen, onClose, children, title, icon, identifier, tableData, onModalOpen}) => {
  const [currentPage, setCurrentPage] = useState(1);

  useEffect(() => {
    // Execute the onModalOpen function when the modal is open
    if (isOpen && onModalOpen) {
      onModalOpen();
    }
  }, [isOpen, onModalOpen]);

  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentItems = tableData.slice(indexOfFirstItem, indexOfLastItem);
  const totalPages = Math.ceil(tableData.length / itemsPerPage);

  // Function to handle page changes in the paginated table
  const handlePageChange = (event: React.ChangeEvent<unknown>, pageNumber: number) => {
    setCurrentPage(pageNumber);
  };

  return (
    // Modal component  
    <Modal
      open={isOpen}
      onClose={onClose}
      closeAfterTransition
    >
      <Fade in={isOpen}>
        <div className="fixed top-0 left-0 w-full h-full flex items-center justify-center">
          <div
            className="absolute top-0 left-0 w-full h-full bg-white opacity-40 p-10"
            onClick={onClose}
          ></div>
          <div className="relative w-5/6 h-5/6 bg-white opacity-80 p-10 rounded-md m-10 overflow-auto">
            {children}
            <div className="flex items-center justify-between mb-4">
              <h2 className="text-2xl font-bold text-black">{title}</h2>
              {icon}
            </div>
            {/* Rendering the SimpleTable component with currentItems */}
            <SimpleTable data={currentItems} identifier={identifier}/>
            {/* Pagination component */}
            <Pagination count={totalPages} page={currentPage} onChange={handlePageChange} shape="rounded" className="text-black" />
            {/* Button to close the modal */}
            <div className='absolute bottom-4 right-4 w-full sm:w-1/6'>
              <Button
                variant="contained"
                size='small'
                className="bg-zinc-600 text-white border border-black p-2 text-base hover:opacity-100 w-full sm:w-52 h-8 mb-2 sm:mr-4 sm:mb-0"
                onClick={onClose}
              >
                Fechar
              </Button>
            </div>
          </div>
        </div>
      </Fade>
    </Modal>
  );
};

export default TransitionModal; // Exporting the TransitionModal component