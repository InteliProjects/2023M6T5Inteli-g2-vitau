import PersonIcon from '@mui/icons-material/Person';
import WorkIcon from '@mui/icons-material/Work';
import { Input, InputLabel } from '@mui/material/';
import FormControl from '@mui/material/FormControl';

interface MetricsProps {
  tecnico: boolean;
  data: any;
}

const Metrics = ({ tecnico, data }: MetricsProps): JSX.Element => {
  let totalTecnicos: number | null = null;
  let totalDemandas: number | null = null;

  if (tecnico && data !== undefined) {
    totalTecnicos = data;
  } else if (!tecnico && data !== undefined) {
    totalDemandas = data;
  }

  const labelContent = () => {
    if (tecnico) {
      if (totalTecnicos === null) {
        return 'Técnicos';
      } else if (totalTecnicos < 0) {
        return 'Técnicos faltantes';
      } else if (totalTecnicos === 0) {
        return 'Técnicos equilibrados';
      } else {
        return 'Técnicos ociosos';
      }
    } else {
      if (totalDemandas === null) {
        return 'Demandas';
      } else if (totalDemandas < 0) {
        return 'Demandas faltantes';
      } else if (totalDemandas === 0) {
        return 'Demandas equilibradas';
      } else {
        return 'Demandas não atendidas';
      }
    }
  };

  const iconComponent = tecnico ? (
    totalTecnicos !== null ? (
      totalTecnicos !== 0 ? (
        <PersonIcon sx={{ fontSize: 40 }} className="text-red-500" />
      ) : (
        <PersonIcon sx={{ fontSize: 40 }} className="text-green-500" />
      )
    ) : <PersonIcon sx={{ fontSize: 40 }} />
  ) : (
    totalDemandas !== null ? (
      totalDemandas !== 0 ? (
        <WorkIcon sx={{ fontSize: 40 }} className="text-red-500" />
      ) : (
        <WorkIcon sx={{ fontSize: 40 }} className="text-green-500" />
      )
    ) : <WorkIcon sx={{ fontSize: 40 }} />
  );

  const dados = tecnico ? (totalTecnicos !== null ? Math.abs(totalTecnicos) : '-') : (totalDemandas !== null ? Math.abs(totalDemandas) : '-');

  return (
    <div className="flex justify-center items-center flex-col">
      {iconComponent}
      <FormControl variant="standard" className="mb-4 sm:mr-6 text-white w-36">
        <InputLabel htmlFor="latitude-input" className="text-white text-center" sx={{ textAlign: 'center' }}>
          {labelContent()}
        </InputLabel>
        <Input id="latitude-input" className="text-white !text-center" readOnly value={dados} />
      </FormControl>
    </div>
  );
};

export default Metrics;
