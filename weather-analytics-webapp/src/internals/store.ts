// store.ts
import create from 'zustand';
import axios from 'axios';
import { URL } from './constants';
import { WeatherData } from '../components/WeatherDataTable';

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
      const response = await axios.get(URL, {
        params: { eventType, timestamp, startDate, endDate },
      });
      set({ weatherDataList: response.data.data, loading: false });
    } catch (error: any) {
      set({ error: error.message, loading: false });
    }
  },
}));
