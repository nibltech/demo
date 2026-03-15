@echo off

set SCRIPTS_DIR=%~dp0

rmdir /S/Q %SCRIPTS_DIR%\..\..\otel\logs 2> nul
rmdir /S/Q %SCRIPTS_DIR%\..\..\grafana\tempo-data 2>nul

:: Bring up the Docker containers
%SCRIPTS_DIR%compose.bat up -d %* 
