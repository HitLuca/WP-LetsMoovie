package utilities;

import types.enums.ErrorCode;
import types.exceptions.BadRequestException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Classe che permette di parsare un parametro inviato come url nella richiesta. Va inizializzato passando come parametro la
 * stringa ottenuta con request.getPathInfo(). Una volta fatto ciò estrae automaticamente un parametro dalla url e lancia un
 * ErrorCode 2 EMPTY_WRONG_FIELD altrimenti nel caso non riesca a parsare.
 * <p/>
 * Il parametro parsato viene immagazinato nella variabile d'istanza "parameter" a cui si accede con gli appositi getter
 * Created by etrunon on 13/07/15.
 */
public class RestUrlMatcher {
    // Regex che matcha solamente le forme "/qualcosa/"
    private Pattern regExIdPattern = Pattern.compile("[\\/]([^\\/?]*)");
    private String parameter;

    public RestUrlMatcher(String pathInfo) throws BadRequestException {

        Matcher matcher;

        matcher = regExIdPattern.matcher(pathInfo);
        if (matcher.find()) {
            parameter = matcher.group(1);
            return;
        }

        throw new BadRequestException(ErrorCode.EMPTY_WRONG_FIELD);
    }

    public String getParameter() {
        return parameter;
    }
}

