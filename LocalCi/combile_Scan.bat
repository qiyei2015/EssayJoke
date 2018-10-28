@echo off  
set ROOT_PATH=%~dp0..\
echo "mkdir Archive And Remove old"

if not exist %ROOT_PATH%\Archive (  
	mkdir %ROOT_PATH%\Archive
)

pushd %ROOT_PATH%\Scan
::echo %cd%
call gradle --refresh-dependencies clean assembleRelease
::call gradle assembleRelease
popd
copy /y %ROOT_PATH%\Scan\build\outputs\aar\Scan-release.aar %ROOT_PATH%\Archive

pause

