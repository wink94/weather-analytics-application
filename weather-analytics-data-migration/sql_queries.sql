CREATE TABLE weather_analytics_data_db.weather_unnormalized (
weather_id int NOT NULL AUTO_INCREMENT,
observed_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
summary varchar(30) default null,
precip_type ENUM ('rain', 'snow', 'sleet', 'hail') DEFAULT null,
temperature_celsius decimal default 0.0,
apprnt_temperature_celsius decimal default 0.0,
humidity decimal default 0.0,
wind_speed_km_h decimal default 0.0,
wind_bearing_degrees int default 0,
visibility_km decimal default 0.0,
cloud_cover tinyint(1) default 0,
pressure_millibar decimal default 0.0,
daily_summary varchar(150) default null,
primary key (weather_id)
)

CREATE TABLE weather_analytics_data_db.weather_data (
weather_data_id int NOT NULL AUTO_INCREMENT,
observed_at datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
summary varchar(30) default null,
precip_type ENUM ('rain', 'snow', 'sleet', 'hail') DEFAULT null,
temperature_celsius decimal default 0.0,
apprnt_temperature_celsius decimal default 0.0,
humidity decimal default 0.0,
wind_speed_km_h decimal default 0.0,
wind_bearing_degrees int default 0,
visibility_km decimal default 0.0,
cloud_cover tinyint(1) default 0,
pressure_millibar decimal default 0.0,
daily_summary varchar(150) default null,
primary key (weather_data_id),
unique key weather_id_observed_at (weather_data_id,observed_at)
)

INSERT INTO weather_analytics_data_db.weather_data
(weather_data_id, observed_at, summary, precip_type, temperature_celsius, apprnt_temperature_celsius, humidity, wind_speed_km_h, wind_bearing_degrees, visibility_km, cloud_cover, pressure_millibar, daily_summary)
select *
from weather_unnormalized

CREATE TABLE weather_analytics_data_db.weather_summary (
weather_summary_id int NOT NULL AUTO_INCREMENT,
summary varchar(30) default null,
precip_type ENUM ('rain', 'snow', 'sleet', 'hail') DEFAULT null,
daily_summary varchar(150) default null,
primary key (weather_summary_id),
unique key weather_summary_id_precip_type (summary,precip_type,daily_summary)
)

INSERT INTO weather_analytics_data_db.weather_summary
(summary, precip_type, daily_summary)
select distinct summary,  precip_type,  daily_summary
from weather_unnormalized

alter table weather_data add column weather_summary_id int NOT NULL

UPDATE weather_analytics_data_db.weather_data wd
join weather_analytics_data_db.weather_summary ws
on wd.summary = ws.summary and wd.daily_summary = ws.daily_summary and wd.precip_type = ws.precip_type
SET  wd.weather_summary_id = ws.weather_summary_id

SET FOREIGN_KEY_CHECKS = 0

ALTER TABLE weather_data
ADD FOREIGN KEY (weather_summary_id) REFERENCES weather_summary(weather_summary_id);

SET FOREIGN_KEY_CHECKS = 1
