package bg.sofia.uni.fmi.mjt.parking;

import java.time.LocalDateTime;

public interface ParkingClientAPI {
    boolean sendSms(String smsNumber, String plate, LocalDateTime moment);
}