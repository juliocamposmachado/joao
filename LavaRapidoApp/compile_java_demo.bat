@echo off
echo ========================================
echo  LAVA RAPIDO - COMPILADOR JAVA DEMO
echo ========================================
echo.

echo Criando versao desktop Java do aplicativo...
echo.

REM Criar diretorio de build
if not exist "build" mkdir build
if not exist "build\classes" mkdir build\classes

echo [1/4] Compilando classes Java...

REM Compilar as classes Java (versao simplificada sem Android)
cd app\src\main\java\com\example\lavarapido

echo Compilando Servico.java...
javac -d ..\..\..\..\..\..\build\classes Servico.java

echo Compilando Fechamento.java...
javac -d ..\..\..\..\..\..\build\classes Fechamento.java

cd ..\..\..\..\..\..

echo [2/4] Classes compiladas com sucesso!

echo [3/4] Criando versao demo desktop...

REM Criar uma versao demo que rode no desktop
echo Criando LavaRapidoDemo.jar...

cd build\classes\com\example\lavarapido
jar cf ..\..\..\..\LavaRapidoDemo.jar *.class
cd ..\..\..\..

if exist "LavaRapidoDemo.jar" (
    echo [4/4] Demo JAR criado com sucesso!
    echo.
    echo ========================================
    echo  ARQUIVO DEMO GERADO!
    echo ========================================
    echo.
    echo Arquivo: LavaRapidoDemo.jar
    echo Localizacao: %CD%\LavaRapidoDemo.jar
    echo.
    echo NOTA: Este e um arquivo de demonstracao das classes.
    echo Para o APK Android completo, use Android Studio.
) else (
    echo ERRO: Nao foi possivel criar o arquivo demo.
)

echo.
echo Para gerar o APK Android real:
echo 1. Abra o projeto no Android Studio
echo 2. Build ^> Build Bundle(s) / APK(s) ^> Build APK(s)
echo.
pause
