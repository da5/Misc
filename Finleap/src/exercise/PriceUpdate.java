package exercise;

public class PriceUpdate {
	
	private final String companyName;
	private final double price;
	
	public PriceUpdate(String companyName, double price) {
		this.companyName = companyName;
		this.price = price;
	}
	
	public String getCompanyName() {
		return this.companyName;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	@Override
	public String toString() {
		return companyName + " - " + price;
	}
	
	@Override
	public boolean equals(Object obj) {
		if( obj == this){
			return true;
		}
		if(!(obj instanceof PriceUpdate)){
			return false;
		}
		PriceUpdate priceUpdate = (PriceUpdate) obj;
//		return (priceUpdate.getCompanyName().equals(this.companyName) && priceUpdate.getPrice()==this.price);
		return (priceUpdate.getCompanyName().equalsIgnoreCase(this.companyName));
	}
	
	@Override
	public int hashCode() {
		int hash = 3;
		hash = hash*7 + this.getCompanyName().hashCode();
//		hash = hash*7 + Double.hashCode(this.getPrice());
		return hash;
	}
}
