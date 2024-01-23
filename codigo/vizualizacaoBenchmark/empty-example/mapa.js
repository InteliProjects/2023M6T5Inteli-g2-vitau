


let dados = [{}]

  

let respostaSalva;
// Endpoint da API
let url = 'http://localhost:8080/api/vrp/solve';

// Opções da requisição
let opcoes = {
    method: 'POST', // Método HTTP
    body: JSON.stringify(dados), // Convertendo os dados para formato JSON
    headers: {'Content-Type': 'application/json'} // Definindo o cabeçalho para indicar que estamos enviando JSON
};

// Fazendo a requisição POST

fetch(url, opcoes)
.then(resposta => resposta.json()) // Converte a resposta para JSON
.then(dadosDaResposta => {
    // Salvar os dados em uma variável
        respostaSalva = dadosDaResposta;
        console.log('Resposta do servidor hehe:', respostaSalva)
        
    })
    .catch(erro => console.error('Erro:', erro));



// Configurações do mapa
const options = {
    lat: 0,
    lng: 0,
    zoom: 4,
    style: 'http://{s}.tile.osm.org/{z}/{x}/{y}.png'
  }
  
  // a biblioteca Mappa cria uma instância do Leaflet
  const mappa = new Mappa('Leaflet');
  
  let myMap;
  let canvas;
  let meteorites;
  
  function setup() {
    canvas = createCanvas(1600, 1000);
  
    // Cria um "tile map" com uma camada de canvas
    myMap = mappa.tileMap(options);
    myMap.overlay(canvas);
  

  }
  
  function draw() {
    drawPoints();
    drawLines();
  }

  function drawPoints() {
    // Limpa o canvas
    clear();
    if (respostaSalva) {
        for (let i = 0; i < respostaSalva.length; i++) {
            const ponto = respostaSalva[i];
            const latitude = ponto.latitude;
            const longitude = ponto.longitude;
    
            // Converte a lat/lng em x/y de um pixel
            const pos = myMap.latLngToPixel(latitude, longitude);
    
            // Verifica se é funcionário
            if (ponto.isFuncionario) {
                fill(255, 0, 0); // Vermelho para funcionários
            } else {
                fill(0, 0, 255); // Azul para não funcionários
            }
    
            // Desenha o ponto no mapa
            ellipse(pos.x, pos.y, 10, 10);
        }
    }
}

function drawLines() {
  if (respostaSalva) {
      noFill();
      stroke(0); // Cor das linhas 
      strokeCap(ROUND);
      strokeWeight(1.5);

      // Percorre os pontos e desenha linhas entre eles
      for (let i = 0; i < respostaSalva.length - 1; i++) {
          const pontoAtual = respostaSalva[i];
          const pontoProximo = respostaSalva[i + 1];

        if (pontoProximo.isFuncionario) {
            continue;
        }
        else{
          const latitudeAtual = pontoAtual.latitude;
          const longitudeAtual = pontoAtual.longitude;

          const latitudeProximo = pontoProximo.latitude;
          const longitudeProximo = pontoProximo.longitude;

          // Converte lat/lng em x/y de pixels para ambos os pontos
          const posAtual = myMap.latLngToPixel(latitudeAtual, longitudeAtual);
          const posProximo = myMap.latLngToPixel(latitudeProximo, longitudeProximo);

          // Desenha a linha entre os dois pontos
          line(posAtual.x, posAtual.y, posProximo.x, posProximo.y);
        }
      }
  }
}