@echo off
echo ========================================
echo  COMPILANDO APK REAL DO LAVA RAPIDO
echo ========================================
echo.

REM Definir caminhos
set BUILD_TOOLS=%ANDROID_HOME%\build-tools\34.0.0
set PLATFORM=%ANDROID_HOME%\platforms\android-34

REM Criar estrutura limpa
if exist "build\simple-apk" rmdir /s /q "build\simple-apk"
mkdir build\simple-apk
mkdir build\simple-apk\src
mkdir build\simple-apk\obj
mkdir build\simple-apk\bin

echo [1/6] Copiando arquivos fonte...
xcopy "LavaRapidoApp\app\src\main\java" "build\simple-apk\src" /E /Y >nul
copy "AndroidManifest_fixed.xml" "build\simple-apk\AndroidManifest.xml" >nul

echo [2/6] Gerando R.java...
"%BUILD_TOOLS%\aapt" package -f -m ^
    -J "build\simple-apk\src" ^
    -M "build\simple-apk\AndroidManifest.xml" ^
    -I "%PLATFORM%\android.jar"

if %errorlevel% neq 0 (
    echo ERRO na geração do R.java!
    pause
    exit /b 1
)

echo [3/6] Compilando Java para bytecode...
javac -d "build\simple-apk\obj" ^
    -cp "%PLATFORM%\android.jar" ^
    -sourcepath "build\simple-apk\src" ^
    "build\simple-apk\src\com\example\lavarapido\*.java" ^
    "build\simple-apk\src\com\example\lavarapido\R.java"

if %errorlevel% neq 0 (
    echo ERRO na compilação Java!
    echo Tentando apenas classes principais...
    javac -d "build\simple-apk\obj" ^
        -cp "%PLATFORM%\android.jar" ^
        "build\simple-apk\src\com\example\lavarapido\Servico.java" ^
        "build\simple-apk\src\com\example\lavarapido\Fechamento.java" ^
        "build\simple-apk\src\com\example\lavarapido\DatabaseHelper.java"
    
    if %errorlevel% neq 0 (
        echo Falha total na compilação!
        pause
        exit /b 1
    )
    echo Compilação parcial realizada...
)

echo [4/6] Convertendo para DEX...
"%BUILD_TOOLS%\d8" --lib "%PLATFORM%\android.jar" ^
    --output "build\simple-apk\bin" ^
    "build\simple-apk\obj\com\example\lavarapido\*.class"

if %errorlevel% neq 0 (
    echo ERRO na conversão DEX!
    pause
    exit /b 1
)

echo [5/6] Criando APK...
"%BUILD_TOOLS%\aapt" package -f ^
    -M "build\simple-apk\AndroidManifest.xml" ^
    -I "%PLATFORM%\android.jar" ^
    -F "build\simple-apk\app-unsigned.apk" ^
    "build\simple-apk\bin"

if %errorlevel% neq 0 (
    echo ERRO no empacotamento!
    pause
    exit /b 1
)

echo [6/6] Finalizando APK...
copy "build\simple-apk\app-unsigned.apk" "build\LavaRapido-FINAL.apk" >nul

if exist "build\LavaRapido-FINAL.apk" (
    echo.
    echo ========================================
    echo  ✅ APK CRIADO COM SUCESSO!
    echo ========================================
    echo.
    echo 📱 Arquivo: LavaRapido-FINAL.apk
    echo 📍 Localização: %CD%\build\LavaRapido-FINAL.apk
    echo.
    dir build\LavaRapido-FINAL.apk | findstr "LavaRapido-FINAL"
    echo.
    echo ⚠️  NOTA: Este é um APK básico para demonstração
    echo ✅ Contém a estrutura correta do Android
    echo 📲 Para APK totalmente funcional, use Android Studio
) else (
    echo ❌ Falha na criação do APK!
)

echo.
pause
