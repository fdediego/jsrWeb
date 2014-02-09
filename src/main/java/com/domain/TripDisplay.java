package com.domain;

import java.util.Date;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

import com.domain.constraints.ReturnAfterOrigin;


@ReturnAfterOrigin
public class TripDisplay
{
	
    public boolean isReturn;

    @NotEmpty
    @NotNull
    public String origin;

    @NotEmpty
    @NotNull
    public String destination;

    @NotNull
    @Future
    public Date originDate;

    @Range(min=0, max = 23)
    public int originTimeHours;

    @Range(min=0, max = 59)
    public int originTimeMinutes;

    //--------
    public Date returnDate;

    @Range(min=0, max = 23)
    public int returnTimeHours;

    @Range(min=0, max = 59)
    public int returnTimeMinutes;

}
