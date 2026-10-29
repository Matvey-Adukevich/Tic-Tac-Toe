const API_URL = 'http://localhost:1111';
let userUuid = null;
let userLogin = null;
let currentGameUuid = null;
let currentGameType = null;
let currentGame = null;
let authToken = null;
let isMoving = false;
let isRefreshing = false;
let stateVersion = 0;

function getAuthHeader() {
    return authToken;
}

async function getUserInfo() {
    try {
        const response = await fetch(`${API_URL}/user/${userUuid}`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const user = await response.json();
            userLogin = user.login;
            document.getElementById('user-uuid').textContent = userUuid;
            document.getElementById('user-login').textContent = userLogin;
        }
    } catch (error) {
        console.error('Ошибка получения информации о пользователе:', error);
    }
}

async function getUserLogin(uuid) {
    try {
        const response = await fetch(`${API_URL}/user/${uuid}`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const user = await response.json();
            return user.login;
        }
    } catch (error) {
        console.error('Ошибка получения логина пользователя:', error);
    }
    return 'Неизвестно';
}

async function findUserByUuid() {
    const uuid = document.getElementById('search-uuid').value.trim();
    const resultDiv = document.getElementById('user-result');

    if (!uuid) {
        resultDiv.innerHTML = '<p class="error">Введите UUID игрока</p>';
        return;
    }

    try {
        const response = await fetch(`${API_URL}/user/${uuid}`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const user = await response.json();
            resultDiv.innerHTML = `
                <div class="user-result-item">
                    <p><strong>UUID:</strong> ${user.uuid}</p>
                    <p><strong>Логин:</strong> ${user.login}</p>
                </div>
            `;
        } else {
            resultDiv.innerHTML = '<p class="error">Игрок не найден</p>';
        }
    } catch (error) {
        resultDiv.innerHTML = '<p class="error">Ошибка поиска: ' + error.message + '</p>';
    }
}

async function updateGamePlayersInfo(game) {
    const player1Login = await getUserLogin(game.uuidPlayer1);
    document.getElementById('player1-login').textContent = player1Login;

    if (game.uuidPlayer2) {
        const player2Login = await getUserLogin(game.uuidPlayer2);
        document.getElementById('player2-login').textContent = player2Login;
    } else {
        document.getElementById('player2-login').textContent = 'Ожидание...';
    }

    if (game.uuidWinner) {
        const winnerLogin = await getUserLogin(game.uuidWinner);
        document.getElementById('winner-login').textContent = winnerLogin;
    } else {
        document.getElementById('winner-login').textContent = '-';
    }
}

function showTab(tabName) {
    const loginForm = document.getElementById('login-form');
    const registerForm = document.getElementById('register-form');
    const tabLogin = document.getElementById('tab-login');
    const tabRegister = document.getElementById('tab-register');

    if (tabName === 'login') {
        loginForm.style.display = 'block';
        registerForm.style.display = 'none';
        tabLogin.classList.add('active');
        tabRegister.classList.remove('active');
    } else {
        loginForm.style.display = 'none';
        registerForm.style.display = 'block';
        tabLogin.classList.remove('active');
        tabRegister.classList.add('active');
    }
}

async function register() {
    const login = document.getElementById('reg-login').value;
    const password = document.getElementById('reg-password').value;

    if (!login || !password) {
        alert('Введите логин и пароль');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/auth/register`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ login, password })
        });

        if (response.ok) {
            alert('Регистрация успешна!');
        } else {
            const text = await response.text();
            alert('Ошибка регистрации: ' + text);
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

async function login() {
    const login = document.getElementById('login-login').value;
    const password = document.getElementById('login-password').value;

    const authString = btoa(`${login}:${password}`);
    authToken = `Basic ${authString}`;

    try {
        const response = await fetch(`${API_URL}/auth/login`, {
            method: 'POST',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const text = await response.text();
            const uuidMatch = text.match(/uuid: ([\da-f-]+)/);
            if (uuidMatch) {
                userUuid = uuidMatch[1];
                await getUserInfo();
                document.getElementById('auth-section').style.display = 'none';
                document.getElementById('game-section').style.display = 'block';
                alert('Вход успешен!');
            }
        } else {
            alert('Ошибка входа');
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

async function createGame() {
    const gameType = document.getElementById('game-type').value;

    try {
        const response = await fetch(`${API_URL}/game/new?opponent=${gameType}&userUuid=${userUuid}`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const game = await response.json();
            currentGame = game;
            currentGameUuid = game.uuid;
            currentGameType = game.gameType;
            stateVersion++;
            document.getElementById('game-uuid').textContent = currentGameUuid;
            document.getElementById('game-state').textContent = game.state;
            await updateGamePlayersInfo(game);
            document.getElementById('game-board').style.display = 'block';
            renderGrid(game.grid);
            alert('Игра создана!');
        } else {
            alert('Ошибка создания игры');
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

async function getWaitingGames() {
    try {
        const response = await fetch(`${API_URL}/game/waiting`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (response.ok) {
            const games = await response.json();
            const gamesList = document.getElementById('games-list');
            gamesList.innerHTML = '';

            for (const game of games) {
                const div = document.createElement('div');
                div.className = 'game-item';
                let creatorLogin = 'Загрузка...';

                try {
                    const userResponse = await fetch(`${API_URL}/user/${game.uuidPlayer1}`, {
                        method: 'GET',
                        headers: {
                            'Authorization': authToken
                        }
                    });
                    if (userResponse.ok) {
                        const user = await userResponse.json();
                        creatorLogin = user.login;
                    }
                } catch (error) {
                    creatorLogin = 'Неизвестно';
                }

                div.innerHTML = `
                    <p>UUID: ${game.uuid}</p>
                    <p>Создатель: ${creatorLogin}</p>
                    <p>Состояние: ${game.state}</p>
                    <button onclick="joinGame('${game.uuid}')">Присоединиться</button>
                `;
                gamesList.appendChild(div);
            }
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

async function joinGame(gameUuid) {
    try {
        const response = await fetch(`${API_URL}/game/join/${gameUuid}`, {
            method: 'POST',
            headers: {
                'Authorization': authToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userUuid)
        });

        if (response.ok) {
            const game = await response.json();
            currentGame = game;
            currentGameUuid = game.uuid;
            currentGameType = game.gameType;
            stateVersion++;
            document.getElementById('game-uuid').textContent = currentGameUuid;
            document.getElementById('game-state').textContent = game.state;
            await updateGamePlayersInfo(game);
            document.getElementById('game-board').style.display = 'block';
            renderGrid(game.grid);
            alert('Вы присоединились к игре!');
        } else {
            alert('Ошибка присоединения');
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    }
}

async function makeMove() {
    if (isMoving) {
        return;
    }

    const row = parseInt(document.getElementById('row').value);
    const col = parseInt(document.getElementById('col').value);

    if (isNaN(row) || isNaN(col) || row < 0 || row > 2 || col < 0 || col > 2) {
        alert('Введите корректные координаты (0-2)');
        return;
    }

    if (currentGame && currentGame.state !== 'TURN') {
        alert('Игра окончена или ещё не началась');
        return;
    }
    if (currentGame && currentGame.uuidCurrentPlayer !== userUuid) {
        alert(`Не ваш ход. Сейчас ход: ${getSymbolByUuid(currentGame, currentGame.uuidCurrentPlayer)}`);
        return;
    }

    const grid = getGridData();
    if (grid[row * 3 + col] !== 0) {
        alert('Клетка уже занята');
        return;
    }
    const symbol = getCurrentSymbol();
    grid[row * 3 + col] = symbol;

    isMoving = true;
    try {
        const response = await fetch(`${API_URL}/game/play/${currentGameUuid}`, {
            method: 'POST',
            headers: {
                'Authorization': authToken,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                uuid: currentGameUuid,
                grid: { grid: grid },
                gameType: currentGameType,
                userUuid: userUuid
            })
        });

        if (response.ok) {
            const game = await response.json();
            currentGame = game;
            stateVersion++;
            document.getElementById('game-state').textContent = game.state;
            renderGrid(game.grid);
            
            if (game.state === 'WIN' || game.state === 'DRAW') {
                alert(`Игра окончена! Результат: ${game.state}`);
            } else if (game.grid.grid[row * 3 + col] !== symbol &&
                    game.uuidCurrentPlayer !== userUuid) {
                alert(`Не ваш ход. Сейчас ход: ${getSymbolByUuid(game, game.uuidCurrentPlayer)}`);
            }
        } else {
            const text = await response.text();
            alert('Ошибка хода: ' + text);
        }
    } catch (error) {
        alert('Ошибка: ' + error.message);
    } finally {
        isMoving = false;
    }
}

async function refreshGame(showError = false) {
    if (!currentGameUuid || isMoving || isRefreshing) {
        return;
    }
    if (!showError && currentGame &&
            (currentGame.state === 'WIN' || currentGame.state === 'DRAW')) {
        return;
    }

    const gameUuid = currentGameUuid;
    const version = stateVersion;
    isRefreshing = true;

    try {
        const response = await fetch(`${API_URL}/game/${gameUuid}`, {
            method: 'GET',
            headers: {
                'Authorization': authToken
            }
        });

        if (gameUuid !== currentGameUuid || version !== stateVersion) {
            return;
        }

        if (response.ok) {
            const game = await response.json();
            if (gameUuid !== currentGameUuid || version !== stateVersion) {
                return;
            }
            currentGame = game;
            currentGameType = game.gameType;
            document.getElementById('game-state').textContent = game.state;
            await updateGamePlayersInfo(game);
            renderGrid(game.grid);
        }
    } catch (error) {
        if (showError) {
            alert('Ошибка: ' + error.message);
        }
    } finally {
        isRefreshing = false;
    }
}

function getCurrentSymbol() {
    if (!currentGame) {
        return 1;
    }
    if (currentGame.uuidCurrentPlayer === currentGame.uuidPlayer1) {
        return 1;
    }
    if (currentGame.uuidCurrentPlayer === currentGame.uuidPlayer2) {
        return 2;
    }
    return 1;
}

function getSymbolByUuid(game, uuid) {
    if (!game || !uuid) {
        return '?';
    }
    if (uuid === game.uuidPlayer1) {
        return game.symbolPlayer1;
    }
    if (uuid === game.uuidPlayer2) {
        return game.symbolPlayer2;
    }
    return '?';
}

function renderGrid(grid) {
    const gridElement = document.getElementById('grid');
    gridElement.innerHTML = '';

    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            const cell = document.createElement('div');
            cell.className = 'cell';
            const value = grid.grid[i * 3 + j];
            
            if (value === 1) {
                cell.textContent = 'X';
                cell.classList.add('x');
            } else if (value === 2) {
                cell.textContent = 'O';
                cell.classList.add('o');
            }
            
            cell.onclick = () => {
                document.getElementById('row').value = i;
                document.getElementById('col').value = j;
            };
            
            gridElement.appendChild(cell);
        }
    }
}

function getGridData() {
    const gridElement = document.getElementById('grid');
    const cells = gridElement.getElementsByClassName('cell');
    const gridData = [];
    
    for (let cell of cells) {
        if (cell.classList.contains('x')) {
            gridData.push(1);
        } else if (cell.classList.contains('o')) {
            gridData.push(2);
        } else {
            gridData.push(0);
        }
    }
    
    return gridData;
}

setInterval(refreshGame, 2000);
