import React, { useState, useRef, useEffect } from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import { IconButton, Modal, Button } from '@mui/material';
import NavigateNextIcon from '@mui/icons-material/NavigateNext';
import NavigateBeforeIcon from '@mui/icons-material/NavigateBefore';
import CloseIcon from '@mui/icons-material/Close';
import Image from 'next/image';
import paginaPrincipal from '../../public/PaginaPrincipalVitau.png';
import botaoUpload from '../../public/BotaoUpload.png';
import logoImagem from '../../public/logo1.png';
import botaoCalcular from '../../public/BotaoCalcular.png';
import botaoModal from '../../public/BotaoTecnicoOrdem.png';
import modalFuncionario from '../../public/tabela - Funcionários.png';
import modalOrdem from '../../public/TabelaOrdem.png';
import botaoExport from '../../public/botaoResultado.png'

interface PropsManual {
  open: boolean;
  onClose: () => void;
}

const ManualDoUsuario: React.FC<PropsManual> = ({ open, onClose }) => {
  const sliderRef = useRef<Slider>(null);
  const settings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 1,
    slidesToScroll: 1,
    draggable: false,
  };

  const nextSlide = () => {
    if (sliderRef.current) {
      sliderRef.current.slickNext();
    }
  };

  const prevSlide = () => {
    if (sliderRef.current) {
      sliderRef.current.slickPrev();
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === 'ArrowLeft') {
      prevSlide();
    } else if (e.key === 'ArrowRight') {
      nextSlide();
    }
  };

  useEffect(() => {
    const handleKeyDown = (e: KeyboardEvent) => {
      console.log('Key pressed:', e.key);
    };
  
    window.addEventListener('keydown', handleKeyDown);
  
    return () => {
      window.removeEventListener('keydown', handleKeyDown);
    };
  }, []);

  return (
    <Modal open={open} onClose={onClose}>
      <div className='flex flex-col items-center justify-center bg-white m-12 ml-16 mr-16 text-black rounded-md-4 relative p-4'>
        <IconButton onClick={onClose} className='absolute top-1 right-1'>
          <CloseIcon />
        </IconButton>
        <div
          className='text-center w-full max-h-[calc(100vh-8rem)] scrollbar-thin scrollbar-thumb-gray-300 scrollbar-track-gray-100'
          onKeyDown={handleKeyDown}
          tabIndex={0}
        >
          <h1 className='text-center text-3xl border-b-2 my-2 border-black p-2 w-full rounded-t-md'>Manual do Usuário</h1>
          <div className='carousel__list' style={{ textAlign: 'center' }}>
            <Slider ref={sliderRef} {...settings} className='center'>
              <div className='carousel__item'>
                <div className='flex justify-between'>
                  <div className='mr-4'>
                    <Image
                      src={paginaPrincipal}
                      alt="Página Principal"
                      width={900}
                      height={600}
                      className='ml-2 mb-1 mt-2 rounded-md'
                    />
                    <p className='text-xs'>Página Principal</p>
                  </div>
                  <div className='flex-1'>
                    <h2 className='text-xl'>Bem-vindo ao Vitau.</h2>
                    <div className="flex justify-center">
                      <Image src={logoImagem} alt="Modal das SLAs" width={400} height={300} className='mb-1 rounded-md' />
                    </div>
                    <p className='mb-2'>Somos a Viabilização da Inovação Técnologica para Atendimento Urbano, uma aplicação voltada para o cálculo de múltiplos roteamentos de veículos, destinados à indústria logística e prestação de serviços em geral.</p>
                    <p className='mb-4'>Este documento tem como propósito facilitar o entendimento de todo o fluxo da plataforma, explicando a funcionalidade de cada componente/elemento.</p>
                  </div>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={botaoUpload} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Botão de Upload</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Página Principal</h1>
                  <br></br>
                  <p>A Primeira interação no site é o botão de upload, onde será possível enviar arquivos csv/Excel com todas as informações referentes aos técnicos e às ordens.</p>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={botaoCalcular} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Botão de Calcular</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Botão Calcular</h1>
                  <br></br>
                  <p className='mb-2'>Com o upload já realizado, ao clicar no botão "Calcular", nosso algoritmo calculará o resultado baseado nas informações enviadas pelo import e irá mostra-lo no mapa da aplicação.</p>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={botaoModal} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Modais</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Modais</h1>
                  <br></br>
                  <p className='mb-2'>Por fim, temos os modais "Técnicos" e "Ordens" que ao interagir irá abrir uma tabelas com suas respectivas informações.</p>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={modalFuncionario} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Tabela dos Funcionários</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Modal do Funcionário</h1>
                  <br></br>
                  <p className='mb-2'>No modal dos funcionários, teremos uma tabela representando todos os funcionários, com suas respectivas ID, Matricula, Latitude e Longitude. Dentro do modal será possivel pesquisar o funcionário por ID ou Matricula.</p>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={modalOrdem} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Tabela das Ordens</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Modal das Ordens</h1>
                  <br></br>
                  <p className='mb-2'>No modal das Ordens, teremos uma tabela representando todos as Ordens, com suas respectivas informações: ID, Número da Ordem, Latitude e Longitude. Dentro do modal será possivel pesquisar a ordem por ID ou Número da Ordem.</p>
                </div>
              </div>
              <div className='carousel__item flex justify-between'>
                <div className='mr-4'>
                  <Image src={botaoExport} alt="Botão de Upload" width={900} height={600} className='ml-2 mb-1 mt-2 rounded-md' />
                  <p className='text-xs'>Botão de Exportar</p>
                </div>
                <div className='flex-1 text-center'>
                  <h1 className='text-xl mb-36'>Botão de Export</h1>
                  <br></br>
                  <p className='mb-2'>Finalizando, temos o botão de exportar, que permite que o usuario baixe o resultado do algoritmo em um formato de arquivo csv/Excel.</p>
                </div>
              </div>
            </Slider>
          </div>
          <div className='flex justify-between'>
            <Button
              variant="contained"
              size='small'
              className="bg-zinc-600 text-white border border-black p-1 text-base hover:opacity-100"
              onClick={prevSlide}
            >
              <NavigateBeforeIcon />
            </Button>
            <Button
              variant="contained"
              size='small'
              className="bg-zinc-600 text-white border border-black p-1 text-base hover:opacity-100"
              onClick={nextSlide}
            >
              <NavigateNextIcon />
            </Button>
          </div>
        </div>
      </div>
    </Modal>
  );
};

export default ManualDoUsuario;
