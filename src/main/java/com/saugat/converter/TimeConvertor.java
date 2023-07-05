/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.saugat.converter;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

/**
 *
 * @author saugat
 */
@FacesConverter(value = "timeconverter")
public class TimeConvertor implements Converter {

    private static final DateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm");

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        try {
            java.util.Date parsedDate = TIME_FORMAT.parse(value);
            return new Time(parsedDate.getTime());
        } catch (ParseException e) {
            // Handle the parsing exception
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object value) {
        if (value instanceof Time) {
            Time time = (Time) value;
            return TIME_FORMAT.format(time);
        }
        return null;
    }

}
