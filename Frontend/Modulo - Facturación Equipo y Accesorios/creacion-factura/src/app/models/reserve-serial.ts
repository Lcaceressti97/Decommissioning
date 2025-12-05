export interface SerialNumber {
  serial_number: string;
  reservation_result: string;
}

export interface SerialNumberListItem {
  sub_warehouse_code: string;
  count: number;
  serial_number_list: SerialNumber[];
}

export interface ReserveSerialApiData {
  result_message: string;
  result_code: string;
  count: number;
  stock_list: any;
  serial_number_list: SerialNumberListItem[] | null;
}

export interface ReserveSerialApiResponse {
  code: number;
  description: string;
  data: ReserveSerialApiData;
  errors: any[];
}
