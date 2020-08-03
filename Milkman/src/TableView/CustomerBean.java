package TableView;

public class CustomerBean 
{
	String date;
	String name;
	String mobile;
	float cqty;
	float cprice;
	float bqty;
	float bprice;
	String address;
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public float getCqty() {
		return cqty;
	}

	public void setCqty(float cqty) {
		this.cqty = cqty;
	}

	public float getCprice() {
		return cprice;
	}

	public void setCprice(float cprice) {
		this.cprice = cprice;
	}

	public float getBqty() {
		return bqty;
	}

	public void setBqty(float bqty) {
		this.bqty = bqty;
	}

	public float getBprice() {
		return bprice;
	}

	public void setBprice(float bprice) {
		this.bprice = bprice;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public CustomerBean(String date, String name, String mobile, float cqty, float cprice, float bqty, float bprice,
			String address) 
	{
		super();
		this.date = date;
		this.name = name;
		this.mobile = mobile;
		this.cqty = cqty;
		this.cprice = cprice;
		this.bqty = bqty;
		this.bprice = bprice;
		this.address = address;
	}

}
