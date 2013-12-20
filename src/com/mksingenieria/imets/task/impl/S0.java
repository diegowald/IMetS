package com.mksingenieria.imets.task.impl;

import android.content.Context;
import android.telephony.SmsMessage;

import com.mksingenieria.imets.exceptions.BaseException;
import com.mksingenieria.imets.model.Device;
import com.mksingenieria.imets.task.SMSSender;
import com.mksingenieria.imets.task.Task;

/**
 * Created by diego.wald on 12/11/13.
 */
public class S0 extends Task {

	public S0(Device device, Context context) {
		super("S*0", device, context);
	}

	@Override
	protected String getSuccessMessage() {
		return "Message sent";
	}

	@Override
	protected String getErrorMessage() {
		return "Error";
	}

	@Override
	protected boolean doJob() {
		return SMSSender.sendSMS(device.phone(), "S*0");
	}	

	/**
	 * El formato es 
	 *  S*0<sp>00.0<sp>+00.0<sp>000   (sp= espacio, primero H y después T)
	 * spllited[0] = S*0
	 * splitted[1] = XX.X <- HR
	 * splitted[2] = +XX.X <- C
	 * @throws BaseException 
	 */
	@Override
	protected String processReceptionMessage(String phoneNumner,
			SmsMessage smsMessage) throws BaseException {

		String[] splitted = smsMessage.getMessageBody().split(" ");
		
		double HR = getHR(splitted[1]);
		double T = getTemp(splitted[2]);
		double DewPt = calculateDewPoint(HR, T);
		return "DewPoint=" + DewPt;
	}

	/**
	 * @param HRString = HR:XX.X%
	 * @return XX.X
	 * @throws BaseException 
	 */
	private double getHR(String HRString) throws BaseException {
		String aux = HRString.trim();
		return Double.parseDouble(aux);
	}

	/**
	 * 
	 * @param TempString = T:+XX.XC
	 * @return
	 * @throws BaseException 
	 */
	private double getTemp(String TempString) throws BaseException {
		String aux = TempString.trim();
		return Double.parseDouble(aux);
	}

	/**
	 * Calculates dew point
	 * @param HR
	 * @param Temp
	 * @return
	 */
	/*double calculateDewPoint(double RH, double T) {
	    	double a = 6.1121; // milibar
	    	double b = 18.678; // 
	    	double c = 257.14; // C
	    	double d = 234.5; // C

	    	double gamma = Math.log(RH / 100.0 * (
	    			(b - T / d) * (T / (c+T))));
	    	double Tdp = (c * gamma) / (b - gamma);
	    	return Tdp;
	    }*/

	double calculateDewPoint(double H, double T) {
		double k = (Math.log10(H)-2)/0.4343 + (17.62 * T)/ (243.12+T);
		return 243.12 * k / (17.62-k);
	}



}

