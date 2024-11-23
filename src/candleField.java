public class candleField {
    
    private String date;
    private Double close_price;

    public candleField(String date, Double close_price){

        this.date = date; 
        this.close_price = close_price;
    }

    public String getDate(){
        return this.date;
    }

    public Double getClose_price(){
        return this.close_price;
    }
}
