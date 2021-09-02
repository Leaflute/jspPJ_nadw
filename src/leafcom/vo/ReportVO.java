package leafcom.vo;

public class ReportVO {
	int sales;
	int margin;
	String oDate;

	String year;
	String month;
	String day;
	
	public int getSales() {
		return sales;
	}
	public void setSales(int sales) {
		this.sales = sales;
	}
	public int getMargin() {
		return margin;
	}
	public void setMargin(int margin) {
		this.margin = margin;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getDay() {
		return day;
	}
	public void setDay(String day) {
		this.day = day;
	}
	
	public String getoDate() {
		return oDate;
	}
	public void setoDate(String oDate) {
		this.oDate = oDate;
	}

	
	@Override
	public String toString() {
		return "ReportVO [sales=" + sales + ", margin=" + margin + ", year=" + year + ", month=" + month + ", day="
				+ day + "]";
	}
}
