package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidation {
	
	  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

	   public static boolean validateEmail(String email) {
	        Pattern pattern = Pattern.compile(EMAIL_REGEX);
	        Matcher matcher = pattern.matcher(email);
	        return matcher.matches();
	    }
	   
	   public static boolean validatePhoneNumber(String phoneNumber) {
	        return phoneNumber != null && phoneNumber.matches("\\d{10}");
	    }
	   
	 public static boolean validateString(String input) {
	        return input != null && !input.trim().isEmpty();
	    }
 
	  public static boolean validateYesOrNo(String input) {
	        return input.equals("y") || input.equals("n");
	    }
	  
	  public static boolean validateDouble(String input) {
	        try {
	            Double.parseDouble(input);
	            return true;
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	  
	  public static boolean validateMainOeuvreType(String type) {
	        return "DE_BASE".equals(type) || "SPECIALISTE".equals(type);
	    }
	  
	  public static boolean validateFactor(String input) {
	        try {
	            double value = Double.parseDouble(input);
	            return value == 1.0 || value == 1.1; 
	        } catch (NumberFormatException e) {
	            return false;
	        }
	    }
	  
	  public static boolean validateDate(String dateStr) {
		    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	        try {
	            LocalDate date = LocalDate.parse(dateStr);
	            LocalDate today = LocalDate.now();
	            return date.isAfter(today); 
	        } catch (DateTimeParseException e) {
	            return false; 
	        }
	    }
}
