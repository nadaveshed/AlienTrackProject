import React, { useEffect, useState, ChangeEvent, FormEvent } from 'react';
import AlienService from './services/AlienService';
import { Client } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import './App.css';
import {isAxiosError} from "axios";

interface Alien {
  id?: number;
  name: string;
  type: string;
  commanderId: number | null;
  weapon: string;
  vehicle: string;
}

const App: React.FC = () => {
  const [aliens, setAliens] = useState<Alien[]>([]);
  const [newAlien, setNewAlien] = useState<Alien>({
    name: '',
    type: '',
    commanderId: null,
    weapon: '',
    vehicle: '',
  });
  const [isConnected, setIsConnected] = useState<boolean>(navigator.onLine);

  useEffect(() => {
    fetchAliens().then(r => r);
    setupWebSocket();
  }, []);
  
  const fetchAliens = async () => {
    const response = await AlienService.getAllAliens();
    setAliens(response.data);
  };

  const setupWebSocket = () => {
    const stompClient = new Client({
      webSocketFactory: () => new SockJS('http://localhost:8080/ws'),
      reconnectDelay: 5000,
      onConnect: () => {
        console.log("WebSocket connection established");
        stompClient.subscribe('/alienInfo/alienUpdates', (data) => {
          if (data.body) {
            console.log("Received data from WebSocket:", data.body);
            try {
              const parsedData: Alien[] = JSON.parse(data.body);
              console.log("Parsed alien data:", JSON.stringify(parsedData, null, 2));
              setAliens(parsedData);
            } catch (error) {
              console.error("Error parsing WebSocket data:", error);
            }
          }
        });
      },
      onStompError: (frame) => {
        console.error("Broker error:", frame.headers["message"], frame.body);
      },
    });

    stompClient.activate();
  };

  const handleInputChange = (e: ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setNewAlien((prev) => ({
      ...prev,
      [name]: name === 'commanderId' && value ? parseInt(value) : (name === 'commanderId' ? null : value),
    }));
  };

  const addAlien = async (e: FormEvent) => {
    e.preventDefault();
    if (newAlien.type === 'WARRIOR' && newAlien.commanderId === null) {
      alert("WARRIOR must have a COMMANDER ID.");
      return;
    }

    if (newAlien.type === 'CHIEF_COMMANDER' && newAlien.commanderId !== null) {
      alert("CHIEF COMMANDER cannot have a COMMANDER ID.");
      newAlien.commanderId = null;
      return;
    }

    try {
      await AlienService.addAlien(newAlien);
      setNewAlien({name: '', type: '', commanderId: null, weapon: '', vehicle: ''});
    } catch (error: unknown) {
      if (isAxiosError(error)) {
        alert(error.response?.data || "Error adding alien.");
      } else {
        alert("An unexpected error occurred.");
      }
    }
  };

  return (
      <div className="App">
        <h1>Alien Management System</h1>

        {!isConnected && <p className="error">No internet connection. Please check your connection.</p>}

        <form onSubmit={addAlien}>
          <input
              type="text"
              name="name"
              placeholder="Alien Name"
              value={newAlien.name}
              onChange={handleInputChange}
              required
          />
          <select
              name="type"
              value={newAlien.type}
              onChange={handleInputChange}
              required
          >
            <option value="" disabled>Select Type</option>
            <option value="WARRIOR">Warrior</option>
            <option value="COMMANDER">Commander</option>
            <option value="CHIEF_COMMANDER">Chief Commander</option>
          </select>

          {/* Show commanderId input only for Commanders and Warriors */}
          {(newAlien.type === 'COMMANDER' || newAlien.type === 'WARRIOR') && (
              <input
                  type="text"
                  name="commanderId"
                  placeholder="Commander ID (optional)"
                  value={newAlien.commanderId ?? ''}
                  onChange={handleInputChange}
              />
          )}

          {/* Show weapon input only for Warriors */}
          {newAlien.type === 'WARRIOR' && (
              <select name="weapon" value={newAlien.weapon} onChange={handleInputChange}>
                <option value="" disabled>Select Weapon</option>
                <option value="WATER_GUN">Water gun</option>
                <option value="PEPPER_SPRAY">Pepper spray</option>
                <option value="CHOPSTICKS">Chopsticks</option>
              </select>
          )}

          {(newAlien.type === 'COMMANDER' || newAlien.type === 'CHIEF_COMMANDER') && (
              <select name="vehicle" value={newAlien.vehicle} onChange={handleInputChange}>
                <option value="" disabled>Select Vehicle</option>
                <option value="BIRD_SCOOTER">Bird scooter</option>
                <option value="MERKAVA_TANK">Merkava tank</option>
                {(newAlien.type === 'CHIEF_COMMANDER') && (
                <option value="EGGED_BUS">Egged Bus</option>
                )}
              </select>
          )}

          <button type="submit" disabled={!isConnected}>Add Alien</button>
        </form>

        <h2>Aliens</h2>
        <table>
          <thead>
          <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Type</th>
            <th>Commander ID</th>
            <th>Weapon</th>
            <th>Vehicle</th>
          </tr>
          </thead>
          <tbody>
          {aliens.map((alien) => (
              <tr key={alien.id}>
                <td>{alien.id}</td>
                <td>{alien.name}</td>
                <td>{alien.type}</td>
                <td>{(alien.commanderId === null || alien.commanderId <= 0) ? '' : alien.commanderId}</td>
                <td>{alien.weapon}</td>
                <td>{alien.vehicle}</td>
              </tr>
          ))}
          </tbody>
        </table>
      </div>
  );
};

export default App;