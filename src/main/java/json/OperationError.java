package json;

import types.enums.ErrorCode;

/** Classe che viene parsata nel Json per informare il client della tipologia di errore avvenuta. Se l'errore non
 * è definibile utilizzare il costruttore vuoto.
 * Created by marco on 07/07/15.
 */
public class OperationError extends OperationResult {
    private ErrorCode errorCode;

    /**
     * Costruttore che imposta l'errore definito. Utilizzare come OperationError(ErrorCode.QUALCHE_ERRORE);
     *
     * @param errorCode
     */
    public OperationError(ErrorCode errorCode)
    {
        this.errorCode = errorCode;
    }

    /**
     * Costruttore vuoto che inizializza il codice errore a null per indicare un errore non definibile.
     */
    public OperationError() {
        errorCode=null;}
}
