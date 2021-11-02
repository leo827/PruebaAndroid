package Librarys;

import java.security.PublicKey;

public class ErrorsMsj {
    public int ErrorId;
    public String ErrorDescription;

    public ErrorsMsj(){
        this.ErrorId = 0;
        this.ErrorDescription = "Error interno";
    }

    public int getErrorId() {
        return ErrorId;
    }

    public void setErrorId(int errorId) {
        ErrorId = errorId;
    }

    public String getErrorDescription() {
        return ErrorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        ErrorDescription = errorDescription;
    }
}
