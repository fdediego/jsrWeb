package com.domain;


import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.logging.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:test-config.xml")
public class TripDisplayValidationTest
{
    private static final Logger log = Logger.getLogger(TripDisplayValidationTest.class.getName());

    private TripDisplay goodSingleTrip;
    private TripDisplay returnTrip;

    @Autowired
    Validator validator;

    private Date buildDate(int d,int m, int y){
    	Calendar cal = Calendar.getInstance();
    	cal.set(y, m, d);
    	return cal.getTime();
    }
    
    @Before
    public void init()
    {
        goodSingleTrip = new TripDisplay();
        goodSingleTrip.origin = "Home";
        goodSingleTrip.originDate = buildDate(1, 3, 2014); // 01-04-2014
        goodSingleTrip.originTimeHours = 21;
        goodSingleTrip.originTimeMinutes = 58;

        goodSingleTrip.destination = "Work";
        goodSingleTrip.isReturn = false;

        //-------

        returnTrip = new TripDisplay();
        returnTrip.origin = "Home";
        returnTrip.originDate = buildDate(1, 3, 2014); // 01-04-2014
        returnTrip.originTimeHours = 21;
        returnTrip.originTimeMinutes = 58;

        returnTrip.destination = "Work";
        returnTrip.isReturn = true;
        returnTrip.returnDate = buildDate(2, 3, 2014); // 02-04-2014
        returnTrip.returnTimeHours = 22;
        returnTrip.returnTimeMinutes = 00;

        //------


    }


    @Test
    public void goodSingleTripTest()
    {
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
        
		for (ConstraintViolation<TripDisplay> cv : violations) {
			log.info(cv.toString());
		}
        
        Assert.assertTrue(violations.isEmpty());
        Assert.assertNull(goodSingleTrip.returnDate);
    }
    

    @Test
    public void validationBlankTest()
    {
        TripDisplay initialTrip = new TripDisplay();
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(initialTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    
	//*********************************************************
	//NANDO: This test method does not make sense now, does it?
	//*********************************************************
//    @Test
//    public void invalidDateFormat()
//    {
//
//    	
//    	//goodSingleTrip.originDateStr = "01/01/2012"; //should be 01-01-2012
//
//        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
//        Assert.assertTrue(!violations.isEmpty());
//        Assert.assertEquals(1, violations.size());
//    }

    @Test
    public void invalidBecauseDateIsInPast()
    {
        goodSingleTrip.originDate = buildDate(1,0,2012); //01-01-2012 should be in the future

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(goodSingleTrip);
        
		for (ConstraintViolation<TripDisplay> cv : violations) {
			log.info(cv.toString());
		}
        
        Assert.assertTrue(!violations.isEmpty());
        Assert.assertEquals(1, violations.size());
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
    	returnTrip.originDate = buildDate(1,0,2012); //should be in the future
        
        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnDateIsNotSet()
    {
    	returnTrip.returnDate = null; //should have a value

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        for (ConstraintViolation<TripDisplay> cv : violations) {
			log.info(cv.toString());
		}        
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void validBecauseReturnDateSameAsOriginDate()
    {
    	returnTrip.returnDate = returnTrip.originDate; //should be after, or equal to the origin date

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnDateBeforeOriginDate()
    {
    	returnTrip.returnDate = buildDate(29, 2, 2014); //29-03-2014 //should be after, or equal to the origin date

        Set<ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }

    @Test
    public void invalidBecauseReturnTimeIsNotAfterOriginTime()
    {
    	returnTrip.returnDate = returnTrip.originDate; //should be after, or equal to the origin date
    	returnTrip.returnTimeHours = returnTrip.originTimeHours;
    	returnTrip.returnTimeMinutes = returnTrip.originTimeMinutes;
    	
        Set <ConstraintViolation<TripDisplay>> violations =  validator.validate(returnTrip);
        Assert.assertTrue(!violations.isEmpty());
    }
}
