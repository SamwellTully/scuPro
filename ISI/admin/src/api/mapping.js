import axios from "axios";

/*
 url配置
 */
axios.defaults.baseURL = "http://8.134.49.56:8000"

export async function getUser(UserId, Tablename) {
    let params = new URLSearchParams();
    params.set("UserId",UserId)
    params.set("Tablename",Tablename)
    return (await axios.post("/mappings/check", params)).data;
  }