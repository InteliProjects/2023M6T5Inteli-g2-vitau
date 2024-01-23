import random
import json

# Definindo os limites para longitude e latitude
# Estes valores podem ser ajustados conforme necessário
LONGITUDE_MIN, LONGITUDE_MAX = -180, 180
LATITUDE_MIN, LATITUDE_MAX = -90, 90

# Gerando 100 exemplos
test_data = []
for i in range(100):
    test_data.append({
        "longitude": random.uniform(LONGITUDE_MIN, LONGITUDE_MAX),
        "latitude": random.uniform(LATITUDE_MIN, LATITUDE_MAX),
        "isFuncionario": random.choice([True, False]),
        "id": i
    })

# Convertendo para uma string JSON formatada
test_data_json = json.dumps(test_data, indent=4)

test_data_json[:500]  # Mostrando apenas os primeiros 500 caracteres para evitar uma resposta excessivamente longa
