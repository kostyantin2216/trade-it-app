package com.tradeitsignals.rest.responses;

/**
 * Created by Kostyantin on 4/13/2016.
 */
public class UBinaryLoginResponse {

    private String TrackingId;          // tracking id of this request for a later troubleshooting
    private String ErrorCode;           // short error code; 'Ok' - for successful request
    private String ErrorMessage;        // error description (if there is an error)

    public UBinaryLoginResponse() { }

    public UBinaryLoginResponse(String trackingId, String errorCode, String errorMessage) {
        TrackingId = trackingId;
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
    }

    public String getTrackingId() {
        return TrackingId;
    }

    public void setTrackingId(String trackingId) {
        TrackingId = trackingId;
    }

    public String getErrorCode() {
        return ErrorCode;
    }

    public void setErrorCode(String errorCode) {
        ErrorCode = errorCode;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "UBinaryLoginResponse{" +
                "TrackingId='" + TrackingId + '\'' +
                ", ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                '}';
    }

}
