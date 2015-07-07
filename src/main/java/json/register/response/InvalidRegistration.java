package json.register.response;

import json.OperationError;
import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/** Classe di errore per definire gli errori che avvengono sulla servlet "doRegister". Contiene codice errore ErrorCode
 * e eventuale lista di parametri errati.
 * Created by marco on 06/07/15.
 */
public class InvalidRegistration extends OperationError {
    private List<String> invalidParameters;

    /**
     * Costruttore che istanzia l'oggetto assegnando l'ErrorCode inicato e aggiunge la lista di parametri allegaga.
     *
     * @param code
     * @param invalidParameters
     */
    public InvalidRegistration(ErrorCode code, List<String> invalidParameters) {
        super(code);
        this.invalidParameters = invalidParameters;
    }

    /*public InvalidRegistration(String invalidParameter) {
        super();
        invalidParameters = new ArrayList<String>();
        invalidParameters.add(invalidParameter);
    }*/
}
