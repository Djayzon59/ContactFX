module fr.jason.contactfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires io;
    requires kernel;
    requires forms;
    requires layout;



    opens fr.jason.contactfx to javafx.fxml;
    exports fr.jason.contactfx;
}