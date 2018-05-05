package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class SearchResult_Controller {

    @FXML
    private Label ExcursionName_BasicSearch, ExcursionID_BasicSearch, PortID_BasicSearch;

    public void setlabels(String ExcursionName_get, String ExcursionID_get, String PortID_get){
        ExcursionName_BasicSearch.setText(ExcursionName_get);
        ExcursionID_BasicSearch.setText(ExcursionID_get);
        PortID_BasicSearch.setText(PortID_get);
    }
}
