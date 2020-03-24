import React, { useState, useEffect } from "react";
import axios from "axios";

export function FetchData(url) {
  const [dataList, setDataList] = useState([]);

  useEffect(() => {
    async function infoFetch() {
      await axios
        .get(url)
        .then(res => {
          setDataList(res.data);
        })
        .catch(err => {
          console.log(err);
        });
    }
    infoFetch();
  }, [url]);

  return dataList;
}
