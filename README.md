# Whats Contacts

App Android scritta in **Kotlin** con **Jetpack Compose** e architettura **MVVM**. Nasce come porting di una mini-app Flutter (che usava codice nativo Kotlin per leggere i contatti) ed è stata costruita per imparare Compose.

L'utente può cercare un contatto, selezionarne uno o più e vederli nella schermata principale, da cui può anche rimuoverli.

## Funzionalità

- Schermata principale con stato vuoto, lista dei contatti selezionati e pulsante per aggiungerne
- Schermata di ricerca con filtro in tempo reale per nome o numero
- Selezione tramite checkbox e rimozione con un tap
- Stato che sopravvive alla rotazione dello schermo
- Interfaccia edge-to-edge (la barra superiore arriva fino al bordo dello schermo)

> I contatti sono **dati finti** inseriti nel codice. La lettura dei contatti reali del dispositivo (con richiesta del permesso a runtime) è nei prossimi passi.

## Architettura

Il progetto segue MVVM con flusso dei dati a senso unico: gli eventi salgono, lo stato scende.

```
app/src/main/java/com/example/whatscontacts/
 --- MainActivity.kt        # punto di ingresso
---- model/                 # Contact e dati di esempio
---- viewmodel/             # ContactsUiState, ContactsViewModel
---- view/                  # App, MainScreen, SearchScreen, ContactRow
```

- **Model**: la data class `Contact` e la lista di esempio.
- **ViewModel**: espone un `StateFlow<ContactsUiState>` e le funzioni che lo modificano. Lo stato è immutabile e si aggiorna con `copy`.
- **View**: funzioni `@Composable` senza logica. Ricevono lo stato e le callback dai parametri (*state hoisting*).

## Tecnologie

- Kotlin
- Jetpack Compose e Material 3
- ViewModel e StateFlow
- GitHub Actions (build automatica dell'APK)

## Compilare e provare l'app

Il progetto si compila con GitHub Actions a ogni push:

1. Apri la scheda **Actions** del repository e seleziona l'ultima esecuzione.
2. Scarica l'artifact **app-debug** (uno zip).
3. Estrai `app-debug.apk` e installalo sul telefono (serve consentire l'installazione da fonti sconosciute).

In locale: apri il progetto con Android Studio ed esegui `app`.

## Cosa ho imparato

- Differenze tra Flutter e Compose: widget → composable, BLoC → ViewModel, `setState` → `remember`
- State hoisting e flusso dati a senso unico
- Gestione dello stato con `StateFlow` e `data class` immutabili
- Liste con `LazyColumn`, gestione degli insets di sistema
- Configurazione di una pipeline CI per compilare un'app Android

## Prossimi passi

- Navigazione con Navigation Compose
- Lettura dei contatti reali con richiesta del permesso a runtime
- Persistenza della selezione con Room
