define({ "api": [
  {
    "type": "get",
    "url": "/api/getUser",
    "title": "",
    "name": "GetUser",
    "group": "GetUser",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "<p>String</p> ",
            "optional": false,
            "field": "username",
            "description": "<p>l'username di cui si vogliono ottenere i dati</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "1": [
          {
            "group": "1",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Json in input non ha contenuto.</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non ha nessun login effettuato.</p> "
          }
        ],
        "8": [
          {
            "group": "8",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non dispone di un livello di autentificazione sufficente a vedere i dati.</p> "
          }
        ],
        "10": [
          {
            "group": "10",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non è loggato</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "1": [
          {
            "group": "1",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>richiesta vuota</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>quando uno o più campi non sono ritenuti validi dal validatore o non sono presenti nel DB</p> "
          },
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "invalidParameters",
            "description": "<p>parametri invalidi</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non dispone di una sessione valida da cui sloggare</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "10": [
          {
            "group": "10",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non dispone di una sessione valida da cui sloggare</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Viene lanciato quando uno o più campi sono vuoti oppure errati (non validabili)</p> "
          },
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>parametri di input che non passano la validazione</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>è già presente una sessione valida</p> "
          }
        ],
        "9": [
          {
            "group": "9",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>la mail in input non è valida e non può ricevere la mail di registrazione</p> "
          },
          {
            "group": "9",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>la mail non valida</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Viene lanciato quando uno o più campi sono vuoti oppure errati (non validabili)</p> "
          },
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>parametri di input che non passano la validazione</p> "
          }
        ],
        "11": [
          {
            "group": "11",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Codice di conferma non valido</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>è già presente una sessione valida con quel client</p> "
          }
        ],
        "11": [
          {
            "group": "11",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>l'utente non dispone di una sessione valida da cui sloggare</p> "
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
            "description": "<p>lanciato quando succedono errori gravi all'interno della servlet</p> "
          }
        ],
        "2": [
          {
            "group": "2",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Viene lanciato quando uno o più campi sono vuoti oppure errati (non validabili)</p> "
          },
          {
            "group": "2",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>parametri di input che non passano la validazione</p> "
          }
        ],
        "3": [
          {
            "group": "3",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>Viene lanciato quando uno o più campi sono già presenti nel database</p> "
          },
          {
            "group": "3",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>parametri di input duplicati</p> "
          }
        ],
        "7": [
          {
            "group": "7",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>è già presente una sessione valida</p> "
          }
        ],
        "9": [
          {
            "group": "9",
            "type": "<p>int</p> ",
            "optional": false,
            "field": "errorCode",
            "description": "<p>la mail in input non è valida e non può ricevere la mail di registrazione</p> "
          },
          {
            "group": "9",
            "type": "<p>String[]</p> ",
            "optional": false,
            "field": "parameters",
            "description": "<p>la mail non valida</p> "
          }
        ]
      }
    },
    "version": "0.0.0",
    "filename": "src/main/java/servlets/Register.java",
    "groupTitle": "Registration"
  }
] });