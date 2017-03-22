package com.tradeitsignals.rest.responses;

import com.tradeitsignals.datamodel.data.UBinaryOption;

import java.util.Arrays;

/**
 * Created by Kostyantin on 9/11/2016.
 */
public class UBinaryOptionsResponse {

    private String TrackingId;            // Tracking id for troubleshooting
    private String ErrorCode;             // "Ok" if request succeeds, short error code if request fails
    private String ErrorMessage;          // error description if request fails
    private UBinaryOption[] Options;      // array of asset options

    public UBinaryOptionsResponse() { }

    public UBinaryOptionsResponse(String trackingId, String errorCode, String errorMessage, UBinaryOption[] options) {
        TrackingId = trackingId;
        ErrorCode = errorCode;
        ErrorMessage = errorMessage;
        Options = options;
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

    public UBinaryOption[] getOptions() {
        return Options;
    }

    public void setOptions(UBinaryOption[] options) {
        Options = options;
    }

    @Override
    public String toString() {
        return "UBinaryOptionsResponse{" +
                "TrackingId='" + TrackingId + '\'' +
                ", ErrorCode='" + ErrorCode + '\'' +
                ", ErrorMessage='" + ErrorMessage + '\'' +
                ", Options=" + Arrays.toString(Options) +
                '}';
    }
}
