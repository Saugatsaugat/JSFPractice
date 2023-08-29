package Entities;

import Model.AbstractEntity;
import Model.IAbstractEntity;
import com.saugat.bean.enums.WalletType;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author saugat
 */
@Entity
@Table(name = "Payment")
public class Payment extends AbstractEntity<Payment> implements IAbstractEntity, Serializable {

    @NotNull
    @Enumerated(EnumType.STRING)
    private WalletType walletType;

    @NotNull
    @Column(name = "success_id")
    private String successId;

    @Column(name = "khalti_type_idx")
    private String khaltiTypeIdx;

    @Column(name = "khalti_merchant_idx")
    private String khaltiMerchantIdx;

    @Column(name = "clientname_received_from_khalti")
    private String clientNameReceivedFromKhalti;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "transaction_date")
    private Date transactionDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "booking_detail_id")
    private BookingDetail bookingDetail;

    public WalletType getWalletType() {
        return walletType;
    }

    public void setWalletType(WalletType walletType) {
        this.walletType = walletType;
    }

    public String getSuccessId() {
        return successId;
    }

    public void setSuccessId(String successId) {
        this.successId = successId;
    }

    public String getKhaltiTypeIdx() {
        return khaltiTypeIdx;
    }

    public void setKhaltiTypeIdx(String khaltiTypeIdx) {
        this.khaltiTypeIdx = khaltiTypeIdx;
    }

    public String getKhaltiMerchantIdx() {
        return khaltiMerchantIdx;
    }

    public void setKhaltiMerchantIdx(String khaltiMerchantIdx) {
        this.khaltiMerchantIdx = khaltiMerchantIdx;
    }

    public String getClientNameReceivedFromKhalti() {
        return clientNameReceivedFromKhalti;
    }

    public void setClientNameReceivedFromKhalti(String clientNameReceivedFromKhalti) {
        this.clientNameReceivedFromKhalti = clientNameReceivedFromKhalti;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BookingDetail getBookingDetail() {
        return bookingDetail;
    }

    public void setBookingDetail(BookingDetail bookingDetail) {
        this.bookingDetail = bookingDetail;
    }

    @Override
    public String getTableName() {
        return "Payment";
    }
}
