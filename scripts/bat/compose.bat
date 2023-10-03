@echo off

set SCRIPTS_DIR=%~dp0
set ROOT_DIR=%SCRIPTS_DIR%..\..

pushd %ROOT_DIR%

:: Combine environment files into one
set COMBINED_ENV_FILE="%TEMP%\nibl-demo-docker.env"
del %COMBINED_ENV_FILE% > nul 2>&1
for /R %%f in (.env) do if exist %%f type "%%f" >> %COMBINED_ENV_FILE% & echo. >> %COMBINED_ENV_FILE% 

:: Start the Docker environment based on the info in the .env file
docker compose --env-file %COMBINED_ENV_FILE% %*

popd