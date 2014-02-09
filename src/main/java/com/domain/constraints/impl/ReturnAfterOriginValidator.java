package com.domain.constraints.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.domain.TripDisplay;
import com.domain.constraints.ReturnAfterOrigin;

public class ReturnAfterOriginValidator implements ConstraintValidator<ReturnAfterOrigin, Object> {

	@Override
	public void initialize(final ReturnAfterOrigin constraintAnnotation) {
	}

	@Override
	public boolean isValid(final Object value, final ConstraintValidatorContext context) {
		TripDisplay tripDisplay = (TripDisplay) value;

		if (!tripDisplay.isReturn) {
			return true;
		}

		if (tripDisplay.originDate == null || tripDisplay.returnDate == null) {
			return false;
		}

		int datesDif = tripDisplay.originDate.compareTo(tripDisplay.returnDate);
		if (datesDif < 0) {
			return true;
		} else if (datesDif > 0) {
			return false;
		} else {
			// check hours and minutes
			if (tripDisplay.originTimeHours < tripDisplay.returnTimeHours) {
				return true;
			} else if (tripDisplay.originTimeHours > tripDisplay.returnTimeHours) {
				return false;
			} else {
				return tripDisplay.originTimeMinutes < tripDisplay.returnTimeMinutes;
			}
		}

	}
}
