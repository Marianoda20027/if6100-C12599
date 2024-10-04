import React, { useState } from 'react';
import Details from './Details';

function App() {
	const [name, setName] = useState('');
  const [names, setNames] = useState<string[]>([]);

  const addName =() => { 
    console.log(names);
    setNames([...names, name]);
  };

	return(
    <>
      <div>{name}</div>
      <div>
        <input type='text' onChange={e => setName(e.target.value)}/>

        <button onClick ={addName}>Agregar</button>

        <Details names={names}/>

      </div>
    </>
  );

}

export default App;