package com.tradeitsignals.datamodel.data;

import java.util.Date;

/**
 * Created by Kostyantin on 9/11/2016.
 */
public class UBinaryDeal {

    private int DealId;                   // Deal id (to be used in open position request)
    private Date StartAt;             // deal start date
    private Date EndAt;               // deal end date
    private boolean ExpirationIsFixed;       // if true - positions opened on this deal will expire with the deal
    //    false - position will expire at now + Duration
    private String Duration;            // Position duration (if expiration is not fixed)
    private float PayMatch;             // Percents (stake) payed if positions wins
    private float PayNoMatch;           // Percents (stake) payed if position losses
    private float MinStake;             // Minimum stake for this deal
    private float MaxStake;             // Maximum stake for this deal

    public UBinaryDeal() { }

    public UBinaryDeal(int dealId, Date startAt, Date endAt, boolean expirationIsFixed,
                       String duration, float payMatch, float payNoMatch, float minStake, float maxStake) {
        DealId = dealId;
        StartAt = startAt;
        EndAt = endAt;
        ExpirationIsFixed = expirationIsFixed;
        Duration = duration;
        PayMatch = payMatch;
        PayNoMatch = payNoMatch;
        MinStake = minStake;
        MaxStake = maxStake;
    }

    public int getDealId() {
        return DealId;
    }

    public void setDealId(int dealId) {
        DealId = dealId;
    }

    public Date getStartAt() {
        return StartAt;
    }

    public void setStartAt(Date startAt) {
        StartAt = startAt;
    }

    public Date getEndAt() {
        return EndAt;
    }

    public void setEndAt(Date endAt) {
        EndAt = endAt;
    }

    public boolean isExpirationIsFixed() {
        return ExpirationIsFixed;
    }

    public void setExpirationIsFixed(boolean expirationIsFixed) {
        ExpirationIsFixed = expirationIsFixed;
    }

    public String getDuration() {
        return Duration;
    }

    public void setDuration(String duration) {
        Duration = duration;
    }

    public float getPayMatch() {
        return PayMatch;
    }

    public void setPayMatch(float payMatch) {
        PayMatch = payMatch;
    }

    public float getPayNoMatch() {
        return PayNoMatch;
    }

    public void setPayNoMatch(float payNoMatch) {
        PayNoMatch = payNoMatch;
    }

    public float getMinStake() {
        return MinStake;
    }

    public void setMinStake(float minStake) {
        MinStake = minStake;
    }

    public float getMaxStake() {
        return MaxStake;
    }

    public void setMaxStake(float maxStake) {
        MaxStake = maxStake;
    }

    @Override
    public String toString() {
        return "UBinaryDeal{" +
                "DealId=" + DealId +
                ", StartAt=" + StartAt +
                ", EndAt=" + EndAt +
                ", ExpirationIsFixed=" + ExpirationIsFixed +
                ", Duration='" + Duration + '\'' +
                ", PayMatch=" + PayMatch +
                ", PayNoMatch=" + PayNoMatch +
                ", MinStake=" + MinStake +
                ", MaxStake=" + MaxStake +
                '}';
    }
}
