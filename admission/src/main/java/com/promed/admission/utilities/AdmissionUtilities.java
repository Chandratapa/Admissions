package com.promed.admission.utilities;

import java.sql.Timestamp;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class AdmissionUtilities {

	public static final String parseDateTimeToString(Timestamp time, String format) {
		try {
			if (time != null && !format.trim().isEmpty()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				return dateFormat.format(time);
			}
		} catch (Exception ex) {

		}

		return "";
	}

	public static final Timestamp parseStringToTimestamp(String date, String format) {

		try {
			if (!date.trim().isEmpty() && !format.trim().isEmpty()) {
				SimpleDateFormat dateFormat = new SimpleDateFormat(format);
				Date parsedDate = dateFormat.parse(date);
				Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
				return timestamp;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return null;
	}

}
