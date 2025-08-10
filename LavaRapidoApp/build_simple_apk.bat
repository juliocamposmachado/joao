@echo off
echo ========================================
echo  LAVA RAPIDO - GERADOR DE APK SIMPLES
echo ========================================
echo.

echo AVISO: Para gerar o APK completo, voce precisa:
echo 1. Android Studio instalado
echo 2. Android SDK configurado
echo 3. Ou usar o Android Command Line Tools
echo.

echo Verificando Java...
java -version
if %ERRORLEVEL% neq 0 (
    echo ERRO: Java nao encontrado!
    pause
    exit /b 1
)

echo.
echo Java encontrado! Mas para compilar um APK Android, precisamos de:
echo.
echo *** OPCOES PARA GERAR O APK ***
echo.
echo OPCAO 1 - Android Studio (RECOMENDADO):
echo 1. Baixe o Android Studio: https://developer.android.com/studio
echo 2. Instale e configure
echo 3. Abra o projeto na pasta LavaRapidoApp
echo 4. Build ^> Build Bundle(s) / APK(s) ^> Build APK(s)
echo.
echo OPCAO 2 - Command Line Tools:
echo 1. Baixe Android Command Line Tools
echo 2. Configure o ANDROID_HOME
echo 3. Execute: gradlew assembleDebug
echo.
echo OPCAO 3 - Online APK Builder:
echo 1. Compacte a pasta LavaRapidoApp
echo 2. Use servicos como ApkOnline.com
echo 3. Faca upload do projeto
echo.
echo ========================================
echo  PROJETO PRONTO PARA COMPILACAO!
echo ========================================
echo.
echo O codigo esta completo e funcional.
echo Escolha uma das opcoes acima para gerar o APK.
echo.

pause
