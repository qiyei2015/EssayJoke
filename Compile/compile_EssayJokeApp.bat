@echo off  
set ROOT_PATH=%~dp0
echo "mkdir out and remove old"
set APP=EssayJokeApp
set APP_PATH=%ROOT_PATH%..\%APP%

if not exist %ROOT_PATH%\out\apk (  
	mkdir %ROOT_PATH%\out\apk
	echo "mkdir out successfull"
)

del /q %ROOT_PATH%\out\apk\%APP%*.apk

pushd %APP_PATH%
::echo %cd%
call gradle clean 
::call gradle --refresh-dependencies
call gradle assembleRelease --parallel
popd
copy /y %APP_PATH%\build\outputs\apk\release\*.apk %ROOT_PATH%\out\apk
echo "build %APP% successfull"

echo "start install %APP% "
for /r %ROOT_PATH%\out\apk %%i in (%APP%*.apk) do set APPFILE=%%i
adb install -r %APPFILE%

pause


