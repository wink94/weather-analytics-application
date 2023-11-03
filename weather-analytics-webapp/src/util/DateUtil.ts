import moment from "moment";

export const convertToTimeStamp = (isoTimestamp: string) => {
  const format = "YYYY-MM-DD HH:mm:ss";
  const convertedTimestamp = moment(isoTimestamp).format(format);
  return convertedTimestamp;
};
