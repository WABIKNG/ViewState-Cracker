package cn.wanghw.models;

public class ViewStateData {
    private String viewState;
    private String viewStateGenerator;
    private boolean viewStateEncrypted;
    private String eventValidation;
    private String path;

    public String getViewState() {
        return viewState;
    }

    public void setViewState(String viewState) {
        this.viewState = viewState;
    }

    public String getViewStateGenerator() {
        return viewStateGenerator;
    }

    public void setViewStateGenerator(String viewStateGenerator) {
        this.viewStateGenerator = viewStateGenerator;
    }

    public boolean isViewStateEncrypted() {
        return viewStateEncrypted;
    }

    public void setViewStateEncrypted(boolean viewStateEncrypted) {
        this.viewStateEncrypted = viewStateEncrypted;
    }

    public String getEventValidation() {
        return eventValidation;
    }

    public void setEventValidation(String eventValidation) {
        this.eventValidation = eventValidation;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "ViewStateData{" +
                "viewState='" + viewState + '\'' +
                ", viewStateGenerator='" + viewStateGenerator + '\'' +
                ", viewStateEncrypted=" + viewStateEncrypted +
                ", eventValidation='" + eventValidation + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
