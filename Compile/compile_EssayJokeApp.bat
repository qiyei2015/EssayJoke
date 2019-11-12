@echo off  
set ROOT_PATH=%~dp0
echo "mkdir out and remove old"
set APP_PATH=%ROOT_PATH%..\EssayJokeApp

if not exist %ROOT_PATH%\out\apk (  
	mkdir %ROOT_PATH%\out\apk
	echo "mkdir out successfull"
)

pushd %APP_PATH%
::echo %cd%
call gradle clean 
::call gradle --refresh-dependencies
call gradle assembleRelease --parallel
popd
copy /y %APP_PATH%\build\outputs\apk\release\*.apk %ROOT_PATH%\out\apk
echo "build EssayJokeApp successfull"
pause


