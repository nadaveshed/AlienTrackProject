import axios, { AxiosResponse } from 'axios';
import {AlienType} from "../enums/AlienType";
import {VehicleType} from "../enums/VehicleType";
import {WeaponType} from "../enums/WeaponType";

interface Alien {
    id?: number;
    name: string;
    type: string;
    commanderId: number | null;
    weapon: string;
    vehicle: string;
}

const API_URL = 'http://localhost:8080/api/aliens';

const getAllAliens = (): Promise<AxiosResponse<Alien[]>> => {
    return axios.get(`${API_URL}/getAll`);
};

const addAlien = (alien: Alien) => {
    console.log("add alien",alien);
    return axios.post(`${API_URL}/newAlien`, alien);
};

const AlienService = {
    getAllAliens,
    addAlien,
};

export default AlienService;
