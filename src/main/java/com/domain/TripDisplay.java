package com.domain;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;


public class TripDisplay
{
    public boolean isReturn;

    @NotEmpty
    public String origin;

    @NotEmpty
    public String destination;

    @NotEmpty
    @DateTimeFormat(iso=ISO.DATE)
    public String originDateStr;

    @Range(min=0, max = 23)
    public int originTimeHours;

    @Range(min=0, max = 59)
    public int originTimeMinutes;

    //--------
    @DateTimeFormat(iso=ISO.DATE)
    public String returnDateStr;

    @Range(min=0, max = 23)
    public int returnTimeHours;

    @Range(min=0, max = 59)
    public int returnTimeMinutes;

}
