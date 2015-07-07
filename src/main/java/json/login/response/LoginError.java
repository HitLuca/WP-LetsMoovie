package json.login.response;

import json.OperationError;
import types.enums.ErrorCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Response di Errore della servlet di login. Contiene l'error code ereditato da OperationError e aggiunge una lista di
 * stringhe contenenti i campi errati: username e password.
 * Created by etrunon on 07/07/15.
 */
public class LoginError extends OperationError {

    public List<String> invalidFields;

    public LoginError(ErrorCode code) {
        super(code);

        invalidFields = new ArrayList<>();
        //Aggiungo i campi
        invalidFields.add("username");
        invalidFields.add("password");
    }


}
