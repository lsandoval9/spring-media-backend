package com.lsandoval9.springmedia.exceptions;

import com.lsandoval9.springmedia.helpers.enums.CredentialTaken;

public class CreadentialsAlreadyTakenException extends RuntimeException{

    public CreadentialsAlreadyTakenException(String message, CredentialTaken credentialTaken) {
        super(message);
    }
}
