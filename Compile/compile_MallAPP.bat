@echo off  
set ROOT_PATH=%~dp0
echo "mkdir out and remove old"

set APP=MallApp

set APP_PATH=%ROOT_PATH%..\%APP%\Mall

if not exist %ROOT_PATH%\out\apk (  
	mkdir %ROOT_PATH%\out\apk
	echo "mkdir out successfull"
)

del /q %ROOT_PATH%\out\apk\Mall*.apk

pushd %APP_PATH%
::echo %cd%
call gradle clean 
::call gradle --refresh-dependencies
call gradle assembleRelease --parallel
popd
copy /y %APP_PATH%\build\outputs\apk\release\*.apk %ROOT_PATH%\out\apk
echo "build %APP% successfull"

echo "start install %APP% "
for /r %ROOT_PATH%\out\apk %%i in (Mall*.apk) do set APPFILE=%%i
adb install -r %APPFILE%

pause


