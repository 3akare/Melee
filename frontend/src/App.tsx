import { useEffect, useState } from "react"
import axios, { AxiosError } from "axios"

function App() {
  const [data, setData] = useState([]);

  const fetchData = async () => {
    try {
      const res = await axios.get("http://localhost:5500/api/blogs");
      setData(res.data)
    } catch (error) {
      console.log((error as AxiosError).message)
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  return (
    <ul>
      {data.map((item: any) => {
        return <li key={item._id}>{item.data}</li>
      })}
    </ul>
  )
}

export default App
