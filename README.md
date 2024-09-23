 'use client';
import React, { useState, useEffect } from 'react';
import '../../public/styles.css';

// Componente para mostrar la lista de partidas
const GameList = ({ displayedGames, selectedGameId, handleGameDetails, handleJoinGame }) => {
  return (
    <ul className="gameListSearch">
      {displayedGames.length > 0 ? (
        displayedGames.map((game) => (
          <li key={game.id} className="gameItemSearch">
            <div className="gameInfoSearch">
              <div>{game.name} - {game.status}</div>
              <div className="buttonGroupSearch">
                <button onClick={() => handleGameDetails(game.id)} className="detailsButtonSearch">
                  {selectedGameId === game.id ? 'Ocultar detalles' : 'Ver detalles'}
                </button>
                <button onClick={() => handleJoinGame(game)} className="joinButtonSearch">
                  Unirme a la partida
                </button>
              </div>
            </div>
            {selectedGameId === game.id && <GameDetails game={game} />}
          </li>
        ))
      ) : (
        <p>No hay partidas disponibles.</p>
      )}
    </ul>
  );
};

// Componente para mostrar los detalles de una partida
const GameDetails = ({ game }) => {
  return (
    <div className="gameDetailsSearch">
      <h3>Detalles del Juego</h3>
      <div className="detailRowSearch"><strong>ID:</strong> {game.id}</div>
      <div className="detailRowSearch"><strong>Nombre:</strong> {game.name}</div>
      <div className="detailRowSearch"><strong>Estado:</strong> {game.status}</div>
      <div className="detailRowSearch">
        <strong>Jugadores:</strong> {game.players.length > 0 ? game.players.join(', ') : 'Ninguno'}
      </div>
      <div className="detailRowSearch"><strong>Ronda Actual:</strong> {game.currentRound}</div>
    </div>
  );
};

// Componente para mostrar el modal para unirse a una partida
const JoinGameModal = ({
  modalVisible,
  playerName,
  requiresPassword,
  inputPassword,
  setPlayerName,
  setInputPassword,
  handleConfirmJoin,
  setModalVisible
}) => {
  if (!modalVisible) return null;

  return (
    <div className="modalSearch">
      <div className="modalContentSearch">
        <span className="closeSearch" onClick={() => setModalVisible(false)}>&times;</span>
        <h3>Unirse a la Partida</h3>
        <p>Ingresa tu nombre de usuario para unirte a la partida:</p>
        <input
          type="text"
          placeholder="Nombre de usuario"
          value={playerName}
          onChange={(e) => setPlayerName(e.target.value)}
          className="inputSearch"
        />
        {requiresPassword && (
          <>
            <p>Este juego requiere una contraseña:</p>
            <input
              type="password"
              placeholder="Contraseña"
              value={inputPassword}
              onChange={(e) => setInputPassword(e.target.value)}
              className="inputSearch"
            />
          </>
        )}
        <button className="buttonCloseSearch" onClick={handleConfirmJoin}>
          Confirmar
        </button>
      </div>
    </div>
  );
};

const Search = () => {
  const [allGames, setAllGames] = useState([]);
  const [displayedGames, setDisplayedGames] = useState([]);
  const [error, setError] = useState('');
  const [filterName, setFilterName] = useState('');
  const [filterId, setFilterId] = useState('');
  const [numberOfGamesToShow, setNumberOfGamesToShow] = useState(50);
  const [filterStatus, setFilterStatus] = useState('lobby');
  const [selectedGameId, setSelectedGameId] = useState(null);
  const [playerName, setPlayerName] = useState('');
  const [inputPassword, setInputPassword] = useState('');
  const [modalVisible, setModalVisible] = useState(false);
  const [requiresPassword, setRequiresPassword] = useState(false);
  const [singleGame, setSingleGame] = useState(null);
  const [page, setPage] = useState(0);

  useEffect(() => {
    fetchAllGames();
    const intervalId = setInterval(() => {
      fetchAllGames();
    }, 5000);
    return () => clearInterval(intervalId);
  }, [filterStatus]);

  // Función para obtener todas las partidas
  const fetchAllGames = async () => {
    setError('');
    try {
      const url = new URL('https://contaminados.akamai.meseguercr.com/api/games');
      url.searchParams.append('status', filterStatus);
      url.searchParams.append('limit', '1000'); // Fetch all games at once

      const response = await fetch(url, {
        method: 'GET',
        headers: {
          Accept: 'application/json',
        },
      });

      if (response.ok) {
        const data = await response.json();
        if (Array.isArray(data.data) && data.data.length > 0) {
          setAllGames(data.data);
          filterAndDisplayGames(data.data, filterName, numberOfGamesToShow, page);
        } else {
          setAllGames([]);
          setDisplayedGames([]);
          setError('No hay partidas disponibles.');
        }
      } else {
        const errorData = await response.json();
        setError('Error al obtener partidas: ' + errorData.msg);
        setAllGames([]);
        setDisplayedGames([]);
      }
    } catch (error) {
      setError('Error en la solicitud: ' + error.message);
      setAllGames([]);
      setDisplayedGames([]);
    }
  };

  // Función para filtrar y mostrar las partidas
  const filterAndDisplayGames = (games, nameFilter, limit, currentPage) => {
    const filteredGames = games.filter((game) =>
      game.name.toLowerCase().includes(nameFilter.toLowerCase())
    );
    const startIndex = currentPage * limit;
    setDisplayedGames(filteredGames.slice(startIndex, startIndex + limit));
  };

  useEffect(() => {
    filterAndDisplayGames(allGames, filterName, numberOfGamesToShow, page);
  }, [filterName, numberOfGamesToShow, page, allGames]);

  // Función para obtener una partida por su ID
  const fetchGameById = async (gameId) => {
    setError('');
    resetGameDetails();

    try {
      const trimmedFilterId = gameId.trim();

      let headers = {
        'Content-Type': 'application/json',
        'player': playerName,
      };

      if (inputPassword.length >= 3) {
        headers['password'] = inputPassword;
      }

      const response = await fetch(`https://contaminados.akamai.meseguercr.com/api/games/${trimmedFilterId}`, {
        method: 'GET',
        headers: headers,
      });

      if (response.ok) {
        const result = await response.json();
        setSingleGame(result.data);
        setRequiresPassword(result.data.password);
      } else {
        const errorData = await response.json();
        setError(`Error: ${errorData.msg}`);
        setSingleGame(null);
      }
    } catch (error) {
      setError('Error en la solicitud: ' + error.message);
      setSingleGame(null);
    }
  };

  // Función para buscar una partida por ID
  const handleSearchById = () => {
    if (!filterId.trim()) {
      alert('Por favor ingrese el ID del juego.');
      return;
    }

    fetchGameById(filterId);
  };

  // Función para manejar los detalles de la partida
  const handleGameDetails = (gameId) => {
    if (selectedGameId === gameId) {
      setSelectedGameId(null);
    } else {
      setSelectedGameId(gameId);
    }
  };

  // Función para unirse a una partida
  const handleJoinGame = (game) => {
    setSelectedGameId(game.id);
    setRequiresPassword(game.password);
    setModalVisible(true);
  };

  // Función para confirmar la unión a una partida
  const handleConfirmJoin = async () => {
    if (!playerName) {
      alert('Por favor, ingresa tu nombre de jugador.');
      return;
    }

    try {
      let headers = {
        'Content-Type': 'application/json',
        'player': playerName,
      };

      if (inputPassword.length >= 3) {
        headers['password'] = inputPassword;
      }

      const response = await fetch(`https://contaminados.akamai.meseguercr.com/api/games/${selectedGameId}`, {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify({ player: playerName }),
      });

      if (response.ok) {
        const result = await response.json();
        alert(`Te has unido a la partida: ${result.data.name}`);
        setModalVisible(false);
        window.location.href = `/WaitingRoom?gameId=${selectedGameId}`;
      } else {
        const errorData = await response.json();
        alert(`Error: ${errorData.msg}`);
      }
    } catch (error) {
      alert('Error en la solicitud: ' + error.message);
    }
  };

  // Funciones para la paginación
  const handleNextPage = () => {
    const nextPage = page + 1;
    const startIndex = nextPage * numberOfGamesToShow;
    if (startIndex < allGames.length) {
      setPage(nextPage);
    }
  };

  const handlePreviousPage = () => {
    if (page > 0) {
      setPage(page - 1);
    }
  };

  // Función para resetear los detalles de la partida
  const resetGameDetails = () => {
    setSelectedGameId(null);
    setInputPassword('');
    setPlayerName('');
    setSingleGame(null);
    setGames([]);
  };

  return (
    <div className="containerSearch">
      <div className="formContainerSearch">
        <h2 className="headerSearch">Lista de Partidas</h2>

        <div className="filtersSearch">
          <div className="formGroupSearch">
            <input
              type="text"
              placeholder="Buscar por nombre de juego"
              value={filterName}
              onChange={(e) => setFilterName(e.target.value)}
              className="inputSearch"
            />
          </div>
          <div className="formGroupSearch">
            <input
              type="number"
              placeholder="Número total de partidas a mostrar"
              value={numberOfGamesToShow}
              min="1"
              onChange={(e) => setNumberOfGamesToShow(e.target.value)}
              className="inputSearch"
            />
          </div>
          <div className="formGroupSearch">
            <select
              value={filterStatus}
              onChange={(e) => setFilterStatus(e.target.value)}
              className="selectSearch"
            >
              <option value="lobby">Lobby</option>
              <option value="rounds">Rounds</option>
              <option value="ended">Ended</option>
            </select>
          </div>
        </div>

        {error && <p className="errorSearch">{error}</p>}

        <GameList
          displayedGames={displayedGames}
          selectedGameId={selectedGameId}
          handleGameDetails={handleGameDetails}
          handleJoinGame={handleJoinGame}
        />

        <div className="paginationButtons">
          <button onClick={handlePreviousPage} disabled={page === 0}>
            Anterior
          </button>
          <button onClick={handleNextPage} disabled={displayedGames.length < numberOfGamesToShow}>
            Siguiente
          </button>
        </div>

        <JoinGameModal
          modalVisible={modalVisible}
          playerName={playerName}
          requiresPassword={requiresPassword}
          inputPassword={inputPassword}
          setPlayerName={setPlayerName}
          setInputPassword={setInputPassword}
          handleConfirmJoin={handleConfirmJoin}
          setModalVisible={setModalVisible}
        />
      </div>
    </div>
  );
};

export default Search;