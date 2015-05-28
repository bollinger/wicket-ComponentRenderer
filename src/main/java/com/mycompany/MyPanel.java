package com.mycompany;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

import java.util.Date;

/**
 * Created by peter on 28/05/15.
 */
public class MyPanel extends Panel {

    public MyPanel(String id) {
        super(id);


        Model<String> dateModel = new Model<String>() {
            public String getObject() {
                Date d = new Date();
                return d.toString();
            }
        };

        add(new Label("date", dateModel));
    }




}
