# Gestionale Mensa
## Generation Italy Graduation Project - <i>Junior Java Developer</i>

## Introduzione
Questo progetto è un gestionale web completo per la gestione di una mensa, sviluppato con l'obiettivo di ottimizzare le attività quotidiane e fornire una panoramica chiara dello stato dell'inventario, degli ordini e delle risorse umane.

## Tecnologie Utilizzate
* **Frontend:** Angular (versione 18), Angular Material, TypeScript
* **Backend:** Spring Boot, Spring Data JPA, Spring Security, Java 17, Lombok
* **Database:** MySQL
* **API:** REST API sviluppate con Spring MVC
* **Testing:** JUnit

## Architettura
Il sistema è strutturato in tre livelli:

* **Frontend:** Un'applicazione Angular monolitica che gestisce l'interfaccia utente e interagisce con il backend tramite chiamate HTTP.
* **Backend:** Un'applicazione Spring Boot che espone le REST API, gestisce la logica di business e interagisce con il database.
* **Database:** Un database MySQL che memorizza tutte le informazioni relative a prodotti, ordini, dipendenti, etc.

## Funzionalità Principali
* **Gestione Inventario:**
    * Inserimento, modifica e cancellazione di prodotti.
    * Monitoraggio delle quantità in magazzino con indicatori visivi per segnalare i prodotti in esaurimento.
* **Gestione Ordini:**
    * Creazione, modifica e visualizzazione degli ordini ai fornitori.
    * Aggiornamento automatico delle quantità in magazzino al ricevimento degli ordini.
    * Visualizzazione dello storico degli ordini.
* **Gestione Personale:**
    * Creazione di account per dipendenti e admin con permessi differenziati.
    * Creazione di profili utente.
    * Monitoraggio delle statistiche del personale.
 * * **Gestione Mansioni:**
    * Aggiornamento periodico delle mansioni.
    * Gestione e modifica delle mansioni.
* **Comunicazioni:**
    * Bacheca per la pubblicazione di annunci e notifiche.
    * Possibilità di filtrare le comunicazioni per data, autore o categoria.
* **Dashboard:**
    * Visualizzazione personalizzata delle informazioni più importanti:
        * Task da completare.
        * Comunicazioni recenti.
        * Cambi turno.
        * Riepilogo delle attività.
        * Statistiche sugli ordini in arrivo.

## Flusso di Lavoro

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
