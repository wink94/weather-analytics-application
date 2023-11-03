// store.ts
import create from 'zustand';
import axios from 'axios';
import { WEATHER_API_BASE_URL } from './constants';
import { WeatherData } from '../components/WeatherDataTable';
import { BASE_URL } from './config';

interface WeatherState {
  weatherDataList: Array<WeatherData>; 
  loading: boolean;
  error: string | null;
  fetchWeatherData: (eventType: string, timestamp: string, startDate: string, endDate: string) => void;
}

export const useWeatherStore = create<WeatherState>((set) => ({
  weatherDataList: [],
  loading: false,
  error: null,
  fetchWeatherData: async (eventType, timestamp, startDate, endDate) => {
    set({ loading: true });
    try {
      const response = await axios.get(`${BASE_URL}${WEATHER_API_BASE_URL}`, {
        params: { eventType, timestamp, startDate, endDate },
      });
      set({ weatherDataList: response.data.data, loading: false });
    } catch (error: any) {
      set({ error: error.message, loading: false });
    }
  },
}));
