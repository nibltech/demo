@echo off

set SCRIPTS_DIR=%~dp0

:: Run ps
%SCRIPTS_DIR%compose.bat ps %* 