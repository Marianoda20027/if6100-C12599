import { useState } from 'react';
import './App.css';

function App() {
  const [name, setName] = useState("");
  const [names, setNames] = useState<string[]>([]);

  const addName = () => {
    if (name) {
      setNames([...names, name]);
      setName("");
    }
  };

  return (
    <>
      <div>
        <ul>
          {names.map((n, index) => (
            <li key={index}>{n}</li>
          ))}
        </ul>
      </div>
      <div>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
        />
        <button onClick={addName}>Agregar</button>
        <ul>
          {names.map((name, index) => (
            <li key={index}>{name}</li>
          ))}
        </ul>
      </div>
    </>
  );
}

export default App;
