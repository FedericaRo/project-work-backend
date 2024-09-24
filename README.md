# Gestionale Mensa
## Generation Italy Graduation Project - <i>Junior Java Developer</i>

## Introduzione
Questo progetto è un gestionale web completo per la gestione di una mensa, sviluppato con l'obiettivo di ottimizzare le attività quotidiane e fornire una panoramica chiara dello stato dell'inventario, degli ordini e delle risorse umane.

## Tecnologie Utilizzate
* **Frontend:** Angular (versione X.X.X), Angular Material, TypeScript
* **Backend:** Spring Boot (versione X.X.X), Spring Data JPA, Spring Security, Java 17, Lombok
* **Database:** MySQL (versione X.X.X), schema dettagliato in [link al diagramma ER]
* **API:** REST API sviluppate con Spring MVC, endpoint principali: `/products`, `/orders`, `/employees`, `/notifications`
* **Testing:** JUnit, Mockito
* **Deployment:** Docker, Kubernetes (opzionale)

## Architettura
[Inserisci qui un diagramma UML che rappresenta l'architettura del sistema]

Il sistema è strutturato in tre livelli:

* **Frontend:** Un'applicazione Angular monolitica che gestisce l'interfaccia utente e interagisce con il backend tramite chiamate HTTP.
* **Backend:** Un'applicazione Spring Boot che espone le REST API, gestisce la logica di business e interagisce con il database.
* **Database:** Un database MySQL che memorizza tutte le informazioni relative a prodotti, ordini, dipendenti e notifiche.

## Funzionalità Principali
* **Gestione Inventario:**
    * Inserimento, modifica e cancellazione di prodotti.
    * Monitoraggio delle quantità in magazzino con indicatori visivi per segnalare i prodotti in esaurimento.
    * Gestione delle scadenze dei prodotti.
* **Gestione Ordini:**
    * Creazione, modifica e visualizzazione degli ordini ai fornitori.
    * Aggiornamento automatico delle quantità in magazzino al ricevimento degli ordini.
    * Visualizzazione dello storico degli ordini.
* **Gestione Personale:**
    * Creazione di profili utente con ruoli e permessi differenziati.
    * Assegnazione di compiti e scadenze.
    * Monitoraggio delle attività assegnate.
* **Comunicazioni:**
    * Bacheca per la pubblicazione di annunci e notifiche.
    * Possibilità di filtrare le comunicazioni per data, autore o categoria.
* **Dashboard:**
    * Visualizzazione personalizzata delle informazioni più importanti:
        * Task da completare.
        * Comunicazioni recenti.
        * Cambi turno.
        * Riepilogo delle attività.
        * Statistiche sull'utilizzo dei prodotti.

## Flusso di Lavoro
[Inserisci qui un diagramma di flusso che illustra il processo di creazione di un ordine]

1. L'utente accede all'applicazione e visualizza la lista dei prodotti.
2. L'utente seleziona i prodotti da ordinare e inserisce le quantità.
3. Il sistema calcola il totale dell'ordine e crea una nuova richiesta.
4. La richiesta viene inviata al backend, che la salva nel database.
5. Il fornitore viene notificato del nuovo ordine.
6. Al ricevimento della merce, l'utente conferma l'arrivo e il sistema aggiorna automaticamente le quantità in magazzino.

## Sicurezza
* **Autenticazione:** Utilizzo di token JWT per l'autenticazione degli utenti.
* **Autorizzazione:** Gestione dei ruoli e dei permessi per limitare l'accesso alle funzionalità.
* **Crittografia:** Utilizzo di algoritmi di crittografia per proteggere i dati sensibili.

## Futuro
* **Integrazione con un sistema contabile.**
* **Sviluppo di un'app mobile.**
* **Introduzione di funzionalità di analisi dei dati per ottimizzare gli acquisti.**

## Contributi
[Spiega come contribuire al progetto, ad esempio creando nuove funzionalità, correggendo bug, ecc.]

## Licenza
[Indica la licenza sotto cui è rilasciato il progetto]

## Contributi
[Spiega come contribuire al progetto, ad esempio creando nuove funzionalità, correggendo bug, ecc.]

## Licenza
[Indica la licenza sotto cui è rilasciato il progetto]
