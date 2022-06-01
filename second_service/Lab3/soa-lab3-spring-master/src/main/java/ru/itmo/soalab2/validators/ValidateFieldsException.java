package ru.itmo.soalab2.validators;

import ru.itmo.soalab2.model.Error;

import java.util.List;

public class ValidateFieldsException extends Exception {

    private List<Error> errorMsg;

    public ValidateFieldsException(List<Error> errorMessage) {
        this.errorMsg = errorMessage;
    }

    public List<Error> getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(List<Error> errorMsg) {
        this.errorMsg = errorMsg;
    }
}
