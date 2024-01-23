// Importing necessary dependencies and styles for Leaflet and Leaflet Routing Machine

import { useRef, useEffect } from "react";
import L from "leaflet";
import "leaflet-routing-machine";
import "leaflet/dist/leaflet.css";
import "leaflet-routing-machine/dist/leaflet-routing-machine.css";

import { Demanda, Location } from "@/app/types/interfaces";

// Define the props for the Map component
interface MapProps {
  data: Location[];
}

// Map component definition
const Map: React.FC<MapProps> = ({ data }: MapProps) => {
  // Create a reference to the Leaflet map
  const mapRef = useRef<L.Map | null>(null);

  // useEffect hook to initialize the Leaflet map and add routes when the component mounts or when data changes
  useEffect(() => {
    const loadLeaflet = async () => {
      // Check if the window is defined and the map has not been initialized
      if (typeof window !== "undefined" && !mapRef.current) {
        // Create a Leaflet map with specified options
        const map = L.map("map", {
          center: [-25.45, -49.27],
          zoom: 13,
          zoomControl: true,
          attributionControl: false,
        });

        // Add an OpenStreetMap tile layer to the map
        L.tileLayer("https://tile.openstreetmap.org/{z}/{x}/{y}.png", {
          attribution: "© OpenStreetMap contributors",
        }).addTo(map);

        // Set the map reference to the created map
        mapRef.current = map;
      }
    };

    // Call the loadLeaflet function
    loadLeaflet();
  }, []);

  useEffect(() => {
    console.log("montando mapa...", data);
    if (mapRef.current) {
      addRoutesToMap(mapRef.current, data);
    }
  }, [data]);

  // Function to add routes to the map
  const addRoutesToMap = async (map: L.Map, locations: Location[]) => {
    for (let i = 0; i < locations.length; i++) {
      const location = locations[i];
      await delay(3500);

      const startPoint = L.latLng(
        parseFloat(location.pontoDePartidaLatitude),
        parseFloat(location.pontoDePartidaLongitude)
      );

      if (location.demandas.length > 0) {
        const firstDemandPoint = L.latLng(
          parseFloat(location.demandas[0].demandaLatitude),
          parseFloat(location.demandas[0].demandaLongitude)
        );

        const waypointsToFirstDemand = [startPoint, firstDemandPoint];

        const controlToFirstDemand = L.Routing.control({
          waypoints: waypointsToFirstDemand,
          routeWhileDragging: true,
          plan: L.Routing.plan(waypointsToFirstDemand, {
            // Function to create markers for the waypoints
            createMarker: function (i, wp) {
              const isStartPoint = i === 0;
              const iconUrl = isStartPoint
                ? "https://developers.google.com/static/maps/documentation/javascript/images/default-marker.png?hl=pt-br"
                : "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png";

              // Create and configure the marker
              const marker = L.marker(wp.latLng, {
                draggable: false,
                icon: L.icon({
                  iconUrl: iconUrl,
                  iconSize: [25, 41],
                  iconAnchor: [12, 41],
                  popupAnchor: [1, -34],
                  tooltipAnchor: [16, -28],
                  shadowSize: [41, 41],
                }),
              });

              // Add a popup to the starting point marker with technician information
              if (isStartPoint) {
                const funcionarioMatricula = location.IdentifierTecnico;
                marker.bindPopup(`
                  Técnico
                  <br/>
                  Matrícula: ${funcionarioMatricula}
                `);
              }

              return marker;
            },
          }),
        }).addTo(map);

        location.demandas.forEach(async (demand: Demanda, index: number) => {
          if (index > 0) {
            await delay(2500);
            const prevDemandPoint = L.latLng(
              parseFloat(location.demandas[index - 1].demandaLatitude),
              parseFloat(location.demandas[index - 1].demandaLongitude)
            );

            const currentDemandPoint = L.latLng(
              parseFloat(demand.demandaLatitude),
              parseFloat(demand.demandaLongitude)
            );

            const waypointsToNextDemand = [prevDemandPoint, currentDemandPoint];

            // Create a Leaflet Routing control for the route to the next demand
            const controlToNextDemand = L.Routing.control({
              waypoints: waypointsToNextDemand,
              routeWhileDragging: true,
              plan: L.Routing.plan(waypointsToNextDemand, {
                // Function to create markers for the waypoints
                createMarker: function (i, wp) {
                  const iconUrl =
                    "https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.7.1/images/marker-icon.png";

                  // Create and configure the marker
                  return L.marker(wp.latLng, {
                    draggable: false,
                    icon: L.icon({
                      iconUrl: iconUrl,
                      iconSize: [25, 41],
                      iconAnchor: [12, 41],
                      popupAnchor: [1, -34],
                      tooltipAnchor: [16, -28],
                      shadowSize: [41, 41],
                    }),
                  }).bindPopup(
                    `Demanda<br/>
                    ID: ${demand.IdentifierDemanda}
                    <br/>
                    Periodo: ${demand.periodo.toLowerCase() === "manha" ? "Manhã" : "Tarde"}
                    `
                  );
                },
              }),
            }).addTo(map);

            controlToNextDemand.getContainer().style.visibility = "hidden";
          }
        });

        controlToFirstDemand.getContainer().style.visibility = "hidden";
      }
    }
  };

  // Function to delay execution
  const delay = (ms: number) => {
    return new Promise((resolve) => setTimeout(resolve, ms));
  };

  // Render the map container
  return <div id="map" className="w-full h-full" />;
};

// Export the Map component
export default Map;
