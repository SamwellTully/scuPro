import request from '@/utils/request'
import axios from "axios";
export async function getList() {
  
  return (await axios.get("http://8.134.49.56:8000/G/selectGenertable")).data
}
