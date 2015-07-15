define({ "api": [
  {
    "type": "get",
    "url": "/api/filmDay/*",
    "title": "",
    "name": "FilmDay",
    "group": "Film",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "Stringa",
            "description": "<p>con la data su cui interrogare (in formato &quot;yyyy-mm-dd&quot;)</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "Lista",
            "description": "<p>dei film proiettati in quella giornata. Contenente tutti i dati del film e la lista degli spettacoli relativi a quel film in quella giornata con data, orario e codice spettacolo.</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: Lanciato quanto i parametri passati tramite la url non matchano</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/FilmDay.java",
    "groupTitle": "Film"
  },
  {
    "type": "get",
    "url": "/api/film/*",
    "title": "",
    "name": "FilmSingolo",
    "group": "Film",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>Int</p> ",
            "optional": false,
            "field": "Numero",
            "description": "<p>intero codice del film da restituire</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>Film</p> ",
            "optional": false,
            "field": "Oggetto",
            "description": "<p>contenente tutti i dati del film degli spettacoli relativi a quel film in quella giornata con data, orario e codice spettacolo.</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: Lanciato quanto i parametri passati tramite la url non matchano</p> "
          }
        ],
        "4": [
          {
            "group": "4",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>FILM_NOT_FOUND: lanciato quando il film cercato non esiste nel DB</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/FilmSingolo.java",
    "groupTitle": "Film"
  },
  {
    "type": "get",
    "url": "/api/filmWeek",
    "title": "",
    "name": "FilmWeek",
    "group": "Film",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "filmList",
            "description": "<p>Lista dei film proiettati in quella settimana. Contenente tutti i dati del film e la lista degli spettacoli relativi a quel film in quella settimana con data, orario e codice spettacolo.</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "4": [
          {
            "group": "4",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>FILM_NOT_FOUND: lanciato quando il film cercato non esiste nel DB</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/FilmWeek.java",
    "groupTitle": "Film"
  },
  {
    "type": "get",
    "url": "/api/user/*",
    "title": "",
    "name": "GetUser",
    "group": "GetUser",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>l'username dell'utente richiesto</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name",
            "description": "<p>il nome dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "surname",
            "description": "<p>il cognome dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>l'email dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "phone_number",
            "description": "<p>il numero di telefono dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "bithday",
            "description": "<p>la data di nascita dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>float</p> ",
            "optional": false,
            "field": "residual_credit",
            "description": "<p>il credito residuo dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "role",
            "description": "<p>i privilegi dell'utente</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: Json in input non ha contenuto o è imparsabile.</p> "
          }
        ],
        "6": [
          {
            "group": "6",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>USER_NOT_FOUND: L'utente è un admin o più e ha cercato dati di un utente non esistente.</p> "
          }
        ],
        "8": [
          {
            "group": "8",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>NOT_AUTHORIZED: l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.</p> "
          }
        ],
        "10": [
          {
            "group": "10",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>NOT_LOGGED_IN: l'utente non è loggato</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/getUser.java",
    "groupTitle": "GetUser"
  },
  {
    "type": "post",
    "url": "/api/login",
    "title": "",
    "name": "Login",
    "group": "Login",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>Username.</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>password.</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>Username.</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "1": [
          {
            "group": "1",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_REQ: richiesta vuota</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: invalidParameters parametri invalidi</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>ALREADY_LOGGED: l'utente è già loggato e non può loggare di nuovo senza prima effettuare il logout</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/Login.java",
    "groupTitle": "Login"
  },
  {
    "type": "post",
    "url": "/api/logout",
    "title": "",
    "name": "Logout",
    "group": "Logout",
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "10": [
          {
            "group": "10",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>NOT_LOGGED_IN: l'utente non dispone di una sessione valida da cui sloggare</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/Logout.java",
    "groupTitle": "Logout"
  },
  {
    "type": "post",
    "url": "/api/passwordRecovery",
    "title": "",
    "name": "PasswordRecovery",
    "group": "PasswordRecovery",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>l'email a cui inviare il recupero password</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>l'indirizzo email a cui è stata inviata la mail</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>ALREADY_LOGGED: è già presente una sessione valida</p> "
          }
        ],
        "9": [
          {
            "group": "9",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>INVALID_MAIL: parameters la mail non valida</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/PasswordRecovery.java",
    "groupTitle": "PasswordRecovery"
  },
  {
    "type": "post",
    "url": "/api/setNewPassword",
    "title": "",
    "name": "SetNewPassword",
    "group": "PasswordRecovery",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>password.</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "verificationCode",
            "description": "<p>il codice di verifica relativo</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>ALREADY_LOGGED: L'utente è già loggato e fino all'implementazione del cambio password non può fare niente</p> "
          }
        ],
        "11": [
          {
            "group": "11",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>WRONG_CONFIRMATION_CODE: Codice di conferma non valido</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/SetNewPassword.java",
    "groupTitle": "PasswordRecovery"
  },
  {
    "type": "post",
    "url": "/api/confirmRegistration",
    "title": "",
    "name": "ConfirmRegistration",
    "group": "Registration",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "verificationCode",
            "description": "<p>il codice di verifica spedito via mail</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>l'username dell'utente appena registrato.</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>ALREADY_LOGGED: è già presente una sessione valida con quel client</p> "
          }
        ],
        "11": [
          {
            "group": "11",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>WRONG_CONFIRMATION_CODE: il codice di conferma della registrazione è errato</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/ConfirmRegistration.java",
    "groupTitle": "Registration"
  },
  {
    "type": "post",
    "url": "/api/register",
    "title": "",
    "name": "Register",
    "group": "Registration",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>Username.</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "password",
            "description": "<p>password.</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "birthday",
            "description": "<p>data di nascita</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name",
            "description": "<p>nome dell'utente</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "surname",
            "description": "<p>cognome dell'utente</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>email dell'utente</p> "
          },
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "phone",
            "description": "<p>numero di telefono dell'utente</p> "
          }
        ]
      }
    },
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>l'indirizzo email a cui è stata inviata la mail</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: parameters parametri di input che non passano la validazione</p> "
          }
        ],
        "3": [
          {
            "group": "3",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>DUPLICATE_FIELD: parameters parametri di input duplicati</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>ALREADY_LOGGED: è già presente una sessione valida</p> "
          }
        ],
        "9": [
          {
            "group": "9",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>INVALID_MAIL: parameters la mail non valida</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/Register.java",
    "groupTitle": "Registration"
  },
  {
    "type": "get",
    "url": "/api/pagamenti/*",
    "title": "",
    "name": "pagamenti",
    "group": "pagamenti",
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>l'username dell'utente richiesto</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "name",
            "description": "<p>il nome dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "surname",
            "description": "<p>il cognome dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "email",
            "description": "<p>l'email dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "phone_number",
            "description": "<p>il numero di telefono dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "bithday",
            "description": "<p>la data di nascita dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>float</p> ",
            "optional": false,
            "field": "residual_credit",
            "description": "<p>il credito residuo dell'utente</p> "
          },
          {
            "group": "Success 200",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "role",
            "description": "<p>i privilegi dell'utente</p> "
          }
        ]
      }
    },
    "error": {
      "fields": {
        "0": [
          {
            "group": "0",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>BAD_REQUEST: lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>EMPTY_WRONG_FIELD: l'Url di richiesta in input non ha contenuto o è imparsabile.</p> "
          }
        ],
        "6": [
          {
            "group": "6",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>USER_NOT_FOUND: L'utente è un admin che ha cercato informazioni su un utente inesistente</p> "
          }
        ],
        "8": [
          {
            "group": "8",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>NOT_AUTHORIZED: l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.</p> "
          }
        ],
        "10": [
          {
            "group": "10",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>NOT_LOGGED_IN: l'utente non è loggato</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/Pagamenti.java",
    "groupTitle": "pagamenti"
  }
] });