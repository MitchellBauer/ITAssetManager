package com.bauerperception.itassetmanager.util;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.TableCell;

import java.io.IOException;

public class HyperLinkCell<T> extends TableCell<T, Void> {
    private final Hyperlink link;

    public HyperLinkCell(String url) {
        link = new Hyperlink("Link");
        link.setOnAction(evt -> {
            //TODO multi os https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
            Runtime rt = Runtime.getRuntime();
            try {
                rt.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);

        setGraphic(empty ? null : link);
    }
}
