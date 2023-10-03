@echo off

set SCRIPTS_DIR=%~dp0

:: Bring up the Docker containers
%SCRIPTS_DIR%compose.bat up -d %* 