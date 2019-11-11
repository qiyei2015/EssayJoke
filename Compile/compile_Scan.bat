@echo off  
set ROOT_PATH=%~dp0
echo "mkdir out and remove old"
set SCAN_PATH=%ROOT_PATH%..\AndroidApp\Scan

if not exist %ROOT_PATH%\out\aar (  
	mkdir %ROOT_PATH%\out\aar
	echo "mkdir out successfull"
)

pushd %SCAN_PATH%
::echo %cd%
call gradle clean 
call gradle --refresh-dependencies
call gradle assembleRelease --parallel
popd
copy /y %SCAN_PATH%\build\outputs\aar\Scan-release.aar %ROOT_PATH%\out\aar
echo "build Scan successfull"
pause

