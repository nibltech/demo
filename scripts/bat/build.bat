@echo off

set SCRIPTS_DIR=%~dp0

pushd %SCRIPTS_DIR%\..\..\spring\Orders
gradlew.bat -x test build
popd

:: Build the Docker images
set SAG_HOME=
%SCRIPTS_DIR%compose.bat build %*