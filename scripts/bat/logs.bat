@echo off

set SCRIPTS_DIR=%~dp0

:: Output the logs from the Docker container(s)
%SCRIPTS_DIR%compose.bat logs %*