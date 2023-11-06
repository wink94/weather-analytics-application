import React, { useEffect, useState } from "react";
import {
  Table,
  TableBody,
  TableCell,
  TableHead,
  TableRow,
  Select,
  MenuItem,
  FormControl,
  InputLabel,
  TextField,
} from "@mui/material";
import Button from "@mui/material/Button";
import CircularProgress from "@mui/material/CircularProgress";
import { useWeatherStore } from "../internals/store";
import { convertToTimeStamp } from "../util/DateUtil";

export interface WeatherData {
  name: string;
  value: number;
}

const WeatherDataTable: React.FC = () => {
  const { weatherDataList, loading, error, fetchWeatherData } =
    useWeatherStore();

  useEffect(() => {
    fetchWeatherData(
      "SUMMARY",
      "DAY",
      "2006-04-01 20:00:00",
      "2006-07-27 23:00:00"
    );
  }, []);

  const [eventType, setEventType] = useState<"SUMMARY" | "PRECIP_TYPE">(
    "SUMMARY"
  );
  const [timestamp, setTimestamp] = useState<
    "CUSTOM" | "DAY" | "MONTH" | "YEAR"
  >("DAY");
  const [customStart, setCustomStart] = useState<string>("");
  const [customEnd, setCustomEnd] = useState<string>("");

  const handleButtonClick = () => {
    fetchWeatherData(eventType, timestamp, customStart, customEnd);
    console.log(customStart, customEnd);
  };

  return (
    <div>
      <h1 style={{ color: "#000000" }}>Weather Data API</h1>

      <FormControl variant="filled" style={{ marginRight: "20px" }}>
        <InputLabel id="event-type-label">Event Type</InputLabel>
        <Select
          labelId="event-type-label"
          value={eventType}
          onChange={(e) =>
            setEventType(e.target.value as "SUMMARY" | "PRECIP_TYPE")
          }
        >
          <MenuItem value="SUMMARY">SUMMARY</MenuItem>
          <MenuItem value="PRECIP_TYPE">PRECIP_TYPE</MenuItem>
        </Select>
      </FormControl>

      <FormControl variant="filled" style={{ marginRight: "20px" }}>
        <InputLabel id="timestamp-label">Timestamp</InputLabel>
        <Select
          labelId="timestamp-label"
          value={timestamp}
          onChange={(e) =>
            setTimestamp(e.target.value as "CUSTOM" | "DAY" | "MONTH" | "YEAR")
          }
        >
          <MenuItem value="CUSTOM">CUSTOM</MenuItem>
          <MenuItem value="DAY">DAY</MenuItem>
          <MenuItem value="MONTH">MONTH</MenuItem>
          <MenuItem value="YEAR">YEAR</MenuItem>
        </Select>
      </FormControl>

      <FormControl
        variant="filled"
        style={{ marginRight: "20px", marginTop: "20px" }}
      >
        <Button
          variant="contained"
          color="primary"
          onClick={handleButtonClick}
          disabled={loading}
        >
          {loading ? <CircularProgress size={24} /> : "Fetch Weather Data"}
        </Button>
      </FormControl>

      {timestamp === "CUSTOM" && (
        <div style={{ marginTop: "30px" }}>
          <TextField
            label="Start"
            type="datetime-local"
            value={customStart}
            onChange={(e) => setCustomStart(convertToTimeStamp(e.target.value))}
            style={{ marginRight: "20px" }}
          />
          <TextField
            label="End"
            type="datetime-local"
            value={customEnd}
            onChange={(e) => setCustomEnd(convertToTimeStamp(e.target.value))}
          />
        </div>
      )}

      <Table style={{ marginTop: "20px" }}>
        <TableHead>
          <TableRow>
            <TableCell>Weather Type ({eventType})</TableCell>
            <TableCell>Value</TableCell>
          </TableRow>
        </TableHead>
        <TableBody>
          {weatherDataList.map((item: WeatherData, index: number) => (
            <TableRow key={index}>
              <TableCell>{item.name}</TableCell>
              <TableCell>{item.value} %</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </div>
  );
};

export default WeatherDataTable;
