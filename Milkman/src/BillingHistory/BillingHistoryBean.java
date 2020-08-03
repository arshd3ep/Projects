package BillingHistory;

public class BillingHistoryBean 
{
	String cname;
	float TCqty;
	float TBqty;
	float TotalBill;
	String dos;
	String doe;
	int status;
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public float getTCqty() {
		return TCqty;
	}
	public void setTCqty(float tCqty) {
		TCqty = tCqty;
	}
	public float getTBqty() {
		return TBqty;
	}
	public void setTBqty(float tBqty) {
		TBqty = tBqty;
	}
	public float getTotalBill() {
		return TotalBill;
	}
	public void setTotalBill(float totalBill) {
		TotalBill = totalBill;
	}
	public String getDos() {
		return dos;
	}
	public void setDos(String dos) {
		this.dos = dos;
	}
	public String getDoe() {
		return doe;
	}
	public void setDoe(String doe) {
		this.doe = doe;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public BillingHistoryBean(){}
	public BillingHistoryBean(String cname, float tCqty, float tBqty, float totalBill, String dos, String doe,
			int status) {
		super();
		this.cname = cname;
		TCqty = tCqty;
		TBqty = tBqty;
		TotalBill = totalBill;
		this.dos = dos;
		this.doe = doe;
		this.status = status;
	}
	
}
