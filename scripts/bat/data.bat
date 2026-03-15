@echo off

set SCRIPTS_DIR=%~dp0

REM Check if the first argument is "consumer"
if "%1"=="consumer" (
    REM Run the API Consumer container
    "%SCRIPTS_DIR%\compose.bat" run --remove-orphans -d api-consumer --name api-consumer
) else (
    REM Run the Nibble Demo API Postman collection 100 times with 1 second delay
    "%SCRIPTS_DIR%\compose.bat" run --remove-orphans -d newman run nibble-demo-api.postman_collection.json -n 100 --delay-request 1000
)
