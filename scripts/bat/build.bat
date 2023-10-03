@echo off

set SCRIPTS_DIR=%~dp0

:: Build the Docker images
set SAG_HOME=
%SCRIPTS_DIR%compose.bat build %*