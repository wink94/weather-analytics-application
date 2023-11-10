import json
import pymysql
import os
from concurrent.futures import ThreadPoolExecutor
import logging

def read_json_file(filename):
    with open(filename, 'r') as file:
        return json.load(file)


def process_data(item):
    try:
        item["Temperature (C)"] = round(item["Temperature (C)"], 1)
        item["Apparent Temperature (C)"] = round(item["Apparent Temperature (C)"], 1)
        item["Visibility (km)"] = round(item["Visibility (km)"], 1)
        item["Formatted Date"] = str(item["Formatted Date"]).strip().split(".")[0]

        return item
    except Exception as exception:
        logging.error(exception, exc_info=True)
        raise exception


def insert_to_db(item,connection):
    try:

        with connection.cursor() as cursor:
            cursor.execute('''
            INSERT INTO weather_analytics_data_db.weather_unnormalized
        (observed_at, summary, precip_type, temperature_celsius, apprnt_temperature_celsius, humidity, wind_speed_km_h, wind_bearing_degrees, visibility_km, cloud_cover, pressure_millibar, daily_summary)
            VALUES (%s, %s, %s, %s, %s,%s, %s,%s, %s, %s,%s, %s)
            ''', (item["Formatted Date"], item["Summary"], item["Precip Type"], item["Temperature (C)"],
                  item["Apparent Temperature (C)"], item["Humidity"], item["Wind Speed (km/h)"], item["Wind Bearing (degrees)"],
                  item["Visibility (km)"], item["Loud Cover"], item["Pressure (millibars)"], item["Daily Summary"]))

        connection.commit()
    except Exception as exception:
        logging.error(exception, exc_info=True)

def process_json(json_data,connection):
    # Process all data
    for weather_data in json_data:
        processed_data = process_data(weather_data)
        print(processed_data)
        insert_to_db(processed_data,connection)



dbUrl=''
dbUser=''
dbPassword=''
database='weather_analytics_data_db'
port=25060

def create_db_connections():
    return pymysql.connect(host=dbUrl, user=dbUser, password=dbPassword, db=database,port=port, autocommit=True)


def execute():
    connections = []
    try:
        # Get all JSON filenames in the current directory
        json_files = [f for f in os.listdir() if f.endswith('.json')]

        with ThreadPoolExecutor(max_workers=3) as executor:
            # Read all JSON files
            connections = [create_db_connections() for _ in range(3)]
            json_data = executor.map(read_json_file, json_files)
            executor.map(process_json, json_data,connections)

    except Exception as exception:
        logging.error(exception, exc_info=True)
    finally:
        for conn in connections:
            conn.close()


if __name__ == '__main__':
    execute()
