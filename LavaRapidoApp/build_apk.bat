@echo off
echo ========================================
echo  LAVA RAPIDO - COMPILADOR DE APK
echo ========================================
echo.

REM Verificar se existe o gradle wrapper
if not exist "gradlew.bat" (
    echo Gerando Gradle Wrapper...
    gradle wrapper
)

echo Iniciando compilacao do APK...
echo.

REM Limpar projeto
echo [1/4] Limpando projeto anterior...
call gradlew clean

REM Compilar projeto
echo [2/4] Compilando codigo fonte...
call gradlew assembleDebug

REM Verificar se o APK foi gerado
if exist "app\build\outputs\apk\debug\app-debug.apk" (
    echo [3/4] APK gerado com sucesso!
    echo [4/4] Localizacao do APK: app\build\outputs\apk\debug\app-debug.apk
    echo.
    echo ========================================
    echo  COMPILACAO CONCLUIDA COM SUCESSO!
    echo ========================================
    echo.
    echo Para instalar no dispositivo:
    echo 1. Conecte o dispositivo Android via USB
    echo 2. Ative a "Depuracao USB" nas configuracoes do desenvolvedor
    echo 3. Execute: adb install app\build\outputs\apk\debug\app-debug.apk
) else (
    echo [ERROR] Falha na geracao do APK!
    echo Verifique os erros acima e tente novamente.
)

echo.
pause
