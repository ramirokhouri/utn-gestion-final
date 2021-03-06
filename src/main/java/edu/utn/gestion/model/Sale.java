package edu.utn.gestion.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by martin on 08/12/15.
 */
@Entity
@Table(name = "sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private double totalAmount;

    @OneToMany(targetEntity = SaleDetail.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "sale_detail_id", referencedColumnName = "id", nullable = false)
    private final List<SaleDetail> saleDetails = new ArrayList<>();

    @Transient
    private final Map<String, SaleDetail> saleDetailMap = new HashMap<>();

    /**
     * No-args constructor. Returns an instance of Sale.
     */
    public Sale() {}

    /**
     * Returns an instance of Sale.
     *
     * @param customer Customer that buys the books.
     * @param employee Employee that records the sale.
     */
    public Sale(Customer customer, Employee employee) {}

    /**
     * Adds a new SaleDetail to the current sale.
     *
     * @param book Book to sale.
     * @param quantity Number of units for the given book.
     */
    public void addSaleDetail(Book book, int quantity) {
        String isbn = book.getIsbn();
        SaleDetail saleDetail = this.saleDetailMap.get(isbn);

        if (saleDetail != null) {
            int currentQuantity = saleDetail.getQuantity();
            int newQuantity = currentQuantity + quantity;
            saleDetail.setQuantity(newQuantity);
        } else {
            saleDetail = new SaleDetail(book, quantity);
            this.saleDetailMap.put(isbn, saleDetail);
            this.saleDetails.add(saleDetail);
        }

        this.updateTotalAmout();
    }

    /**
     * Updates the total amount for the current sale.
     */
    private void updateTotalAmout() {
        this.totalAmount = 0.0;

        for (SaleDetail saleDetail : this.saleDetails) {
            this.totalAmount += saleDetail.getAmount();
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<SaleDetail> getSaleDetails() {
        return saleDetails;
    }
}
