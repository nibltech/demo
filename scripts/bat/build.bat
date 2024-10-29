@echo off

set SCRIPTS_DIR=%~dp0

pushd %SCRIPTS_DIR%\..\..\spring\Orders
call gradlew.bat -x test copyDependencies build
popd

:: Build the Docker images
set SAG_HOME=
%SCRIPTS_DIR%compose.bat build %*