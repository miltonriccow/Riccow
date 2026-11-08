# Conta Clara para Android

Aplicativo Android nativo em Java, com interfaces Android (sem WebView ou PWA). As cinco lições e os exercícios funcionam offline; o progresso fica armazenado no dispositivo.

## Gerar o APK de teste

1. Instale o Android Studio com JDK 17 e o Android SDK 35.
2. Abra a pasta `android` como projeto no Android Studio e aguarde a sincronização do Gradle.
3. Selecione **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
4. O APK de debug estará em `app/build/outputs/apk/debug/app-debug.apk`.

O APK de debug é destinado a testes. Para distribuição, gere um APK ou Android App Bundle assinado com sua própria chave em **Build > Generate Signed Bundle / APK**. O progresso da versão web não é transferido para o app nativo.

A seção **Prática contínua** gera exercícios com números variados em seis temas: patrimônio, orçamento, resultado, juros simples, balanço patrimonial e lançamentos de débito e crédito. São modelos combinados com valores diferentes, não um catálogo infinito de lições inéditas. Cada acerto dá 5 pontos; lições concluídas dão 10 pontos uma única vez.

## Teia de conhecimento

Depois de acertar a pergunta de uma lição, o estudante pode escolher entre duas lições relacionadas ou seguir para a prática. Depois de cada acerto na prática, há dois temas relacionados para continuar, gerando outro exercício. Os caminhos são definidos previamente e os exercícios variam conforme modelos e valores; não há geração de lições novas por IA. Todas as lições também continuam acessíveis diretamente, e o progresso existente permanece no dispositivo.
