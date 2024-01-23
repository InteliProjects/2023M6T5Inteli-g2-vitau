from flask import Flask, request, jsonify
import requests
import json

app = Flask(__name__)

def api(source_indices, destination_indices, coordenadas):
    coordenadas_formatadas = [f"{coord[1]},{coord[0]}" for coord in coordenadas]

    # Seleciona as coordenadas de origem e destino baseadas nos índices fornecidos
    coordenadas_origem = [coordenadas_formatadas[i] for i in source_indices]
    coordenadas_destino = [coordenadas_formatadas[i] for i in destination_indices]

    # Combina as coordenadas de origem e destino para a URL
    todas_coordenadas = coordenadas_origem + coordenadas_destino

    # Constrói a URL
    url = f"http://router.project-osrm.org/table/v1/driving/{';'.join(todas_coordenadas)}"
    
    # Adiciona os índices de origem e destino
    url += f"?sources={';'.join(map(str, range(len(coordenadas_origem))))}"
    url += f"&destinations={';'.join(map(str, range(len(coordenadas_origem), len(todas_coordenadas))))}"
    
    # Faz a requisição para a API
    response = requests.get(url)
    if response.status_code == 200:
        data = response.json()
        return data['durations']
    else:
        print(f"Erro na requisição: {response.status_code}")
        return None

def calculate_matrix(coordenadas):
    razao = len(coordenadas) // 50
    resto = len(coordenadas) % 50

    matrix = [[] for i in range(len(coordenadas))]

    for i in range(0, razao):
        j = 0
        if razao != 0:
            for j in range(razao):
                source_indices = list(range(i * 50, (i + 1) * 50))
                destination_indices = list(range(j * 50, (j + 1) * 50))

                matrix_aux = api(source_indices, destination_indices, coordenadas)
                line_aux = 0
                for line in range(i * 50, (i + 1) * 50):
                    matrix[line].extend(matrix_aux[line_aux])
                    line_aux += 1

        if resto != 0:
            sources_indices = list(range(i * 50, (i + 1) * 50))
            destination_indices = list(range(razao * 50, len(coordenadas)))

            matrix_aux = api(source_indices, destination_indices, coordenadas)
            line_aux = 0
            for line in range(i * 50, (i + 1) * 50):
                matrix[line].extend(matrix_aux[line_aux])
                line_aux += 1

    if resto != 0:
        source_indices = list(range(razao * 50, len(coordenadas)))
        for j in range(razao):
            destination_indices = list(range(j * 50, (j + 1) * 50))

            matrix_aux = api(source_indices, destination_indices, coordenadas)
            line_aux = 0
            for line in range(razao * 50, len(coordenadas)):
                matrix[line].extend(matrix_aux[line_aux])
                line_aux += 1

        if resto != 0:
            destination_indices = list(range(razao * 50, len(coordenadas)))

            matrix_aux = api(source_indices, destination_indices, coordenadas)
            line_aux = 0
            for line in range(razao * 50, len(coordenadas)):
                matrix[line].extend(matrix_aux[line_aux])
                line_aux += 1

    return matrix

def toInfinito(matrix):
    for i in range(len(matrix)):
        matrix[i][i] = float("inf")

@app.route('/calculate_durations', methods=['POST'])
def calculate_matrix_route():
    try:
        json_data = request.get_json()

        # Ensure json_data is parsed as a dictionary
        if not isinstance(json_data, dict):
            raise ValueError("Invalid JSON format")

        coordenadas = [(item["latitude"], item["longitude"]) for item in json_data['dados']]

        matrix = calculate_matrix(coordenadas)
        toInfinito(matrix)

        # Create a dictionary to represent the matrix
        matrix_dict = {'matrix': matrix}

        return jsonify(matrix_dict)
        
    except Exception as e:
        return jsonify({"error": str(e)})

if __name__ == '__main__':
    app.run(debug=True)
