package VariationLog;

public class VariationBean 
{
	String name;
	String date;
	float Vcqty;
	float Vbqty;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getVcqty() {
		return Vcqty;
	}
	public void setVcqty(float vcqty) {
		Vcqty = vcqty;
	}
	public float getVbqty() {
		return Vbqty;
	}
	public void setVbqty(float vbqty) {
		Vbqty = vbqty;
	}
	public VariationBean(){}
	public VariationBean(String name, String date, float vcqty, float vbqty) {
		super();
		this.name = name;
		this.date = date;
		Vcqty = vcqty;
		Vbqty = vbqty;
	}
}
