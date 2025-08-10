@echo off
echo ========================================
echo  CRIANDO APK FINAL DO LAVA RAPIDO
echo ========================================
echo.

set BUILD_TOOLS=%ANDROID_HOME%\build-tools\34.0.0
set PLATFORM=%ANDROID_HOME%\platforms\android-34

REM Limpar e criar estrutura
if exist "build\final" rmdir /s /q "build\final"
mkdir build\final
mkdir build\final\src\com\example\lavarapido
mkdir build\final\obj
mkdir build\final\bin

echo [1/5] Preparando código fonte...
copy "LavaRapidoApp\app\src\main\java\com\example\lavarapido\Servico.java" "build\final\src\com\example\lavarapido\" >nul
copy "LavaRapidoApp\app\src\main\java\com\example\lavarapido\Fechamento.java" "build\final\src\com\example\lavarapido\" >nul
copy "LavaRapidoApp\app\src\main\java\com\example\lavarapido\DatabaseHelper.java" "build\final\src\com\example\lavarapido\" >nul
copy "AndroidManifest_fixed.xml" "build\final\AndroidManifest.xml" >nul

echo [2/5] Gerando arquivo R.java...
"%BUILD_TOOLS%\aapt" package -f -m ^
    -J "build\final\src" ^
    -M "build\final\AndroidManifest.xml" ^
    -I "%PLATFORM%\android.jar"

if %errorlevel% neq 0 (
    echo Erro no R.java, mas continuando...
)

echo [3/5] Compilando classes Java essenciais...
javac -d "build\final\obj" ^
    -cp "%PLATFORM%\android.jar" ^
    "build\final\src\com\example\lavarapido\Servico.java"

javac -d "build\final\obj" ^
    -cp "%PLATFORM%\android.jar" ^
    "build\final\src\com\example\lavarapido\Fechamento.java"

javac -d "build\final\obj" ^
    -cp "%PLATFORM%\android.jar;build\final\obj" ^
    "build\final\src\com\example\lavarapido\DatabaseHelper.java"

echo [4/5] Convertendo para formato Android DEX...
if exist "build\final\obj\com\example\lavarapido\*.class" (
    "%BUILD_TOOLS%\d8" --lib "%PLATFORM%\android.jar" ^
        --output "build\final\bin" ^
        build\final\obj\com\example\lavarapido\*.class
)

echo [5/5] Empacotando APK final...
"%BUILD_TOOLS%\aapt" package -f ^
    -M "build\final\AndroidManifest.xml" ^
    -I "%PLATFORM%\android.jar" ^
    -F "build\final\app.apk" ^
    "build\final\bin"

REM Copiar APK final
if exist "build\final\app.apk" (
    copy "build\final\app.apk" "build\LavaRapido-COMPILADO.apk" >nul
)

echo.
echo ========================================
echo  RESULTADO DA COMPILACAO
echo ========================================

if exist "build\LavaRapido-COMPILADO.apk" (
    echo ✅ APK básico criado com sucesso!
    echo 📱 Arquivo: LavaRapido-COMPILADO.apk
    echo 📍 Local: %CD%\build\LavaRapido-COMPILADO.apk
    echo 📏 Tamanho:
    dir build\LavaRapido-COMPILADO.apk | findstr "LavaRapido-COMPILADO"
    echo.
    echo ⚠️  IMPORTANTE:
    echo - Este APK contém apenas as classes de dados
    echo - Para APK completo com UI, use Android Studio
    echo - Estrutura Android válida criada
) else (
    echo ❌ Não foi possível criar APK completo
    echo.
    echo 🔍 DIAGNÓSTICO - O QUE IMPEDE A COMPILAÇÃO TOTAL:
    echo.
    echo ❌ Dependências Android UI:
    echo    - RecyclerView adapters precisam de bibliotecas Material Design
    echo    - Activities dependem de recursos XML compilados
    echo    - Layouts XML precisam ser processados pelo AAPT
    echo.
    echo ❌ Recursos não incluídos:
    echo    - Layouts XML (5 arquivos de interface)
    echo    - Strings, cores e estilos personalizados
    echo    - Ícones e drawable resources
    echo.
    echo ❌ Bibliotecas externas:
    echo    - AppCompat library
    echo    - Material Design Components
    echo    - RecyclerView support library
    echo.
    echo ✅ SOLUÇÃO COMPLETA:
    echo    Use Android Studio para compilação total
    echo    Todas as dependências serão resolvidas automaticamente
)

echo.
pause
