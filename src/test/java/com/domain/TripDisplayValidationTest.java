package com.domain;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.logging.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-config.xml")
public class TripDisplayValidationTest
{
    private static final Logger log = Logger.getLogger(TripDisplayValidationTest.class.getName());

    private TripDisplay goodSingleTrip;
    private TripDisplay returnTrip;

    @Autowired
    Validator validator;

    @Before
    public void init()
    {
        goodSingleTrip = new TripDisplay();
        goodSingleTrip.origin = "Home";
        goodSingleTrip.originDateStr = "01-04-2014";
        goodSingleTrip.originTimeHours = 21;
        goodSingleTrip.originTimeMinutes = 58;

        goodSingleTrip.destination = "Work";
        goodSingleTrip.isReturn = false;

        //-------

        returnTrip = new TripDisplay();
        returnTrip.origin = "Home";
        returnTrip.originDateStr = "01-04-2014";
        returnTrip.originTimeHours = 21;
        returnTrip.originTimeMinutes = 58;

        returnTrip.destination = "Work";
        returnTrip.isReturn = true;
        returnTrip.returnDateStr = "02-04-2014";
        returnTrip.returnTimeHours = 21;
        returnTrip.returnTimeMinutes = 58;

        //------


    }


    @Test
    public void goodSingleTripTest()
    {
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
        Assert.assertTrue(violations.isEmpty());
        Assert.assertEquals(goodSingleTrip.returnDateStr, null);
    }

    @Test
    public void validationBlankTest()
    {
        TripDisplay initialTrip = new TripDisplay();
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(initialTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidDateFormat()
    {
        goodSingleTrip.originDateStr = "01/01/2012"; //should be 01-01-2012

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
        Assert.assertTrue(!violations.isEmpty());
        junit.framework.Assert.assertEquals(1, violations.size());
    }

    @Test
    public void invalidBecauseDateIsInPast()
    {
        goodSingleTrip.originDateStr = "01-01-2012"; //should be in the future

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
        Assert.assertTrue(!violations.isEmpty());
        junit.framework.Assert.assertEquals(1, violations.size());
    }

    //----------------------

    @Test
    public void goodReturn()
    {
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnDateIsInPast()
    {
        returnTrip.returnDateStr = "01-01-2012"; //should be in the future

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnDateIsNotSet()
    {
        returnTrip.returnDateStr = ""; //should have a value

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnDateBeforeOriginDate()
    {
        returnTrip.returnDateStr = "29-03-2015"; //should be after, or equal to the origin date

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnTimeIsNotAfterOriginTime()
    {
        returnTrip.returnDateStr = returnTrip.originDateStr; //should be after, or equal to the origin date
        Set <ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }
}
