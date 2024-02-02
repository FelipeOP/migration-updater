package updater.src.com.models;

import java.time.LocalDate;

public class Document {

    private String recid;
    private String impRoute;
    private String documentName;
    private String pathResult;
    private String ipmState;
    private String owccState;
    private LocalDate processDate;

    public String getRecid() {
        return recid;
    }

    public void setRecid(String recid) {
        this.recid = recid;
    }

    public String getImpRoute() {
        return impRoute;
    }

    public void setImpRoute(String impRoute) {
        this.impRoute = impRoute;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public String getPathResult() {
        return pathResult;
    }

    public void setPathResult(String pathResult) {
        this.pathResult = pathResult;
    }

    public String getIpmState() {
        return ipmState;
    }

    public void setIpmState(String ipmState) {
        this.ipmState = ipmState;
    }

    public String getOwccState() {
        return owccState;
    }

    public void setOwccState(String owccState) {
        this.owccState = owccState;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((recid == null) ? 0 : recid.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Document other = (Document) obj;
        if (recid == null) {
            if (other.recid != null)
                return false;
        } else if (!recid.equals(other.recid))
            return false;
        return true;
    }

}
