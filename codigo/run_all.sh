
echo "iniciando o planejador"
cd backend/vitau_vrp
mvn clean install
mvn spring-boot:run &
sleep 5
echo "iniciando a api de distancias"
cd ../../api_osmr_py
pip install flask requests futures
python3 osrm.py &
sleep 5
echo "iniciando o front"
cd ../front_end
npm install
npm run dev