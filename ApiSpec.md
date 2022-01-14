# Tic tac toe API spec v1

For single session game against computer

## POST /board
Creates a new playing board
```json
{
  "board": [
    0,
    0,
    0,
    1,
    1,
    1,
    -1,
    -1,
    -1
  ]
}
```

## GET /board
Gets the current board being played on
```json
{
  "board": [
    0,
    0,
    0,
    1,
    1,
    1,
    -1,
    -1,
    -1
  ]
}
```
## PUT /board/{player}

Puts the symbol on the desired position

```json
{
  "position": 0
}
```

# Tic tac toe API spec v2
For single session game amongst 2 players
Creates a new playing board

Request:

```json
{
  "gameType": "MULTIPLAYER"
}
```
if `gameType` is NOT available, player will play with computer.

Response:
```json
{
  "board": [
    0,
    0,
    0,
    1,
    1,
    1,
    -1,
    -1,
    -1
  ]
}
```

## PUT /board/{player}

Puts the symbol on the desired position

```json
{
  "position": 0
}
```

# Tic tac toe API spec v3

For multiple session game against computer and other player