package example;

import java.sql.Struct;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.ws.rs.core.Response;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.sqs.model.Message;

public class Handler implements RequestHandler<String, Response>{

	@Override
	public Response handleRequest(String request, Context context) {
		
		
		
		Calendar date = java.util.Calendar.getInstance();
		Date curdate = date.getTime();
		int curHour = curdate.getHours();
		int curMinute = curdate.getMinutes();
		
		curHour -= 5;
		if(curHour < 0) curHour += 24;
		
		int curTime = curHour*60 + curMinute;
	
		
		int ind = Arrays.binarySearch(busTimes, curTime);
		if(ind < 0) ind = Math.abs(ind+1);
		
		String result = "Current time is " + curTime + " The next few bus times are: ";
		boolean pm = false;

		for(int i = ind; i < busTimes.length && i < ind+5; i++) {
			int thistime = busTimes[i];
			String curString = "";
			int thishour = thistime/60;
			if(thishour >= 12) pm = true;
			if(thishour > 12) thishour -=12;
			thistime %=60;
			int thisminute = thistime;
			curString += thishour+":";
			if(thisminute < 10) curString+= "0";
			curString += thisminute;
			if(pm) curString+="pm";
			else curString+= "am";
			curString += ", ";
			result+= curString;
		}
		result = result.substring(0, result.length()-2);
		ResponseEntity re = new ResponseEntity(result);
		return Response.status(200).entity(re).header("Content-type", "application/json").build();
		
	}
	
	static final int[] busTimes = {444,
			455,
			466,
			477,
			488,
			499,
			510,
			521,
			532,
			543,
			554,
			565,
			576,
			587,
			598,
			609,
			620,
			631,
			642,
			654,
			666,
			678,
			690,
			702,
			714,
			726,
			738,
			750,
			762,
			774,
			786,
			798,
			810,
			822,
			834,
			846,
			858,
			870,
			882,
			894,
			906,
			919,
			931,
			943,
			955,
			967,
			979,
			991,
			1003,
			1015, 
			1027, 
			1039, 
			1051, 
			1063, 
			1075, 
			1087, 
			1099, 
			1111, 
			1123, 
			1135, 
			1170, 
			1204, 
			1238, 
			1272, 
			1306, 
			1340, 
			1374};
}

class ResponseEntity{
	String fulfillmentText;
	Message fulfillmentMessages[] = new Message[0];
	String source = "";
	Struct payload = null;
	Context outputContexts[]= new Context[0];
	
	public String getFulfillmentText() {
		return fulfillmentText;
	}

    public void setText(String f) {
        this.fulfillmentText = f;
    }

    public ResponseEntity(String f) {
        this.fulfillmentText = f;
    }

    public ResponseEntity() {
    }
}