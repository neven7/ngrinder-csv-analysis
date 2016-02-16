package com.weibo.ngrinder;

public class NgrinderOutput {

	private String DateTime;
	private double vuser;
	private double Tests;
	private double Errors;
	private double Mean_Test_Time;
	private double Test_Time_Standard_Deviation;
	private double TPS;
	private double Mean_response_length;
	private double Response_bytes_per_second;
	private double Response_errors;
	private double Mean_time_to_resolve_host;
	private double Mean_time_to_establish_connection;
	private double Mean_time_to_first_byte;
	public String getDateTime() {
		return DateTime;
	}
	public void setDateTime(String dateTime) {
		DateTime = dateTime;
	}
	public double getVuser() {
		return vuser;
	}
	public void setVuser(double vuser) {
		this.vuser = vuser;
	}
	public double getTests() {
		return Tests;
	}
	public void setTests(double tests) {
		Tests = tests;
	}
	public double getErrors() {
		return Errors;
	}
	public void setErrors(double errors) {
		Errors = errors;
	}
	public double getMean_Test_Time() {
		return Mean_Test_Time;
	}
	public void setMean_Test_Time(double mean_Test_Time) {
		Mean_Test_Time = mean_Test_Time;
	}
	public double getTest_Time_Standard_Deviation() {
		return Test_Time_Standard_Deviation;
	}
	public void setTest_Time_Standard_Deviation(double test_Time_Standard_Deviation) {
		Test_Time_Standard_Deviation = test_Time_Standard_Deviation;
	}
	public double getTPS() {
		return TPS;
	}
	public void setTPS(double tPS) {
		TPS = tPS;
	}
	public double getMean_response_length() {
		return Mean_response_length;
	}
	public void setMean_response_length(double mean_response_length) {
		Mean_response_length = mean_response_length;
	}
	public double getResponse_bytes_per_second() {
		return Response_bytes_per_second;
	}
	public void setResponse_bytes_per_second(double response_bytes_per_second) {
		Response_bytes_per_second = response_bytes_per_second;
	}
	public double getResponse_errors() {
		return Response_errors;
	}
	public void setResponse_errors(double response_errors) {
		Response_errors = response_errors;
	}
	public double getMean_time_to_resolve_host() {
		return Mean_time_to_resolve_host;
	}
	public void setMean_time_to_resolve_host(double mean_time_to_resolve_host) {
		Mean_time_to_resolve_host = mean_time_to_resolve_host;
	}
	public double getMean_time_to_establish_connection() {
		return Mean_time_to_establish_connection;
	}
	public void setMean_time_to_establish_connection(
			double mean_time_to_establish_connection) {
		Mean_time_to_establish_connection = mean_time_to_establish_connection;
	}
	public double getMean_time_to_first_byte() {
		return Mean_time_to_first_byte;
	}
	public void setMean_time_to_first_byte(double mean_time_to_first_byte) {
		Mean_time_to_first_byte = mean_time_to_first_byte;
	}
	
	
	
	
	
}
