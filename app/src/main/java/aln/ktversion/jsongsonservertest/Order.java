package aln.ktversion.jsongsonservertest;

import java.util.List;

public class Order {
    private String storeId;
    private String orderId;
    private String customerName;
    private String phone;
    private String address;
    private Integer payType;
    private Integer status;
    private List<String> OrderList;
    private double totalPay;

    public void getBookCalculatePay(){
        if(OrderList.size() > 0){
            double sum = 0;
            for(String s : OrderList){
                Book book = new GsonString().toGsonBook(s);
                sum += book.getPrice();
            }
            setTotalPay(sum);
        }
    }

    public Order() {
    }

    public Order(String storeId, String orderId, String customerName, String phone, String address, Integer payType, Integer status, List<String> orderList, double totalPay) {
        this.storeId = storeId;
        this.orderId = orderId;
        this.customerName = customerName;
        this.phone = phone;
        this.address = address;
        this.payType = payType;
        this.status = status;
        OrderList = orderList;
        this.totalPay = totalPay;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<String> getOrderList() {
        return OrderList;
    }

    public void setOrderList(List<String> orderList) {
        OrderList = orderList;
    }

    public double getTotalPay() {
        return totalPay;
    }

    public void setTotalPay(double totalPay) {
        this.totalPay = totalPay;
    }

}
