package cn.wanghw.models;

public class ViewStateData {
    private String viewState;
    private String viewStateGenerator;
    private boolean viewStateEncrypted;
    private String eventValidation;

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

    @Override
    public String toString() {
        return "ViewStateData{" +
                "viewState='" + viewState + '\'' +
                ", viewStateGenerator='" + viewStateGenerator + '\'' +
                ", viewStateEncrypted=" + viewStateEncrypted +
                ", eventValidation='" + eventValidation + '\'' +
                '}';
    }
}
