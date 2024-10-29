@echo off

set SCRIPTS_DIR=%~dp0

rmdir /S/Q %SCRIPTS_DIR%\..\..\otel\logs

:: Bring up the Docker containers
%SCRIPTS_DIR%compose.bat up -d %* 