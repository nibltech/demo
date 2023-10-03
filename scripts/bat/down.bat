@echo off

set SCRIPTS_DIR=%~dp0

:: Bring down the Docker containers
%SCRIPTS_DIR%compose.bat down %*